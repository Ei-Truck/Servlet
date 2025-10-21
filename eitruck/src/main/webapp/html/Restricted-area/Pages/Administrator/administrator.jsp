<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administrador - CRUD</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/servlet-administrador" method="post">
    <input type="hidden" name="acao_principal" value="inserir">
    <input type="hidden" name="sub_acao" value="inserir">

    <label for="cpf"> Especifique o cpf: </label>
    <input type="text" name="cpf" id="cpf" required>

    <label for="nome"> Especifique o nome completo: </label>
    <input type="text" name="nome" id="nome" required>

    <label for="email"> Especifique o email: </label>
    <input type="text" name="email" id="email" required>

    <label for="telefone"> Especifique o telefone: </label>
    <input type="text" name="telefone" id="telefone" required>

    <label for="senha"> Especifique a senha: </label>
    <input type="password" name="senha" id="senha" required>

    <input type="submit" value="inserir">
</form>

<form action="${pageContext.request.contextPath}/servlet-administrador" method="get">
    <input type="hidden" name="acao_principal" value="buscar">

    <input type="submit" name="sub_acao" value="buscar_todos">
</form>
</body>
</html>