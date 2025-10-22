<%@ page import="org.example.eitruck.model.Analista" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Analista> analistas = (List<Analista>) request.getAttribute("analistas");
%>
<html>
<head>
    <title>Buscar todos</title>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) { // Abre o bloco
%>
<h1>Exibindo todos os analistas</h1>
<%
    }
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
        <%
            if (analistas != null) {
                for (int i=0; i < analistas.size(); i++){
        %>
        <tr>
            <td><%=analistas.get(i).getId()%></td>
            <td><%=analistas.get(i).getIdUnidade()%></td>
            <td><%=analistas.get(i).getCpf()%></td>
            <td><%=analistas.get(i).getNomeCompleto()%></td>
            <td><%=analistas.get(i).getEmail()%></td>
            <td><%=analistas.get(i).getDtContratacao()%></td>
            <td><%=analistas.get(i).getCargo()%></td>
        </tr>
        <%
            } // Fecha o for
        } else {
        %>
        <tr>
            <td colspan="7">Nenhum analista encontrado ou erro ao carregar dados.</td>
        </tr>
        <%
            } // Fecha o if
        %>
        </tbody>
    </table>
</body>
</html>