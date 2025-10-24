<%@ page import="java.util.List" %>
<%@ page import="org.example.eitruck.model.Endereco" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String subAcao = request.getParameter("sub_acao");
    List<Endereco> enderecos = (List<Endereco>) request.getAttribute("enderecos");
    String errorMessage = (String) request.getAttribute("errorMessage");

    // Capturar e decodificar o erro da URL
    String errorParam = request.getParameter("error");
    if (errorParam != null) {
        errorParam = URLDecoder.decode(errorParam, "UTF-8");
    }

    // Parâmetros do filtro
    String filtroId = request.getParameter("filtro_id");
    String filtroCep = request.getParameter("filtro_cep");
    String filtroRua = request.getParameter("filtro_rua");
    String filtroNumero = request.getParameter("filtro_numero");
    String filtroBairro = request.getParameter("filtro_bairro");
    String filtroCidade = request.getParameter("filtro_cidade");
    String filtroEstado = request.getParameter("filtro_estado");
    String filtroPais = request.getParameter("filtro_pais");

    // Verificar se há algum filtro ativo
    boolean algumFiltro = (filtroId != null && !filtroId.isEmpty()) ||
            (filtroCep != null && !filtroCep.isEmpty()) ||
            (filtroRua != null && !filtroRua.isEmpty()) ||
            (filtroNumero != null && !filtroNumero.isEmpty()) ||
            (filtroBairro != null && !filtroBairro.isEmpty()) ||
            (filtroCidade != null && !filtroCidade.isEmpty()) ||
            (filtroEstado != null && !filtroEstado.isEmpty()) ||
            (filtroPais != null && !filtroPais.isEmpty());
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

        .filtro-container {
            margin: 20px 0;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
            border: 1px solid #dee2e6;
        }

        .filtro-item {
            display: flex;
            flex-direction: column;
        }

        .filtro-item label {
            font-weight: bold;
            margin-bottom: 5px;
            font-size: 12px;
        }

        .filtro-input {
            padding: 6px;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .filtro-linha {
            display: flex;
            gap: 10px;
            align-items: end;
            flex-wrap: wrap;
            margin-bottom: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table th, table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
            font-size: 14px;
        }

        table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        table tr:hover {
            background-color: #f5f5f5;
        }

        button {
            padding: 6px 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        button:hover {
            background-color: #0056b3;
        }

        .btn-limpar {
            background-color: #6c757d;
        }

        .btn-limpar:hover {
            background-color: #545b62;
        }

        .btn-excluir {
            background-color: #dc3545;
        }

        .btn-excluir:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
<%
    if ("buscar_todos".equals(subAcao)) {
%>

<button onclick="window.location.href='html/Restricted-area/Pages/Addresses/addresses.jsp'">Adicionar Endereço</button>

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

<h1>Exibindo todos os Endereços</h1>

<%-- Formulário de Filtro com Múltiplos Campos --%>
<div class="filtro-container">
    <form action="${pageContext.request.contextPath}/servlet-enderecos" method="get" style="margin: 20px 0;">
        <input type="hidden" name="acao" value="filtrar">
        <input type="hidden" name="sub_acao" value="buscar_todos">

        <div class="filtro-linha">
            <div class="filtro-item">
                <label for="filtroId">ID:</label>
                <input type="text" id="filtroId" name="filtro_id" class="filtro-input"
                       value="<%= filtroId != null ? filtroId : "" %>"
                       placeholder="Ex: 1, 2"
                       style="width: 80px;">
            </div>

            <div class="filtro-item">
                <label for="filtroCep">CEP:</label>
                <input type="text" id="filtroCep" name="filtro_cep" class="filtro-input"
                       value="<%= filtroCep != null ? filtroCep : "" %>"
                       placeholder="Ex: 12345"
                       style="width: 100px;">
            </div>

            <div class="filtro-item">
                <label for="filtroRua">Rua:</label>
                <input type="text" id="filtroRua" name="filtro_rua" class="filtro-input"
                       value="<%= filtroRua != null ? filtroRua : "" %>"
                       placeholder="Ex: Principal"
                       style="width: 150px;">
            </div>

            <div class="filtro-item">
                <label for="filtroNumero">Número:</label>
                <input type="text" id="filtroNumero" name="filtro_numero" class="filtro-input"
                       value="<%= filtroNumero != null ? filtroNumero : "" %>"
                       placeholder="Ex: 100"
                       style="width: 80px;">
            </div>
        </div>

        <div class="filtro-linha">
            <div class="filtro-item">
                <label for="filtroBairro">Bairro:</label>
                <input type="text" id="filtroBairro" name="filtro_bairro" class="filtro-input"
                       value="<%= filtroBairro != null ? filtroBairro : "" %>"
                       placeholder="Ex: Centro"
                       style="width: 120px;">
            </div>

            <div class="filtro-item">
                <label for="filtroCidade">Cidade:</label>
                <input type="text" id="filtroCidade" name="filtro_cidade" class="filtro-input"
                       value="<%= filtroCidade != null ? filtroCidade : "" %>"
                       placeholder="Ex: São Paulo"
                       style="width: 120px;">
            </div>

            <div class="filtro-item">
                <label for="filtroEstado">Estado:</label>
                <input type="text" id="filtroEstado" name="filtro_estado" class="filtro-input"
                       value="<%= filtroEstado != null ? filtroEstado : "" %>"
                       placeholder="Ex: SP"
                       style="width: 80px;">
            </div>

            <div class="filtro-item">
                <label for="filtroPais">País:</label>
                <input type="text" id="filtroPais" name="filtro_pais" class="filtro-input"
                       value="<%= filtroPais != null ? filtroPais : "" %>"
                       placeholder="Ex: Brasil"
                       style="width: 100px;">
            </div>

            <div class="filtro-item" style="display: flex; gap: 10px; margin-left: 20px;">
                <button type="submit"
                        style="padding: 8px 20px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold;">
                    Aplicar Filtro
                </button>

                <button type="button" onclick="limparFiltro()"
                        style="padding: 8px 20px; background-color: #6c757d; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold;">
                    Limpar
                </button>
            </div>
        </div>

        <div style="margin-top: 10px; font-size: 12px; color: #666;">
            <em>Preencha um ou mais campos para filtrar. Os filtros são combinados (AND).</em>
        </div>
    </form>

    <div style="margin-top: 5px; font-size: 14px; color: #333;">
        <strong>
            <%
                if (enderecos != null) {
                    if (algumFiltro) {
                        out.print("Registros filtrados: " + enderecos.size());

                        // Mostra os filtros ativos
                        out.print(" | Filtros: ");
                        List<String> filtrosAtivos = new ArrayList<>();
                        if (filtroId != null && !filtroId.isEmpty()) filtrosAtivos.add("ID: " + filtroId);
                        if (filtroCep != null && !filtroCep.isEmpty()) filtrosAtivos.add("CEP: " + filtroCep);
                        if (filtroRua != null && !filtroRua.isEmpty()) filtrosAtivos.add("Rua: " + filtroRua);
                        if (filtroNumero != null && !filtroNumero.isEmpty()) filtrosAtivos.add("Número: " + filtroNumero);
                        if (filtroBairro != null && !filtroBairro.isEmpty()) filtrosAtivos.add("Bairro: " + filtroBairro);
                        if (filtroCidade != null && !filtroCidade.isEmpty()) filtrosAtivos.add("Cidade: " + filtroCidade);
                        if (filtroEstado != null && !filtroEstado.isEmpty()) filtrosAtivos.add("Estado: " + filtroEstado);
                        if (filtroPais != null && !filtroPais.isEmpty()) filtrosAtivos.add("País: " + filtroPais);
                        out.print(String.join(", ", filtrosAtivos));
                    } else {
                        out.print("Total de registros: " + enderecos.size());
                    }
                }
            %>
        </strong>
    </div>
</div>

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
        <th><strong>AÇÕES</strong></th>
    </tr>
    </thead>

    <tbody>
    <%
        if (enderecos != null && !enderecos.isEmpty()) {
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
        <td>
            <form action="${pageContext.request.contextPath}/servlet-enderecos" method="get" style="display:inline;">
                <input type="hidden" name="acao" value="editar">
                <input type="hidden" name="id" value="<%= enderecos.get(i).getId() %>">
                <button type="submit" class="btn-editar" style="background-color: #28a745; color: white; padding: 6px 12px; border: none; border-radius: 4px; cursor: pointer; margin-right: 5px;">Editar</button>
            </form>
            <form action="${pageContext.request.contextPath}/servlet-enderecos" method="post" style="display:inline;">
                <input type="hidden" name="acao_principal" value="excluir">
                <input type="hidden" name="id" value="<%= enderecos.get(i).getId() %>">
                <button type="submit" class="btn-excluir" onclick="return confirm('Tem certeza que deseja excluir este endereço?')">Excluir</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="9">
            <% if (algumFiltro) { %>
            Nenhum endereço encontrado com os filtros aplicados.
            <% } else { %>
            Nenhum endereço encontrado ou erro ao carregar dados.
            <% } %>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<script>
    function limparFiltro() {
        window.location.href = '${pageContext.request.contextPath}/servlet-enderecos?acao=buscar&sub_acao=buscar_todos';
    }

    // Opcional: enviar form ao pressionar Enter em qualquer campo
    document.addEventListener('DOMContentLoaded', function() {
        const inputs = document.querySelectorAll('input[type="text"]');
        inputs.forEach(input => {
            input.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    document.querySelector('button[type="submit"]').click();
                }
            });
        });
    });
</script>
<%
    }
%>
</body>
</html>