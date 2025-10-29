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
    <title>Administrador - Ei Truck</title>
    <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/image/Group%2036941.png">
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
            position: relative;
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

        .password-toggle {
            position: absolute;
            right: 12px;
            top: 38px;
            background: none;
            border: none;
            cursor: pointer;
            color: #666;
            font-size: 16px;
        }

        .password-toggle:hover {
            color: var(--brand-blue);
        }

        .error-notification {
            background-color: #ffebee;
            border: 1px solid #f44336;
            color: #c62828;
            padding: 12px 16px;
            border-radius: 4px;
            margin: 10px 0;
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

            .password-toggle {
                top: 42px;
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
                <li><a href="/dashboard" class="nav-item"><span>📊</span> Dashboard</a></li>

                <li>
                    <a href="${pageContext.request.contextPath}/servlet-administrador?acao=buscar&sub_acao=buscar_todos" class="nav-item active">
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
                    <a href="${pageContext.request.contextPath}/servlet-enderecos?acao=buscar&sub_acao=buscar_todos" class="nav-item">
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
                <h1>Gerenciar Administradores</h1>
                <p>Cadastre e consulte administradores do sistema</p>
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
                    <h2>Cadastrar Novo Administrador</h2>
                    <div class="crud-actions">
                        <a href="${pageContext.request.contextPath}/servlet-administrador?acao_principal=buscar&sub_acao=buscar_todos" class="btn btn-secondary">
                            <span>←</span> Voltar para Lista
                        </a>
                    </div>
                </div>

                <div class="form-container">
                    <%
                        String errorMessage = (String) request.getAttribute("errorMessage");

                        if (errorMessage != null) {
                    %>
                    <div class="error-notification">
                        <strong>Erro:</strong> <%= errorMessage %>
                    </div>
                    <%
                        }
                    %>
                    <form action="${pageContext.request.contextPath}/servlet-administrador" method="post">
                        <input type="hidden" name="acao_principal" value="inserir">
                        <input type="hidden" name="sub_acao" value="inserir">

                        <div class="form-row">
                            <div class="form-group">
                                <label for="cpf">CPF:</label>
                                <input type="text" name="cpf" id="cpf" class="form-control"
                                       value="${cpf != null ? cpf.replaceAll('[^0-9]', '') : ''}"
                                       required maxlength="11" oninput="validarCPF(this)"
                                       placeholder="Apenas números (ex: 12345678901)">
                            </div>

                            <div class="form-group">
                                <label for="telefone">Telefone:</label>
                                <input type="text" name="telefone" id="telefone" class="form-control"
                                       value="${telefone != null ? telefone.replaceAll('[^0-9]', '') : ''}"
                                       required maxlength="11" oninput="validarTelefone(this)"
                                       placeholder="Apenas números (ex: 11999999999)">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="nome">Nome Completo:</label>
                            <input type="text" name="nome" id="nome" class="form-control"
                                   value="${nome != null ? nome : ''}" required
                                   oninput="validarNome(this)" placeholder="Nome completo do administrador">
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="email">Email:</label>
                                <input type="text" name="email" id="email" class="form-control"
                                       value="${email != null ? email : ''}" required
                                       oninput="validarEmail(this)" placeholder="exemplo@email.com">
                            </div>

                            <div class="form-group">
                                <label for="senha">Senha:</label>
                                <input type="password" name="senha" id="senha" class="form-control"
                                       value="${senha != null ? senha : ''}" required
                                       oninput="validarSenha(this)"
                                       placeholder="Digite a senha">
                                <button type="button" class="password-toggle" onclick="toggleSenha()">
                                    <span id="senhaIcon">👁️</span>
                                </button>
                            </div>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">Cadastrar Administrador</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // Função para alternar a visibilidade da senha
    function toggleSenha() {
        const senhaInput = document.getElementById('senha');
        const senhaIcon = document.getElementById('senhaIcon');

        if (senhaInput.type === 'password') {
            senhaInput.type = 'text';
            senhaIcon.textContent = '🙈';
        } else {
            senhaInput.type = 'password';
            senhaIcon.textContent = '👁️';
        }
    }

    // Validações em tempo real
    function validarCPF(input) {
        // Remove TODOS os caracteres não numéricos
        let cpf = input.value.replace(/[^0-9]/g, '');

        // Atualiza o campo APENAS com números
        input.value = cpf;

        // Validação básica de CPF
        if (cpf.length > 0 && cpf.length !== 11) {
            input.setCustomValidity('CPF deve ter exatamente 11 dígitos numéricos.');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarTelefone(input) {
        // Remove TODOS os caracteres não numéricos
        let telefone = input.value.replace(/[^0-9]/g, '');

        // Atualiza o campo APENAS com números
        input.value = telefone;

        // Validação básica de telefone
        if (telefone.length > 0 && telefone.length < 10) {
            input.setCustomValidity('Telefone deve ter pelo menos 10 dígitos (com DDD).');
        } else if (telefone.length > 11) {
            input.setCustomValidity('Telefone deve ter no máximo 11 dígitos.');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarNome(input) {
        const nome = input.value.trim();
        // Permite letras, espaços e alguns caracteres especiais comuns em nomes
        const nomeRegex = /^[a-zA-ZÀ-ÿ\s']{2,100}$/;

        if (nome && !nomeRegex.test(nome)) {
            input.setCustomValidity('Nome deve conter entre 2 e 100 caracteres válidos (apenas letras e espaços).');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarEmail(input) {
        const email = input.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (email && !emailRegex.test(email)) {
            input.setCustomValidity('Email inválido. Deve conter @ e domínio válido.');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarSenha(input) {
        const senha = input.value;
        // Regex: mínimo 8 caracteres, pelo menos uma letra e um número
        const senhaRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

        if (senha && !senhaRegex.test(senha)) {
            input.setCustomValidity('Senha deve ter no mínimo 8 caracteres, incluindo pelo menos uma letra e um número.');
        } else {
            input.setCustomValidity('');
        }
    }

    // Validação no envio do formulário
    document.querySelector('form').addEventListener('submit', function(e) {
        const cpf = document.getElementById('cpf');
        const telefone = document.getElementById('telefone');
        const nome = document.getElementById('nome');
        const email = document.getElementById('email');
        const senha = document.getElementById('senha');

        // Valida CPF (exatamente 11 dígitos)
        const cpfNumeros = cpf.value.replace(/[^0-9]/g, '');
        if (cpfNumeros.length !== 11) {
            e.preventDefault();
            alert('CPF deve ter exatamente 11 dígitos numéricos (sem pontos ou traços).');
            cpf.focus();
            return;
        }

        // Valida Telefone (mínimo 10 dígitos)
        const telefoneNumeros = telefone.value.replace(/[^0-9]/g, '');
        if (telefoneNumeros.length < 10 || telefoneNumeros.length > 11) {
            e.preventDefault();
            alert('Telefone deve ter entre 10 e 11 dígitos (com DDD, sem parênteses, espaços ou traços).');
            telefone.focus();
            return;
        }

        // Valida Nome
        const nomeRegex = /^[a-zA-ZÀ-ÿ\s']{2,100}$/;
        if (!nomeRegex.test(nome.value.trim())) {
            e.preventDefault();
            alert('Nome deve conter entre 2 e 100 caracteres válidos (apenas letras e espaços).');
            nome.focus();
            return;
        }

        // Valida Email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email.value.trim())) {
            e.preventDefault();
            alert('Email inválido. Deve conter @ e domínio válido.');
            email.focus();
            return;
        }

        // Valida Senha
        const senhaRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
        if (!senhaRegex.test(senha.value)) {
            e.preventDefault();
            alert('Senha deve ter no mínimo 8 caracteres, incluindo pelo menos uma letra e um número.');
            senha.focus();
            return;
        }
    });
</script>
</body>
</html>