<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.example.eitruck.Dao.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Ei Truck</title>
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
            border: none;
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

        /* Stats Grid */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-bottom: 30px;
            margin-top: 75px;
        }

        .stat-card {
            background: white;
            padding: 50px 25px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            display: flex;
            align-items: center;
            transition: transform 0.3s ease;
            cursor: pointer;
            border: none;
            width: 100%;
            text-align: left;
            font-family: inherit;
        }

        .stat-card:hover {
            transform: translateY(-5px);
        }

        .stat-icon {
            font-size: 40px;
            margin-right: 20px;
        }

        .stat-info h3 {
            color: #666;
            font-size: 14px;
            margin-bottom: 5px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .stat-number {
            font-size: 32px;
            font-weight: bold;
            color: var(--brand-blue);
        }

        .card-form {
            display: contents;
        }

        /* Responsividade */
        @media (max-width: 1200px) {
            .stats-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

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

            .stats-grid {
                grid-template-columns: 1fr;
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
<%
    // Inicializar os DAOs e obter as contagens
    AdministradorDAO administradorDAO = new AdministradorDAO();
    AnalistaDAO analistaDAO = new AnalistaDAO();
    SegmentoDAO segmentoDAO = new SegmentoDAO();
    UnidadeDAO unidadeDAO = new UnidadeDAO();
    EnderecoDAO enderecoDAO = new EnderecoDAO();
    TipoOcorrenciaDAO tipoOcorrenciaDAO = new TipoOcorrenciaDAO();

    int totalAdministradores = administradorDAO.numeroRegistros();
    int totalAnalistas = analistaDAO.numeroRegistros();
    int totalSegmentos = segmentoDAO.numeroRegistros();
    int totalUnidades = unidadeDAO.numeroRegistros();
    int totalEnderecos = enderecoDAO.numeroRegistros();
    int totalOcorrencias = tipoOcorrenciaDAO.numeroRegistros();
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
                <li><a href="../Dashboard/dashboard.jsp" class="nav-item active"><span>📊</span> Dashboard</a></li>

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
                    <a href="${pageContext.request.contextPath}/servlet-enderecos?acao=buscar&sub_acao=buscar_todos" class="nav-item">
                        <span>📍</span> Gerenciar Endereços
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/servlet-ocorrencias?acao=buscar&sub_acao=buscar_todos" class="nav-item">
                        <span>⚠️</span> Gerenciar Tipos de Ocorrência
                    </a>
                </li>

                <li><a href="../../../../login.jsp" class="nav-item logout"><span>🚪</span> Sair</a></li>
            </ul>
        </nav>
    </div>

    <!-- Conteúdo Principal -->
    <div class="main-content">
        <header class="content-header">
            <div class="header-left">
                <h1>Dashboard</h1>
                <p>Bem-vindo ao painel administrativo</p>
            </div>
            <div class="header-right">
                <div class="user-info">
                    <span>Administrador</span>
                </div>
            </div>
        </header>

        <div class="page-content">
            <!-- Dashboard Content -->
            <div class="stats-grid top-row">
                <!-- Administradores -->
                <form action="${pageContext.request.contextPath}/servlet-administrador" method="get" class="card-form">
                    <input type="hidden" name="acao_principal" value="buscar">
                    <input type="hidden" name="sub_acao" value="buscar_todos">
                    <button type="submit" class="stat-card">
                        <div class="stat-icon">👨‍💼</div>
                        <div class="stat-info">
                            <h3>Administradores</h3>
                            <span class="stat-number" id="total-administradores">
                                <%= totalAdministradores >= 0 ? totalAdministradores : "Erro" %>
                            </span>
                        </div>
                    </button>
                </form>

                <!-- Analistas -->
                <form action="${pageContext.request.contextPath}/servlet-analista" method="get" class="card-form">
                    <input type="hidden" name="acao_principal" value="buscar">
                    <input type="hidden" name="sub_acao" value="buscar_todos">
                    <button type="submit" class="stat-card">
                        <div class="stat-icon">👥</div>
                        <div class="stat-info">
                            <h3>Analistas</h3>
                            <span class="stat-number" id="total-analistas">
                                <%= totalAnalistas >= 0 ? totalAnalistas : "Erro" %>
                            </span>
                        </div>
                    </button>
                </form>

                <!-- Segmentos -->
                <form action="${pageContext.request.contextPath}/servlet-segmentos" method="get" class="card-form">
                    <input type="hidden" name="acao_principal" value="buscar">
                    <input type="hidden" name="sub_acao" value="buscar_todos">
                    <button type="submit" class="stat-card">
                        <div class="stat-icon">📁</div>
                        <div class="stat-info">
                            <h3>Segmentos</h3>
                            <span class="stat-number" id="total-segmentos">
                                <%= totalSegmentos >= 0 ? totalSegmentos : "Erro" %>
                            </span>
                        </div>
                    </button>
                </form>
            </div>

            <div class="stats-grid bottom-row">
                <!-- Unidades -->
                <form action="${pageContext.request.contextPath}/servlet-unidade" method="get" class="card-form">
                    <input type="hidden" name="acao_principal" value="buscar">
                    <input type="hidden" name="sub_acao" value="buscar_todos">
                    <button type="submit" class="stat-card">
                        <div class="stat-icon">🏢</div>
                        <div class="stat-info">
                            <h3>Unidades</h3>
                            <span class="stat-number" id="total-unidades">
                                <%= totalUnidades >= 0 ? totalUnidades : "Erro" %>
                            </span>
                        </div>
                    </button>
                </form>

                <!-- Endereços -->
                <form action="${pageContext.request.contextPath}/servlet-enderecos" method="get" class="card-form">
                    <input type="hidden" name="acao_principal" value="buscar">
                    <input type="hidden" name="sub_acao" value="buscar_todos">
                    <button type="submit" class="stat-card">
                        <div class="stat-icon">📍</div>
                        <div class="stat-info">
                            <h3>Endereços</h3>
                            <span class="stat-number" id="total-enderecos">
                                <%= totalEnderecos >= 0 ? totalEnderecos : "Erro" %>
                            </span>
                        </div>
                    </button>
                </form>

                <!-- Tipos de Ocorrência -->
                <form action="${pageContext.request.contextPath}/servlet-ocorrencias" method="get" class="card-form">
                    <input type="hidden" name="acao_principal" value="buscar">
                    <input type="hidden" name="sub_acao" value="buscar_todos">
                    <button type="submit" class="stat-card">
                        <div class="stat-icon">⚠️</div>
                        <div class="stat-info">
                            <h3>Tipos de Ocorrência</h3>
                            <span class="stat-number" id="total-ocorrencias">
                                <%= totalOcorrencias >= 0 ? totalOcorrencias : "Erro" %>
                            </span>
                        </div>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    // Adiciona tooltips para os cards
    document.addEventListener('DOMContentLoaded', function() {
        const cards = document.querySelectorAll('.stat-card');
        cards.forEach(card => {
            card.title = 'Clique para gerenciar';
        });
    });
</script>
</body>
</html>