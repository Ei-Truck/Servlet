<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Endereco" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Endereco> enderecos = (List<Endereco>) request.getAttribute("enderecos");
    String errorMessage = (String) request.getAttribute("errorMessage");

    // Capturar e decodificar o erro da URL
    String errorParam = request.getParameter("error");
    if (errorParam != null) {
        errorParam = URLDecoder.decode(errorParam, "UTF-8");
    }

    // Parâmetros do filtro
    String filtroId = request.getParameter("filtro_id");
    String filtroCep = request.getParameter("filtro_cep");
    String filtroRua = request.getParameter("filtro_rua");
    String filtroNumero = request.getParameter("filtro_numero");
    String filtroBairro = request.getParameter("filtro_bairro");
    String filtroCidade = request.getParameter("filtro_cidade");
    String filtroEstado = request.getParameter("filtro_estado");
    String filtroPais = request.getParameter("filtro_pais");

    // Verificar se há algum filtro ativo
    boolean algumFiltro = (filtroId != null && !filtroId.isEmpty()) ||
            (filtroCep != null && !filtroCep.isEmpty()) ||
            (filtroRua != null && !filtroRua.isEmpty()) ||
            (filtroNumero != null && !filtroNumero.isEmpty()) ||
            (filtroBairro != null && !filtroBairro.isEmpty()) ||
            (filtroCidade != null && !filtroCidade.isEmpty()) ||
            (filtroEstado != null && !filtroEstado.isEmpty()) ||
            (filtroPais != null && !filtroPais.isEmpty());
%>
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
    <title>Endereços - Ei Truck</title>
    <link rel="icon" type="image/png" href="../../../image/Group 36941.png">
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
            border: none;
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

        /* Filters */
        .filters {
            padding: 20px 30px;
            border-bottom: 1px solid #eaeaea;
            display: flex;
            gap: 20px;
            align-items: end;
            flex-wrap: wrap;
        }

        .filter-group {
            display: flex;
            flex-direction: column;
        }

        .filter-group label {
            font-weight: 600;
            color: #555;
            margin-bottom: 8px;
            font-size: 14px;
        }

        .filter-input {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.3s ease;
            width: 180px;
        }

        .filter-input:focus {
            outline: none;
            border-color: var(--brand-blue);
        }

        /* Table */
        .table-container {
            padding: 0 30px 30px;
        }

        .crud-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .crud-table th {
            background-color: #f8f9fa;
            color: var(--brand-blue);
            font-weight: 600;
            padding: 15px;
            text-align: left;
            border-bottom: 2px solid #eaeaea;
        }

        .crud-table td {
            padding: 15px;
            border-bottom: 1px solid #eaeaea;
        }

        .crud-table tr:hover {
            background-color: #f8f9fa;
        }

        /* Buttons */
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

        .btn-danger {
            background: #dc3545;
            color: white;
        }

        .btn-danger:hover {
            background: #c82333;
        }

        .btn-sm {
            padding: 6px 12px;
            font-size: 12px;
        }

        .action-buttons {
            display: flex;
            gap: 8px;
        }

        /* Error Notification */
        .error-notification {
            background: #ffebee;
            color: #c62828;
            padding: 15px;
            border-radius: 6px;
            margin: 0 30px 20px;
            border: 1px solid #ef5350;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .error-notification button {
            background: none;
            border: none;
            color: #c62828;
            font-size: 18px;
            cursor: pointer;
        }

        /* Responsividade */
        @media (max-width: 1024px) {
            .filters {
                flex-direction: column;
                align-items: stretch;
            }

            .filter-input {
                width: 100%;
            }

            .filter-group:last-child {
                flex-direction: row;
                gap: 10px;
            }
        }

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

            .crud-header {
                flex-direction: column;
                gap: 15px;
                align-items: flex-start;
            }

            .crud-actions {
                width: 100%;
                justify-content: flex-end;
            }

            .action-buttons {
                flex-direction: column;
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

            .filters,
            .table-container {
                padding: 15px;
            }

            .crud-header {
                padding: 15px;
            }
        }
    </style>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) {
%>
<div class="admin-container">
    <!-- Menu Lateral -->
    <div class="sidebar">
        <div class="sidebar-header">
            <h2>Ei Truck</h2>
            <p>Painel Administrativo</p>
        </div>
        <nav class="sidebar-nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/html/Restricted-area/Pages/Dashboard/dashboard.jsp" class="nav-item"><span>📊</span> Dashboard</a></li>

                <li>
                    <a href="${pageContext.request.contextPath}/servlet-administrador?acao=buscar&sub_acao=buscar_todos" class="nav-item">
                        <span>👨‍💼</span> Gerenciar Administradores
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/servlet-analista?acao=buscar&sub_acao=buscar_todos" class="nav-item">
                        <span>👥</span> Gerenciar Analistas
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/servlet-segmentos?acao=buscar&sub_acao=buscar_todos" class="nav-item">
                        <span>📁</span> Gerenciar Segmentos
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/servlet-unidade?acao=buscar&sub_acao=buscar_todos" class="nav-item">
                        <span>🏢</span> Gerenciar Unidades
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/servlet-enderecos?acao=buscar&sub_acao=buscar_todos" class="nav-item active">
                        <span>📍</span> Gerenciar Endereços
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/servlet-ocorrencias?acao=buscar&sub_acao=buscar_todos" class="nav-item">
                        <span>⚠️</span> Gerenciar Tipos de Ocorrência
                    </a>
                </li>

                <li><a href="${pageContext.request.contextPath}/logout" class="nav-item logout"><span>🚪</span> Sair</a></li>
            </ul>
        </nav>
    </div>

    <!-- Conteúdo Principal -->
    <div class="main-content">
        <header class="content-header">
            <div class="header-left">
                <h1>Gerenciar Endereços</h1>
                <p>Cadastre e gerencie os endereços do sistema</p>
            </div>
            <div class="header-right">
                <div class="user-info">
                    <span>Administrador</span>
                </div>
            </div>
        </header>

        <div class="page-content">
            <div class="crud-section">
                <div class="crud-header">
                    <h2>Endereços</h2>
                    <div class="crud-actions">
                        <button onclick="window.location.href='html/Restricted-area/Pages/Addresses/addresses.jsp'" class="btn btn-primary">
                            <span>➕</span> Adicionar Endereço
                        </button>
                    </div>
                </div>

                <%-- Notificação de erro --%>
                <% if (errorMessage != null || errorParam != null) { %>
                <div class="error-notification" id="errorNotification">
                    <span>Erro: <%= errorMessage != null ? errorMessage : errorParam %></span>
                    <button onclick="document.getElementById('errorNotification').style.display='none'">×</button>
                </div>

                <script>
                    // Opcional: remover automaticamente após 5 segundos
                    setTimeout(function() {
                        var notification = document.getElementById('errorNotification');
                        if (notification) {
                            notification.style.display = 'none';
                        }
                    }, 5000);
                </script>
                <% } %>

                <div class="filters">
                    <form action="${pageContext.request.contextPath}/servlet-enderecos" method="get" style="display: contents;">
                        <input type="hidden" name="acao" value="filtrar">
                        <input type="hidden" name="sub_acao" value="buscar_todos">

                        <div class="filter-group">
                            <label for="filtroId">ID</label>
                            <input type="text" id="filtroId" name="filtro_id" class="filter-input"
                                   value="<%= filtroId != null ? filtroId : "" %>"
                                   placeholder="Ex: 1, 2">
                        </div>

                        <div class="filter-group">
                            <label for="filtroCep">CEP</label>
                            <input type="text" id="filtroCep" name="filtro_cep" class="filter-input"
                                   value="<%= filtroCep != null ? filtroCep : "" %>"
                                   placeholder="Ex: 12345678">
                        </div>

                        <div class="filter-group">
                            <label for="filtroRua">Rua</label>
                            <input type="text" id="filtroRua" name="filtro_rua" class="filter-input"
                                   value="<%= filtroRua != null ? filtroRua : "" %>"
                                   placeholder="Ex: Principal">
                        </div>

                        <div class="filter-group">
                            <label for="filtroNumero">Número</label>
                            <input type="text" id="filtroNumero" name="filtro_numero" class="filter-input"
                                   value="<%= filtroNumero != null ? filtroNumero : "" %>"
                                   placeholder="Ex: 100">
                        </div>

                        <div class="filter-group">
                            <label for="filtroBairro">Bairro</label>
                            <input type="text" id="filtroBairro" name="filtro_bairro" class="filter-input"
                                   value="<%= filtroBairro != null ? filtroBairro : "" %>"
                                   placeholder="Ex: Centro">
                        </div>

                        <div class="filter-group">
                            <label for="filtroCidade">Cidade</label>
                            <input type="text" id="filtroCidade" name="filtro_cidade" class="filter-input"
                                   value="<%= filtroCidade != null ? filtroCidade : "" %>"
                                   placeholder="Ex: São Paulo">
                        </div>

                        <div class="filter-group">
                            <label for="filtroEstado">Estado</label>
                            <input type="text" id="filtroEstado" name="filtro_estado" class="filter-input"
                                   value="<%= filtroEstado != null ? filtroEstado : "" %>"
                                   placeholder="Ex: SP">
                        </div>

                        <div class="filter-group">
                            <label for="filtroPais">País</label>
                            <input type="text" id="filtroPais" name="filtro_pais" class="filter-input"
                                   value="<%= filtroPais != null ? filtroPais : "" %>"
                                   placeholder="Ex: Brasil">
                        </div>

                        <div class="filter-group" style="flex-direction: row; align-items: end; gap: 10px;">
                            <button type="submit" class="btn btn-secondary">
                                <span>🔍</span> Aplicar Filtros
                            </button>

                            <button type="button" onclick="limparFiltro()" class="btn btn-secondary">
                                <span>🔄</span> Limpar
                            </button>
                        </div>
                    </form>
                </div>

                <div style="padding: 0 30px 15px; font-size: 14px; color: #333; margin-top: 35px">
                    <strong>
                        <%
                            if (enderecos != null) {
                                if (algumFiltro) {
                                    out.print("Registros filtrados: " + enderecos.size());

                                    // Mostra os filtros ativos
                                    out.print(" | Filtros: ");
                                    List<String> filtrosAtivos = new ArrayList<>();
                                    if (filtroId != null && !filtroId.isEmpty()) filtrosAtivos.add("ID: " + filtroId);
                                    if (filtroCep != null && !filtroCep.isEmpty()) filtrosAtivos.add("CEP: " + filtroCep);
                                    if (filtroRua != null && !filtroRua.isEmpty()) filtrosAtivos.add("Rua: " + filtroRua);
                                    if (filtroNumero != null && !filtroNumero.isEmpty()) filtrosAtivos.add("Número: " + filtroNumero);
                                    if (filtroBairro != null && !filtroBairro.isEmpty()) filtrosAtivos.add("Bairro: " + filtroBairro);
                                    if (filtroCidade != null && !filtroCidade.isEmpty()) filtrosAtivos.add("Cidade: " + filtroCidade);
                                    if (filtroEstado != null && !filtroEstado.isEmpty()) filtrosAtivos.add("Estado: " + filtroEstado);
                                    if (filtroPais != null && !filtroPais.isEmpty()) filtrosAtivos.add("País: " + filtroPais);
                                    out.print(String.join(", ", filtrosAtivos));
                                } else {
                                    out.print("Total de registros: " + enderecos.size());
                                }
                            }
                        %>
                    </strong>
                </div>

                <div class="table-container">
                    <table class="crud-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>CEP</th>
                            <th>Rua</th>
                            <th>Número</th>
                            <th>Bairro</th>
                            <th>Cidade</th>
                            <th>Estado</th>
                            <th>País</th>
                            <th>Ações</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (enderecos != null && !enderecos.isEmpty()) {
                                for (int i=0; i < enderecos.size(); i++){
                        %>
                        <tr>
                            <td><%=enderecos.get(i).getId()%></td>
                            <td><%=enderecos.get(i).getCep()%></td>
                            <td><%=enderecos.get(i).getRua()%></td>
                            <td><%=enderecos.get(i).getNumero()%></td>
                            <td><%=enderecos.get(i).getBairro()%></td>
                            <td><%=enderecos.get(i).getCidade()%></td>
                            <td><%=enderecos.get(i).getEstado()%></td>
                            <td><%=enderecos.get(i).getPais()%></td>
                            <td>
                                <div class="action-buttons">
                                    <form action="${pageContext.request.contextPath}/servlet-enderecos" method="get" style="display:inline;">
                                        <input type="hidden" name="acao" value="editar">
                                        <input type="hidden" name="id" value="<%= enderecos.get(i).getId() %>">
                                        <button type="submit" class="btn btn-success btn-sm">
                                            <span>✏️</span> Editar
                                        </button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/servlet-enderecos" method="post" style="display:inline;">
                                        <input type="hidden" name="acao_principal" value="excluir">
                                        <input type="hidden" name="id" value="<%= enderecos.get(i).getId() %>">
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Tem certeza que deseja excluir este endereço?')">
                                            <span>🗑️</span> Excluir
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="9" style="text-align: center; padding: 30px;">
                                <% if (algumFiltro) { %>
                                Nenhum endereço encontrado com os filtros aplicados.
                                <% } else { %>
                                Nenhum endereço encontrado ou erro ao carregar dados.
                                <% } %>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function limparFiltro() {
        window.location.href = '${pageContext.request.contextPath}/servlet-enderecos?acao=buscar&sub_acao=buscar_todos';
    }
</script>
<%
    }
%>
</body>
</html>