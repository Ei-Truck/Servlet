<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Units - CRUD</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/servlet-unidade" method="post">
    <input type="hidden" name="acao_principal" value="inserir">
    <input type="hidden" name="sub_acao" value="inserir">

    <label for="id_segmento"> Especifique o segmento: </label>
    <input type="text" name="id_segmento" id="id_segmento" required>

    <label for="id_endereco"> Especifique o endereco: </label>
    <input type="text" name="id_endereco" id="id_endereco" required>

    <label for="nome"> Especifique o nome: </label>
    <input type="text" name="nome" id="nome" required>

    <input type="submit" value="inserir">
</form>

<form action="${pageContext.request.contextPath}/servlet-unidade" method="get">
    <input type="hidden" name="acao_principal" value="buscar">

    <input type="submit" name="sub_acao" value="buscar_todos">
</form>
</body>
</html>
