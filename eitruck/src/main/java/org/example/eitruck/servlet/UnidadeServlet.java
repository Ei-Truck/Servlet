package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import org.example.eitruck.Dao.UnidadeDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.eitruck.model.Analista;
import org.example.eitruck.model.Unidade;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/servlet-unidade")
public class UnidadeServlet extends HttpServlet {
    private UnidadeDAO unidadeDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Inicializa o objeto ANTES de qualquer requisição.
        this.unidadeDao = new UnidadeDAO();
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
                inserirUnidade(request, response, acao, sub_acao);
                break;
            case "atualizar":
                atualizarUnidade(request, response);
                break;
            case "excluir":
                excluirUnidade(request, response);
                break;
        }
    }

    private void listarUnidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Unidade> unidades = unidadeDao.buscarTodos();
        request.setAttribute("Unidade", unidades);
        request.getRequestDispatcher("").forward(request, response);
        //todo colocar caminho correto
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("").forward(request, response);
        //todo colocar caminho certo
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Unidade> unidades = unidadeDao.buscarPorId(id);

        if (!unidades.isEmpty()) {
            request.setAttribute("Unidade", unidades.get(0));
            request.getRequestDispatcher("").forward(request, response);
            //todo colocar o caminho
        } else {
            response.sendRedirect("Unidade?erro=Unidade não encontrado");
        }
    }

    private void inserirUnidade(HttpServletRequest request, HttpServletResponse response, String acao, String sub_acao) throws IOException, ServletException {
        String errorMessage = null;
        boolean success = false;
        boolean isFormSubmission = false;

        try {
            String idSegmento = request.getParameter("id_segmento");
            String idEndereco = request.getParameter("id_endereco");
            String nome = request.getParameter("nome");
            //todo trocar tudo para os atributos de Unidade

            System.out.println("Id do segmento: " + idSegmento);
            System.out.println("Id do endereço: " + idEndereco);
            System.out.println("Nome: " + nome);


            int id_segmento = Integer.parseInt(idSegmento);
            int id_endereco = Integer.parseInt(idEndereco);


            Unidade unidade = new Unidade(id_segmento, id_endereco, nome);
            //todo ver os parametros do construtor
            success = unidadeDao.cadastrar(unidade);

            if (success) {
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos");
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

        request.setAttribute("id_segmento", request.getParameter("id_segmento"));
        request.setAttribute("id_endereco", request.getParameter("id_endereco"));
        request.setAttribute("nome", request.getParameter("nome"));

        request.setAttribute("sub_acao", sub_acao);

        if (acao != null) {
            request.setAttribute("acao", acao);
        }

        RequestDispatcher respacher = request.getRequestDispatcher("html/Restricted-area/Pages/Units/units.jsp");
        if (respacher != null) {
            respacher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar analista");
        }
    }

    private void excluirUnidade(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int resultado = unidadeDao.apagar(id);

            if (resultado > 0) {
                // Sucesso: redireciona para a lista sem mensagem de erro
                response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos");
            } else if (resultado == -2) {
                // Erro específico: unidade está sendo usada por um analista
                String errorMessage = URLEncoder.encode("Não é possível excluir essa unidade porque ela está sendo usada por um analista.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            } else {
                // Erro genérico
                String errorMessage = URLEncoder.encode("Erro ao excluir unidade. Tente novamente.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro interno ao excluir unidade.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-unidade?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws IOException, ServletException {
        try {
            List<Unidade> unidades = unidadeDao.buscarTodos();
            request.setAttribute("unidades", unidades);
            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Units/processar_units.jsp");
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

    private void atualizarUnidade(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            Analista analista = extrairAnalistaDoRequest(request);
//            String novaSenha = request.getParameter("novaSenha");
//
//            if (UnidadeDao.atualizar(analista, novaSenha)) {
//                response.sendRedirect("Unidade?sucesso=Unidade atualizado com sucesso");
//            } else {
//                response.sendRedirect("Unidade?erro=Erro ao atualizar analista");
//            }
//        } catch (Exception e) {
//            response.sendRedirect("Unidade?erro=" + e.getMessage());
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
//                resultados = UnidadeDao.buscarPorId(Integer.parseInt(valor));
//                break;
//            case "cpf":
//                resultados = UnidadeDao.buscarPorCpf(valor);
//                break;
//            case "nome":
//                resultados = UnidadeDao.buscarPorNome(valor);
//                break;
//            case "email":
//                resultados = UnidadeDao.buscarPorEmail(valor);
//                break;
//            case "cargo":
//                resultados = UnidadeDao.buscarPorCargo(valor);
//                break;
//            case "unidade":
//                resultados = UnidadeDao.buscarPorIdUnidade(Integer.parseInt(valor));
//                break;
//            case "erro_busca":
//                // Tratar erro ou apenas retornar lista vazia
//                resultados = UnidadeDao.buscarTodos(); // Exibe todos se a busca falhar
//                break;
//                //todo checar e fazer a troca pelos daos de UNidade
//        }
//
//        request.setAttribute("Unidades", resultados);
//        request.setAttribute("resultadoBusca", true);
//        request.getRequestDispatcher("").forward(request, response);
//        //todo caminho do Dispatcher
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
//        //todo trocar para os atributos de Unidade
//
//        return new Unidade(id, idUnidade, cpf, nome, dtContratacao, email, senha, cargo, telefone);
    }
}
