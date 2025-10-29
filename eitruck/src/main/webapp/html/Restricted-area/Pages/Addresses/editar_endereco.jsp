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
    <title>Editar Endereço - Ei Truck</title>
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
                <h1>Editar Endereço</h1>
                <p>Atualize os dados do endereço</p>
            </div>
            <div class="header-right">
                <div class="user-info">
                    <span>Administrador</span>
                </div>
            </div>
        </header>

        <div class="page-content">
            <!-- Formulário de Edição -->
            <div class="crud-section">
                <div class="crud-header">
                    <h2>Editar Endereço</h2>
                    <div class="crud-actions">
                        <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos'" class="btn btn-secondary">
                            <span>←</span> Voltar para Lista
                        </button>
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

                    <form action="${pageContext.request.contextPath}/servlet-enderecos" method="post">
                        <input type="hidden" name="acao_principal" value="atualizar">
                        <input type="hidden" name="id" value="${endereco.id}">

                        <div class="form-row">
                            <div class="form-group">
                                <label for="cep">CEP:</label>
                                <input type="text" name="cep" id="cep" class="form-control"
                                       value="${endereco.cep != null ? endereco.cep.replaceAll('[^0-9]', '') : ''}"
                                       required maxlength="8" oninput="validarCEP(this)"
                                       placeholder="Apenas números (ex: 12345678)">
                            </div>

                            <div class="form-group">
                                <label for="rua">Rua:</label>
                                <input type="text" name="rua" id="rua" class="form-control"
                                       value="${endereco.rua != null ? endereco.rua : ''}" required
                                       oninput="validarRua(this)" placeholder="Nome da rua">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="numero">Número:</label>
                                <input type="number" name="numero" id="numero" class="form-control"
                                       value="${endereco.numero != null ? endereco.numero : ''}" required
                                       oninput="validarNumero(this)" placeholder="Número do endereço"
                                       min="1" max="99999">
                            </div>

                            <div class="form-group">
                                <label for="bairro">Bairro:</label>
                                <input type="text" name="bairro" id="bairro" class="form-control"
                                       value="${endereco.bairro != null ? endereco.bairro : ''}" required
                                       oninput="validarBairro(this)" placeholder="Nome do bairro">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="cidade">Cidade:</label>
                                <input type="text" name="cidade" id="cidade" class="form-control"
                                       value="${endereco.cidade != null ? endereco.cidade : ''}" required
                                       oninput="validarCidade(this)" placeholder="Nome da cidade">
                            </div>

                            <div class="form-group">
                                <label for="estado">Estado (UF):</label>
                                <input type="text" name="estado" id="estado" class="form-control"
                                       value="${endereco.estado != null ? endereco.estado : ''}" required
                                       maxlength="2" oninput="validarEstado(this)"
                                       placeholder="UF (ex: SP, RJ)">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="pais">País:</label>
                            <input type="text" name="pais" id="pais" class="form-control"
                                   value="${endereco.pais != null ? endereco.pais : ''}" required
                                   oninput="validarPais(this)" placeholder="Nome do país">
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-success">Atualizar Endereço</button>
                            <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos'"
                                    class="btn btn-secondary">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function validarCEP(input) {
        // Remove TODOS os caracteres não numéricos
        let cep = input.value.replace(/[^0-9]/g, '');

        // Atualiza o campo APENAS com números
        input.value = cep;

        // Validação do formato: exatamente 8 dígitos
        const cepRegex = /^\d{8}$/;

        if (cep.length > 0 && !cepRegex.test(cep)) {
            input.setCustomValidity('CEP deve ter exatamente 8 dígitos numéricos.');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarRua(input) {
        const rua = input.value.trim();
        // Permite letras, números, espaços e alguns caracteres especiais comuns em endereços
        const ruaRegex = /^[a-zA-ZÀ-ÿ0-9\s.,'-]{2,100}$/;

        if (rua && !ruaRegex.test(rua)) {
            input.setCustomValidity('Nome da rua deve conter entre 2 e 100 caracteres válidos.');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarNumero(input) {
        const numero = input.value;
        // Valida que é um número positivo e dentro de um range razoável
        if (numero && (numero < 1 || numero > 99999)) {
            input.setCustomValidity('Número deve estar entre 1 e 99999.');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarBairro(input) {
        const bairro = input.value.trim();
        const bairroRegex = /^[a-zA-ZÀ-ÿ0-9\s.,'-]{2,50}$/;

        if (bairro && !bairroRegex.test(bairro)) {
            input.setCustomValidity('Nome do bairro deve conter entre 2 e 50 caracteres válidos.');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarCidade(input) {
        const cidade = input.value.trim();
        const cidadeRegex = /^[a-zA-ZÀ-ÿ\s.'-]{2,50}$/;

        if (cidade && !cidadeRegex.test(cidade)) {
            input.setCustomValidity('Nome da cidade deve conter entre 2 e 50 caracteres válidos (apenas letras).');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarEstado(input) {
        const estado = input.value.toUpperCase().trim();
        // Atualiza o valor para maiúsculas
        input.value = estado;

        // Lista de UFs válidas do Brasil
        const ufsValidas = ['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'];

        if (estado && !ufsValidas.includes(estado)) {
            input.setCustomValidity('UF inválida. Use uma sigla de estado válida do Brasil.');
        } else {
            input.setCustomValidity('');
        }
    }

    function validarPais(input) {
        const pais = input.value.trim();
        const paisRegex = /^[a-zA-ZÀ-ÿ\s.'-]{2,50}$/;

        if (pais && !paisRegex.test(pais)) {
            input.setCustomValidity('Nome do país deve conter entre 2 e 50 caracteres válidos (apenas letras).');
        } else {
            input.setCustomValidity('');
        }
    }

    // Validação no envio do formulário
    document.querySelector('form').addEventListener('submit', function(e) {
        const cep = document.getElementById('cep');
        const rua = document.getElementById('rua');
        const numero = document.getElementById('numero');
        const bairro = document.getElementById('bairro');
        const cidade = document.getElementById('cidade');
        const estado = document.getElementById('estado');
        const pais = document.getElementById('pais');

        // Valida CEP (exatamente 8 dígitos)
        const cepNumeros = cep.value.replace(/[^0-9]/g, '');
        const cepRegex = /^\d{8}$/;
        if (!cepRegex.test(cepNumeros)) {
            e.preventDefault();
            alert('CEP deve ter exatamente 8 dígitos numéricos.');
            cep.focus();
            return;
        }

        // Valida Rua
        const ruaRegex = /^[a-zA-ZÀ-ÿ0-9\s.,'-]{2,100}$/;
        if (!ruaRegex.test(rua.value.trim())) {
            e.preventDefault();
            alert('Nome da rua deve conter entre 2 e 100 caracteres válidos.');
            rua.focus();
            return;
        }

        // Valida Número
        if (!numero.value || numero.value < 1 || numero.value > 99999) {
            e.preventDefault();
            alert('Número deve estar entre 1 e 99999.');
            numero.focus();
            return;
        }

        // Valida Bairro
        const bairroRegex = /^[a-zA-ZÀ-ÿ0-9\s.,'-]{2,50}$/;
        if (!bairroRegex.test(bairro.value.trim())) {
            e.preventDefault();
            alert('Nome do bairro deve conter entre 2 e 50 caracteres válidos.');
            bairro.focus();
            return;
        }

        // Valida Cidade
        const cidadeRegex = /^[a-zA-ZÀ-ÿ\s.'-]{2,50}$/;
        if (!cidadeRegex.test(cidade.value.trim())) {
            e.preventDefault();
            alert('Nome da cidade deve conter entre 2 e 50 caracteres válidos (apenas letras).');
            cidade.focus();
            return;
        }

        // Valida Estado (UF)
        const ufsValidas = ['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'];
        if (!ufsValidas.includes(estado.value.toUpperCase().trim())) {
            e.preventDefault();
            alert('UF inválida. Use uma sigla de estado válida do Brasil.');
            estado.focus();
            return;
        }

        // Valida País
        const paisRegex = /^[a-zA-ZÀ-ÿ\s.'-]{2,50}$/;
        if (!paisRegex.test(pais.value.trim())) {
            e.preventDefault();
            alert('Nome do país deve conter entre 2 e 50 caracteres válidos (apenas letras).');
            pais.focus();
            return;
        }
    });
</script>
</body>
</html>