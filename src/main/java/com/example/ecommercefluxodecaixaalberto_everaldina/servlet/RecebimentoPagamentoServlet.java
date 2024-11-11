package com.example.ecommercefluxodecaixaalberto_everaldina.servlet;

import com.example.ecommercefluxodecaixaalberto_everaldina.db.DatabaseConnection;
import com.example.ecommercefluxodecaixaalberto_everaldina.models.Despesa;
import com.example.ecommercefluxodecaixaalberto_everaldina.models.RecebimentoPagamento;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@WebServlet("/recebimento-pagamento/mes")
public class RecebimentoPagamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mes = request.getParameter("mes");  // Espera o mês no formato "MM" (ex: "10" para outubro)
        String ano = request.getParameter("ano");

        if (mes == null || ano == null || mes.length() != 2 || ano.length() != 4) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Parâmetros inválidos. Informe mês e ano no formato correto.\"}");
            return;
        }
        
        List<Despesa> despesas = new ArrayList<>();
        List<RecebimentoPagamento> despesasExtras = new ArrayList<>();
        List<RecebimentoPagamento> rendimentoFinanceiro = new ArrayList<>();
        List<RecebimentoPagamento> outrasReceitas = new ArrayList<>();

        float salario = 0;
        float comissao = 0;

        
        Connection connection = null;

        try{
            connection = DatabaseConnection.getConnection();
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao acessar o banco de dados.\"}");
            e.printStackTrace();
            return;
        }

        

        
        // Obter salário do mês e comissão
        String sqlSalarioComissao = "SELECT f.comissao, c.salario FROM Funcionario f JOIN Cargo c ON f.fk_id_cargo = c.id_cargo";
        try (PreparedStatement stmt = connection.prepareStatement(sqlSalarioComissao);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                comissao += rs.getFloat("comissao");
                salario += rs.getFloat("salario");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao selecionar salário e comissão.\"}");
            e.printStackTrace();
            return;
        }

        // Obter despesas do mês
        String sqlDespesas = "SELECT * FROM Despesa WHERE MONTH(data_vencimento) = ? AND YEAR(data_vencimento) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlDespesas)) {
            stmt.setInt(1, Integer.parseInt(mes));
            stmt.setInt(2, Integer.parseInt(ano));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Despesa despesa = new Despesa();
                    despesa.setIdDespesa(rs.getInt("id_despesa"));
                    despesa.setDataVencimento(rs.getDate("data_vencimento"));
                    despesa.setValor(rs.getDouble("valor"));
                    despesa.setDescricao(rs.getString("descricao"));
                    despesas.add(despesa);
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao selecionar despesas.\"}");
            e.printStackTrace();
            return;
        }

        // Obter registros de Recebimento_Pagamento para diferentes categorias
        String sqlRecebPag = "SELECT * FROM Recebimento_Pagamento WHERE MONTH(data) = ? AND YEAR(data) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlRecebPag)) {
            stmt.setInt(1, Integer.parseInt(mes));
            stmt.setInt(2, Integer.parseInt(ano));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RecebimentoPagamento recPag = new RecebimentoPagamento();
                    recPag.setIdRecPag(rs.getInt("id_rec_pag"));
                    recPag.setData(rs.getDate("data"));
                    recPag.setValor(rs.getDouble("valor"));
                    recPag.setPagamentoRecebimento(rs.getString("pagamento_recebimento"));
                    recPag.setFkIdModalidade(rs.getInt("fk_id_modalidade"));
                    recPag.setFkIdEmpresa(rs.getInt("fk_id_empresa"));

                    if ("pagamento".equals(recPag.getPagamentoRecebimento())) {
                        despesasExtras.add(recPag);
                    } else if ("recebimento".equals(recPag.getPagamentoRecebimento())) {
                        if (rendimentoFinanceiro.size() < 10) {
                            rendimentoFinanceiro.add(recPag);
                        } else if (outrasReceitas.size() < 3) {
                            outrasReceitas.add(recPag);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao selecionar recebimentos e pagamentos.\"}");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Erro ao fechar a conexão com o banco de dados.\"}");
                e.printStackTrace();
                return;
            }
        }

        // Configura a resposta para JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Construir o JSON de resposta
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("salario", salario);
        responseObject.addProperty("comissao", comissao);
        responseObject.add("despesas", new Gson().toJsonTree(despesas));
        responseObject.add("despesasExtras", new Gson().toJsonTree(despesasExtras));
        responseObject.add("rendimentoFinanceiro", new Gson().toJsonTree(rendimentoFinanceiro));
        responseObject.add("outrasReceitas", new Gson().toJsonTree(outrasReceitas));

        // Enviar a resposta
        response.getWriter().write(responseObject.toString());
    }
}
