<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Se o usuário já está logado, faz logout automaticamente
    if (session != null && session.getAttribute("nomeAdimin") != null) {
        session.invalidate(); // Destrói a sessão atual
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Administrativo - Ei Truck</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/area-restrita/login.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/imagens/Group%2036941.png">
</head>
<body>
<div class="login-container">
    <div class="login-header">
        <h1>Área Administrativa</h1>
        <p>Ei Truck - Acesso Restrito</p>
    </div>

    <div class="login-form">
        <%
            String erro = (String) request.getAttribute("erroLogin");
            if (erro != null) {
                out.print("<script>");
                out.print("window.addEventListener('DOMContentLoaded', () => {");
                out.print("const notification = document.createElement('div');");
                out.print("notification.className = 'error-toast';");
                out.print("notification.textContent = '" + erro.replace("'", "\\'") + "';");
                out.print("document.body.appendChild(notification);");
                out.print("setTimeout(() => notification.classList.add('show'), 100);");
                out.print("setTimeout(() => { notification.classList.remove('show'); setTimeout(() => notification.remove(), 500); }, 4000);");
                out.print("});");
                out.print("</script>");
            }
        %>

        <!-- Formulário envia via POST para o servlet /login -->
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label for="email">E-mail:</label>
                <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required
                       value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
            </div>

            <div class="form-group">
                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required>
            </div>

            <div class="show-password">
                <input type="checkbox" id="mostrarSenha">
                <label for="mostrarSenha">Mostrar senha</label>
            </div>

            <button type="submit" class="login-btn">Entrar</button>
        </form>
    </div>
</div>

<div class="back-link">
    <a href="${pageContext.request.contextPath}/index.html">Voltar</a>
</div>

<script>
    // Função para resetar o botão de login
    function resetLoginButton() {
        const btn = document.querySelector('.login-btn');
        if (btn) {
            btn.textContent = 'Entrar';
            btn.classList.remove('login-btn-autentication');
            btn.disabled = false;
        }
    }

    // Resetar o botão quando a página for carregada
    document.addEventListener('DOMContentLoaded', resetLoginButton);

    // Resetar o botão quando a página for mostrada (incluindo quando volta do cache)
    window.addEventListener('pageshow', function(event) {
        if (event.persisted) {
            resetLoginButton();
        }
    });

    // Mostrar/ocultar senha
    document.getElementById("mostrarSenha").addEventListener("change", function () {
        const senhaInput = document.getElementById("senha");
        senhaInput.type = this.checked ? "text" : "password";
    });

    // Efeito visual de autenticação no botão
    document.getElementById('loginForm').addEventListener('submit', function () {
        const btn = document.querySelector('.login-btn');
        btn.textContent = 'Autenticando...';
        btn.classList.add('login-btn-autentication');
        btn.disabled = true;
    });
</script>
</body>
</html>