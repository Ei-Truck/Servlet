<%@ page import="org.example.eitruck.model.Analista" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.Dao.AnalistaDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    AnalistaDAO analistaDAO = new AnalistaDAO();
    String subAcao = request.getParameter("sub_acao");
    List<Analista> analistas = (List<Analista>) request.getAttribute("analistas");
    if (analistas != null) analistas = analistaDAO.buscarTodos();
%>
<html>
<head>
    <title>Buscar todos</title>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) { // Abre o bloco
%>

<button onclick="window.location.href='html/Restricted-area/Pages/Analyst/analista.jsp'">Adicionar Analista</button>

<h1>Exibindo todos os analistas</h1>
    <table>
        <thead>
        <tr>
            <th><strong>ID</strong></th>
            <th><strong>NOME UNIDADE</strong></th>
            <th><strong>CPF</strong></th>
            <th><strong>NOME COMPLETO</strong></th>
            <th><strong>EMAIL</strong></th>
            <th><strong>DATA CONTRATAÇÃO</strong></th>
            <th><strong>CARGO</strong></th>
        </tr>
        </thead>


        <tbody>
        <%
            for (int i=0; i < analistas.size(); i++){
        %>
        <tr>
            <td><%=analistas.get(i).getId()%></td>
            <td><%=analistas.get(i).getNomeUnidade()%></td>
            <td><%=analistas.get(i).getCpf()%></td>
            <td><%=analistas.get(i).getNomeCompleto()%></td>
            <td><%=analistas.get(i).getEmail()%></td>
            <td><%=analistas.get(i).getDtContratacao()%></td>
            <td><%=analistas.get(i).getCargo()%></td>
        </tr>
        <%
            } // Fecha o for
        %>
        </tbody>
    </table>
<%
    }
%>
</body>
</html>