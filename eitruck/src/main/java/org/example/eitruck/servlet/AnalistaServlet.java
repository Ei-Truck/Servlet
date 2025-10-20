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
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/servlet-analista")
public class AnalistaServlet extends HttpServlet {
    private AnalistaDAO analistaDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        switch (acao != null ? acao : "listar") {
            case "novo":
                mostrarFormularioNovo(request, response);
                break;
            case "editar":
                mostrarFormularioEditar(request, response);
                break;
            case "buscar":
                buscarAnalista(request, response);
                break;
            default:
                listarAnalistas(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
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
        request.getRequestDispatcher("/WEB-INF/analista/lista.jsp").forward(request, response);
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
        try {
            String idUnidade = request.getParameter("id_unidade");
            String cpf = request.getParameter("cpf");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String data_contratacao = request.getParameter("data_contratacao");
            String senha = request.getParameter("senha");
            String cargo = request.getParameter("cargo");

            System.out.println("Id de unidade: " + idUnidade);
            System.out.println("Cpf: " + cpf);
            System.out.println("Nome: " + nome);
            System.out.println("Email: " + email);
            System.out.println("Data de contratacao: " + data_contratacao);
            System.out.println("Senha: " + senha);
            System.out.println("Cargo: " + cargo);

            int id_unidade = Integer.parseInt(idUnidade);
            Date data_contratacaoDate = Date.valueOf(data_contratacao);

            Analista analista = new Analista(id_unidade, cpf, nome, email, data_contratacaoDate, senha, cargo);
            analistaDAO.cadastrar(analista);

            RequestDispatcher rd = request.getRequestDispatcher("Erro.jsp");
            if (rd != null) {
                rd.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar analista");
            }
            return;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        request.setAttribute("sub_acao", sub_acao);

        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        RequestDispatcher respacher = request.getRequestDispatcher("Erro.jsp");
        if (respacher != null) {
            respacher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar analista");
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

    private void excluirAnalista(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int resultado = analistaDAO.apagar(id);

        if (resultado > 0) {
            response.sendRedirect("analista?sucesso=Analista excluído com sucesso");
        } else {
            response.sendRedirect("analista?erro=Erro ao excluir analista");
        }
    }

    private void buscarAnalista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("tipo");
        String valor = request.getParameter("valor");
        List<Analista> resultados = null;

        switch (tipo) {
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
        }

        request.setAttribute("analistas", resultados);
        request.setAttribute("resultadoBusca", true);
        request.getRequestDispatcher("/WEB-INF/analista/lista.jsp").forward(request, response);
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