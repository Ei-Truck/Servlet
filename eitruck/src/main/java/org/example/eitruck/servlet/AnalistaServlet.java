package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import org.example.eitruck.Dao.AnalistaDAO;
import org.example.eitruck.model.Analista;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/servlet-analista")
public class AnalistaServlet extends HttpServlet {

    private AnalistaDAO analistaDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Inicializa o objeto ANTES de qualquer requisição.
        this.analistaDAO = new AnalistaDAO();
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
                inserirAnalista(request, response, acao, sub_acao);
                break;
            case "atualizar":
                atualizarAnalista(request, response);
                break;
            case "excluir":
                excluirAnalista(request, response);
                break;
        }
    }

    private void listarAnalistas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Analista> analistas = analistaDAO.buscarTodos();
        request.setAttribute("analistas", analistas);
        request.getRequestDispatcher("html/Restricted-area/Pages/Analyst/processar_analista.jsp").forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/analista/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Analista> analistas = analistaDAO.buscarPorId(id);

        if (!analistas.isEmpty()) {
            request.setAttribute("analista", analistas.get(0));
            request.getRequestDispatcher("/WEB-INF/analista/formulario.jsp").forward(request, response);
        } else {
            response.sendRedirect("analista?erro=Analista não encontrado");
        }
    }

    private void inserirAnalista(HttpServletRequest request, HttpServletResponse response, String acao, String sub_acao) throws IOException, ServletException {
        String errorMessage = null;
        boolean success = false;
        boolean isFormSubmission = false;

        try {
            String idUnidade = request.getParameter("id_unidade");
            String cpf = request.getParameter("cpf");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String data_contratacao = request.getParameter("data_contratacao");
            String senha = request.getParameter("senha");
            String cargo = request.getParameter("cargo");
            String telefone = request.getParameter("telefone");

            System.out.println("Id de unidade: " + idUnidade);
            System.out.println("Cpf: " + cpf);
            System.out.println("Nome: " + nome);
            System.out.println("Email: " + email);
            System.out.println("Data de contratacao: " + data_contratacao);
            System.out.println("Senha: " + senha);
            System.out.println("Cargo: " + cargo);
            System.out.println("Telefone: " + telefone);

            // 1. ADICIONAR O NULL CHECK AQUI:
            if (data_contratacao == null || data_contratacao.trim().isEmpty()) {
                // Se for nulo/vazio, trate como erro do cliente (Bad Request)
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Data de contratação é obrigatória.");
                return;
            }

            // 2. Tente fazer a conversão AGORA:
            int id_unidade = Integer.parseInt(idUnidade);
            LocalDate data_contratacaoDate = LocalDate.parse(data_contratacao, DateTimeFormatter.ISO_LOCAL_DATE); // Linha 113 agora segura contra null

            Analista analista = new Analista(id_unidade, cpf, nome, email, data_contratacaoDate, senha, cargo, telefone);
            success = analistaDAO.cadastrar(analista);

            if (success) {
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/servlet-analista?acao_principal=buscar&sub_acao=buscar_todos");
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

        // Mantém os dados do formulário para repopular
        request.setAttribute("id_unidade", request.getParameter("id_unidade"));
        request.setAttribute("cpf", request.getParameter("cpf"));
        request.setAttribute("nome", request.getParameter("nome"));
        request.setAttribute("email", request.getParameter("email"));
        request.setAttribute("data_contratacao", request.getParameter("data_contratacao"));
        request.setAttribute("cargo", request.getParameter("cargo"));
        request.setAttribute("telefone", request.getParameter("telefone"));

        request.setAttribute("sub_acao", sub_acao);

        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("html/Restricted-area/Pages/Analyst/analista.jsp"); // substitua pelo nome do seu JSP atual
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar analista");
        }
    }

    private void excluirAnalista(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int resultado = analistaDAO.apagar(id);

            if (resultado > 0) {
                // Sucesso: redireciona para a lista sem mensagem de erro
                response.sendRedirect(request.getContextPath() + "/servlet-analista?acao_principal=buscar&sub_acao=buscar_todos");
            } else {
                // Erro: redireciona com mensagem de erro codificada
                String errorMessage = URLEncoder.encode("Erro ao excluir analista. Tente novamente.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-analista?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-analista?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro interno ao excluir analista.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-analista?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

//    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
//            throws IOException, ServletException {
//        try {
//            List<Analista> analistas = analistaDAO.buscarTodos();
//            request.setAttribute("analistas", analistas);
//
//            encaminhar(request, response, "/html/Restricted-area/Pages/Analyst/processar_analista.jsp");
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        request.setAttribute("sub_acao", subAcao);
//
//        if (acao != null) {
//            request.setAttribute("acao", acao);
//        }
//
//        encaminhar(request, response, "Erro.jsp");
//    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws IOException, ServletException {
        try {
            List<Analista> analistas = analistaDAO.buscarTodos();
            request.setAttribute("analistas", analistas);
            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Analyst/processar_analista.jsp");
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
        try {
            Analista analista = extrairAnalistaDoRequest(request);
            String novaSenha = request.getParameter("novaSenha");

            if (analistaDAO.atualizar(analista, novaSenha)) {
                response.sendRedirect("analista?sucesso=Analista atualizado com sucesso");
            } else {
                response.sendRedirect("analista?erro=Erro ao atualizar analista");
            }
        } catch (Exception e) {
            response.sendRedirect("analista?erro=" + e.getMessage());
        }
    }

    private void buscarAnalista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("tipo");
        String valor = request.getParameter("valor");
        List<Analista> resultados = null;

        // CORREÇÃO: Trate 'tipo' nulo com uma string padrão ("erro_busca")
        switch (tipo != null ? tipo : "erro_busca") {
            case "id":
                resultados = analistaDAO.buscarPorId(Integer.parseInt(valor));
                break;
            case "cpf":
                resultados = analistaDAO.buscarPorCpf(valor);
                break;
            case "nome":
                resultados = analistaDAO.buscarPorNome(valor);
                break;
            case "email":
                resultados = analistaDAO.buscarPorEmail(valor);
                break;
            case "cargo":
                resultados = analistaDAO.buscarPorCargo(valor);
                break;
            case "unidade":
                resultados = analistaDAO.buscarPorIdUnidade(Integer.parseInt(valor));
                break;
            case "erro_busca":
                // Tratar erro ou apenas retornar lista vazia
                resultados = analistaDAO.buscarTodos(); // Exibe todos se a busca falhar
                break;
        }

        request.setAttribute("analistas", resultados);
        request.setAttribute("resultadoBusca", true);
        request.getRequestDispatcher("html/Restricted-area/Pages/Analyst/processar_analista.jsp").forward(request, response);
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