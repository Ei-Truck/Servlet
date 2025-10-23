<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Administrador" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Administrador> administradores = (List<Administrador>) request.getAttribute("administradores");
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

<button onclick="window.location.href='html/Restricted-area/Pages/Administrator/administrator.jsp'">Adicionar Administrador</button>

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

<h1>Exibindo todos os Administradores</h1>

<table>
    <thead>
    <tr>
        <th><strong>ID</strong></th>
        <th><strong>NOME COMPLETO</strong></th>
        <th><strong>CPF</strong></th>
        <th><strong>EMAIL</strong></th>
        <th><strong>TELEFONE</strong></th>
        <th><strong>AÇÕES</strong></th>
    </tr>
    </thead>

    <tbody>
    <%
        if (administradores != null && !administradores.isEmpty()) {
            for (int i=0; i < administradores.size(); i++){
    %>
    <tr>
        <td><%=administradores.get(i).getId()%></td>
        <td><%=administradores.get(i).getNomeCompleto()%></td>
        <td><%=administradores.get(i).getCpf()%></td>
        <td><%=administradores.get(i).getEmail()%></td>
        <td><%=administradores.get(i).getTelefone()%></td>
        <td>
            <form action="${pageContext.request.contextPath}/servlet-administrador" method="post" style="display:inline;">
                <input type="hidden" name="acao_principal" value="excluir">
                <input type="hidden" name="id" value="<%= administradores.get(i).getId() %>">
                <button type="submit" onclick="return confirm('Tem certeza que deseja excluir este administrador?')">Excluir</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6">Nenhum administrador encontrado ou erro ao carregar dados.</td>
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