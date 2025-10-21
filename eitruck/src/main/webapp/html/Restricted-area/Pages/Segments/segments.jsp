<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Segmentos - CRUD</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/servlet-segmentos" method="post">
  <input type="hidden" name="acao_principal" value="inserir">
  <input type="hidden" name="sub_acao" value="inserir">

  <label for="nome"> Especifique o nome: </label>
  <input type="text" name="nome" id="nome" required>

  <label for="descricao"> Especifique a descrição: </label>
  <input type="text" name="descricao" id="descricao" required>

  <input type="submit" value="inserir">
</form>

<form action="${pageContext.request.contextPath}/servlet-segmentos" method="get">
  <input type="hidden" name="acao_principal" value="buscar">

  <input type="submit" name="sub_acao" value="buscar_todos">
</form>
</body>
</html>
