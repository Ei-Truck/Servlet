<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Endereco" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Endereco> enderecos = (List<Endereco>) request.getAttribute("enderecos");
%>
<html>
<head>
    <title>Buscar todos</title>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) { // Abre o bloco
%>

<button onclick="window.location.href='html/Restricted-area/Pages/Addresses/addresses.jsp'">Adicionar Endereço</button>

<h1>Exibindo todos os Endereços</h1>

<table>
    <thead>
    <tr>
        <th><strong>ID</strong></th>
        <th><strong>CEP</strong></th>
        <th><strong>RUA</strong></th>
        <th><strong>NÚMERO</strong></th>
        <th><strong>BAIRRO</strong></th>
        <th><strong>CIDADE</strong></th>
        <th><strong>ESTADO</strong></th>
        <th><strong>PAÍS</strong></th>
    </tr>
    </thead>


    <tbody>
    <%
        if (enderecos != null) {
            for (int i=0; i < enderecos.size(); i++){
    %>
    <tr>
        <td><%=enderecos.get(i).getId()%></td>
        <td><%=enderecos.get(i).getCep()%></td>
        <td><%=enderecos.get(i).getRua()%></td>
        <td><%=enderecos.get(i).getNumero()%></td>
        <td><%=enderecos.get(i).getBairro()%></td>
        <td><%=enderecos.get(i).getCidade()%></td>
        <td><%=enderecos.get(i).getEstado()%></td>
        <td><%=enderecos.get(i).getPais()%></td>
    </tr>
    <%
        } // Fecha o for
    } else {
    %>
    <tr>
        <td colspan="7">Nenhum endereço encontrado ou erro ao carregar dados.</td>
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