package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import org.example.eitruck.Dao.AnalistaDAO;
import org.example.eitruck.Dao.TipoOcorrenciaDAO;
import org.example.eitruck.model.Analista;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.eitruck.model.TipoOcorrencia;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/servlet-ocorrencias")
public class TipoOcorrenciaServlet extends HttpServlet {
    private TipoOcorrenciaDAO tipoOcorrenciaDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Inicializa o objeto ANTES de qualquer requisição.
        this.tipoOcorrenciaDAO = new TipoOcorrenciaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        switch (acao != null ? acao : "listar") {
            case "buscar":
                buscarTodos(request, response, acao, "buscar_todos");
                break;
            case "filtrar": // NOVO CASO PARA FILTRAR
                filtrarTiposOcorrencia(request, response);
                break;
            case "listar":
            default:
                buscarTodos(request, response, acao, "buscar_todos");
        }
    }

    private void filtrarTiposOcorrencia(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String filtroId = request.getParameter("filtro_id");
            String filtroTipoEvento = request.getParameter("filtro_tipo_evento");
            String filtroPontuacao = request.getParameter("filtro_pontuacao");
            String filtroGravidade = request.getParameter("filtro_gravidade");

            List<TipoOcorrencia> ocorrencias;

            // Verifica se pelo menos um filtro foi preenchido
            boolean algumFiltro = (filtroId != null && !filtroId.trim().isEmpty()) ||
                    (filtroTipoEvento != null && !filtroTipoEvento.trim().isEmpty()) ||
                    (filtroPontuacao != null && !filtroPontuacao.trim().isEmpty()) ||
                    (filtroGravidade != null && !filtroGravidade.trim().isEmpty());

            if (algumFiltro) {
                // Busca com filtros
                ocorrencias = tipoOcorrenciaDAO.filtrarTiposOcorrenciaMultiplos(filtroId, filtroTipoEvento,
                        filtroPontuacao, filtroGravidade);
            } else {
                // Se nenhum filtro, busca todos
                ocorrencias = tipoOcorrenciaDAO.buscarTodos();
            }

            request.setAttribute("ocorrencias", ocorrencias);

            // Mantém os parâmetros do filtro para mostrar no formulário
            request.setAttribute("filtroId", filtroId);
            request.setAttribute("filtroTipoEvento", filtroTipoEvento);
            request.setAttribute("filtroPontuacao", filtroPontuacao);
            request.setAttribute("filtroGravidade", filtroGravidade);

            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Occurrences/processar_occurrences.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Erro ao filtrar tipos de ocorrência: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            buscarTodos(request, response, "buscar", "buscar_todos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao_principal");
        String sub_acao = request.getParameter("sub_acao");

        switch (acao) {
            case "inserir":
                inserirTipoOcorrencia(request, response, acao, sub_acao);
                break;
            case "atualizar":
                atualizarAnalista(request, response);
                break;
            case "excluir":
                excluirOcorrencia(request, response);
                break;
        }
    }

    private void inserirTipoOcorrencia(HttpServletRequest request, HttpServletResponse response, String acao, String sub_acao) throws IOException, ServletException {
        String errorMessage = null;
        boolean success = false;
        boolean isFormSubmission = false;

        try {
            String tipo_evento = request.getParameter("tipo_evento");
            String pontuacao = request.getParameter("pontuacao");
            String gravidade = request.getParameter("gravidade");

            System.out.println("Tipo de evento: " + tipo_evento);
            System.out.println("Pontuacao: " + pontuacao);
            System.out.println("Gravidade: " + gravidade);

            int pontuacaoInt = Integer.parseInt(pontuacao);

            TipoOcorrencia tipoOcorrencia = new TipoOcorrencia(tipo_evento, pontuacaoInt, gravidade);
            success = tipoOcorrenciaDAO.cadastrar(tipoOcorrencia);

            if (success) {
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/servlet-ocorrencias?acao_principal=buscar&sub_acao=buscar_todos");
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

        request.setAttribute("tipo_evento", request.getParameter("tipo_evento"));
        request.setAttribute("pontuacao", request.getParameter("pontuacao"));
        request.setAttribute("gravidade", request.getParameter("gravidade"));

        request.setAttribute("sub_acao", sub_acao);

        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        RequestDispatcher respacher = request.getRequestDispatcher("html/Restricted-area/Pages/Occurrences/occurrences.jsp");
        if (respacher != null) {
            respacher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar analista");
        }
    }

    private void excluirOcorrencia(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int resultado = tipoOcorrenciaDAO.apagar(id);

            if (resultado > 0) {
                // Sucesso: redireciona para a lista sem mensagem de erro
                response.sendRedirect(request.getContextPath() + "/servlet-ocorrencias?acao_principal=buscar&sub_acao=buscar_todos");
            } else {
                // Erro: redireciona com mensagem de erro codificada
                String errorMessage = URLEncoder.encode("Erro ao excluir tipo de ocorrência. Tente novamente.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-ocorrencias?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-ocorrencias?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro interno ao excluir tipo de ocorrência.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-ocorrencias?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws IOException, ServletException {
        try {
            List<TipoOcorrencia> tipoOcorrencia = tipoOcorrenciaDAO.buscarTodos();
            request.setAttribute("ocorrencias", tipoOcorrencia);
            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Occurrences/processar_occurrences.jsp");
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
