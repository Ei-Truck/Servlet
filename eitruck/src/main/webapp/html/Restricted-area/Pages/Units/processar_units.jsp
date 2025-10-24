<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Unidade" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Unidade> unidades = (List<Unidade>) request.getAttribute("unidades");
    String errorMessage = (String) request.getAttribute("errorMessage");

    // Capturar e decodificar o erro da URL
    String errorParam = request.getParameter("error");
    if (errorParam != null) {
        errorParam = URLDecoder.decode(errorParam, "UTF-8");
    }

    // Parâmetros do filtro
    String filtroId = request.getParameter("filtro_id");
    String filtroNome = request.getParameter("filtro_nome");
    String filtroNomeSegmento = request.getParameter("filtro_nome_segmento");
    String filtroNomeEndereco = request.getParameter("filtro_nome_endereco");

    // Verificar se há algum filtro ativo
    boolean algumFiltro = (filtroId != null && !filtroId.isEmpty()) ||
            (filtroNome != null && !filtroNome.isEmpty()) ||
            (filtroNomeSegmento != null && !filtroNomeSegmento.isEmpty()) ||
            (filtroNomeEndereco != null && !filtroNomeEndereco.isEmpty());
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
        .btn-editar {
            background-color: #28a745;
            color: white;
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        .btn-editar:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) {
%>

<button onclick="window.location.href='html/Restricted-area/Pages/Units/units.jsp'">Adicionar Unidade</button>

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

<h1>Exibindo todas as Unidades</h1>

<%-- Formulário de Filtro com Múltiplos Campos --%>
<div class="filtro-container">
    <form action="${pageContext.request.contextPath}/servlet-unidade" method="get" style="margin: 20px 0;">
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
                <label for="filtroNome">Nome:</label>
                <input type="text" id="filtroNome" name="filtro_nome" class="filtro-input"
                       value="<%= filtroNome != null ? filtroNome : "" %>"
                       placeholder="Ex: Unidade A"
                       style="width: 150px;">
            </div>

            <div class="filtro-item">
                <label for="filtroNomeSegmento">Nome Segmento:</label>
                <input type="text" id="filtroNomeSegmento" name="filtro_nome_segmento" class="filtro-input"
                       value="<%= filtroNomeSegmento != null ? filtroNomeSegmento : "" %>"
                       placeholder="Ex: Transporte"
                       style="width: 150px;">
            </div>

            <div class="filtro-item">
                <label for="filtroNomeEndereco">Nome Endereço:</label>
                <input type="text" id="filtroNomeEndereco" name="filtro_nome_endereco" class="filtro-input"
                       value="<%= filtroNomeEndereco != null ? filtroNomeEndereco : "" %>"
                       placeholder="Ex: São Paulo"
                       style="width: 150px;">
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
                if (unidades != null) {
                    if (algumFiltro) {
                        out.print("Registros filtrados: " + unidades.size());

                        // Mostra os filtros ativos
                        out.print(" | Filtros: ");
                        List<String> filtrosAtivos = new ArrayList<>();
                        if (filtroId != null && !filtroId.isEmpty()) filtrosAtivos.add("ID: " + filtroId);
                        if (filtroNome != null && !filtroNome.isEmpty()) filtrosAtivos.add("Nome: " + filtroNome);
                        if (filtroNomeSegmento != null && !filtroNomeSegmento.isEmpty()) filtrosAtivos.add("Nome Segmento: " + filtroNomeSegmento);
                        if (filtroNomeEndereco != null && !filtroNomeEndereco.isEmpty()) filtrosAtivos.add("Nome Endereço: " + filtroNomeEndereco);
                        out.print(String.join(", ", filtrosAtivos));
                    } else {
                        out.print("Total de registros: " + unidades.size());
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
        <th><strong>NOME</strong></th>
        <th><strong>NOME SEGMENTO</strong></th>
        <th><strong>NOME ENDEREÇO</strong></th>
        <th><strong>AÇÕES</strong></th>
    </tr>
    </thead>

    <tbody>
    <%
        if (unidades != null && !unidades.isEmpty()) {
            for (int i=0; i < unidades.size(); i++){
    %>
    <tr>
        <td><%=unidades.get(i).getId()%></td>
        <td><%=unidades.get(i).getNome()%></td>
        <td><%=unidades.get(i).getNomeSegmento()%></td>
        <td><%=unidades.get(i).getNomeEndereco()%></td>
        <td>
            <form action="${pageContext.request.contextPath}/servlet-unidade" method="get" style="display:inline;">
                <input type="hidden" name="acao" value="editar">
                <input type="hidden" name="id" value="<%= unidades.get(i).getId() %>">
                <button type="submit" class="btn-editar" style="background-color: #28a745; color: white; padding: 6px 12px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; margin-left: 5px;">Editar</button>
            </form>
            <form action="${pageContext.request.contextPath}/servlet-unidade" method="post" style="display:inline;">
                <input type="hidden" name="acao_principal" value="excluir">
                <input type="hidden" name="id" value="<%= unidades.get(i).getId() %>">
                <button type="submit" class="btn-excluir" onclick="return confirm('Tem certeza que deseja excluir esta unidade?')">Excluir</button>
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
            Nenhuma unidade encontrada com os filtros aplicados.
            <% } else { %>
            Nenhuma unidade encontrada ou erro ao carregar dados.
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
        window.location.href = '${pageContext.request.contextPath}/servlet-unidade?acao=buscar&sub_acao=buscar_todos';
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