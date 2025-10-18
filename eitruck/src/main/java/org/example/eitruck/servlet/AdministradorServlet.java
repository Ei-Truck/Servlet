package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import org.example.eitruck.Dao.AdministradorDAO;
import org.example.eitruck.model.Administrador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.eitruck.Dao.AdministradorDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/administradores")
public class AdministradorServlet extends HttpServlet {
    private AdministradorDAO administradorDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            administradorDAO = new AdministradorDAO();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar AdministradorDAO: " + e.getMessage());
            throw new ServletException("Erro de inicialização do DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        listarAdministradores(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("cadastrar".equals(action)) {
            cadastrarAdministrador(request, response);
        } else if ("editar".equals(action)) {
            editarAdministrador(request, response);
        } else if ("excluir".equals(action)) {
            excluirAdministrador(request, response);
        } else {
            response.sendRedirect("administradores?error=Ação POST desconhecida");
        }
    }

    private void listarAdministradores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Aplicar filtros se existirem
            String filterId = request.getParameter("filter-id");
            String filterNome = request.getParameter("filter-nome");
            String filterCpf = request.getParameter("filter-cpf");
            String filterEmail = request.getParameter("filter-email");

            List<Administrador> administradores;

            if (filterId != null && !filterId.trim().isEmpty()) {
                administradores = administradorDAO.buscarPorId(Integer.parseInt(filterId.trim()));
            } else if (filterCpf != null && !filterCpf.trim().isEmpty()) {
                administradores = administradorDAO.buscarPorCpf(filterCpf.trim());
            } else if (filterNome != null && !filterNome.trim().isEmpty()) {
                administradores = administradorDAO.buscarNomeCompleto(filterNome.trim());
            } else if (filterEmail != null && !filterEmail.trim().isEmpty()) {
                administradores = administradorDAO.buscarPorEmail(filterEmail.trim());
            } else {
                // Buscar todos
                administradores = administradorDAO.buscarTodos();
            }

            request.setAttribute("administradores", administradores);
            RequestDispatcher dispatcher = request.getRequestDispatcher("src/main/webapp/html/Administrador.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("administradores?error=ID de filtro inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar administradores");
        }
    }

    private void cadastrarAdministrador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nomeCompleto = request.getParameter("nome_completo");
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            int novoId = gerarNovoId();

            Administrador admin = new Administrador(novoId, cpf, nomeCompleto, email, senha);

            boolean sucesso = administradorDAO.cadastrar(admin);

            if (sucesso) {
                response.sendRedirect("administradores?success=Cadastro realizado com sucesso");
            } else {
                response.sendRedirect("administradores?error=Erro ao cadastrar administrador");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("administradores?error=ID inválido para cadastro");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }

    private void editarAdministrador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nomeCompleto = request.getParameter("nome_completo");
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            Administrador admin = new Administrador(id, cpf, nomeCompleto, email, senha);

            boolean sucesso = administradorDAO.atualizar(admin, senha);

            if (sucesso) {
                response.sendRedirect("administradores?success=Administrador atualizado com sucesso");
            } else {
                response.sendRedirect("administradores?error=Erro ao atualizar administrador.");
            }
        } catch (NumberFormatException nfe) {
            response.sendRedirect("administradores?error=ID de administrador inválido para edição");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }

    private void excluirAdministrador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            int resultado = administradorDAO.apagar(id);

            if (resultado > 0) {
                response.sendRedirect("administradores?success=Administrador excluído com sucesso");
            } else {
                response.sendRedirect("administradores?error=Erro ao excluir administrador. ID não encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }



    private int gerarNovoId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}