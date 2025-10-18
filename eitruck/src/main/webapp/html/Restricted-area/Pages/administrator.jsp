<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.eitruck.model.Administrador" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Administradores - Ei Truck</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleCss/Restricted-area/Pages/administrator.css">
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/image/Group 36941.png">
</head>
<body>
<div class="admin-container">
  <!-- Menu Lateral -->
  <div class="sidebar">
    <div class="sidebar-header">
      <h2>Ei Truck</h2>
      <p>Painel Administrativo</p>
    </div>
    <nav class="sidebar-nav">
      <ul>
        <li><a href="dashboard.jsp" class="nav-item"><span>📊</span> Dashboard</a></li>
        <li><a href="administrator.jsp" class="nav-item active"><span>👨‍💼</span> Gerenciar Administradores</a></li>
        <li><a href="analyst.jsp" class="nav-item"><span>👥</span> Gerenciar Analistas</a></li>
        <li><a href="segments.jsp" class="nav-item"><span>📁</span> Gerenciar Segmentos</a></li>
        <li><a href="units.jsp" class="nav-item"><span>🏢</span> Gerenciar Unidades</a></li>
        <li><a href="addresses.jsp" class="nav-item"><span>📍</span> Gerenciar Endereços</a></li>
        <li><a href="occurrences.jsp" class="nav-item"><span>⚠️</span> Gerenciar Tipos de Ocorrência</a></li>
        <li><a href="../login.html" class="nav-item logout"><span>🚪</span> Sair</a></li>
      </ul>
    </nav>
  </div>

  <!-- Conteúdo Principal -->
  <div class="main-content">
    <header class="content-header">
      <div class="header-left">
        <h1>Gerenciar Administradores</h1>
        <p>Cadastre e gerencie os administradores do sistema</p>
      </div>
      <div class="header-right">
        <div class="user-info">
          <span>Administração</span>
        </div>
      </div>
    </header>

    <div class="page-content">
      <div class="crud-section">
        <div class="crud-header">
          <h2>Administradores</h2>
          <div class="crud-actions">
            <button class="btn btn-primary" id="add-btn">
              <span>➕</span> Adicionar Administrador
            </button>
          </div>
        </div>

        <div class="filters">
          <form id="filter-form" method="get" action="administradores">
            <div class="filter-group1">
              <div class="filter-group">
                <label>ID</label>
                <input type="number" class="filter-input" name="filter-id"
                       placeholder="Filtrar por ID..." value="${param['filter-id']}">
              </div>
              <div class="filter-group">
                <label>Nome</label>
                <input type="text" class="filter-input" name="filter-nome"
                       placeholder="Filtrar por nome..." value="${param['filter-nome']}">
              </div>
              <div class="filter-group">
                <label>CPF</label>
                <input type="text" class="filter-input" name="filter-cpf"
                       placeholder="Filtrar por CPF..." value="${param['filter-cpf']}">
              </div>
              <div class="filter-group">
                <label>E-mail</label>
                <input type="text" class="filter-input" name="filter-email"
                       placeholder="Filtrar por e-mail..." value="${param['filter-email']}">
              </div>
            </div>
            <div class="filter-group">
              <button type="submit" class="btn btn-secondary" id="apply-filters">
                <span>🔍</span> Aplicar Filtros
              </button>
              <button type="button" class="btn btn-secondary" id="clear-filters">
                <span>🔄</span> Limpar
              </button>
            </div>
          </form>
        </div>

        <div class="table-container">
          <table class="crud-table">
            <thead>
            <tr>
              <th>ID</th>
              <th>Nome Completo</th>
              <th>CPF</th>
              <th>E-mail</th>
              <th>Ações</th>
            </tr>
            </thead>
            <tbody id="table-body">
            <%
              List<Administrador> administradores = (List<Administrador>) request.getAttribute("administradores");
              if (administradores != null) {
                for (Administrador admin : administradores) {
            %>
            <tr>
              <td><%= admin.getId() %></td>
              <td><%= admin.getNomeCompleto() %></td>
              <td><%= admin.getCpf() %></td>
              <td><%= admin.getEmail() %></td>
              <td>
                <button class="btn btn-edit" data-id="<%= admin.getId() %>"
                        data-nome="<%= admin.getNomeCompleto() %>"
                        data-cpf="<%= admin.getCpf() %>"
                        data-email="<%= admin.getEmail() %>">Editar</button>
                <button class="btn btn-delete" data-id="<%= admin.getId() %>">Excluir</button>
              </td>
            </tr>
            <%
                }
              }
            %>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div id="crud-modal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <h3 id="modal-title">Adicionar Administrador</h3>
      <span class="close">&times;</span>
    </div>
    <div class="modal-body">
      <form id="crud-form" method="post" action="administradores">
        <input type="hidden" id="admin-id" name="id">
        <input type="hidden" id="action-type" name="action">
        <div class="form-group">
          <label>Nome Completo</label>
          <input type="text" class="form-control" name="nome_completo" required>
        </div>
        <div class="form-group">
          <label>CPF</label>
          <input type="text" class="form-control" name="cpf" required>
        </div>
        <div class="form-group">
          <label>E-mail</label>
          <input type="email" class="form-control" name="email" required>
        </div>
        <div class="form-group">
          <label>Senha</label>
          <input type="password" class="form-control" name="senha" required>
        </div>
      </form>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-secondary" id="cancel-btn">Cancelar</button>
      <button type="button" class="btn btn-primary" id="save-btn">Salvar</button>
    </div>
  </div>
</div>

<script src="${pageContext.request.contextPath}/JS/Restricted-area/Pages/administrator.js"></script>
</body>
</html>