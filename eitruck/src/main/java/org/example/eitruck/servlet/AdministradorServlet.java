//package org.example.eitruck.servlet;
//
//import jakarta.servlet.RequestDispatcher;
//import org.example.eitruck.Dao.AdministradorDAO;
//import org.example.eitruck.model.Administrador;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/administradores")
//public class AdministradorServlet extends HttpServlet {
//    private AdministradorDAO administradorDAO;
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        administradorDAO = new AdministradorDAO();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String action = request.getParameter("action");
//
//        if ("buscar".equals(action)) {
//            buscarAdministradores(request, response);
//        } else {
//            listarAdministradores(request, response);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String action = request.getParameter("action");
//
//        if ("cadastrar".equals(action)) {
//            cadastrarAdministrador(request, response);
//        } else if ("editar".equals(action)) {
//            editarAdministrador(request, response);
//        } else if ("excluir".equals(action)) {
//            excluirAdministrador(request, response);
//        }
//    }
//
//    private void listarAdministradores(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        try {
//            String filterId = request.getParameter("filter-id");
//            String filterNome = request.getParameter("filter-nome");
//            String filterCpf = request.getParameter("filter-cpf");
//            String filterEmail = request.getParameter("filter-email");
//
//            List<Administrador> administradores;
//
//            if (filterId != null && !filterId.isEmpty()) {
//                administradores = administradorDAO.buscarPorId(Integer.parseInt(filterId));
//            } else if (filterCpf != null && !filterCpf.isEmpty()) {
//                administradores = administradorDAO.buscarPorCpf(filterCpf);
//            } else if (filterNome != null && !filterNome.isEmpty()) {
//                administradores = administradorDAO.buscarNomeCompleto(filterNome);
//            } else if (filterEmail != null && !filterEmail.isEmpty()) {
//                administradores = administradorDAO.buscarPorEmail(filterEmail);
//            } else {
//                administradores = administradorDAO.buscarTodos();
//            }
//
//            request.setAttribute("administradores", administradores);
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/Restricted-area/Pages/administrator.jsp");
//            dispatcher.forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar administradores");
//        }
//    }
//
//    private void buscarAdministradores(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        listarAdministradores(request, response);
//    }
//
//    private void cadastrarAdministrador(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        try {
//            String nomeCompleto = request.getParameter("nome_completo");
//            String cpf = request.getParameter("cpf");
//            String email = request.getParameter("email");
//            String senha = request.getParameter("senha");
//
//            int novoId = gerarNovoId();
//
//            Administrador admin = new Administrador(novoId, cpf, nomeCompleto, email, senha);
//
//            boolean sucesso = administradorDAO.cadastrar(admin);
//
//            if (sucesso) {
//                response.sendRedirect("administradores?success=Cadastro realizado com sucesso");
//            } else {
//                response.sendRedirect("administradores?error=Erro ao cadastrar administrador");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("administradores?error=Erro interno do servidor");
//        }
//    }
//
//    private void editarAdministrador(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        try {
//            int id = Integer.parseInt(request.getParameter("id"));
//            String nomeCompleto = request.getParameter("nome_completo");
//            String cpf = request.getParameter("cpf");
//            String email = request.getParameter("email");
//            String senha = request.getParameter("senha");
//
//            List<Administrador> administradores = administradorDAO.buscarPorId(id);
//            if (administradores == null || administradores.isEmpty()) {
//                response.sendRedirect("administradores?error=Administrador não encontrado");
//                return;
//            }
//
//            Administrador admin = administradores.get(0);
//
//            // Atualiza campos no objeto
//            admin.setNomeCompleto(nomeCompleto);
//            admin.setCpf(cpf);
//            admin.setEmail(email);
//
//            // Chama o método consolidado do DAO
//            boolean atualizado = administradorDAO.atualizar(admin, (senha != null && !senha.isEmpty()) ? senha : null);
//
//            if (atualizado) {
//                response.sendRedirect("administradores?success=Administrador atualizado com sucesso");
//            } else {
//                response.sendRedirect("administradores?error=Erro ao atualizar administrador");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("administradores?error=Erro interno do servidor");
//        }
//    }
//
//    private void excluirAdministrador(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        try {
//            int id = Integer.parseInt(request.getParameter("id"));
//
//            int resultado = administradorDAO.apagar(id);
//
//            if (resultado > 0) {
//                response.sendRedirect("administradores?success=Administrador excluído com sucesso");
//            } else {
//                response.sendRedirect("administradores?error=Erro ao excluir administrador");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("administradores?error=Erro interno do servidor");
//        }
//    }
//
//    private int gerarNovoId() {
//        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
//    }
//}
