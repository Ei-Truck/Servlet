<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Administrador" %>
<%@ page import="org.example.eitruck.model.TipoOcorrencia" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<TipoOcorrencia> ocorrencias = (List<TipoOcorrencia>) request.getAttribute("ocorrencias");
%>
<html>
<head>
    <title>Buscar todos</title>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) { // Abre o bloco
%>

<button onclick="window.location.href='html/Restricted-area/Pages/Occurrences/occurrences.jsp'">Adicionar Tipo de Ocorrência</button>

<h1>Exibindo todos os Tipos de Ocorrências</h1>

<table>
    <thead>
    <tr>
        <th><strong>ID</strong></th>
        <th><strong>TIPO DE EVENTO</strong></th>
        <th><strong>PONTUAÇÃO</strong></th>
        <th><strong>GRAVIDADE</strong></th>
    </tr>
    </thead>


    <tbody>
    <%
        if (ocorrencias != null) {
            for (int i=0; i < ocorrencias.size(); i++){
    %>
    <tr>
        <td><%=ocorrencias.get(i).getId()%></td>
        <td><%=ocorrencias.get(i).getTipoEvento()%></td>
        <td><%=ocorrencias.get(i).getPontuacao()%></td>
        <td><%=ocorrencias.get(i).getGravidade()%></td>
    </tr>
    <%
        } // Fecha o for
    } else {
    %>
    <tr>
        <td colspan="7">Nenhum tipo de ocorrência encontrado ou erro ao carregar dados.</td>
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