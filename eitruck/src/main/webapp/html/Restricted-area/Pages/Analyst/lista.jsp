<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--  <title>EI Truck - Analistas</title>--%>
<%--  <meta charset="UTF-8">--%>
<%--  <style>--%>
<%--    table { border-collapse: collapse; width: 100%; }--%>
<%--    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }--%>
<%--    th { background-color: #f2f2f2; }--%>
<%--    .sucesso { color: green; padding: 10px; }--%>
<%--    .erro { color: red; padding: 10px; }--%>
<%--  </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Gerenciar Analistas</h1>--%>

<%--<c:if test="${not empty param.sucesso}">--%>
<%--  <div class="sucesso">${param.sucesso}</div>--%>
<%--</c:if>--%>
<%--<c:if test="${not empty param.erro}">--%>
<%--  <div class="erro">${param.erro}</div>--%>
<%--</c:if>--%>

<%--<div>--%>
<%--  <a href="analista?acao=novo">Novo Analista</a>--%>
<%--</div>--%>

<%--<h2>Buscar Analista</h2>--%>
<%--<form action="analista" method="get">--%>
<%--  <input type="hidden" name="acao" value="buscar">--%>
<%--  <select name="tipo">--%>
<%--    <option value="id">ID</option>--%>
<%--    <option value="cpf">CPF</option>--%>
<%--    <option value="nome">Nome</option>--%>
<%--    <option value="email">Email</option>--%>
<%--    <option value="cargo">Cargo</option>--%>
<%--    <option value="unidade">ID Unidade</option>--%>
<%--  </select>--%>
<%--  <input type="text" name="valor" required>--%>
<%--  <button type="submit">Buscar</button>--%>
<%--</form>--%>

<%--<h2>Lista de Analistas</h2>--%>
<%--<table>--%>
<%--  <thead>--%>
<%--  <tr>--%>
<%--    <th>ID</th>--%>
<%--    <th>Nome</th>--%>
<%--    <th>CPF</th>--%>
<%--    <th>Email</th>--%>
<%--    <th>Cargo</th>--%>
<%--    <th>Unidade</th>--%>
<%--    <th>Data Contratação</th>--%>
<%--    <th>Telefone</th>--%>
<%--    <th>Ações</th>--%>
<%--  </tr>--%>
<%--  </thead>--%>
<%--  <tbody>--%>
<%--  <c:forEach var="analista" items="${analistas}">--%>
<%--    <tr>--%>
<%--      <td>${analista.id}</td>--%>
<%--      <td>${analista.nomeCompleto}</td>--%>
<%--      <td>${analista.cpf}</td>--%>
<%--      <td>${analista.email}</td>--%>
<%--      <td>${analista.cargo}</td>--%>
<%--      <td>${analista.idUnidade}</td>--%>
<%--      <td>${analista.dtContratacao}</td>--%>
<%--      <td>${analista.telefone}</td>--%>
<%--      <td>--%>
<%--&lt;%&ndash;        <a href="analista?acao=editar&id=${analista.id}">Editar</a>&ndash;%&gt;--%>
<%--        <form action="analista" method="post" style="display:inline;">--%>
<%--          <input type="hidden" name="acao" value="excluir">--%>
<%--          <input type="hidden" name="id" value="${analista.id}">--%>
<%--          <button type="submit" onclick="return confirm('Confirma exclusão?')">Excluir</button>--%>
<%--        </form>--%>
<%--      </td>--%>
<%--    </tr>--%>
<%--  </c:forEach>--%>
<%--  </tbody>--%>
<%--</table>--%>
<%--</body>--%>
<%--</html>--%>