<%@ page import="org.example.eitruck.model.Analista" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Analista> analistas = (List<Analista>) request.getAttribute("sub_acao");
%>
<html>
<head>
    <title>Buscar todos</title>
</head>
<body>
    <%
        if ("buscar_todos".equals(subAcao))
    %>

    <table>
        <thead>
        <tr>
            <th><strong>ID</strong></th>
            <th><strong>ID UNIDADE</strong></th>
            <th><strong>CPF</strong></th>
            <th><strong>NOME COMPLETO</strong></th>
            <th><strong>EMAIL</strong></th>
            <th><strong>DATA CONTRATAÇÃO</strong></th>
            <th><strong>CARGO</strong></th>
        </tr>
        </thead>

        <tbody>
        <%for (Analista a : analistas){%>
        <tr>
            <td><%=a.getId()%>%></td>
            <td><%=a.getIdUnidade()%>%></td>
            <td><%=a.getCpf()%>%></td>
            <td><%=a.getNomeCompleto()%>%></td>
            <td><%=a.getEmail()%>%></td>
            <td><%=a.getDtContratacao()%>%></td>
            <td><%=a.getCargo()%>%></td>
        </tr>
        <%}%>
        </tbody>
    </table>
</body>
</html>
