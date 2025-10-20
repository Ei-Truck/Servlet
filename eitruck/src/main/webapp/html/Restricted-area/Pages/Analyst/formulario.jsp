<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--  <title>EI Truck - ${empty analista ? 'Novo' : 'Editar'} Analista</title>--%>
<%--  <meta charset="UTF-8">--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>${empty analista ? 'Cadastrar' : 'Editar'} Analista</h1>--%>

<%--<form action="analista" method="post">--%>
<%--  <input type="hidden" name="acao" value="${empty analista ? 'inserir' : 'atualizar'}">--%>

<%--  <c:if test="${not empty analista}">--%>
<%--    <input type="hidden" name="id" value="${analista.id}">--%>
<%--&lt;%&ndash;  </c:if>&ndash;%&gt;--%>

<%--  <div>--%>
<%--    <label for="id">ID:</label>--%>
<%--    <input type="number" id="id" name="id" value="${analista.id}" ${empty analista ? '' : 'readonly'} required>--%>
<%--  </div>--%>

<%--  <div>--%>
<%--    <label for="idUnidade">ID Unidade:</label>--%>
<%--    <input type="number" id="idUnidade" name="idUnidade" value="${analista.idUnidade}" required>--%>
<%--  </div>--%>

<%--  <div>--%>
<%--    <label for="cpf">CPF:</label>--%>
<%--    <input type="text" id="cpf" name="cpf" value="${analista.cpf}" required--%>
<%--           pattern="\d{3}\.\d{3}\.\d{3}-\d{2}" placeholder="000.000.000-00">--%>
<%--  </div>--%>

<%--  <div>--%>
<%--    <label for="nome">Nome Completo:</label>--%>
<%--    <input type="text" id="nome" name="nome" value="${analista.nomeCompleto}" required>--%>
<%--  </div>--%>

<%--  <div>--%>
<%--    <label for="dtContratacao">Data Contratação:</label>--%>
<%--    <input type="date" id="dtContratacao" name="dtContratacao"--%>
<%--           value="${analista.dtContratacao}" required>--%>
<%--  </div>--%>

<%--  <div>--%>
<%--    <label for="email">Email:</label>--%>
<%--    <input type="email" id="email" name="email" value="${analista.email}" required>--%>
<%--  </div>--%>

<%--  <div>--%>
<%--    <label for="senha">Senha:</label>--%>
<%--    <input type="password" id="senha" name="senha" ${empty analista ? 'required' : ''}>--%>
<%--  </div>--%>

<%--  <c:if test="${not empty analista}">--%>
<%--    <div>--%>
<%--      <label for="novaSenha">Nova Senha (deixe em branco para manter atual):</label>--%>
<%--      <input type="password" id="novaSenha" name="novaSenha">--%>
<%--    </div>--%>
<%--  </c:if>--%>

<%--  <div>--%>
<%--    <label for="cargo">Cargo:</label>--%>
<%--    <input type="text" id="cargo" name="cargo" value="${analista.cargo}" required>--%>
<%--  </div>--%>

<%--  <div>--%>
<%--    <label for="telefone">Telefone:</label>--%>
<%--    <input type="text" id="telefone" name="telefone" value="${analista.telefone}"--%>
<%--           pattern="\(\d{2}\)\s\d{4,5}-\d{4}" placeholder="(00) 00000-0000">--%>
<%--  </div>--%>

<%--  <div>--%>
<%--    <button type="submit">${empty analista ? 'Cadastrar' : 'Atualizar'}</button>--%>
<%--    <a href="analista">Cancelar</a>--%>
<%--  </div>--%>
<%--</form>--%>

<%--<script>--%>
<%--  // Máscaras para os campos--%>
<%--  document.getElementById('cpf').addEventListener('input', function(e) {--%>
<%--    let value = e.target.value.replace(/\D/g, '');--%>
<%--    if (value.length <= 11) {--%>
<%--      value = value.replace(/(\d{3})(\d)/, '$1.$2')--%>
<%--              .replace(/(\d{3})(\d)/, '$1.$2')--%>
<%--              .replace(/(\d{3})(\d{1,2})$/, '$1-$2');--%>
<%--    }--%>
<%--    e.target.value = value;--%>
<%--  });--%>

<%--  document.getElementById('telefone').addEventListener('input', function(e) {--%>
<%--    let value = e.target.value.replace(/\D/g, '');--%>
<%--    if (value.length <= 11) {--%>
<%--      value = value.replace(/(\d{2})(\d)/, '($1) $2')--%>
<%--              .replace(/(\d{5})(\d)/, '$1-$2')--%>
<%--              .replace(/(-\d{4})\d+?$/, '$1');--%>
<%--    }--%>
<%--    e.target.value = value;--%>
<%--  });--%>
<%--</script>--%>
<%--</body>--%>
<%--</html>--%>