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

    private void listarAnalistas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<TipoOcorrencia> tipoOcorrencias = TipoOcorrenciaDAO.buscarTodos();
//        request.setAttribute("tipoOcorrencias", tipoOcorrencias);
//        request.getRequestDispatcher("").forward(request, response);
//        //todo colocar caminho certo
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("").forward(request, response);
//        //todo colocar caminho certo
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        List<TipoOcorrencia> tipoOcorrencias = TipoOcorrenciaDAO.buscarPorId(id);
//
//        if (!tipoOcorrencias.isEmpty()) {
//            request.setAttribute("tipoOcorrencia", tipoOcorrencias.get(0));
//            request.getRequestDispatcher("").forward(request, response);
//            //todo colocar caminho certo
//        } else {
//            response.sendRedirect("tipoOcorrencia?erro=tipoOcorrencia não encontrado");
//        }
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
//        try {
//            Analista analista = extrairAnalistaDoRequest(request);
//            String novaSenha = request.getParameter("novaSenha");
//
//            if (analistaDAO.atualizar(analista, novaSenha)) {
//                response.sendRedirect("analista?sucesso=Analista atualizado com sucesso");
//            } else {
//                response.sendRedirect("analista?erro=Erro ao atualizar analista");
//            }
//        } catch (Exception e) {
//            response.sendRedirect("analista?erro=" + e.getMessage());
//        }
    }

    private void excluirAnalista(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        int resultado = analistaDAO.apagar(id);
//
//        if (resultado > 0) {
//            response.sendRedirect("analista?sucesso=Analista excluído com sucesso");
//        } else {
//            response.sendRedirect("analista?erro=Erro ao excluir analista");
//        }
    }

    private void buscarAnalista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String tipo = request.getParameter("tipo");
//        String valor = request.getParameter("valor");
//        List<Analista> resultados = null;
//
//        // CORREÇÃO: Trate 'tipo' nulo com uma string padrão ("erro_busca")
//        switch (tipo != null ? tipo : "erro_busca") {
//            case "id":
//                resultados = analistaDAO.buscarPorId(Integer.parseInt(valor));
//                break;
//            case "cpf":
//                resultados = analistaDAO.buscarPorCpf(valor);
//                break;
//            case "nome":
//                resultados = analistaDAO.buscarPorNome(valor);
//                break;
//            case "email":
//                resultados = analistaDAO.buscarPorEmail(valor);
//                break;
//            case "cargo":
//                resultados = analistaDAO.buscarPorCargo(valor);
//                break;
//            case "unidade":
//                resultados = analistaDAO.buscarPorIdUnidade(Integer.parseInt(valor));
//                break;
//            case "erro_busca":
//                // Tratar erro ou apenas retornar lista vazia
//                resultados = analistaDAO.buscarTodos(); // Exibe todos se a busca falhar
//                break;
//        }
//
//        request.setAttribute("analistas", resultados);
//        request.setAttribute("resultadoBusca", true);
//        request.getRequestDispatcher("html/Restricted-area/Pages/Analyst/processar_analista.jsp").forward(request, response);
    }

    private Analista extrairAnalistaDoRequest(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int idUnidade = Integer.parseInt(request.getParameter("idUnidade"));
        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        LocalDate dtContratacao = LocalDate.parse(request.getParameter("dtContratacao"));
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cargo = request.getParameter("cargo");
        String telefone = request.getParameter("telefone");

        return new Analista(id, idUnidade, cpf, nome, dtContratacao, email, senha, cargo, telefone);
    }
}
