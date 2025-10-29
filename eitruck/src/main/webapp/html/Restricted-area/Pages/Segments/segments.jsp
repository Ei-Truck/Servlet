<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  // Verifica se o usuário está logado
  if (session == null || session.getAttribute("nomeAdimin") == null) {
    response.sendRedirect(request.getContextPath() + "/login.jsp");
    return;
  }

  String nomeAdmin = (String) session.getAttribute("nomeAdimin");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cadastrar Segmentos - Ei Truck</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/image/Group%2036941.png">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
    :root {
      --brand-blue: #022B3A;
      --brand-blue-2: #00546B;
      --brand-green: #00b377;
      --sidebar-width: 280px;
      --header-height: 80px;
    }

    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: #f5f7fa;
      color: #333;
    }

    .admin-container {
      display: flex;
      min-height: 100vh;
    }

    /* Sidebar */
    .sidebar {
      width: var(--sidebar-width);
      background: var(--brand-blue);
      color: white;
      position: fixed;
      height: 100vh;
      overflow-y: auto;
    }

    .sidebar-header {
      padding: 30px 25px;
      border-bottom: 1px solid rgba(255,255,255,0.1);
    }

    .sidebar-header h2 {
      font-size: 24px;
      margin-bottom: 5px;
    }

    .sidebar-header p {
      opacity: 0.8;
      font-size: 14px;
    }

    .sidebar-nav ul {
      list-style: none;
      padding: 20px 0;
    }

    .sidebar-nav li {
      margin-bottom: 5px;
    }

    .nav-item {
      display: flex;
      align-items: center;
      padding: 15px 25px;
      color: rgba(255,255,255,0.8);
      text-decoration: none;
      transition: all 0.3s ease;
      border-left: 4px solid transparent;
      background: none;
      width: 100%;
      text-align: left;
      cursor: pointer;
      font-family: inherit;
      font-size: inherit;
    }

    .nav-item span {
      margin-right: 12px;
      font-size: 18px;
    }

    .nav-item:hover {
      background: rgba(255,255,255,0.1);
      color: white;
      border-left-color: var(--brand-green);
    }

    .nav-item.active {
      background: rgba(255,255,255,0.15);
      color: white;
      border-left-color: var(--brand-green);
    }

    .nav-item.logout {
      margin-top: 20px;
      border-top: 1px solid rgba(255,255,255,0.1);
      padding-top: 20px;
      color: #ff6b6b;
    }

    /* Main Content */
    .main-content {
      flex: 1;
      margin-left: var(--sidebar-width);
      min-height: 100vh;
    }

    .content-header {
      background: white;
      padding: 0 30px;
      height: var(--header-height);
      display: flex;
      align-items: center;
      justify-content: space-between;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
      border-bottom: 1px solid #eaeaea;
    }

    .header-left h1 {
      font-size: 24px;
      color: var(--brand-blue);
      margin-bottom: 5px;
    }

    .header-left p {
      color: #666;
      font-size: 14px;
    }

    .user-info {
      background: #f8f9fa;
      padding: 10px 20px;
      border-radius: 20px;
      font-weight: 600;
      color: var(--brand-blue);
    }

    /* Page Content */
    .page-content {
      padding: 30px;
    }

    /* CRUD Section */
    .crud-section {
      background: white;
      border-radius: 12px;
      box-shadow: 0 4px 15px rgba(0,0,0,0.1);
      overflow: hidden;
      margin-bottom: 30px;
    }

    .crud-header {
      padding: 25px 30px;
      border-bottom: 1px solid #eaeaea;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .crud-header h2 {
      color: var(--brand-blue);
      font-size: 20px;
    }

    .crud-actions {
      display: flex;
      gap: 15px;
    }

    .form-container {
      padding: 30px;
    }

    .form-group {
      margin-bottom: 20px;
    }

    .form-group label {
      display: block;
      margin-bottom: 8px;
      font-weight: 600;
      color: #555;
    }

    .form-control {
      width: 100%;
      padding: 12px;
      border: 1px solid #ddd;
      border-radius: 6px;
      font-size: 14px;
      transition: border-color 0.3s ease;
    }

    .form-control:focus {
      outline: none;
      border-color: var(--brand-blue);
    }

    .form-row {
      display: flex;
      gap: 15px;
    }

    .form-row .form-group {
      flex: 1;
    }

    .form-actions {
      display: flex;
      gap: 10px;
      justify-content: flex-end;
      margin-top: 30px;
    }

    .btn {
      padding: 10px 20px;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      font-weight: 600;
      transition: all 0.3s ease;
      text-decoration: none;
      display: inline-flex;
      align-items: center;
      gap: 8px;
      font-size: 14px;
    }

    .btn-primary {
      background: var(--brand-blue);
      color: white;
    }

    .btn-primary:hover {
      background: var(--brand-blue-2);
    }

    .btn-secondary {
      background: #6c757d;
      color: white;
    }

    .btn-secondary:hover {
      background: #5a6268;
    }

    .btn-success {
      background: var(--brand-green);
      color: white;
    }

    .btn-success:hover {
      background: #00a366;
    }

    /* Responsividade */
    @media (max-width: 768px) {
      .sidebar {
        width: 70px;
        overflow: visible;
      }

      .sidebar-header h2,
      .sidebar-header p,
      .nav-item span:last-child {
        display: none;
      }

      .main-content {
        margin-left: 70px;
      }

      .nav-item {
        justify-content: center;
        padding: 15px;
      }

      .nav-item span:first-child {
        margin-right: 0;
        font-size: 20px;
      }

      .form-row {
        flex-direction: column;
        gap: 0;
      }

      .crud-header {
        flex-direction: column;
        gap: 15px;
        align-items: flex-start;
      }

      .crud-actions {
        width: 100%;
        justify-content: flex-end;
      }
    }

    @media (max-width: 480px) {
      .page-content {
        padding: 15px;
      }

      .content-header {
        padding: 0 15px;
        flex-direction: column;
        height: auto;
        padding: 15px;
        gap: 10px;
      }

      .header-left,
      .header-right {
        width: 100%;
      }
    }
    .nav-item span i {
      width: 20px;
      text-align: center;
      font-size: 16px;
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
        <li><a href="${pageContext.request.contextPath}/dashboard" class="nav-item"><span><i class="fas fa-chart-bar"></i></span> Dashboard</a></li>
        <li>
          <a href="${pageContext.request.contextPath}/servlet-administrador?acao=buscar&sub_acao=buscar_todos" class="nav-item">
            <span><i class="fas fa-user-shield"></i></span> Gerenciar Administradores
          </a>
        </li>

        <li>
          <a href="${pageContext.request.contextPath}/servlet-analista?acao=buscar&sub_acao=buscar_todos" class="nav-item">
            <span><i class="fas fa-users"></i></span> Gerenciar Analistas
          </a>
        </li>

        <li>
          <a href="${pageContext.request.contextPath}/servlet-segmentos?acao=buscar&sub_acao=buscar_todos" class="nav-item active">
            <span><i class="fas fa-folder"></i></span> Gerenciar Segmentos
          </a>
        </li>

        <li>
          <a href="${pageContext.request.contextPath}/servlet-unidade?acao=buscar&sub_acao=buscar_todos" class="nav-item">
            <span><i class="fas fa-building"></i></span> Gerenciar Unidades
          </a>
        </li>

        <li>
          <a href="${pageContext.request.contextPath}/servlet-enderecos?acao=buscar&sub_acao=buscar_todos" class="nav-item">
            <span><i class="fas fa-map-marker-alt"></i></span> Gerenciar Endereços
          </a>
        </li>

        <li>
          <a href="${pageContext.request.contextPath}/servlet-ocorrencias?acao=buscar&sub_acao=buscar_todos" class="nav-item">
            <span><i class="fas fa-exclamation-triangle"></i></span> Gerenciar Tipos de Ocorrência
          </a>
        </li>

        <li><a href="${pageContext.request.contextPath}/logout" class="nav-item logout"><span><i class="fas fa-sign-out-alt"></i></span> Sair</a></li>
      </ul>
    </nav>
  </div>

  <!-- Conteúdo Principal -->
  <div class="main-content">
    <header class="content-header">
      <div class="header-left">
        <h1>Gerenciar Segmentos</h1>
        <p>Cadastre e consulte segmentos do sistema</p>
      </div>
      <div class="header-right">
        <div class="user-info">
          <span>Administrador</span>
        </div>
      </div>
    </header>

    <div class="page-content">
      <!-- Formulário de Inserção -->
      <div class="crud-section">
        <div class="crud-header">
          <h2>Cadastrar Novo Segmento</h2>
          <div class="crud-actions">
            <form action="${pageContext.request.contextPath}/servlet-segmentos" method="get">
              <input type="hidden" name="acao_principal" value="buscar">
              <button type="submit" name="sub_acao" value="buscar_todos" class="btn btn-secondary">
                <span>←</span> Voltar para Lista
              </button>
            </form>
          </div>
        </div>

        <div class="form-container">
          <%
            String errorMessage = (String) request.getAttribute("errorMessage");

            if (errorMessage != null) {
          %>
          <div style="background: #ffebee; color: #c62828; padding: 15px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #ef5350;">
            <strong>Erro em algum cadastro</strong>
          </div>
          <%
            }
          %>
          <form action="${pageContext.request.contextPath}/servlet-segmentos" method="post">
            <input type="hidden" name="acao_principal" value="inserir">
            <input type="hidden" name="sub_acao" value="inserir">

            <div class="form-group">
              <label for="nome">Nome do Segmento:</label>
              <input type="text" name="nome" id="nome" class="form-control" value="${nome != null ? nome : ''}" required>
            </div>

            <div class="form-group">
              <label for="descricao">Descrição:</label>
              <input type="text" name="descricao" id="descricao" class="form-control" value="${descricao != null ? descricao : ''}" required>
            </div>

            <div class="form-actions">
              <button type="submit" class="btn btn-primary">Cadastrar Segmento</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>