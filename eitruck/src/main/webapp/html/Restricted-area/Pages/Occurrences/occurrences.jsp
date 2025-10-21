<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tipos de ocorrência - CRUD</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/servlet-ocorrencias" method="post">
  <input type="hidden" name="acao_principal" value="inserir">
  <input type="hidden" name="sub_acao" value="inserir">

  <label for="tipo_evento"> Especifique tipo de evento: </label>
  <input type="text" name="tipo_evento" id="tipo_evento" required>

  <label for="pontuacao"> Especifique a pontuacao: </label>
  <input type="text" name="pontuacao" id="pontuacao" required>

  <label for="gravidade"> Especifique a gravidade: </label>
  <input type="text" name="gravidade" id="gravidade" required>

  <input type="submit" value="inserir">
</form>

<form action="${pageContext.request.contextPath}/servlet-ocorrencias" method="get">
  <input type="hidden" name="acao_principal" value="buscar">

  <input type="submit" name="sub_acao" value="buscar_todos">
</form>
</body>
</html>
