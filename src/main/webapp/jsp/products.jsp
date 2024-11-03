<%--
  Created by IntelliJ IDEA.
  User: Alberto
  Date: 03/11/2024
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Produtos</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>Lista de Produtos</h2>
<%
    // Este é um exemplo estático. Em uma aplicação real, esses dados viriam de um banco de dados.
    String[] produtos = {"Produto 1", "Produto 2", "Produto 3"};
    for (String produto : produtos) {
%>
<div class="produto">
    <h3><%= produto %></h3>
    <p>Descrição do produto...</p>
    <a href="productDetails.jsp?name=<%= produto %>">Ver detalhes</a>
</div>
<%
    }
%>
</body>
</html>
