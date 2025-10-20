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
//
//        // Verificar se a tabela existe ao inicializar o servlet
//        System.out.println("Inicializando AdministradorServlet...");
//        administradorDAO.buscarTodos();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        System.out.println("Recebida requisição GET para /administradores"); // DEBUG
//
//        String action = request.getParameter("action");
//
//        if ("buscar".equals(action)) {
//            System.out.println("Ação: buscar"); // DEBUG
//            buscarAdministradores(request, response);
//        } else {
//            System.out.println("Ação: listar"); // DEBUG
//            listarAdministradores(request, response);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        System.out.println("Recebida requisição POST para /administradores"); // DEBUG
//
//        String action = request.getParameter("action");
//
//        if ("cadastrar".equals(action)) {
//            System.out.println("Ação: cadastrar"); // DEBUG
//            cadastrarAdministrador(request, response);
//        } else if ("editar".equals(action)) {
//            System.out.println("Ação: editar"); // DEBUG
//            editarAdministrador(request, response);
//        } else if ("excluir".equals(action)) {
//            System.out.println("Ação: excluir"); // DEBUG
//            excluirAdministrador(request, response);
//        } else {
//            // Se nenhuma ação específica, listar
//            listarAdministradores(request, response);
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
//            System.out.println("Filtros - ID: " + filterId + ", Nome: " + filterNome +
//                    ", CPF: " + filterCpf + ", Email: " + filterEmail); // DEBUG
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
//            System.out.println("Número de administradores encontrados: " +
//                    (administradores != null ? administradores.size() : "null")); // DEBUG
//
//            request.setAttribute("administradores", administradores);
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/Restricted-area/Pages/administrator.jsp");
//            dispatcher.forward(request, response);
//
//        } catch (Exception e) {
//            System.err.println("Erro ao listar administradores: " + e.getMessage()); // DEBUG
//            e.printStackTrace();
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar administradores: " + e.getMessage());
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
//            System.out.println("Cadastrando administrador - Nome: " + nomeCompleto +
//                    ", CPF: " + cpf + ", Email: " + email); // DEBUG
//
//            int novoId = gerarNovoId();
//
//            Administrador admin = new Administrador(novoId, cpf, nomeCompleto, email, senha);
//
//            boolean sucesso = administradorDAO.cadastrar(admin);
//
//            if (sucesso) {
//                System.out.println("Cadastro realizado com sucesso"); // DEBUG
//                response.sendRedirect("administradores?success=Cadastro realizado com sucesso");
//            } else {
//                System.out.println("Erro ao cadastrar"); // DEBUG
//                response.sendRedirect("administradores?error=Erro ao cadastrar administrador");
//            }
//
//        } catch (Exception e) {
//            System.err.println("Erro no cadastro: " + e.getMessage()); // DEBUG
//            e.printStackTrace();
//            response.sendRedirect("administradores?error=Erro interno do servidor: " + e.getMessage());
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
//            System.out.println("Editando administrador ID: " + id +
//                    " - Nome: " + nomeCompleto + ", CPF: " + cpf +
//                    ", Email: " + email); // DEBUG
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
//                System.out.println("Atualização realizada com sucesso"); // DEBUG
//                response.sendRedirect("administradores?success=Administrador atualizado com sucesso");
//            } else {
//                System.out.println("Erro na atualização"); // DEBUG
//                response.sendRedirect("administradores?error=Erro ao atualizar administrador");
//            }
//
//        } catch (Exception e) {
//            System.err.println("Erro na edição: " + e.getMessage()); // DEBUG
//            e.printStackTrace();
//            response.sendRedirect("administradores?error=Erro interno do servidor: " + e.getMessage());
//        }
//    }
//
//    private void excluirAdministrador(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        try {
//            int id = Integer.parseInt(request.getParameter("id"));
//
//            System.out.println("Excluindo administrador ID: " + id); // DEBUG
//
//            int resultado = administradorDAO.apagar(id);
//
//            if (resultado > 0) {
//                System.out.println("Exclusão realizada com sucesso"); // DEBUG
//                response.sendRedirect("administradores?success=Administrador excluído com sucesso");
//            } else {
//                System.out.println("Erro na exclusão"); // DEBUG
//                response.sendRedirect("administradores?error=Erro ao excluir administrador");
//            }
//
//        } catch (Exception e) {
//            System.err.println("Erro na exclusão: " + e.getMessage()); // DEBUG
//            e.printStackTrace();
//            response.sendRedirect("administradores?error=Erro interno do servidor: " + e.getMessage());
//        }
//    }
//
//    private int gerarNovoId() {
//        // Gera um ID baseado no timestamp para evitar conflitos
//        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
//    }
//}