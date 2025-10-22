<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Administrador" %>
<%@ page import="org.example.eitruck.model.TipoOcorrencia" %>
<%@ page import="org.example.eitruck.model.Segmento" %>
<%@ page import="org.example.eitruck.model.Unidade" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Unidade> unidades = (List<Unidade>) request.getAttribute("unidades");
%>
<html>
<head>
    <title>Buscar todos</title>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) { // Abre o bloco
%>

<button onclick="window.location.href='html/Restricted-area/Pages/Units/units.jsp'">Adicionar Unidade</button>

<h1>Exibindo todos os Unidades</h1>

<table>
    <thead>
    <tr>
        <th><strong>ID</strong></th>
        <th><strong>NOME</strong></th>
        <th><strong>ID SEGMENTO</strong></th>
        <th><strong>ID ENDEREÇO</strong></th>
    </tr>
    </thead>


    <tbody>
    <%
        if (unidades != null) {
            for (int i=0; i < unidades.size(); i++){
    %>
    <tr>
        <td><%=unidades.get(i).getId()%></td>
        <td><%=unidades.get(i).getNome()%></td>
        <td><%=unidades.get(i).getIdSegmento()%></td>
        <td><%=unidades.get(i).getIdEndereco()%></td>
    </tr>
    <%
        } // Fecha o for
    } else {
    %>
    <tr>
        <td colspan="7">Nenhuma unidade encontrado ou erro ao carregar dados.</td>
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