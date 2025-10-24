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
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/image/Group%2036941.png">
    <style>
        body {
            background: #022B3A;
            overflow: hidden;
            font-family: 'Open Sans', sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        .loading-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            width: 1000px;
            transform: scale(1.5);
            margin-top: -200px;
            user-select: none;
        }

        .loop-wrapper {
            position: relative;
            display: block;
            width: 100%;
            max-width: 600px;
            height: 250px;
            overflow: hidden;
            border-bottom: 3px solid #fff;
            color: #fff;
            margin-bottom: 20px;
        }

        .loading-text {
            color: #fff;
            font-size: 24px;
            font-weight: 600;
            text-align: center;
            margin-top: 20px;
        }

        /* --- Montanhas e colinas --- */
        .mountain {
            position: absolute;
            right: -900px;
            bottom: -20px;
            width: 2px;
            height: 2px;
            box-shadow:
                    0 0 0 50px #00546B,
                    60px 50px 0 70px #00546B,
                    90px 90px 0 50px #00546B,
                    250px 250px 0 50px #00546B,
                    290px 320px 0 50px #00546B,
                    320px 400px 0 50px #00546B;
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
            box-shadow:
                    0 0 0 50px #00546B,
                    -20px 0 0 20px #00546B,
                    -90px 0 0 50px #00546B,
                    250px 0 0 50px #00546B,
                    290px 0 0 50px #00546B,
                    620px 0 0 50px #00546B;
            animation: hill 4s 2s linear infinite;
        }

        /* --- Árvores --- */
        .tree,
        .tree:nth-child(2),
        .tree:nth-child(3) {
            position: absolute;
            height: 100px;
            width: 35px;
            bottom: 0;
            background: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/130015/tree.svg) no-repeat;
        }

        /* --- Pedras --- */
        .rock {
            margin-top: -17%;
            height: 2%;
            width: 2%;
            bottom: -2px;
            border-radius: 20px;
            position: absolute;
            background: #ddd;
        }

        /* --- Caminhão e rodas --- */
        .truck,
        .wheels {
            transition: all ease;
            width: 85px;
            margin-right: -60px;
            bottom: 0px;
            right: 50%;
            position: absolute;
            background: #eee;
        }

        .truck {
            background: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/130015/truck.svg) no-repeat;
            background-size: contain;
            height: 60px;
        }

        .truck:before {
            content: " ";
            position: absolute;
            width: 25px;
            box-shadow:
                    -30px 28px 0 1.5px #fff,
                    -35px 18px 0 1.5px #fff;
        }

        .wheels {
            background: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/130015/wheels.svg) no-repeat;
            height: 15px;
            margin-bottom: 0;
        }

        /* --- Animações individuais --- */
        .tree {
            animation: tree 3s 0.000s linear infinite;
        }

        .tree:nth-child(2) {
            animation: tree2 2s 0.150s linear infinite;
        }

        .tree:nth-child(3) {
            animation: tree3 8s 0.050s linear infinite;
        }

        .rock {
            animation: rock 4s -0.530s linear infinite;
        }

        .truck {
            animation: truck 4s 0.080s ease infinite;
        }

        .wheels {
            animation: truck 4s 0.001s ease infinite;
        }

        .truck:before {
            animation: wind 1.5s 0.000s ease infinite;
        }

        /* --- Keyframes --- */
        @keyframes tree {
            0% {
                transform: translate(1350px);
            }

            100% {
                transform: translate(-50px);
            }
        }

        @keyframes tree2 {
            0% {
                transform: translate(650px);
            }

            100% {
                transform: translate(-50px);
            }
        }

        @keyframes tree3 {
            0% {
                transform: translate(2750px);
            }

            100% {
                transform: translate(-50px);
            }
        }

        @keyframes rock {
            0% {
                right: -200px;
            }

            100% {
                right: 2000px;
            }
        }

        @keyframes truck {
            6% {
                transform: translateY(0px);
            }

            7% {
                transform: translateY(-6px);
            }

            9% {
                transform: translateY(0px);
            }

            10% {
                transform: translateY(-1px);
            }

            11% {
                transform: translateY(0px);
            }
        }

        @keyframes wind {
            50% {
                transform: translateY(3px)
            }
        }

        @keyframes mtn {
            100% {
                transform: translateX(-2000px) rotate(130deg);
            }
        }

        @keyframes hill {
            100% {
                transform: translateX(-2000px);
            }
        }

        /* --- Responsividade --- */
        @media (max-width: 768px) {
            .loop-wrapper {
                height: 200px;
                margin-bottom: 15px;
            }

            .loading-text {
                font-size: 20px;
            }
        }

        @media (max-width: 480px) {
            .loop-wrapper {
                height: 150px;
                margin-bottom: 10px;
            }

            .loading-text {
                font-size: 18px;
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
    // Redireciona após alguns segundos para o dashboard
    setTimeout(() => {
        window.location.href = "${pageContext.request.contextPath}/html/Restricted-area/Pages/Dashboard/dashboard.jsp";
    }, 500);
</script>
</body>

</html>