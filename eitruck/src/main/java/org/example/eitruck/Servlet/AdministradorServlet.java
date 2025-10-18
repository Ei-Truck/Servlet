package org.example.eitruck.Servlet;

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
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("buscar".equals(action)) {
            buscarAdministradores(request, response);
        } else {
            listarAdministradores(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("cadastrar".equals(action)) {
            cadastrarAdministrador(request, response);
        } else if ("editar".equals(action)) {
            editarAdministrador(request, response);
        } else if ("excluir".equals(action)) {
            excluirAdministrador(request, response);
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

            if (filterId != null && !filterId.isEmpty()) {
                administradores = administradorDAO.buscarPorId(Integer.parseInt(filterId));
            } else if (filterCpf != null && !filterCpf.isEmpty()) {
                administradores = administradorDAO.buscarPorCpf(filterCpf);
            } else if (filterNome != null && !filterNome.isEmpty()) {
                administradores = administradorDAO.buscarNomeCompleto(filterNome);
            } else if (filterEmail != null && !filterEmail.isEmpty()) {
                administradores = administradorDAO.buscarPorEmail(filterEmail);
            } else {
                // Buscar todos
                administradores = buscarTodosAdministradores();
            }

            request.setAttribute("administradores", administradores);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Restricted-area/Pages/administrator.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar administradores");
        }
    }

    private void buscarAdministradores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementar busca específica
        listarAdministradores(request, response);
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

            // Buscar o administrador existente
            List<Administrador> administradores = administradorDAO.buscarPorId(id);
            if (administradores == null || administradores.isEmpty()) {
                response.sendRedirect("administradores?error=Administrador não encontrado");
                return;
            }

            Administrador admin = administradores.get(0);

            // Atualizar campos
            int resultado = administradorDAO.alterarNomeCompleto(admin, nomeCompleto);
            if (resultado <= 0) {
                response.sendRedirect("administradores?error=Erro ao atualizar nome");
                return;
            }

            resultado = administradorDAO.alterarEmail(admin, email);
            if (resultado <= 0) {
                response.sendRedirect("administradores?error=Erro ao atualizar email");
                return;
            }

            if (senha != null && !senha.isEmpty()) {
                resultado = administradorDAO.alterarSenha(admin, senha);
                if (resultado <= 0) {
                    response.sendRedirect("administradores?error=Erro ao atualizar senha");
                    return;
                }
            }

            response.sendRedirect("administradores?success=Administrador atualizado com sucesso");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }

    private void excluirAdministrador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            // Buscar o administrador para passar como parâmetro
            List<Administrador> administradores = administradorDAO.buscarPorId(id);
            if (administradores == null || administradores.isEmpty()) {
                response.sendRedirect("administradores?error=Administrador não encontrado");
                return;
            }

            Administrador admin = administradores.get(0);
            int resultado = administradorDAO.apagar(admin, id);

            if (resultado > 0) {
                response.sendRedirect("administradores?success=Administrador excluído com sucesso");
            } else {
                response.sendRedirect("administradores?error=Erro ao excluir administrador");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }

    private List<Administrador> buscarTodosAdministradores() {
        return java.util.Collections.emptyList();
    }

    private int gerarNovoId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}