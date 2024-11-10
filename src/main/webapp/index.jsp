<%--
  Created by IntelliJ IDEA.
  User: Alberto
  Date: 03/11/2024
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Fluxo de Caixa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
  <div class="container">
      <h1>Fluxo de Caixa</h1>
      
      <div class="input-section">
          <label for="mes">Mês:</label>
          <select id="mes">
              <option value="01">Janeiro</option>
              <option value="02">Fevereiro</option>
              <option value="03">Março</option>
              <option value="04">Abril</option>
              <option value="05">Maio</option>
              <option value="06">Junho</option>
              <option value="07">Julho</option>
              <option value="08">Agosto</option>
              <option value="09">Setembro</option>
              <option value="10">Outubro</option>
              <option value="11">Novembro</option>
              <option value="12">Dezembro</option>
          </select>

          <label for="ano">Ano:</label>
          <input type="number" id="ano" min="2000" max="2100" value="2024">

          <button onclick="gerarFluxoCaixa()">Gerar Fluxo de Caixa</button>
      </div>

      <div id="fluxoCaixa"></div>
      
      <button id="downloadBtn" onclick="baixarPDF()" style="display: none;">Baixar PDF</button>
  </div>

  <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>

