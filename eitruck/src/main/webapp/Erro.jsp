<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erro - Ei Truck</title>
    <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/image/Group%2036941.png">
    <style>
        :root {
            --brand-blue: #022B3A;
            --brand-blue-2: #00546B;
            --brand-green: #00b377;
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
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            line-height: 1.6;
        }

        .error-container {
            max-width: 600px;
            width: 90%;
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            overflow: hidden;
            text-align: center;
        }

        .error-header {
            background: var(--brand-blue);
            color: white;
            padding: 40px 30px;
        }

        .error-header h1 {
            font-size: 36px;
            margin-bottom: 10px;
        }

        .error-header p {
            opacity: 0.9;
            font-size: 18px;
        }

        .error-content {
            padding: 40px 30px;
        }

        .error-icon {
            font-size: 80px;
            margin-bottom: 20px;
        }

        .error-code {
            color: var(--brand-blue);
            font-size: 24px;
            font-weight: 600;
            margin-bottom: 15px;
        }

        .error-message {
            font-size: 18px;
            color: #555;
            margin-bottom: 30px;
        }

        .error-details {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 20px;
            margin: 25px 0;
            text-align: left;
            border-left: 4px solid var(--brand-green);
        }

        .error-details h3 {
            color: var(--brand-blue);
            margin-bottom: 10px;
        }

        .error-details p {
            color: #666;
            font-size: 14px;
        }

        .error-actions {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
            margin-top: 30px;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            font-size: 16px;
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

        .error-footer {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 1px solid #eaeaea;
            color: #888;
            font-size: 14px;
        }

        .error-footer a {
            color: var(--brand-blue);
            text-decoration: none;
        }

        .error-footer a:hover {
            text-decoration: underline;
        }

        /* Responsividade */
        @media (max-width: 768px) {
            .error-header h1 {
                font-size: 28px;
            }

            .error-header p {
                font-size: 16px;
            }

            .error-content {
                padding: 30px 20px;
            }

            .error-actions {
                flex-direction: column;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }

        @media (max-width: 480px) {
            body {
                padding: 20px;
            }

            .error-container {
                width: 100%;
            }

            .error-header {
                padding: 30px 20px;
            }
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="error-header">
        <h1>Ei Truck</h1>
        <p>Painel Administrativo</p>
    </div>

    <div class="error-content">
        <div class="error-icon">⚠️</div>
        <div class="error-code">Erro 500 - Internal Server Error</div>
        <div class="error-message">
            Ocorreu um erro inesperado no servidor.
            Nossa equipe já foi notificada e está trabalhando para resolver o problema.
        </div>

        <div class="error-details">
            <h3>O que você pode fazer:</h3>
            <p>
                • Tente novamente em alguns minutos<br>
                • Verifique sua conexão com a internet<br>
                • Entre em contato com o suporte técnico se o problema persistir
            </p>
        </div>

        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-secondary">
                <span>🔐</span> Fazer Login Novamente
            </a>
        </div>

        <div class="error-footer">
            <p>Se precisar de ajuda, entre em contato com nosso <a href="mailto:suporte@eitruck.com">suporte técnico</a>.</p>
        </div>
    </div>
</div>
</body>
</html>