<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Segmento" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Segmento> segmentos = (List<Segmento>) request.getAttribute("segmentos");
    String errorMessage = (String) request.getAttribute("errorMessage");

    // Capturar e decodificar o erro da URL
    String errorParam = request.getParameter("error");
    if (errorParam != null) {
        errorParam = URLDecoder.decode(errorParam, "UTF-8");
    }

    // Parâmetros do filtro
    String filtroId = request.getParameter("filtro_id");
    String filtroNome = request.getParameter("filtro_nome");
    String filtroDescricao = request.getParameter("filtro_descricao");

    // Verificar se há algum filtro ativo
    boolean algumFiltro = (filtroId != null && !filtroId.isEmpty()) ||
            (filtroNome != null && !filtroNome.isEmpty()) ||
            (filtroDescricao != null && !filtroDescricao.isEmpty());
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
    <title>Segmentos - Ei Truck</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="icon" type="image/png" href="../../../../imagens/Group 36941.png">
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
            width: 200px;
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
            margin-bottom: 20px;
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

            .filters {
                flex-direction: column;
                align-items: stretch;
            }

            .filter-input {
                width: 100%;
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

            .action-buttons {
                flex-direction: column;
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
                <p>Cadastre e gerencie os segmentos de atuação</p>
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
                    <h2>Segmentos</h2>
                    <div class="crud-actions">
                        <button onclick="window.location.href='html/area-restrita/paginas/segmento/segmento.jsp'" class="btn btn-primary">
                            <span>➕</span> Adicionar Segmento
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
                    <form action="${pageContext.request.contextPath}/servlet-segmentos" method="get" style="display: contents;">
                        <input type="hidden" name="acao" value="filtrar">
                        <input type="hidden" name="sub_acao" value="buscar_todos">

                        <div class="filter-group">
                            <label for="filtroId">ID</label>
                            <input type="text" id="filtroId" name="filtro_id" class="filter-input"
                                   value="<%= filtroId != null ? filtroId : "" %>"
                                   placeholder="Ex: 1, 2, 3"
                                   oninput="this.value = this.value.replace(/[^0-9]/g, '')">
                        </div>

                        <div class="filter-group">
                            <label for="filtroNome">Nome</label>
                            <input type="text" id="filtroNome" name="filtro_nome" class="filter-input"
                                   value="<%= filtroNome != null ? filtroNome : "" %>"
                                   placeholder="Ex: transporte">
                        </div>

                        <div class="filter-group">
                            <label for="filtroDescricao">Descrição</label>
                            <input type="text" id="filtroDescricao" name="filtro_descricao" class="filter-input"
                                   value="<%= filtroDescricao != null ? filtroDescricao : "" %>"
                                   placeholder="Ex: logística">
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
                            if (segmentos != null) {
                                if (algumFiltro) {
                                    out.print("Registros filtrados: " + segmentos.size());

                                    // Mostra os filtros ativos
                                    out.print(" | Filtros: ");
                                    List<String> filtrosAtivos = new ArrayList<>();
                                    if (filtroId != null && !filtroId.isEmpty()) filtrosAtivos.add("ID: " + filtroId);
                                    if (filtroNome != null && !filtroNome.isEmpty()) filtrosAtivos.add("Nome: " + filtroNome);
                                    if (filtroDescricao != null && !filtroDescricao.isEmpty()) filtrosAtivos.add("Descrição: " + filtroDescricao);
                                    out.print(String.join(", ", filtrosAtivos));
                                } else {
                                    out.print("Total de registros: " + segmentos.size());
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
                            <th>Nome</th>
                            <th>Descrição</th>
                            <th>Ações</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (segmentos != null && !segmentos.isEmpty()) {
                                for (int i=0; i < segmentos.size(); i++){
                        %>
                        <tr>
                            <td><%=segmentos.get(i).getId()%></td>
                            <td><%=segmentos.get(i).getNome()%></td>
                            <td><%=segmentos.get(i).getDescricao()%></td>
                            <td>
                                <div class="action-buttons">
                                    <form action="${pageContext.request.contextPath}/servlet-segmentos" method="get" style="display:inline;">
                                        <input type="hidden" name="acao" value="editar">
                                        <input type="hidden" name="id" value="<%= segmentos.get(i).getId() %>">
                                        <button type="submit" class="btn btn-success btn-sm">
                                            <span>✏️</span> Editar
                                        </button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/servlet-segmentos" method="post" style="display:inline;" onsubmit="confirmarDeletar(event)">
                                        <input type="hidden" name="acao_principal" value="excluir">
                                        <input type="hidden" name="id" value="<%= segmentos.get(i).getId() %>">
                                        <button type="submit" class="btn btn-danger btn-sm">
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
                            <td colspan="4" style="text-align: center; padding: 30px;">
                                <% if (algumFiltro) { %>
                                Nenhum segmento encontrado com os filtros aplicados.
                                <% } else { %>
                                Nenhum segmento encontrado ou erro ao carregar dados.
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
        window.location.href = '${pageContext.request.contextPath}/servlet-segmentos?acao=buscar&sub_acao=buscar_todos';
    }

    function confirmarDeletar(event) {
        event.preventDefault();

        Swal.fire({
            title: "Quer mesmo excluir?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "red",
            cancelButtonColor: "#ababab",
        }).then((result) => {
            if (result.isConfirmed) {
                event.target.submit();
            }
        })

        return false;
    }
</script>
<%
    }
%>
</body>
</html>