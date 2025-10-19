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
                <li><a href="administradores" class="nav-item active"><span>👨‍💼</span> Gerenciar Administradores</a></li>
                <li><a href="analyst.jsp" class="nav-item"><span>👥</span> Gerenciar Analistas</a></li>
                <li><a href="segments.jsp" class="nav-item"><span>📁</span> Gerenciar Segmentos</a></li>
                <li><a href="units.jsp" class="nav-item"><span>🏢</span> Gerenciar Unidades</a></li>
                <li><a href="addresses.jsp" class="nav-item"><span>📍</span> Gerenciar Endereços</a></li>
                <li><a href="occurrences.jsp" class="nav-item"><span>⚠️</span> Gerenciar Tipos de Ocorrência</a></li>
                <li><a href="../login.jsp" class="nav-item logout"><span>🚪</span> Sair</a></li>
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
            <%
                String successMsg = request.getParameter("success");
                String errorMsg = request.getParameter("error");
                if (successMsg != null) {
            %>
            <div class="alert-success"><%= successMsg %></div>
            <%  } else if (errorMsg != null) { %>
            <div class="alert-error"><%= errorMsg %></div>
            <%  } %>

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
                        <input type="hidden" name="action" value="buscar">
                        <div class="filter-group1">
                            <div class="filter-group">
                                <label for="filter-id">ID</label>
                                <input type="number" class="filter-input" id="filter-id" name="filter-id"
                                       placeholder="Filtrar por ID..."
                                       value="<%= request.getParameter("filter-id") != null ? request.getParameter("filter-id") : "" %>">
                            </div>
                            <div class="filter-group">
                                <label for="filter-nome">Nome</label>
                                <input type="text" class="filter-input" id="filter-nome" name="filter-nome"
                                       placeholder="Filtrar por nome..."
                                       value="<%= request.getParameter("filter-nome") != null ? request.getParameter("filter-nome") : "" %>">
                            </div>
                            <div class="filter-group">
                                <label for="filter-cpf">CPF</label>
                                <input type="text" class="filter-input" id="filter-cpf" name="filter-cpf"
                                       placeholder="Filtrar por CPF..."
                                       value="<%= request.getParameter("filter-cpf") != null ? request.getParameter("filter-cpf") : "" %>">
                            </div>
                            <div class="filter-group">
                                <label for="filter-email">E-mail</label>
                                <input type="text" class="filter-input" id="filter-email" name="filter-email"
                                       placeholder="Filtrar por e-mail..."
                                       value="<%= request.getParameter("filter-email") != null ? request.getParameter("filter-email") : "" %>">
                            </div>
                        </div>
                        <div class="filter-group" style="display: flex; gap: 10px; margin-top: 15px;">
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
                            if (administradores != null && !administradores.isEmpty()) {
                                for (Administrador admin : administradores) {
                        %>
                        <tr>
                            <td><%= admin.getId() %></td>
                            <td><%= admin.getNomeCompleto() != null ? admin.getNomeCompleto() : "" %></td>
                            <td><%= admin.getCpf() != null ? admin.getCpf() : "" %></td>
                            <td><%= admin.getEmail() != null ? admin.getEmail() : "" %></td>
                            <td>
                                <button class="btn btn-edit"
                                        data-id="<%= admin.getId() %>"
                                        data-nome="<%= admin.getNomeCompleto() != null ? admin.getNomeCompleto() : "" %>"
                                        data-cpf="<%= admin.getCpf() != null ? admin.getCpf() : "" %>"
                                        data-email="<%= admin.getEmail() != null ? admin.getEmail() : "" %>">
                                    Editar
                                </button>
                                <button class="btn btn-delete"
                                        data-id="<%= admin.getId() %>">
                                    Excluir
                                </button>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="5" style="text-align: center; padding: 20px;">
                                Nenhum administrador encontrado
                            </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal para Adicionar/Editar -->
<div id="crud-modal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 id="modal-title">Adicionar Administrador</h3>
            <span class="close">&times;</span>
        </div>
        <div class="modal-body">
            <form id="crud-form" method="post" action="administradores">
                <input type="hidden" id="admin-id" name="id">
                <input type="hidden" id="action-type" name="action" value="cadastrar">

                <div class="form-group">
                    <label for="nome_completo">Nome Completo *</label>
                    <input type="text" class="form-control" id="nome_completo" name="nome_completo" required>
                </div>

                <div class="form-group">
                    <label for="cpf">CPF *</label>
                    <input type="text" class="form-control" id="cpf" name="cpf" required
                           placeholder="000.000.000-00">
                </div>

                <div class="form-group">
                    <label for="email">E-mail *</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="senha">Senha <span id="senha-required">*</span></label>
                    <input type="password" class="form-control" id="senha" name="senha">
                    <small id="senha-help" style="color: #666; font-size: 12px;">
                        Para edição, deixe em branco para manter a senha atual
                    </small>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" id="cancel-btn">Cancelar</button>
            <button type="button" class="btn btn-primary" id="save-btn">Salvar</button>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Elementos do modal único
        const addBtn = document.getElementById('add-btn');
        const modal = document.getElementById('crud-modal');
        const closeBtn = document.querySelector('.close');
        const cancelBtn = document.getElementById('cancel-btn');
        const saveBtn = document.getElementById('save-btn');
        const form = document.getElementById('crud-form');
        const modalTitle = document.getElementById('modal-title');
        const adminIdInput = document.getElementById('admin-id');
        const actionTypeInput = document.getElementById('action-type');
        const senhaInput = document.getElementById('senha');
        const senhaRequired = document.getElementById('senha-required');
        const senhaHelp = document.getElementById('senha-help');

        // Abrir modal para adicionar
        addBtn.addEventListener('click', function() {
            modalTitle.textContent = 'Adicionar Administrador';
            form.reset();
            adminIdInput.value = '';
            actionTypeInput.value = 'cadastrar';

            // Tornar o campo senha obrigatório no cadastro
            senhaInput.required = true;
            senhaRequired.style.display = 'inline';
            senhaHelp.style.display = 'none';
            senhaInput.placeholder = 'Digite a senha';

            modal.style.display = 'block';
        });

        // Fechar modal
        function closeModal() {
            modal.style.display = 'none';
            form.reset();
        }

        closeBtn.addEventListener('click', closeModal);
        cancelBtn.addEventListener('click', closeModal);

        // Abrir modal para editar
        document.querySelectorAll('.btn-edit').forEach(button => {
            button.addEventListener('click', function() {
                const id = this.getAttribute('data-id');
                const nome = this.getAttribute('data-nome');
                const cpf = this.getAttribute('data-cpf');
                const email = this.getAttribute('data-email');

                modalTitle.textContent = 'Editar Administrador';
                adminIdInput.value = id;
                actionTypeInput.value = 'editar';

                // Preencher os campos do formulário
                document.querySelector('[name="nome_completo"]').value = nome;
                document.querySelector('[name="cpf"]').value = cpf;
                document.querySelector('[name="email"]').value = email;

                // Senha não é obrigatória na edição
                senhaInput.required = false;
                senhaRequired.style.display = 'none';
                senhaHelp.style.display = 'block';
                senhaInput.value = '';
                senhaInput.placeholder = 'Deixe em branco para manter a senha atual';

                modal.style.display = 'block';
            });
        });

        // Salvar (tanto adicionar quanto editar)
        saveBtn.addEventListener('click', function() {
            // Validação específica para cadastro (senha obrigatória)
            if (actionTypeInput.value === 'cadastrar' && !senhaInput.value.trim()) {
                alert('Por favor, preencha a senha para cadastro.');
                senhaInput.focus();
                return;
            }

            if (form.checkValidity()) {
                form.submit();
            } else {
                alert('Por favor, preencha todos os campos obrigatórios.');
            }
        });

        // Excluir administrador
        document.querySelectorAll('.btn-delete').forEach(button => {
            button.addEventListener('click', function() {
                const id = this.getAttribute('data-id');
                const nome = this.getAttribute('data-nome');

                if (confirm('Tem certeza que deseja excluir o administrador "' + nome + '"?')) {
                    const form = document.createElement('form');
                    form.method = 'post';
                    form.action = 'administradores';

                    const inputAction = document.createElement('input');
                    inputAction.type = 'hidden';
                    inputAction.name = 'action';
                    inputAction.value = 'excluir';
                    form.appendChild(inputAction);

                    const inputId = document.createElement('input');
                    inputId.type = 'hidden';
                    inputId.name = 'id';
                    inputId.value = id;
                    form.appendChild(inputId);

                    document.body.appendChild(form);
                    form.submit();
                }
            });
        });

        // Limpar filtros
        document.getElementById('clear-filters').addEventListener('click', function() {
            document.querySelectorAll('.filter-input').forEach(input => {
                input.value = '';
            });
            // Submeter o formulário de filtro vazio
            document.getElementById('filter-form').submit();
        });

        // Fechar modal ao clicar fora
        window.addEventListener('click', function(event) {
            if (event.target === modal) {
                closeModal();
            }
        });

        // Máscara para CPF
        const cpfInput = document.getElementById('cpf');
        if (cpfInput) {
            cpfInput.addEventListener('input', function(e) {
                let value = e.target.value.replace(/\D/g, '');
                if (value.length <= 11) {
                    value = value.replace(/(\d{3})(\d)/, '$1.$2');
                    value = value.replace(/(\d{3})(\d)/, '$1.$2');
                    value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
                    e.target.value = value;
                }
            });
        }
    });
</script>
</body>
</html>