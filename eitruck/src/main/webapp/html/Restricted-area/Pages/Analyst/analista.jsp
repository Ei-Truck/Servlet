<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/servlet-analista" method="post">
        <input type="hidden" name="acao_principal" value="inserir">
        <input type="hidden" name="sub_acao" value="inserir">

        <label for="id_unidade"> Especifique a unidade: </label>
        <input type="text" name="id_unidade" id="id_unidade" required>

        <label for="cpf"> Especifique o cpf: </label>
        <input type="text" name="cpf" id="cpf" required>

        <label for="nome"> Especifique o nome completo: </label>
        <input type="text" name="nome" id="nome" required>

        <label for="email"> Especifique o email: </label>
        <input type="text" name="email" id="email" required>

        <label for="data_contratacao"> Especifique a data de contratação: </label>
        <input type="date" name="data_contratacao" id="data_contratacao" required>

        <label for="senha"> Especifique a senha: </label>
        <input type="password" name="senha" id="senha" required>

        <label for="cargo"> Especifique o cargo: </label>
        <input type="text" name="cargo" id="cargo" required>

        <input type="submit" value="inserir">
    </form>
</body>
</html>
