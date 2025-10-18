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
  <style>
    .edit-options {
      display: flex;
      flex-direction: column;
      gap: 10px;
      margin-bottom: 20px;
    }
    .edit-option {
      padding: 12px;
      border: 1px solid #ddd;
      border-radius: 5px;
      cursor: pointer;
      transition: all 0.3s;
    }
    .edit-option:hover {
      background-color: #f5f5f5;
      border-color: #007bff;
    }
    .edit-option.selected {
      background-color: #e3f2fd;
      border-color: #007bff;
    }
    .edit-form {
      display: none;
    }
    .edit-form.active {
      display: block;
    }
  </style>
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

<!-- Modal para Adicionar -->
<div id="add-modal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <h3>Adicionar Administrador</h3>
      <span class="close">&times;</span>
    </div>
    <div class="modal-body">
      <form id="add-form" method="post" action="administradores">
        <input type="hidden" name="action" value="cadastrar">
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
      <button type="button" class="btn btn-secondary" id="cancel-add-btn">Cancelar</button>
      <button type="button" class="btn btn-primary" id="save-add-btn">Salvar</button>
    </div>
  </div>
</div>

<!-- Modal para Editar com Opções -->
<div id="edit-modal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <h3>Editar Administrador</h3>
      <span class="close">&times;</span>
    </div>
    <div class="modal-body">
      <input type="hidden" id="edit-admin-id">

      <div class="edit-options">
        <div class="edit-option" data-option="nome">
          <strong>📝 Editar apenas o Nome</strong>
          <p>Alterar somente o nome completo do administrador</p>
        </div>
        <div class="edit-option" data-option="cpf">
          <strong>🔢 Editar apenas o CPF</strong>
          <p>Alterar somente o CPF do administrador</p>
        </div>
        <div class="edit-option" data-option="email">
          <strong>📧 Editar apenas o E-mail</strong>
          <p>Alterar somente o e-mail do administrador</p>
        </div>
        <div class="edit-option" data-option="senha">
          <strong>🔒 Editar apenas a Senha</strong>
          <p>Alterar somente a senha do administrador</p>
        </div>
        <div class="edit-option" data-option="tudo">
          <strong>🔄 Editar Todos os Campos</strong>
          <p>Alterar todos os dados do administrador</p>
        </div>
      </div>

      <!-- Formulário para editar nome -->
      <div id="edit-nome-form" class="edit-form">
        <form id="nome-form" method="post" action="administradores">
          <input type="hidden" name="action" value="editarNome">
          <input type="hidden" name="id" id="nome-id">
          <div class="form-group">
            <label>Nome Completo</label>
            <input type="text" class="form-control" name="nome_completo" id="edit-nome" required>
          </div>
        </form>
      </div>

      <!-- Formulário para editar CPF -->
      <div id="edit-cpf-form" class="edit-form">
        <form id="cpf-form" method="post" action="administradores">
          <input type="hidden" name="action" value="editarCpf">
          <input type="hidden" name="id" id="cpf-id">
          <div class="form-group">
            <label>CPF</label>
            <input type="text" class="form-control" name="cpf" id="edit-cpf" required>
          </div>
        </form>
      </div>

      <!-- Formulário para editar email -->
      <div id="edit-email-form" class="edit-form">
        <form id="email-form" method="post" action="administradores">
          <input type="hidden" name="action" value="editarEmail">
          <input type="hidden" name="id" id="email-id">
          <div class="form-group">
            <label>E-mail</label>
            <input type="email" class="form-control" name="email" id="edit-email" required>
          </div>
        </form>
      </div>

      <!-- Formulário para editar senha -->
      <div id="edit-senha-form" class="edit-form">
        <form id="senha-form" method="post" action="administradores">
          <input type="hidden" name="action" value="editarSenha">
          <input type="hidden" name="id" id="senha-id">
          <div class="form-group">
            <label>Nova Senha</label>
            <input type="password" class="form-control" name="senha" required>
          </div>
        </form>
      </div>

      <!-- Formulário para editar tudo -->
      <div id="edit-tudo-form" class="edit-form">
        <form id="tudo-form" method="post" action="administradores">
          <input type="hidden" name="action" value="editarTudo">
          <input type="hidden" name="id" id="tudo-id">
          <div class="form-group">
            <label>Nome Completo</label>
            <input type="text" class="form-control" name="nome_completo" id="edit-tudo-nome" required>
          </div>
          <div class="form-group">
            <label>CPF</label>
            <input type="text" class="form-control" name="cpf" id="edit-tudo-cpf" required>
          </div>
          <div class="form-group">
            <label>E-mail</label>
            <input type="email" class="form-control" name="email" id="edit-tudo-email" required>
          </div>
          <div class="form-group">
            <label>Senha</label>
            <input type="password" class="form-control" name="senha" required>
          </div>
        </form>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-secondary" id="cancel-edit-btn">Cancelar</button>
      <button type="button" class="btn btn-primary" id="save-edit-btn" style="display: none;">Salvar</button>
    </div>
  </div>
</div>

<script src="${pageContext.request.contextPath}/JS/Restricted-area/Pages/administrator.js"></script>
</body>
</html>