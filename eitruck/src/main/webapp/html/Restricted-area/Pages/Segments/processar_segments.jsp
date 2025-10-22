<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Administrador" %>
<%@ page import="org.example.eitruck.model.TipoOcorrencia" %>
<%@ page import="org.example.eitruck.model.Segmento" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Segmento> segmentos = (List<Segmento>) request.getAttribute("segmentos");
%>
<html>
<head>
    <title>Buscar todos</title>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) { // Abre o bloco
%>

<button onclick="window.location.href='html/Restricted-area/Pages/Segments/segments.jsp'">Adicionar Segmento</button>

<h1>Exibindo todos os Segmentos</h1>

<table>
    <thead>
    <tr>
        <th><strong>ID</strong></th>
        <th><strong>NOME</strong></th>
        <th><strong>DESCRIÇÃO</strong></th>
    </tr>
    </thead>


    <tbody>
    <%
        if (segmentos != null) {
            for (int i=0; i < segmentos.size(); i++){
    %>
    <tr>
        <td><%=segmentos.get(i).getId()%></td>
        <td><%=segmentos.get(i).getNome()%></td>
        <td><%=segmentos.get(i).getDescricao()%></td>
    </tr>
    <%
        } // Fecha o for
    } else {
    %>
    <tr>
        <td colspan="7">Nenhum segmento encontrado ou erro ao carregar dados.</td>
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