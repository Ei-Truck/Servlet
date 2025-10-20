<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.eitruck.model.Administrador" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <title>Administradores</title>
</head>
<body>
<h1>Gerenciar Administradores</h1>

<%
  String success = request.getParameter("success");
  String error = request.getParameter("error");
  List<Administrador> admins = (List<Administrador>) request.getAttribute("administradores");
%>

<% if (success != null) { %>
<p style="color: green"><%= success %></p>
<% } %>
<% if (error != null) { %>
<p style="color: red"><%= error %></p>
<% } %>

<form method="get" action="administradores">
  <input type="hidden" name="action" value="buscar">
  ID: <input type="number" name="filter-id" value="<%= request.getParameter("filter-id") != null ? request.getParameter("filter-id") : "" %>">
  Nome: <input type="text" name="filter-nome" value="<%= request.getParameter("filter-nome") != null ? request.getParameter("filter-nome") : "" %>">
  <button type="submit">Filtrar</button>
  <a href="administradores">Limpar</a>
</form>

<button onclick="abrirModal()">Adicionar Administrador</button>

<table border="1" style="width: 100%; margin-top: 20px;">
  <tr>
    <th>ID</th>
    <th>Nome</th>
    <th>CPF</th>
    <th>E-mail</th>
    <th>Ações</th>
  </tr>
  <% if (admins != null && !admins.isEmpty()) {
    for (Administrador admin : admins) { %>
  <tr>
    <td><%= admin.getId() %></td>
    <td><%= admin.getNomeCompleto() != null ? admin.getNomeCompleto() : "" %></td>
    <td><%= admin.getCpf() != null ? admin.getCpf() : "" %></td>
    <td><%= admin.getEmail() != null ? admin.getEmail() : "" %></td>
    <td>
      <button onclick="editar(<%= admin.getId() %>, '<%= admin.getNomeCompleto() != null ? admin.getNomeCompleto().replace("'", "\\'") : "" %>', '<%= admin.getCpf() != null ? admin.getCpf() : "" %>', '<%= admin.getEmail() != null ? admin.getEmail() : "" %>')">Editar</button>
      <button onclick="excluir(<%= admin.getId() %>)">Excluir</button>
    </td>
  </tr>
  <%   }
  } else { %>
  <tr>
    <td colspan="5">Nenhum administrador encontrado</td>
  </tr>
  <% } %>
</table>

<div id="modal" style="display:none; position: fixed; top: 20%; left: 20%; background: white; padding: 20px; border: 1px solid black;">
  <h3 id="modalTitulo">Adicionar Administrador</h3>
  <form id="formAdmin" method="post" action="administradores">
    <input type="hidden" id="adminId" name="id">
    <input type="hidden" id="actionType" name="action" value="cadastrar">

    Nome: <input type="text" id="nomeCompleto" name="nome_completo" required><br>
    CPF: <input type="text" id="cpf" name="cpf" required><br>
    E-mail: <input type="email" id="email" name="email" required><br>
    Senha: <input type="password" id="senha" name="senha"><br>

    <button type="button" onclick="fecharModal()">Cancelar</button>
    <button type="button" onclick="salvar()">Salvar</button>
  </form>
</div>

<script>
  function abrirModal() {
    document.getElementById("modalTitulo").textContent = "Adicionar Administrador";
    document.getElementById("formAdmin").reset();
    document.getElementById("adminId").value = "";
    document.getElementById("actionType").value = "cadastrar";
    document.getElementById("modal").style.display = "block";
  }

  function editar(id, nome, cpf, email) {
    document.getElementById("modalTitulo").textContent = "Editar Administrador";
    document.getElementById("adminId").value = id;
    document.getElementById("actionType").value = "editar";
    document.getElementById("nomeCompleto").value = nome;
    document.getElementById("cpf").value = cpf;
    document.getElementById("email").value = email;
    document.getElementById("modal").style.display = "block";
  }

  function fecharModal() {
    document.getElementById("modal").style.display = "none";
  }

  function salvar() {
    document.getElementById("formAdmin").submit();
  }

  function excluir(id) {
    if (confirm("Tem certeza que deseja excluir este administrador?")) {
      var form = document.createElement("form");
      form.method = "post";
      form.action = "administradores";

      var inputAction = document.createElement("input");
      inputAction.name = "action";
      inputAction.value = "excluir";
      form.appendChild(inputAction);

      var inputId = document.createElement("input");
      inputId.name = "id";
      inputId.value = id;
      form.appendChild(inputId);

      document.body.appendChild(form);
      form.submit();
    }
  }
</script>
</body>
</html>