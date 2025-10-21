<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Endereços - CRUD</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/servlet-enderecos" method="post">
  <input type="hidden" name="acao_principal" value="inserir">
  <input type="hidden" name="sub_acao" value="inserir">

  <label for="cep"> Especifique o cep: </label>
  <input type="text" name="cep" id="cep" required>

  <label for="rua"> Especifique a rua: </label>
  <input type="text" name="rua" id="rua" required>

  <label for="numero"> Especifique o numero: </label>
  <input type="text" name="numero" id="numero" required>

  <label for="bairro"> Especifique o bairro: </label>
  <input type="text" name="bairro" id="bairro" required>

  <label for="cidade"> Especifique a cidade: </label>
  <input type="text" name="cidade" id="cidade" required>

  <label for="estado"> Especifique o estado: </label>
  <input type="text" name="estado" id="estado" required>

  <label for="pais"> Especifique o pais: </label>
  <input type="text" name="pais" id="pais" required>

  <input type="submit" value="inserir">
</form>

<form action="${pageContext.request.contextPath}/servlet-enderecos" method="get">
  <input type="hidden" name="acao_principal" value="buscar">

  <input type="submit" name="sub_acao" value="buscar_todos">
</form>
</body>
</html>
