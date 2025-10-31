package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import org.example.eitruck.dao.SegmentoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.eitruck.model.Segmento;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/servlet-segmentos")
public class SegmentoServlet extends HttpServlet {
    private SegmentoDAO segmentoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Inicializa o objeto ANTES de qualquer requisição.
        this.segmentoDAO = new SegmentoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        switch (acao != null ? acao : "listar") {
            case "editar":
                carregarSegmentoParaEdicao(request, response);
                break;
            case "buscar":
                buscarTodos(request, response, acao, "buscar_todos");
                break;
            case "filtrar":
                filtrarSegmentos(request, response);
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
                inserirSegmento(request, response, acao, request.getParameter("sub_acao"));
                break;
            case "atualizar":
                atualizarSegmento(request, response);
                break;
            case "excluir":
                excluirSegmento(request, response);
                break;
        }
    }

    // Método inserir
    private void inserirSegmento(HttpServletRequest request, HttpServletResponse response, String acao, String sub_acao) throws IOException, ServletException {
        String errorMessage = null;
        boolean success = false;
        boolean isFormSubmission = false;

        try {
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");

            System.out.println("Nome: " + nome);
            System.out.println("Descrição: " + descricao);

            Segmento segmento = new Segmento(nome, descricao);
            success = segmentoDAO.cadastrar(segmento);

            if (success) {
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos");
                    return;
                }
                return;
            } else {
                errorMessage = "Erro ao cadastrar analista no banco de dados.";
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
            errorMessage = "Erro ao cadastrar analista no banco de dados.";
        }

        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("isFormSubmission", isFormSubmission);

        request.setAttribute("nome", request.getParameter("nome"));
        request.setAttribute("descricao", request.getParameter("descricao"));

        request.setAttribute("sub_acao", sub_acao);

        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        RequestDispatcher respacher = request.getRequestDispatcher("html/area-restrita/paginas/segmento/segmento.jsp");
        if (respacher != null) {
            respacher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar analista");
        }
    }

    // Método excluir
    private void excluirSegmento(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int resultado = segmentoDAO.apagar(id);

            if (resultado > 0) {
                // Sucesso: redireciona para a lista sem mensagem de erro
                response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos");
            } else if (resultado == -2) {
                // Erro específico: segmento está sendo usado por uma unidade
                String errorMessage = URLEncoder.encode("Não é possível excluir esse segmento por ele estar instanciado em uma unidade.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            } else {
                // Erro genérico
                String errorMessage = URLEncoder.encode("Erro ao excluir segmento. Tente novamente.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro interno ao excluir segmento.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    // Método para carregar o Segmento para Edição
    private void carregarSegmentoParaEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            List<Segmento> segmentos = segmentoDAO.buscarPorId(id);

            if (segmentos != null && !segmentos.isEmpty()) {
                Segmento segmento = segmentos.get(0);
                request.setAttribute("segmento", segmento);
                RequestDispatcher rd = request.getRequestDispatcher("/html/area-restrita/paginas/segmento/editar_segmento.jsp");
                rd.forward(request, response);
            } else {
                String errorMessage = URLEncoder.encode("Segmento não encontrado.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro ao carregar segmento para edição.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    // Método atualizar
    private void atualizarSegmento(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String errorMessage = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");

            System.out.println("Atualizando segmento - ID: " + id + ", Nome: " + nome + ", Descrição: " + descricao);

            int resultado = segmentoDAO.alterarTodos(id, nome, descricao);

            if (resultado > 0) {
                response.sendRedirect(request.getContextPath() + "/servlet-segmentos?acao_principal=buscar&sub_acao=buscar_todos");
                return;
            } else {
                errorMessage = "Erro ao atualizar segmento no banco de dados.";
            }
        } catch (NumberFormatException e) {
            errorMessage = "ID inválido.";
        } catch (Exception e) {
            errorMessage = "Ocorreu um erro inesperado: " + e.getMessage();
            e.printStackTrace();
        }

        // Se houve erro, recarrega a página de edição com a mensagem de erro
        request.setAttribute("errorMessage", errorMessage);
        carregarSegmentoParaEdicao(request, response);
    }

    // Método para mostrar a tabela
    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws IOException, ServletException {
        try {
            List<Segmento> segmentos = segmentoDAO.buscarTodos();
            request.setAttribute("segmentos", segmentos);
            RequestDispatcher rd = request.getRequestDispatcher("/html/area-restrita/paginas/segmento/processar_segmento.jsp");
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
    private void filtrarSegmentos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String filtroId = request.getParameter("filtro_id");
            String filtroNome = request.getParameter("filtro_nome");
            String filtroDescricao = request.getParameter("filtro_descricao");

            List<Segmento> segmentos;

            // Verifica se pelo menos um filtro foi preenchido
            boolean algumFiltro = (filtroId != null && !filtroId.trim().isEmpty()) ||
                    (filtroNome != null && !filtroNome.trim().isEmpty()) ||
                    (filtroDescricao != null && !filtroDescricao.trim().isEmpty());

            if (algumFiltro) {
                // Busca com filtros
                segmentos = segmentoDAO.filtrarSegmentosMultiplos(filtroId, filtroNome, filtroDescricao);
            } else {
                // Se nenhum filtro, busca todos
                segmentos = segmentoDAO.buscarTodos();
            }

            request.setAttribute("segmentos", segmentos);

            // Mantém os parâmetros do filtro para mostrar no formulário
            request.setAttribute("filtroId", filtroId);
            request.setAttribute("filtroNome", filtroNome);
            request.setAttribute("filtroDescricao", filtroDescricao);

            RequestDispatcher rd = request.getRequestDispatcher("/html/area-restrita/paginas/segmento/processar_segmento.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Erro ao filtrar segmentos: " + e.getMessage();
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
