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
        String acao = request.getParameter("acao");

        switch (acao != null ? acao : "listar") {
            case "editar":
                carregarAnalistaParaEdicao(request, response);
                break;
            case "buscar":
                buscarTodos(request, response, acao, "buscar_todos");
                break;
            case "filtrar":
                filtrarAnalistas(request, response);
                break;
            case "listar":
            default:
                buscarTodos(request, response, acao, "buscar_todos");
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

    private void carregarAnalistaParaEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            List<Analista> analistas = analistaDAO.buscarPorId(id);

            if (analistas != null && !analistas.isEmpty()) {
                Analista analista = analistas.get(0);
                request.setAttribute("analista", analista);
                RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Analyst/editar_analista.jsp");
                rd.forward(request, response);
            } else {
                String errorMessage = URLEncoder.encode("Analista não encontrado.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-analista?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-analista?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro ao carregar analista para edição.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-analista?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    private void atualizarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String errorMessage = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int idUnidade = Integer.parseInt(request.getParameter("id_unidade"));
            String cpf = request.getParameter("cpf");
            String nomeCompleto = request.getParameter("nome_completo");
            String email = request.getParameter("email");
            String dataContratacaoStr = request.getParameter("data_contratacao");
            String cargo = request.getParameter("cargo");
            String telefone = request.getParameter("telefone");

            // Validações
            if (dataContratacaoStr == null || dataContratacaoStr.trim().isEmpty()) {
                errorMessage = "Data de contratação é obrigatória.";
            } else if (cpf == null || cpf.replaceAll("[^0-9]", "").length() < 8) {
                errorMessage = "CPF deve ter pelo menos 8 dígitos numéricos.";
            } else if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                errorMessage = "Email inválido. Deve conter @ e domínio.";
            } else if (telefone == null || !telefone.matches("^[0-9]{10,11}$")) {
                errorMessage = "Telefone inválido. Deve conter pelo menos 10 números";
            } else {
                LocalDate dataContratacao = LocalDate.parse(dataContratacaoStr, DateTimeFormatter.ISO_LOCAL_DATE);

                // Buscar a senha atual para não alterá-la
                List<Analista> analistaAtual = analistaDAO.buscarPorId(id);
                if (analistaAtual != null && !analistaAtual.isEmpty()) {
                    String senhaAtual = analistaAtual.get(0).getSenha();

                    int resultado = analistaDAO.alterarTodos(id, idUnidade, cpf, nomeCompleto, dataContratacao, email, senhaAtual, cargo, telefone);

                    if (resultado > 0) {
                        response.sendRedirect(request.getContextPath() + "/servlet-analista?acao_principal=buscar&sub_acao=buscar_todos");
                        return;
                    } else {
                        errorMessage = "Erro ao atualizar analista no banco de dados.";
                    }
                } else {
                    errorMessage = "Analista não encontrado.";
                }
            }
        } catch (NumberFormatException e) {
            errorMessage = "ID da unidade deve ser um número válido.";
        } catch (DateTimeParseException e) {
            errorMessage = "Data de contratação inválida. Use o formato AAAA-MM-DD.";
        } catch (Exception e) {
            errorMessage = "Ocorreu um erro inesperado: " + e.getMessage();
            e.printStackTrace();
        }

        // Se houve erro, recarrega a página de edição com a mensagem de erro
        request.setAttribute("errorMessage", errorMessage);
        carregarAnalistaParaEdicao(request, response);
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

    private void filtrarAnalistas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String filtroId = request.getParameter("filtro_id");
            String filtroNomeUnidade = request.getParameter("filtro_nome_unidade");
            String filtroNomeCompleto = request.getParameter("filtro_nome_completo");
            String filtroCpf = request.getParameter("filtro_cpf");
            String filtroEmail = request.getParameter("filtro_email");
            String filtroCargo = request.getParameter("filtro_cargo");

            List<Analista> analistas;

            // Verifica se pelo menos um filtro foi preenchido
            boolean algumFiltro = (filtroId != null && !filtroId.trim().isEmpty()) ||
                    (filtroNomeUnidade != null && !filtroNomeUnidade.trim().isEmpty()) ||
                    (filtroNomeCompleto != null && !filtroNomeCompleto.trim().isEmpty()) ||
                    (filtroCpf != null && !filtroCpf.trim().isEmpty()) ||
                    (filtroEmail != null && !filtroEmail.trim().isEmpty()) ||
                    (filtroCargo != null && !filtroCargo.trim().isEmpty());

            if (algumFiltro) {
                // Busca com filtros
                analistas = analistaDAO.filtrarAnalistasMultiplos(filtroId, filtroNomeUnidade, filtroNomeCompleto,
                        filtroCpf, filtroEmail, filtroCargo);
            } else {
                // Se nenhum filtro, busca todos
                analistas = analistaDAO.buscarTodos();
            }

            request.setAttribute("analistas", analistas);

            // Mantém os parâmetros do filtro para mostrar no formulário
            request.setAttribute("filtroId", filtroId);
            request.setAttribute("filtroNomeUnidade", filtroNomeUnidade);
            request.setAttribute("filtroNomeCompleto", filtroNomeCompleto);
            request.setAttribute("filtroCpf", filtroCpf);
            request.setAttribute("filtroEmail", filtroEmail);
            request.setAttribute("filtroCargo", filtroCargo);

            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Analyst/processar_analista.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Erro ao filtrar analistas: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            buscarTodos(request, response, "buscar", "buscar_todos");
        }
    }

    public void encaminhar(HttpServletRequest request, HttpServletResponse response, String jspErro) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(jspErro);
        if (rd != null) {
            rd.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao encaminhar");
        }
    }
}