<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Administrador" %>
<%@ page import="org.example.eitruck.model.Endereco" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Administrador> administradores = (List<Administrador>) request.getAttribute("administradores");
%>
<html>
<head>
    <title>Buscar todos</title>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) { // Abre o bloco
%>

<button onclick="window.location.href='html/Restricted-area/Pages/Administrator/administrator.jsp'">Adicionar Administrador</button>

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
        if (administradores != null) {
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
        } // Fecha o for
    } else {
    %>
    <tr>
        <td colspan="7">Nenhum administrador encontrado ou erro ao carregar dados.</td>
    </tr>
    <%
        } // Fecha o if
    %>
    </tbody>
</table>
<%
    }
%>
</body>
</html>