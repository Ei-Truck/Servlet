<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administradores - Ei Truck</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleCss/Restricted-area/admin.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/image/Group%2036941.png">
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
                <li><a href="administradores.jsp" class="nav-item active"><span>👨‍💼</span> Gerenciar Administradores</a></li>
                <li><a href="analistas.jsp" class="nav-item"><span>👥</span> Gerenciar Analistas</a></li>
                <li><a href="segmentos.jsp" class="nav-item"><span>📁</span> Gerenciar Segmentos</a></li>
                <li><a href="unidades.jsp" class="nav-item"><span>🏢</span> Gerenciar Unidades</a></li>
                <li><a href="enderecos.jsp" class="nav-item"><span>📍</span> Gerenciar Endereços</a></li>
                <li><a href="ocorrencias.jsp" class="nav-item"><span>⚠️</span> Gerenciar Tipos de Ocorrência</a></li>
                <li><a href="${pageContext.request.contextPath}/logout" class="nav-item logout"><span>🚪</span> Sair</a></li>
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
                    <span>Administrador</span>
                </div>
            </div>
        </header>

        <div class="page-content">
            <!-- Mensagens de Sucesso/Erro -->
            <%
                String success = (String) request.getAttribute("success");
                String error = (String) request.getAttribute("error");
                if (success != null) {
            %>
            <div class="success-message">
                <%= success %>
            </div>
            <% } %>

            <% if (error != null) { %>
            <div class="error-message">
                <%= error %>
            </div>
            <% } %>

            <div class="crud-section">
                <div class="crud-header">
                    <h2>Administradores</h2>
                    <div class="crud-actions">
                        <a href="${pageContext.request.contextPath}/adicionarAdministrador.jsp" class="btn btn-primary">
                            <span>➕</span> Adicionar Administrador
                        </a>
                    </div>
                </div>

                <!-- Filtros -->
                <div class="filters-container">
                    <form action="${pageContext.request.contextPath}/AdministradorServlet" method="GET" class="filter-form">
                        <input type="hidden" name="action" value="filter">
                        <div class="filter-row">
                            <div class="filter-group">
                                <label for="filterId">ID</label>
                                <input type="number" id="filterId" name="filterId"
                                       placeholder="Filtrar por ID..." value="<%= request.getParameter("filterId") != null ? request.getParameter("filterId") : "" %>">
                            </div>
                            <div class="filter-group">
                                <label for="filterNome">Nome</label>
                                <input type="text" id="filterNome" name="filterNome"
                                       placeholder="Filtrar por nome..." value="<%= request.getParameter("filterNome") != null ? request.getParameter("filterNome") : "" %>">
                            </div>
                            <div class="filter-group">
                                <label for="filterCpf">CPF</label>
                                <input type="text" id="filterCpf" name="filterCpf"
                                       placeholder="Filtrar por CPF..." value="<%= request.getParameter("filterCpf") != null ? request.getParameter("filterCpf") : "" %>">
                            </div>
                            <div class="filter-group">
                                <label for="filterEmail">E-mail</label>
                                <input type="text" id="filterEmail" name="filterEmail"
                                       placeholder="Filtrar por e-mail..." value="<%= request.getParameter("filterEmail") != null ? request.getParameter("filterEmail") : "" %>">
                            </div>
                        </div>
                        <div class="filter-actions">
                            <button type="submit" class="btn btn-secondary">
                                <span>🔍</span> Aplicar Filtros
                            </button>
                            <a href="${pageContext.request.contextPath}/administradores.jsp" class="btn btn-secondary">
                                <span>🔄</span> Limpar
                            </a>
                        </div>
                    </form>
                </div>

                <!-- Tabela -->
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
                        <tbody>
                        <%
                            // Aqui você carregaria a lista de administradores do banco
                            // Por enquanto, vou deixar um exemplo estático
                            java.util.List<Object> administradores = new java.util.ArrayList<>();
                            // administradores = (java.util.List<Object>) request.getAttribute("administradores");

                            if (administradores != null && !administradores.isEmpty()) {
                                for (Object admin : administradores) {
                                    // Aqui você acessaria os atributos do objeto administrador
                        %>
                        <tr>
                            <td>1</td>
                            <td>João Silva</td>
                            <td>123.456.789-00</td>
                            <td>joao@email.com</td>
                            <td class="actions-cell">
                                <a href="${pageContext.request.contextPath}/alterarAdministrador.jsp?id=1" class="btn btn-success btn-sm">Alterar</a>
                                <a href="${pageContext.request.contextPath}/deletarAdministrador.jsp?id=1" class="btn btn-danger btn-sm">Excluir</a>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="5" style="text-align: center;">Nenhum administrador encontrado</td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>