package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import org.example.eitruck.Dao.AnalistaDAO;
import org.example.eitruck.Dao.SegmentoDAO;
import org.example.eitruck.model.Analista;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.eitruck.model.Segmento;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        String acao = request.getParameter("acao"); // Use apenas "acao" ou "acao_principal" de forma consistente
        // Mudei o parâmetro para "acao" para ser consistente com o switch

        switch (acao != null ? acao : "listar") {
            // ...
            case "buscar":
                // 1. Chame o método de busca específica
                buscarTodos(request, response, acao, "buscar_todos"); // <--- CHAMADA CORRETA PARA BUSCA ESPECÍFICA
                break;
            case "listar": // Caso padrão para listar todos
            default:
                // 2. Chame o método que busca todos e encaminha para o JSP
                buscarTodos(request, response, acao, "buscar_todos"); // Reutilizando seu método buscarTodos
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao_principal");
        String sub_acao = request.getParameter("sub_acao");

        switch (acao) {
            case "inserir":
                inserirSegmento(request, response, acao, sub_acao);
                break;
            case "atualizar":
                atualizarAnalista(request, response);
                break;
            case "excluir":
                excluirSegmento(request, response);
                break;
        }
    }

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

        RequestDispatcher respacher = request.getRequestDispatcher("html/Restricted-area/Pages/Segments/segments.jsp");
        if (respacher != null) {
            respacher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar analista");
        }
    }

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

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws IOException, ServletException {
        try {
            List<Segmento> segmentos = segmentoDAO.buscarTodos();
            request.setAttribute("segmentos", segmentos);
            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Segments/processar_segments.jsp");
            rd.forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("sub_acao", subAcao);

        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        encaminhar(request, response, "Erro.jsp");
    }

    public void encaminhar(HttpServletRequest request, HttpServletResponse response, String jspErro) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(jspErro);
        if (rd != null) {
            rd.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao encaminhar");
        }
    }

    private void atualizarAnalista(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
