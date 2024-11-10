package com.example.ecommercefluxodecaixaalberto_everaldina.servlet;

import com.example.ecommercefluxodecaixaalberto_everaldina.db.DatabaseConnection;
import com.example.ecommercefluxodecaixaalberto_everaldina.models.Despesa;
import com.example.ecommercefluxodecaixaalberto_everaldina.models.RecebimentoPagamento;
import com.google.gson.Gson;


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

@WebServlet("/recebimento-pagamento/mes")
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

        List<RecebimentoPagamento> recebimentosPagamentos = new ArrayList<>();

        String sql = "SELECT * FROM RecebimentoPagamento WHERE MONTH(data) = ? AND YEAR(data) = ?";


        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
        } catch ( Exception e ) {
            response.getWriter().println("Erro ao verificar a conexão: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(mes));
            stmt.setInt(2, Integer.parseInt(ano));
    
            // Executa a consulta e obtém os resultados
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RecebimentoPagamento recPag = new RecebimentoPagamento();
                recPag.setIdRecPag(rs.getInt("id_rec_pag"));
                recPag.setData(rs.getDate("data"));
                recPag.setValor(rs.getDouble("valor"));
                recPag.setPagamentoRecebimento(rs.getString("pagamento_recebimento"));
                recPag.setFkIdModalidade(rs.getInt("fk_id_modalidade"));
                recPag.setFkIdEmpresa(rs.getInt("fk_id_empresa"));
                recebimentosPagamentos.add(recPag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao acessar o banco de dados.\"}");
            return;
        }

        // Configura a resposta para JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Converte a lista de despesas para JSON usando Gson e envia a resposta
        Gson gson = new Gson();
        String json = gson.toJson(recebimentosPagamentos);
        response.getWriter().write(json);


        // Processa o ResultSet e popula a lista de despesas

        if (connection != null) {
            try {
                connection.close(); // Fecha a conexão
            } catch (SQLException e) {
                response.getWriter().println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
