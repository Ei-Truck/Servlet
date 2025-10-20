<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html lang="pt-BR">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>Administradores - Ei Truck</title>--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleCss/Restricted-area/Pages/administrator.css">--%>
<%--    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/image/Group 36941.png">--%>
<%--</head>--%>
<%--<body>--%>

<%--<!-- Botão de ação -->--%>
<%--<div class="actions-header" style="margin-bottom: 20px;">--%>
<%--    <button class="btn btn-primary" onclick="novoAdministrador()">--%>
<%--        ＋ Novo Administrador--%>
<%--    </button>--%>
<%--</div>--%>

<%--<!-- Filtros -->--%>
<%--<div class="filters">--%>
<%--    <form id="filter-form" method="get" action="administradores">--%>
<%--        <input type="hidden" name="action" value="buscar">--%>
<%--        <div class="filter-group1">--%>
<%--            <div class="filter-group">--%>
<%--                <label for="filter-id">ID</label>--%>
<%--                <input type="number" class="filter-input" id="filter-id" name="filter-id"--%>
<%--                       placeholder="Filtrar por ID..." value="${param['filter-id']}">--%>
<%--            </div>--%>
<%--            <div class="filter-group">--%>
<%--                <label for="filter-nome">Nome</label>--%>
<%--                <input type="text" class="filter-input" id="filter-nome" name="filter-nome"--%>
<%--                       placeholder="Filtrar por nome..." value="${param['filter-nome']}">--%>
<%--            </div>--%>
<%--            <div class="filter-group">--%>
<%--                <label for="filter-cpf">CPF</label>--%>
<%--                <input type="text" class="filter-input" id="filter-cpf" name="filter-cpf"--%>
<%--                       placeholder="Filtrar por CPF..." value="${param['filter-cpf']}">--%>
<%--            </div>--%>
<%--            <div class="filter-group">--%>
<%--                <label for="filter-email">E-mail</label>--%>
<%--                <input type="text" class="filter-input" id="filter-email" name="filter-email"--%>
<%--                       placeholder="Filtrar por e-mail..." value="${param['filter-email']}">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="filter-group" style="display: flex; gap: 10px; margin-top: 15px;">--%>
<%--            <button type="submit" class="btn btn-secondary" id="apply-filters">--%>
<%--                <span>🔍</span> Aplicar Filtros--%>
<%--            </button>--%>
<%--            <button type="button" class="btn btn-secondary" id="clear-filters">--%>
<%--                <span>🔄</span> Limpar--%>
<%--            </button>--%>
<%--        </div>--%>
<%--    </form>--%>
<%--</div>--%>

<%--<!-- Tabela -->--%>
<%--<div class="table-container">--%>
<%--    <table class="crud-table">--%>
<%--        <thead>--%>
<%--        <tr>--%>
<%--            <th>ID</th>--%>
<%--            <th>Nome Completo</th>--%>
<%--            <th>CPF</th>--%>
<%--            <th>E-mail</th>--%>
<%--            <th>Ações</th>--%>
<%--        </tr>--%>
<%--        </thead>--%>
<%--        <tbody id="table-body">--%>
<%--        <c:choose>--%>
<%--            <c:when test="${not empty administradores}">--%>
<%--                <c:forEach items="${administradores}" var="admin">--%>
<%--                    <tr>--%>
<%--                        <td>${admin.id}</td>--%>
<%--                        <td>${not empty admin.nomeCompleto ? admin.nomeCompleto : ''}</td>--%>
<%--                        <td>${not empty admin.cpf ? admin.cpf : ''}</td>--%>
<%--                        <td>${not empty admin.email ? admin.email : ''}</td>--%>
<%--                        <td>--%>
<%--                            <button class="btn btn-edit"--%>
<%--                                    data-id="${admin.id}"--%>
<%--                                    data-nome="${admin.nomeCompleto}"--%>
<%--                                    data-cpf="${admin.cpf}"--%>
<%--                                    data-email="${admin.email}">--%>
<%--                                Editar--%>
<%--                            </button>--%>
<%--                            <button class="btn btn-delete"--%>
<%--                                    data-id="${admin.id}">--%>
<%--                                Excluir--%>
<%--                            </button>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                </c:forEach>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <tr>--%>
<%--                    <td colspan="5" style="text-align: center; padding: 20px;">--%>
<%--                        Nenhum administrador encontrado--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>
<%--        </tbody>--%>
<%--    </table>--%>
<%--</div>--%>

<%--<script>--%>
<%--    // Funções JavaScript aqui--%>
<%--    document.getElementById('clear-filters').addEventListener('click', function() {--%>
<%--        document.querySelectorAll('.filter-input').forEach(input => input.value = '');--%>
<%--        document.getElementById('filter-form').submit();--%>
<%--    });--%>

<%--    document.querySelectorAll('.btn-edit').forEach(btn => {--%>
<%--        btn.addEventListener('click', function() {--%>
<%--            const id = this.dataset.id;--%>
<%--            window.location.href = '${pageContext.request.contextPath}/administradores?action=editar&id=' + id;--%>
<%--        });--%>
<%--    });--%>

<%--    document.querySelectorAll('.btn-delete').forEach(btn => {--%>
<%--        btn.addEventListener('click', function() {--%>
<%--            const id = this.dataset.id;--%>
<%--            if(confirm('Tem certeza que deseja excluir este administrador?')) {--%>
<%--                window.location.href = '${pageContext.request.contextPath}/administradores?action=excluir&id=' + id;--%>
<%--            }--%>
<%--        });--%>
<%--    });--%>

<%--    function novoAdministrador() {--%>
<%--        window.location.href = '${pageContext.request.contextPath}/administradores?action=novo';--%>
<%--    }--%>
<%--</script>--%>

<%--</body>--%>
<%--</html>--%>