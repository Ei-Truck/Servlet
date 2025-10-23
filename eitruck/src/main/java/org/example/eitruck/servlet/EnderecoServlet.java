package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import org.example.eitruck.Dao.AnalistaDAO;
import org.example.eitruck.Dao.EnderecoDAO;
import org.example.eitruck.Dao.UnidadeDAO;
import org.example.eitruck.model.Analista;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.eitruck.model.Endereco;
import org.example.eitruck.model.Unidade;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/servlet-enderecos")
public class EnderecoServlet extends HttpServlet {
    private EnderecoDAO enderecoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Inicializa o objeto ANTES de qualquer requisição.
        this.enderecoDAO = new EnderecoDAO();
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
                inserirEndereco(request, response, acao, sub_acao);
                break;
            case "atualizar":
                atualizarAnalista(request, response);
                break;
            case "excluir":
                excluirEndereco(request, response);
                break;
        }
    }

    private void listarAnalistas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Analista> analistas = analistaDAO.buscarTodos();
//        request.setAttribute("analistas", analistas);
//        request.getRequestDispatcher("html/Restricted-area/Pages/Analyst/processar_analista.jsp").forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("/WEB-INF/analista/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        List<Analista> analistas = analistaDAO.buscarPorId(id);
//
//        if (!analistas.isEmpty()) {
//            request.setAttribute("analista", analistas.get(0));
//            request.getRequestDispatcher("/WEB-INF/analista/formulario.jsp").forward(request, response);
//        } else {
//            response.sendRedirect("analista?erro=Analista não encontrado");
//        }
    }

    private void inserirEndereco(HttpServletRequest request, HttpServletResponse response, String acao, String sub_acao) throws IOException, ServletException {
        String errorMessage = null;
        boolean success = false;
        boolean isFormSubmission = false;

        try {
            String cep = request.getParameter("cep");
            String rua = request.getParameter("rua");
            String numero = request.getParameter("numero");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String pais = request.getParameter("pais");

            System.out.println("CEP: " + cep);
            System.out.println("Rua: " + rua);
            System.out.println("Numero: " + numero);
            System.out.println("Bairro: " + bairro);
            System.out.println("Cidade: " + cidade);
            System.out.println("Estado: " + estado);
            System.out.println("País: " + pais);

            int numeroInt = Integer.parseInt(numero);

            Endereco endereco = new Endereco(cep, rua, numeroInt, bairro, cidade, estado, pais);
            success = enderecoDAO.cadastrar(endereco);

            if (success) {
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos");
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

        request.setAttribute("cep", request.getParameter("cep"));
        request.setAttribute("rua", request.getParameter("rua"));
        request.setAttribute("bairro", request.getParameter("bairro"));
        request.setAttribute("cidade", request.getParameter("cidade"));
        request.setAttribute("estado", request.getParameter("estado"));
        request.setAttribute("pais", request.getParameter("pais"));
        request.setAttribute("numero", request.getParameter("numero"));

        request.setAttribute("sub_acao", sub_acao);

        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        RequestDispatcher respacher = request.getRequestDispatcher("html/Restricted-area/Pages/Addresses/addresses.jsp");
        if (respacher != null) {
            respacher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar analista");
        }
    }

    private void excluirEndereco(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int resultado = enderecoDAO.apagar(id);

            if (resultado > 0) {
                // Sucesso: redireciona para a lista sem mensagem de erro
                response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos");
            } else if (resultado == -2) {
                // Erro específico: endereço está sendo usado por uma unidade
                String errorMessage = URLEncoder.encode("Não é possível excluir esse endereço por ele estar instanciado em uma unidade.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            } else {
                // Erro genérico
                String errorMessage = URLEncoder.encode("Erro ao excluir endereço. Tente novamente.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro interno ao excluir endereço.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws IOException, ServletException {
        try {
            List<Endereco> enderecos = enderecoDAO.buscarTodos();
            request.setAttribute("enderecos", enderecos);
            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Addresses/processar_addresses.jsp");
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
//        RequestDispatcher rd = request.getRequestDispatcher(jspErro);
//        if (rd != null) {
//            rd.forward(request, response);
//        } else {
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao encaminhar");
//        }
    }

    private void atualizarAnalista(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            Analista analista = extrairAnalistaDoRequest(request);
//            String novaSenha = request.getParameter("novaSenha");
//
//            if (analistaDAO.atualizar(analista, novaSenha)) {
//                response.sendRedirect("analista?sucesso=Analista atualizado com sucesso");
//            } else {
//                response.sendRedirect("analista?erro=Erro ao atualizar analista");
//            }
//        } catch (Exception e) {
//            response.sendRedirect("analista?erro=" + e.getMessage());
//        }
    }

    private void excluirAnalista(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        int resultado = analistaDAO.apagar(id);
//
//        if (resultado > 0) {
//            response.sendRedirect("analista?sucesso=Analista excluído com sucesso");
//        } else {
//            response.sendRedirect("analista?erro=Erro ao excluir analista");
//        }
    }

    private void buscarUnidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String tipo = request.getParameter("tipo");
//        String valor = request.getParameter("valor");
//        List<Unidade> resultados = null;
//
//        // CORREÇÃO: Trate 'tipo' nulo com uma string padrão ("erro_busca")
//        switch (tipo != null ? tipo : "erro_busca") {
//            case "id":
//                resultados = UnidadeDAO.buscarPorId(Integer.parseInt(valor));
//                break;
//            case "cpf":
//                resultados = UnidadeDAO.buscarPorCpf(valor);
//                break;
//            case "nome":
//                resultados = UnidadeDAO.buscarPorNome(valor);
//                break;
//            case "email":
//                resultados = UnidadeDAO.buscarPorEmail(valor);
//                break;
//            case "cargo":
//                resultados = UnidadeDAO.buscarPorCargo(valor);
//                break;
//            case "unidade":
//                resultados = UnidadeDAO.buscarPorId(Integer.parseInt(valor));
//                break;
//            case "erro_busca":
//                // Tratar erro ou apenas retornar lista vazia
//                resultados = UnidadeDAO.buscarTodos(); // Exibe todos se a busca falhar
//                break;
//        }
//
//        request.setAttribute("Unidades", resultados);
//        request.setAttribute("resultadoBusca", true);
//        request.getRequestDispatcher("").forward(request, response);
    }

    private void extrairUnidadeDoRequest(HttpServletRequest request) {
//        int id = Integer.parseInt(request.getParameter("id"));
//        int idUnidade = Integer.parseInt(request.getParameter("idUnidade"));
//        String cpf = request.getParameter("cpf");
//        String nome = request.getParameter("nome");
//        LocalDate dtContratacao = LocalDate.parse(request.getParameter("dtContratacao"));
//        String email = request.getParameter("email");
//        String senha = request.getParameter("senha");
//        String cargo = request.getParameter("cargo");
//        String telefone = request.getParameter("telefone");
//
//        return new Unidade(id, );
    }
}
