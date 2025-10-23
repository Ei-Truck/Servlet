package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import org.example.eitruck.Dao.AdministradorDAO;
import org.example.eitruck.Dao.AnalistaDAO;
import org.example.eitruck.model.Administrador;
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

@WebServlet("/servlet-administrador")
public class AdministradorServlet extends HttpServlet {

    private AdministradorDAO administradorDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Inicializa o objeto ANTES de qualquer requisição.
        this.administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        switch (acao != null ? acao : "listar") {
            case "buscar":
                buscarTodos(request, response, acao, "buscar_todos");
                break;
            case "filtrar": // NOVO CASO PARA FILTRAR
                filtrarAdministradores(request, response);
                break;
            case "listar":
            default:
                buscarTodos(request, response, acao, "buscar_todos");
        }
    }

    private void filtrarAdministradores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String filtroId = request.getParameter("filtro_id");
            String filtroNome = request.getParameter("filtro_nome");
            String filtroCpf = request.getParameter("filtro_cpf");
            String filtroEmail = request.getParameter("filtro_email");
            String filtroTelefone = request.getParameter("filtro_telefone");

            List<Administrador> administradores;

            // Verifica se pelo menos um filtro foi preenchido
            boolean algumFiltro = (filtroId != null && !filtroId.trim().isEmpty()) ||
                    (filtroNome != null && !filtroNome.trim().isEmpty()) ||
                    (filtroCpf != null && !filtroCpf.trim().isEmpty()) ||
                    (filtroEmail != null && !filtroEmail.trim().isEmpty()) ||
                    (filtroTelefone != null && !filtroTelefone.trim().isEmpty());

            if (algumFiltro) {
                // Busca com filtros
                administradores = administradorDAO.filtrarAdministradoresMultiplos(filtroId, filtroNome, filtroCpf,
                        filtroEmail, filtroTelefone);
            } else {
                // Se nenhum filtro, busca todos
                administradores = administradorDAO.buscarTodos();
            }

            request.setAttribute("administradores", administradores);

            // Mantém os parâmetros do filtro para mostrar no formulário
            request.setAttribute("filtroId", filtroId);
            request.setAttribute("filtroNome", filtroNome);
            request.setAttribute("filtroCpf", filtroCpf);
            request.setAttribute("filtroEmail", filtroEmail);
            request.setAttribute("filtroTelefone", filtroTelefone);

            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Administrator/processar_administrador.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Erro ao filtrar administradores: " + e.getMessage();
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
                inserirAdministrador(request, response, acao, sub_acao);
                break;
            case "atualizar":
                atualizarAnalista(request, response);
                break;
            case "excluir":
                excluirAdministrador(request, response);
                break;
        }
    }

    private void inserirAdministrador(HttpServletRequest request, HttpServletResponse response, String acao, String sub_acao) throws IOException, ServletException {
        String errorMessage = null;
        boolean success = false;
        boolean isFormSubmission = false;

        try {
            String cpf = request.getParameter("cpf");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String telefone = request.getParameter("telefone");

            System.out.println("CPF: " + cpf);
            System.out.println("Nome: " + nome);
            System.out.println("Email: " + email);
            System.out.println("Senha: " + senha);
            System.out.println("Telefone: " + telefone);

            Administrador administrador = new Administrador(telefone, cpf, nome, email, senha);
            success = administradorDAO.cadastrar(administrador);

            if (success) {
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/servlet-administrador?acao_principal=buscar&sub_acao=buscar_todos");
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

        request.setAttribute("cpf", request.getParameter("cpf"));
        request.setAttribute("nome", request.getParameter("nome"));
        request.setAttribute("email", request.getParameter("email"));
        request.setAttribute("telefone", request.getParameter("telefone"));

        request.setAttribute("sub_acao", sub_acao);
        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        RequestDispatcher respacher = request.getRequestDispatcher("html/Restricted-area/Pages/Administrator/administrator.jsp");
        if (respacher != null) {
            respacher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar analista");
        }
    }

    private void excluirAdministrador(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int resultado = administradorDAO.apagar(id);

            if (resultado > 0) {
                // Sucesso: redireciona para a lista sem mensagem de erro
                response.sendRedirect(request.getContextPath() + "/servlet-administrador?acao_principal=buscar&sub_acao=buscar_todos");
            } else {
                // Erro: redireciona com mensagem de erro codificada
                String errorMessage = URLEncoder.encode("Erro ao excluir administrador. Tente novamente.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-administrador?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-administrador?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro interno ao excluir administrador.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-administrador?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws IOException, ServletException {
        try {
            List<Administrador> administradores = administradorDAO.buscarTodos();
            request.setAttribute("administradores", administradores);
            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Administrator/processar_administrador.jsp");
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