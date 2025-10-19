package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import org.example.eitruck.Dao.AnalistaDAO;
import org.example.eitruck.model.Analista;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/analistas")
public class AnalistaServlet extends HttpServlet {
    private AnalistaDAO analistaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        analistaDAO = new AnalistaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("buscar".equals(action)) {
            buscarAnalistas(request, response);
        } else {
            listarAnalistas(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("cadastrar".equals(action)) {
            cadastrarAnalista(request, response);
        } else if ("editar".equals(action)) {
            editarAnalista(request, response);
        } else if ("excluir".equals(action)) {
            excluirAnalista(request, response);
        }
    }

    private void listarAnalistas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String filterId = request.getParameter("filter-id");
            String filterIdUnidade = request.getParameter("filter-id_unidade");
            String filterNome = request.getParameter("filter-nome");
            String filterCargo = request.getParameter("filter-cargo");

            List<Analista> analistas;

            if (filterId != null && !filterId.isEmpty()) {
                analistas = analistaDAO.buscarPorId(Integer.parseInt(filterId));
            } else if (filterIdUnidade != null && !filterIdUnidade.isEmpty()) {
                analistas = analistaDAO.buscarPorIdUnidade(Integer.parseInt(filterIdUnidade));
            } else if (filterNome != null && !filterNome.isEmpty()) {
                analistas = analistaDAO.buscarPorNome(filterNome);
            } else if (filterCargo != null && !filterCargo.isEmpty()) {
                analistas = analistaDAO.buscarPorCargo(filterCargo);
            } else {
                analistas = analistaDAO.buscarTodos();
            }

            request.setAttribute("analistas", analistas);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Restricted-area/Pages/analyst.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar analistas");
        }
    }

    private void buscarAnalistas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        listarAnalistas(request, response);
    }

    private void cadastrarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idUnidade = Integer.parseInt(request.getParameter("id_unidade"));
            String cpf = request.getParameter("cpf");
            String nomeCompleto = request.getParameter("nome_completo");
            String email = request.getParameter("email");
            LocalDate dtContratacao = LocalDate.parse(request.getParameter("dt_contratacao"));
            String senha = request.getParameter("senha");
            String cargo = request.getParameter("cargo");

            int novoId = gerarNovoId();

            Analista analista = new Analista(novoId, idUnidade, cpf, nomeCompleto, dtContratacao, email, senha, cargo, null);

            boolean sucesso = analistaDAO.cadastrar(analista);

            if (sucesso) {
                response.sendRedirect("analistas?success=Cadastro realizado com sucesso");
            } else {
                response.sendRedirect("analistas?error=Erro ao cadastrar analista");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("analistas?error=Erro interno do servidor");
        }
    }

    private void editarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int idUnidade = Integer.parseInt(request.getParameter("id_unidade"));
            String cpf = request.getParameter("cpf");
            String nomeCompleto = request.getParameter("nome_completo");
            String email = request.getParameter("email");
            LocalDate dtContratacao = LocalDate.parse(request.getParameter("dt_contratacao"));
            String cargo = request.getParameter("cargo");
            String senha = request.getParameter("senha");

            List<Analista> analistas = analistaDAO.buscarPorId(id);
            if (analistas == null || analistas.isEmpty()) {
                response.sendRedirect("analistas?error=Analista não encontrado");
                return;
            }

            Analista analista = analistas.get(0);

            // Atualiza campos no objeto
            analista.setIdUnidade(idUnidade);
            analista.setCpf(cpf);
            analista.setNomeCompleto(nomeCompleto);
            analista.setEmail(email);
            analista.setDtContratacao(dtContratacao);
            analista.setCargo(cargo);

            // Chama o método consolidado do DAO
            boolean atualizado = analistaDAO.atualizar(analista, (senha != null && !senha.isEmpty()) ? senha : null);

            if (atualizado) {
                response.sendRedirect("analistas?success=Analista atualizado com sucesso");
            } else {
                response.sendRedirect("analistas?error=Erro ao atualizar analista");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("analistas?error=Erro interno do servidor");
        }
    }

    private void excluirAnalista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            int resultado = analistaDAO.apagar(id);

            if (resultado > 0) {
                response.sendRedirect("analistas?success=Analista excluído com sucesso");
            } else {
                response.sendRedirect("analistas?error=Erro ao excluir analista");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("analistas?error=Erro interno do servidor");
        }
    }

    private int gerarNovoId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}