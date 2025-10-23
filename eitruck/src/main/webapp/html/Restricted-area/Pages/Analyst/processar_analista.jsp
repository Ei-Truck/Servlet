<%@ page import="org.example.eitruck.model.Analista" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLDecoder" %>
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

    // REMOVA estas linhas que sobrescrevem a lista
    // AnalistaDAO analistaDAO = new AnalistaDAO();
    // if (analistas != null) analistas = analistaDAO.buscarTodos();
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
            <form action="${pageContext.request.contextPath}/servlet-analista" method="post" style="display:inline;">
                <input type="hidden" name="acao_principal" value="excluir">
                <input type="hidden" name="id" value="<%= analistas.get(i).getId() %>">
                <button type="submit" onclick="return confirm('Tem certeza que deseja excluir este analista?')">Excluir</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="8">Nenhum analista encontrado ou erro ao carregar dados.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
    }
%>
</body>
</html>