package com.example.ecommercefluxodecaixaalberto_everaldina.servlet;

import com.example.ecommercefluxodecaixaalberto_everaldina.db.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnectionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            if (connection != null && !connection.isClosed()) {
                response.getWriter().println("Conexão com o banco de dados foi bem-sucedida!");
            } else {
                response.getWriter().println("Falha ao conectar ao banco de dados.");
            }
        } catch (SQLException e) {
            response.getWriter().println("Erro ao verificar a conexão: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // Fecha a conexão
                } catch (SQLException e) {
                    response.getWriter().println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}