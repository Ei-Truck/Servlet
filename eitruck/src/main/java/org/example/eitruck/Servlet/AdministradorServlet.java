package org.example.eitruck.Servlet;

import jakarta.servlet.RequestDispatcher;
import org.example.eitruck.Dao.AdministradorDAO;
import org.example.eitruck.model.Administrador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        listarAdministradores(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "cadastrar":
                cadastrarAdministrador(request, response);
                break;
            case "editarNome":
                editarNome(request, response);
                break;
            case "editarCpf":
                editarCpf(request, response);
                break;
            case "editarEmail":
                editarEmail(request, response);
                break;
            case "editarSenha":
                editarSenha(request, response);
                break;
            case "editarTudo":
                editarTudo(request, response);
                break;
            case "excluir":
                excluirAdministrador(request, response);
                break;
            default:
                response.sendRedirect("administradores?error=Ação desconhecida");
        }
    }

    private void listarAdministradores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
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
                administradores = administradorDAO.buscarPorEmailCorreto(filterEmail);
            } else {
                administradores = administradorDAO.buscarTodos();
            }

            request.setAttribute("administradores", administradores);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Restricted-area/Pages/administrator.jsp");
            dispatcher.forward(request, response);

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

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }

    private void editarNome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String novoNome = request.getParameter("nome_completo");

            List<Administrador> administradores = administradorDAO.buscarPorId(id);
            if (administradores == null || administradores.isEmpty()) {
                response.sendRedirect("administradores?error=Administrador não encontrado");
                return;
            }

            Administrador admin = administradores.get(0);
            int resultado = administradorDAO.alterarNomeCompleto(admin, novoNome);

            if (resultado > 0) {
                response.sendRedirect("administradores?success=Nome atualizado com sucesso");
            } else {
                response.sendRedirect("administradores?error=Erro ao atualizar nome");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }

    private void editarCpf(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String novoCpf = request.getParameter("cpf");

            List<Administrador> administradores = administradorDAO.buscarPorId(id);
            if (administradores == null || administradores.isEmpty()) {
                response.sendRedirect("administradores?error=Administrador não encontrado");
                return;
            }

            Administrador admin = administradores.get(0);
            int resultado = administradorDAO.alterarCpf(admin, novoCpf);

            if (resultado > 0) {
                response.sendRedirect("administradores?success=CPF atualizado com sucesso");
            } else {
                response.sendRedirect("administradores?error=Erro ao atualizar CPF");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }

    private void editarEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String novoEmail = request.getParameter("email");

            List<Administrador> administradores = administradorDAO.buscarPorId(id);
            if (administradores == null || administradores.isEmpty()) {
                response.sendRedirect("administradores?error=Administrador não encontrado");
                return;
            }

            Administrador admin = administradores.get(0);
            int resultado = administradorDAO.alterarEmail(admin, novoEmail);

            if (resultado > 0) {
                response.sendRedirect("administradores?success=E-mail atualizado com sucesso");
            } else {
                response.sendRedirect("administradores?error=Erro ao atualizar e-mail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }

    private void editarSenha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String novaSenha = request.getParameter("senha");

            List<Administrador> administradores = administradorDAO.buscarPorId(id);
            if (administradores == null || administradores.isEmpty()) {
                response.sendRedirect("administradores?error=Administrador não encontrado");
                return;
            }

            Administrador admin = administradores.get(0);
            int resultado = administradorDAO.alterarSenha(admin, novaSenha);

            if (resultado > 0) {
                response.sendRedirect("administradores?success=Senha atualizada com sucesso");
            } else {
                response.sendRedirect("administradores?error=Erro ao atualizar senha");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("administradores?error=Erro interno do servidor");
        }
    }

    private void editarTudo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nomeCompleto = request.getParameter("nome_completo");
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            List<Administrador> administradores = administradorDAO.buscarPorId(id);
            if (administradores == null || administradores.isEmpty()) {
                response.sendRedirect("administradores?error=Administrador não encontrado");
                return;
            }

            Administrador admin = administradores.get(0);

            // Atualizar nome
            int resultado = administradorDAO.alterarNomeCompleto(admin, nomeCompleto);
            if (resultado <= 0) {
                response.sendRedirect("administradores?error=Erro ao atualizar nome");
                return;
            }

            // Atualizar email
            resultado = administradorDAO.alterarEmail(admin, email);
            if (resultado <= 0) {
                response.sendRedirect("administradores?error=Erro ao atualizar email");
                return;
            }

            // Atualizar CPF
            resultado = administradorDAO.alterarCpf(admin, cpf);
            if (resultado <= 0) {
                response.sendRedirect("administradores?error=Erro ao atualizar CPF");
                return;
            }

            // Atualizar senha
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

    private int gerarNovoId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}