<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.TipoOcorrencia" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<TipoOcorrencia> ocorrencias = (List<TipoOcorrencia>) request.getAttribute("ocorrencias");
    String errorMessage = (String) request.getAttribute("errorMessage");

    // Capturar e decodificar o erro da URL
    String errorParam = request.getParameter("error");
    if (errorParam != null) {
        errorParam = URLDecoder.decode(errorParam, "UTF-8");
    }

    // Parâmetros do filtro
    String filtroId = request.getParameter("filtro_id");
    String filtroTipoEvento = request.getParameter("filtro_tipo_evento");
    String filtroPontuacao = request.getParameter("filtro_pontuacao");
    String filtroGravidade = request.getParameter("filtro_gravidade");

    // Verificar se há algum filtro ativo
    boolean algumFiltro = (filtroId != null && !filtroId.isEmpty()) ||
            (filtroTipoEvento != null && !filtroTipoEvento.isEmpty()) ||
            (filtroPontuacao != null && !filtroPontuacao.isEmpty()) ||
            (filtroGravidade != null && !filtroGravidade.isEmpty());
%>
<html>
<head>
    <title>Buscar todos</title>
    <style>
        .error-notification {
            background-color: #ffebee;
            border: 1px solid #f44336;
            color: #c62828;
            padding: 12px 16px;
            border-radius: 4px;
            margin: 10px 0;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .error-notification button {
            background: none;
            border: none;
            color: #c62828;
            font-size: 18px;
            cursor: pointer;
        }

        .error-notification button:hover {
            color: #b71c1c;
        }

        .filtro-container {
            margin: 20px 0;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
            border: 1px solid #dee2e6;
        }

        .filtro-item {
            display: flex;
            flex-direction: column;
        }

        .filtro-item label {
            font-weight: bold;
            margin-bottom: 5px;
            font-size: 12px;
        }

        .filtro-input {
            padding: 6px;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .filtro-linha {
            display: flex;
            gap: 10px;
            align-items: end;
            flex-wrap: wrap;
            margin-bottom: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table th, table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
            font-size: 14px;
        }

        table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        table tr:hover {
            background-color: #f5f5f5;
        }

        button {
            padding: 6px 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        button:hover {
            background-color: #0056b3;
        }

        .btn-limpar {
            background-color: #6c757d;
        }

        .btn-limpar:hover {
            background-color: #545b62;
        }

        .btn-excluir {
            background-color: #dc3545;
        }

        .btn-excluir:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) {
%>

<button onclick="window.location.href='html/Restricted-area/Pages/Occurrences/occurrences.jsp'">Adicionar Tipo de Ocorrência</button>

<%-- Notificação de erro --%>
<% if (errorMessage != null || errorParam != null) { %>
<div class="error-notification" id="errorNotification">
    <span>Erro: <%= errorMessage != null ? errorMessage : errorParam %></span>
    <button onclick="document.getElementById('errorNotification').style.display='none'">×</button>
</div>

<script>
    // Opcional: remover automaticamente após 5 segundos
    setTimeout(function() {
        var notification = document.getElementById('errorNotification');
        if (notification) {
            notification.style.display = 'none';
        }
    }, 5000);
</script>
<% } %>

<h1>Exibindo todos os Tipos de Ocorrências</h1>

<%-- Formulário de Filtro com Múltiplos Campos --%>
<div class="filtro-container">
    <form action="${pageContext.request.contextPath}/servlet-ocorrencias" method="get" style="margin: 20px 0;">
        <input type="hidden" name="acao" value="filtrar">
        <input type="hidden" name="sub_acao" value="buscar_todos">

        <div class="filtro-linha">
            <div class="filtro-item">
                <label for="filtroId">ID:</label>
                <input type="text" id="filtroId" name="filtro_id" class="filtro-input"
                       value="<%= filtroId != null ? filtroId : "" %>"
                       placeholder="Ex: 1, 2"
                       style="width: 80px;">
            </div>

            <div class="filtro-item">
                <label for="filtroTipoEvento">Tipo de Evento:</label>
                <input type="text" id="filtroTipoEvento" name="filtro_tipo_evento" class="filtro-input"
                       value="<%= filtroTipoEvento != null ? filtroTipoEvento : "" %>"
                       placeholder="Ex: Colisão"
                       style="width: 200px;">
            </div>

            <div class="filtro-item">
                <label for="filtroPontuacao">Pontuação:</label>
                <input type="text" id="filtroPontuacao" name="filtro_pontuacao" class="filtro-input"
                       value="<%= filtroPontuacao != null ? filtroPontuacao : "" %>"
                       placeholder="Ex: 10, 20"
                       style="width: 120px;">
            </div>

            <div class="filtro-item">
                <label for="filtroGravidade">Gravidade:</label>
                <input type="text" id="filtroGravidade" name="filtro_gravidade" class="filtro-input"
                       value="<%= filtroGravidade != null ? filtroGravidade : "" %>"
                       placeholder="Ex: Alta, Média"
                       style="width: 120px;">
            </div>

            <div class="filtro-item" style="display: flex; gap: 10px; margin-left: 20px;">
                <button type="submit"
                        style="padding: 8px 20px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold;">
                    Aplicar Filtro
                </button>

                <button type="button" onclick="limparFiltro()"
                        style="padding: 8px 20px; background-color: #6c757d; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold;">
                    Limpar
                </button>
            </div>
        </div>

        <div style="margin-top: 10px; font-size: 12px; color: #666;">
            <em>Preencha um ou mais campos para filtrar. Os filtros são combinados (AND).</em>
        </div>
    </form>

    <div style="margin-top: 5px; font-size: 14px; color: #333;">
        <strong>
            <%
                if (ocorrencias != null) {
                    if (algumFiltro) {
                        out.print("Registros filtrados: " + ocorrencias.size());

                        // Mostra os filtros ativos
                        out.print(" | Filtros: ");
                        List<String> filtrosAtivos = new ArrayList<>();
                        if (filtroId != null && !filtroId.isEmpty()) filtrosAtivos.add("ID: " + filtroId);
                        if (filtroTipoEvento != null && !filtroTipoEvento.isEmpty()) filtrosAtivos.add("Tipo Evento: " + filtroTipoEvento);
                        if (filtroPontuacao != null && !filtroPontuacao.isEmpty()) filtrosAtivos.add("Pontuação: " + filtroPontuacao);
                        if (filtroGravidade != null && !filtroGravidade.isEmpty()) filtrosAtivos.add("Gravidade: " + filtroGravidade);
                        out.print(String.join(", ", filtrosAtivos));
                    } else {
                        out.print("Total de registros: " + ocorrencias.size());
                    }
                }
            %>
        </strong>
    </div>
</div>

<table>
    <thead>
    <tr>
        <th><strong>ID</strong></th>
        <th><strong>TIPO DE EVENTO</strong></th>
        <th><strong>PONTUAÇÃO</strong></th>
        <th><strong>GRAVIDADE</strong></th>
        <th><strong>AÇÕES</strong></th>
    </tr>
    </thead>

    <tbody>
    <%
        if (ocorrencias != null && !ocorrencias.isEmpty()) {
            for (int i=0; i < ocorrencias.size(); i++){
    %>
    <tr>
        <td><%=ocorrencias.get(i).getId()%></td>
        <td><%=ocorrencias.get(i).getTipoEvento()%></td>
        <td><%=ocorrencias.get(i).getPontuacao()%></td>
        <td><%=ocorrencias.get(i).getGravidade()%></td>
        <td>
            <form action="${pageContext.request.contextPath}/servlet-ocorrencias" method="post" style="display:inline;">
                <input type="hidden" name="acao_principal" value="excluir">
                <input type="hidden" name="id" value="<%= ocorrencias.get(i).getId() %>">
                <button type="submit" class="btn-excluir" onclick="return confirm('Tem certeza que deseja excluir este tipo de ocorrência?')">Excluir</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5">
            <% if (algumFiltro) { %>
            Nenhum tipo de ocorrência encontrado com os filtros aplicados.
            <% } else { %>
            Nenhum tipo de ocorrência encontrado ou erro ao carregar dados.
            <% } %>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<script>
    function limparFiltro() {
        window.location.href = '${pageContext.request.contextPath}/servlet-ocorrencias?acao=buscar&sub_acao=buscar_todos';
    }

    // Opcional: enviar form ao pressionar Enter em qualquer campo
    document.addEventListener('DOMContentLoaded', function() {
        const inputs = document.querySelectorAll('input[type="text"]');
        inputs.forEach(input => {
            input.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    document.querySelector('button[type="submit"]').click();
                }
            });
        });
    });
</script>
<%
    }
%>
</body>
</html>