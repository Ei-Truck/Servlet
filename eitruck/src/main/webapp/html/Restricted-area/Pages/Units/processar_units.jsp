<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Unidade" %>
<%@ page import="java.net.URLDecoder" %>
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
            <form action="${pageContext.request.contextPath}/servlet-unidade" method="post" style="display:inline;">
                <input type="hidden" name="acao_principal" value="excluir">
                <input type="hidden" name="id" value="<%= unidades.get(i).getId() %>">
                <button type="submit" onclick="return confirm('Tem certeza que deseja excluir esta unidade?')">Excluir</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5">Nenhuma unidade encontrada ou erro ao carregar dados.</td>
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