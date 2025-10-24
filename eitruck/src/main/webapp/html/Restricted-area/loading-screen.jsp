<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Verifica se o usuário está logado
    if (session == null || session.getAttribute("nomeAdimin") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tela de Carregamento - Ei Truck</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleCss/Restricted-area/loading-screen.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/image/Group%2036941.png">
    <style>
        :root {
            --brand-blue: #022B3A;
            --brand-green: #00b377;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: var(--brand-blue);
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            overflow: hidden;
        }

        .loading-container {
            text-align: center;
            position: relative;
        }

        .loading-text {
            margin-top: 30px;
            font-size: 20px;
            font-weight: 600;
            color: white;
            animation: pulse 1.5s ease-in-out infinite;
        }

        @keyframes pulse {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.7; }
        }

        /* Animação do caminhão */
        .loop-wrapper {
            margin: 0 auto;
            position: relative;
            display: block;
            width: 600px;
            height: 250px;
            overflow: hidden;
            border-bottom: 3px solid #fff;
            perspective: 1000px;
        }

        .mountain {
            position: absolute;
            right: -900px;
            bottom: -20px;
            width: 2px;
            height: 2px;
            box-shadow:
                    0 0 0 50px var(--brand-green),
                    60px 50px 0 70px var(--brand-green),
                    90px 90px 0 50px var(--brand-green),
                    250px 250px 0 50px var(--brand-green),
                    290px 320px 0 50px var(--brand-green),
                    320px 400px 0 50px var(--brand-green);
            transform: rotate(130deg);
            animation: mtn 20s linear infinite;
        }

        .hill {
            position: absolute;
            right: -900px;
            bottom: -50px;
            width: 400px;
            border-radius: 50%;
            height: 20px;
            background: var(--brand-green);
            animation: hill 4s 2s linear infinite;
        }

        .tree, .tree:nth-child(2), .tree:nth-child(3) {
            position: absolute;
            height: 100px;
            width: 35px;
            bottom: 0;
            background: var(--brand-green);
            border-radius: 10px 10px 0 0;
        }

        .tree:nth-child(2) {
            height: 70px;
            width: 25px;
        }

        .tree:nth-child(3) {
            height: 120px;
            width: 40px;
        }

        .rock {
            margin-top: -17%;
            height: 2%;
            width: 2%;
            bottom: -2px;
            border-radius: 20px;
            position: absolute;
            background: var(--brand-green);
        }

        .truck, .wheels {
            transition: all ease;
            width: 85px;
            margin-right: -60px;
            bottom: 0;
            right: 50%;
            position: absolute;
            background: var(--brand-green);
        }

        .truck {
            height: 60px;
            border-radius: 15px 15px 0 0;
            animation: truck 10s linear infinite;
        }

        .truck:before {
            content: " ";
            position: absolute;
            width: 25px;
            box-shadow:
                    -30px 0px 0 -3px var(--brand-green),
                    -35px 8px 0 -3px var(--brand-green);
        }

        .wheels {
            background: #022B3A;
            border-radius: 50%;
            margin-left: -40px;
            height: 35px;
            bottom: -10px;
            transform: scaleX(0.8);
            animation: wheels 10s linear infinite;
        }

        .wheels:after {
            content: " ";
            position: absolute;
            background: #022B3A;
            width: 35px;
            height: 35px;
            border-radius: 50%;
            bottom: 0;
            left: 55px;
        }

        @keyframes mtn {
            100% { transform: translateX(-2000px) rotate(130deg); }
        }

        @keyframes hill {
            100% { transform: translateX(-2000px); }
        }

        @keyframes truck {
            0% { transform: translateX(200px); }
            100% { transform: translateX(-2000px); }
        }

        @keyframes wheels {
            0% { transform: translateX(200px) scaleX(0.8); }
            100% { transform: translateX(-2000px) scaleX(0.8); }
        }

        .tree { animation: tree 8s 0.000s linear infinite; }
        .tree:nth-child(2) { animation: tree 8s 0.150s linear infinite; }
        .tree:nth-child(3) { animation: tree 8s 0.300s linear infinite; }
        .rock { animation: rock 8s 0.000s linear infinite; }

        @keyframes tree {
            0% { transform: translateX(200px); }
            100% { transform: translateX(-2000px); }
        }

        @keyframes rock {
            0% { transform: translateX(200px); }
            100% { transform: translateX(-2000px); }
        }

        /* Responsividade */
        @media (max-width: 768px) {
            .loop-wrapper {
                width: 400px;
                height: 200px;
            }

            .loading-text {
                font-size: 18px;
            }
        }

        @media (max-width: 480px) {
            .loop-wrapper {
                width: 300px;
                height: 150px;
            }

            .loading-text {
                font-size: 16px;
            }
        }
    </style>
</head>

<body>
<div class="loading-container">
    <div class="loop-wrapper">
        <div class="mountain"></div>
        <div class="hill"></div>
        <div class="tree"></div>
        <div class="tree"></div>
        <div class="tree"></div>
        <div class="rock"></div>
        <div class="truck"></div>
        <div class="wheels"></div>
    </div>
    <div class="loading-text">Carregando...</div>
</div>

<script>
    // Redireciona após 2 segundos para o dashboard
    setTimeout(() => {
        window.location.href = "${pageContext.request.contextPath}/html/Restricted-area/Pages/Dashboard/dashboard.jsp";
    }, 2000);
</script>
</body>

</html>