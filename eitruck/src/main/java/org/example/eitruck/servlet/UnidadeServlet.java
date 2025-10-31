package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import org.example.eitruck.dao.UnidadeDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.eitruck.model.Unidade;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/servlet-unidade")
public class UnidadeServlet extends HttpServlet {
    private UnidadeDAO unidadeDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Inicializa o objeto ANTES de qualquer requisição.
        this.unidadeDao = new UnidadeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        switch (acao != null ? acao : "listar") {
            case "editar":
                carregarUnidadeParaEdicao(request, response);
                break;
            case "buscar":
                buscarTodos(request, response, acao, "buscar_todos");
                break;
            case "filtrar":
                filtrarUnidades(request, response);
                break;
            case "listar":
            default:
                buscarTodos(request, response, acao, "buscar_todos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao_principal");

        switch (acao) {
            case "inserir":
                inserirUnidade(request, response, acao, request.getParameter("sub_acao"));
                break;
            case "atualizar":
                atualizarUnidade(request, response);
                break;
            case "excluir":
                excluirUnidade(request, response);
                break;
        }
    }

    // Método inserir
    private void inserirUnidade(HttpServletRequest request, HttpServletResponse response, String acao, String sub_acao) throws IOException, ServletException {
        String errorMessage = null;
        boolean success = false;
        boolean isFormSubmission = false;

        try {
            String idSegmento = request.getParameter("id_segmento");
            String idEndereco = request.getParameter("id_endereco");
            String nome = request.getParameter("nome");

            System.out.println("Id do segmento: " + idSegmento);
            System.out.println("Id do endereço: " + idEndereco);
            System.out.println("Nome: " + nome);


            int id_segmento = Integer.parseInt(idSegmento);
            int id_endereco = Integer.parseInt(idEndereco);


            Unidade unidade = new Unidade(id_segmento, id_endereco, nome);
            success = unidadeDao.cadastrar(unidade);

            if (success) {
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos");
                    return;
                }
                return;
            } else {
                errorMessage = "Erro ao cadastrar unidade no banco de dados.";
            }
        } catch (NumberFormatException e) {
            errorMessage = "ID da unidade deve ser um número válido.";
        } catch (DateTimeParseException e) {
            errorMessage = "Data de contratação inválida. Use o formato AAAA-MM-DD.";
        } catch (IllegalArgumentException e) {
            // Já tem a mensagem de erro
        } catch (Exception e) {
            errorMessage = "Ocorreu um erro inesperado: " + e.getMessage();
        }

        if (errorMessage == null) {
            errorMessage = "Erro ao cadastrar unidade no banco de dados.";
        }

        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("isFormSubmission", isFormSubmission);

        request.setAttribute("id_segmento", request.getParameter("id_segmento"));
        request.setAttribute("id_endereco", request.getParameter("id_endereco"));
        request.setAttribute("nome", request.getParameter("nome"));

        request.setAttribute("sub_acao", sub_acao);

        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        RequestDispatcher respacher = request.getRequestDispatcher("html/area-restrita/paginas/unidade/unidade.jsp");
        if (respacher != null) {
            respacher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar unidade");
        }
    }

    // Método excluir
    private void excluirUnidade(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int resultado = unidadeDao.apagar(id);

            if (resultado > 0) {
                // Sucesso: redireciona para a lista sem mensagem de erro
                response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos");
            } else if (resultado == -2) {
                // Erro específico: unidade está sendo usada por um analista
                String errorMessage = URLEncoder.encode("Não é possível excluir essa unidade porque ela está sendo usada por um analista.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            } else {
                // Erro genérico
                String errorMessage = URLEncoder.encode("Erro ao excluir unidade. Tente novamente.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro interno ao excluir unidade.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    // Método para carregar a Unidade para Edição
    private void carregarUnidadeParaEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("Carregando unidade para edição, ID: " + id);

            List<Unidade> unidades = unidadeDao.buscarPorId(id);

            System.out.println("Resultado da busca: " + (unidades != null ? unidades.size() : "null"));

            if (unidades != null && !unidades.isEmpty()) {
                Unidade unidade = unidades.get(0);
                System.out.println("Unidade encontrada: " + unidade.getNome());
                request.setAttribute("unidade", unidade);
                RequestDispatcher rd = request.getRequestDispatcher("/html/area-restrita/paginas/unidade/editar_unidade.jsp");
                rd.forward(request, response);
            } else {
                System.out.println("Unidade não encontrada para o ID: " + id);
                String errorMessage = URLEncoder.encode("Unidade não encontrada.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de parse do ID");
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro ao carregar unidade para edição: " + e.getMessage(), "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    // Método atualizar
    private void atualizarUnidade(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String errorMessage = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int idSegmento = Integer.parseInt(request.getParameter("id_segmento"));
            int idEndereco = Integer.parseInt(request.getParameter("id_endereco"));
            String nome = request.getParameter("nome");

            System.out.println("Atualizando unidade - ID: " + id + ", ID Segmento: " + idSegmento + ", ID Endereço: " + idEndereco + ", Nome: " + nome);

            int resultado = unidadeDao.alterarTodos(id, nome, idSegmento, idEndereco);

            if (resultado > 0) {
                response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos");
                return;
            } else {
                errorMessage = "Erro ao atualizar unidade no banco de dados.";
            }
        } catch (NumberFormatException e) {
            errorMessage = "Erro ao atualizar unidade no banco de dados.";
        } catch (Exception e) {
            errorMessage = "Erro ao atualizar unidade no banco de dados.";
            e.printStackTrace();
        }

        // Se houve erro, recarrega a página de edição com a mensagem de erro
        request.setAttribute("errorMessage", errorMessage);
        carregarUnidadeParaEdicao(request, response);
    }

    // Método para mostrar a tabela
    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws IOException, ServletException {
        try {
            List<Unidade> unidades = unidadeDao.buscarTodos();
            request.setAttribute("unidades", unidades);
            RequestDispatcher rd = request.getRequestDispatcher("/html/area-restrita/paginas/unidade/processar_unidade.jsp");
            rd.forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("sub_acao", subAcao);

        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        encaminhar(request, response, "erro.jsp");
    }

    // Método filtrar
    private void filtrarUnidades(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String filtroId = request.getParameter("filtro_id");
            String filtroNome = request.getParameter("filtro_nome");
            String filtroNomeSegmento = request.getParameter("filtro_nome_segmento");
            String filtroNomeEndereco = request.getParameter("filtro_nome_endereco");

            List<Unidade> unidades;

            // Verifica se pelo menos um filtro foi preenchido
            boolean algumFiltro = (filtroId != null && !filtroId.trim().isEmpty()) ||
                    (filtroNome != null && !filtroNome.trim().isEmpty()) ||
                    (filtroNomeSegmento != null && !filtroNomeSegmento.trim().isEmpty()) ||
                    (filtroNomeEndereco != null && !filtroNomeEndereco.trim().isEmpty());

            if (algumFiltro) {
                // Busca com filtros
                unidades = unidadeDao.filtrarUnidadesMultiplos(filtroId, filtroNome, filtroNomeSegmento, filtroNomeEndereco);
            } else {
                // Se nenhum filtro, busca todos
                unidades = unidadeDao.buscarTodos();
            }

            request.setAttribute("unidades", unidades);

            // Mantém os parâmetros do filtro para mostrar no formulário
            request.setAttribute("filtroId", filtroId);
            request.setAttribute("filtroNome", filtroNome);
            request.setAttribute("filtroNomeSegmento", filtroNomeSegmento);
            request.setAttribute("filtroNomeEndereco", filtroNomeEndereco);

            RequestDispatcher rd = request.getRequestDispatcher("/html/area-restrita/paginas/unidade/processar_unidade.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Erro ao filtrar unidades: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            buscarTodos(request, response, "buscar", "buscar_todos");
        }
    }

    // Método encaminhar
    public void encaminhar(HttpServletRequest request, HttpServletResponse response, String jspErro) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(jspErro);
        if (rd != null) {
            rd.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao encaminhar");
        }
    }
}
