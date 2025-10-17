<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Administrativo - Ei Truck</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleCss/Restricted-area/login.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/image/Group%2036941.png">
</head>
<body>
<div class="login-container">
    <div class="login-header">
        <h1>Área Administrativa</h1>
        <p>Ei Truck - Acesso Restrito</p>
    </div>

    <div class="login-form">
        <!-- Mensagem de erro vinda do servlet -->
        <%
            String erro = (String) request.getAttribute("erroLogin");
            if (erro != null) {
        %>
        <div id="errorMessage" class="error-message" style="display:block;"><%= erro %></div>
        <%
        } else {
        %>
        <div id="errorMessage" class="error-message" style="display:none;"></div>
        <%
            }
        %>

        <!-- Formulário envia via POST para o servlet /login -->
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label for="email">E-mail:</label>
                <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required>
            </div>

            <div class="form-group">
                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required>
            </div>

            <div class="show-password">
                <input type="checkbox" id="mostrarSenha" style="cursor: pointer;">
                <label for="mostrarSenha">Mostrar senha</label>
            </div>

            <button type="submit" class="login-btn">Entrar</button>
        </form>
    </div>
</div>

<div class="back-link">
    <a href="${pageContext.request.contextPath}/Landing-page/index.html">Voltar</a>
</div>

<script>
    // Mostrar/ocultar senha
    document.getElementById("mostrarSenha").addEventListener("change", function () {
        const senhaInput = document.getElementById("senha");
        senhaInput.type = this.checked ? "text" : "password";
    });

    // Efeito visual de autenticação no botão (sem bloquear o envio)
    document.getElementById('loginForm').addEventListener('submit', function () {
        const btn = document.querySelector('.login-btn');
        btn.textContent = 'Autenticando...';
        btn.classList.add('login-btn-autentication');
        btn.disabled = true;
    });

    // Ao voltar à página (ex: depois de erro), restaura o botão
    window.addEventListener('pageshow', function () {
        const btn = document.querySelector('.login-btn');
        if (btn) {
            btn.textContent = 'Entrar';
            btn.classList.remove('login-btn-autentication');
            btn.disabled = false;
        }
    });
</script>
</body>
</html>
