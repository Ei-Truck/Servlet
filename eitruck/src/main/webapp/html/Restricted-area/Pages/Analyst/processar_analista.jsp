<%@ page import="org.example.eitruck.model.Analista" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Analista> analistas = (List<Analista>) request.getAttribute("analistas");
    String errorMessage = (String) request.getAttribute("errorMessage");

    // Capturar e decodificar o erro da URL
    String errorParam = request.getParameter("error");
    if (errorParam != null) {
        errorParam = URLDecoder.decode(errorParam, "UTF-8");
    }

    // Parâmetros do filtro
    String filtroId = request.getParameter("filtro_id");
    String filtroNomeUnidade = request.getParameter("filtro_nome_unidade");
    String filtroNomeCompleto = request.getParameter("filtro_nome_completo");
    String filtroCpf = request.getParameter("filtro_cpf");
    String filtroEmail = request.getParameter("filtro_email");
    String filtroCargo = request.getParameter("filtro_cargo");

    // Verificar se há algum filtro ativo
    boolean algumFiltro = (filtroId != null && !filtroId.isEmpty()) ||
            (filtroNomeUnidade != null && !filtroNomeUnidade.isEmpty()) ||
            (filtroNomeCompleto != null && !filtroNomeCompleto.isEmpty()) ||
            (filtroCpf != null && !filtroCpf.isEmpty()) ||
            (filtroEmail != null && !filtroEmail.isEmpty()) ||
            (filtroCargo != null && !filtroCargo.isEmpty());
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

<button onclick="window.location.href='html/Restricted-area/Pages/Analyst/analista.jsp'">Adicionar Analista</button>

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

<h1>Exibindo todos os analistas</h1>

<%-- Formulário de Filtro com Múltiplos Campos --%>
<div class="filtro-container">
    <form action="${pageContext.request.contextPath}/servlet-analista" method="get" style="margin: 20px 0;">
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
                <label for="filtroNomeUnidade">Nome Unidade:</label>
                <input type="text" id="filtroNomeUnidade" name="filtro_nome_unidade" class="filtro-input"
                       value="<%= filtroNomeUnidade != null ? filtroNomeUnidade : "" %>"
                       placeholder="Ex: Unidade A"
                       style="width: 150px;">
            </div>

            <div class="filtro-item">
                <label for="filtroNomeCompleto">Nome Completo:</label>
                <input type="text" id="filtroNomeCompleto" name="filtro_nome_completo" class="filtro-input"
                       value="<%= filtroNomeCompleto != null ? filtroNomeCompleto : "" %>"
                       placeholder="Ex: João Silva"
                       style="width: 200px;">
            </div>

            <div class="filtro-item">
                <label for="filtroCpf">CPF:</label>
                <input type="text" id="filtroCpf" name="filtro_cpf" class="filtro-input"
                       value="<%= filtroCpf != null ? filtroCpf : "" %>"
                       placeholder="Ex: 12345678901"
                       style="width: 150px;">
            </div>
        </div>

        <div class="filtro-linha">
            <div class="filtro-item">
                <label for="filtroEmail">Email:</label>
                <input type="text" id="filtroEmail" name="filtro_email" class="filtro-input"
                       value="<%= filtroEmail != null ? filtroEmail : "" %>"
                       placeholder="Ex: joao@email.com"
                       style="width: 200px;">
            </div>

            <div class="filtro-item">
                <label for="filtroCargo">Cargo:</label>
                <input type="text" id="filtroCargo" name="filtro_cargo" class="filtro-input"
                       value="<%= filtroCargo != null ? filtroCargo : "" %>"
                       placeholder="Ex: Analista Jr"
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
                if (analistas != null) {
                    if (algumFiltro) {
                        out.print("Registros filtrados: " + analistas.size());

                        // Mostra os filtros ativos
                        out.print(" | Filtros: ");
                        List<String> filtrosAtivos = new ArrayList<>();
                        if (filtroId != null && !filtroId.isEmpty()) filtrosAtivos.add("ID: " + filtroId);
                        if (filtroNomeUnidade != null && !filtroNomeUnidade.isEmpty()) filtrosAtivos.add("Nome Unidade: " + filtroNomeUnidade);
                        if (filtroNomeCompleto != null && !filtroNomeCompleto.isEmpty()) filtrosAtivos.add("Nome Completo: " + filtroNomeCompleto);
                        if (filtroCpf != null && !filtroCpf.isEmpty()) filtrosAtivos.add("CPF: " + filtroCpf);
                        if (filtroEmail != null && !filtroEmail.isEmpty()) filtrosAtivos.add("Email: " + filtroEmail);
                        if (filtroCargo != null && !filtroCargo.isEmpty()) filtrosAtivos.add("Cargo: " + filtroCargo);
                        out.print(String.join(", ", filtrosAtivos));
                    } else {
                        out.print("Total de registros: " + analistas.size());
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
        <th><strong>NOME UNIDADE</strong></th>
        <th><strong>NOME COMPLETO</strong></th>
        <th><strong>CPF</strong></th>
        <th><strong>EMAIL</strong></th>
        <th><strong>DATA CONTRATAÇÃO</strong></th>
        <th><strong>CARGO</strong></th>
        <th><strong>AÇÕES</strong></th>
    </tr>
    </thead>

    <tbody>
    <%
        if (analistas != null && !analistas.isEmpty()) {
            for (int i=0; i < analistas.size(); i++){
    %>
    <tr>
        <td><%=analistas.get(i).getId()%></td>
        <td><%=analistas.get(i).getNomeUnidade()%></td>
        <td><%=analistas.get(i).getNomeCompleto()%></td>
        <td><%=analistas.get(i).getCpf()%></td>
        <td><%=analistas.get(i).getEmail()%></td>
        <td><%=analistas.get(i).getDtContratacao()%></td>
        <td><%=analistas.get(i).getCargo()%></td>
        <td>
            <form action="${pageContext.request.contextPath}/servlet-analista" method="get" style="display:inline;">
                <input type="hidden" name="acao" value="editar">
                <input type="hidden" name="id" value="<%= analistas.get(i).getId() %>">
                <button type="submit" class="btn-editar" style="background-color: #28a745; color: white; padding: 6px 12px; border: none; border-radius: 4px; cursor: pointer; margin-right: 5px;">Editar</button>
            </form>
            <form action="${pageContext.request.contextPath}/servlet-analista" method="post" style="display:inline;">
                <input type="hidden" name="acao_principal" value="excluir">
                <input type="hidden" name="id" value="<%= analistas.get(i).getId() %>">
                <button type="submit" class="btn-excluir" onclick="return confirm('Tem certeza que deseja excluir este analista?')">Excluir</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="8">
            <% if (algumFiltro) { %>
            Nenhum analista encontrado com os filtros aplicados.
            <% } else { %>
            Nenhum analista encontrado ou erro ao carregar dados.
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
        window.location.href = '${pageContext.request.contextPath}/servlet-analista?acao=buscar&sub_acao=buscar_todos';
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