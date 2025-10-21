package org.example.eitruck.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import org.example.eitruck.Dao.AnalistaDAO;
import org.example.eitruck.Dao.UnidadeDAO;
import org.example.eitruck.model.Analista;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.eitruck.model.Unidade;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("")
public class UnidadeServlet extends HttpServlet {
    private UnidadeDAO UnidadeDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Inicializa o objeto ANTES de qualquer requisição.
        this.UnidadeDao = new UnidadeDAO();
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
        List<Unidade> unidades = UnidadeDao.buscarTodos();
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
        List<Unidade> unidades = UnidadeDao.buscarPorId(id);

        if (!unidades.isEmpty()) {
            request.setAttribute("Unidade", unidades.get(0));
            request.getRequestDispatcher("").forward(request, response);
            //todo colocar o caminho
        } else {
            response.sendRedirect("Unidade?erro=Unidade não encontrado");
        }
    }

    private void inserirUnidade(HttpServletRequest request, HttpServletResponse response, String acao, String sub_acao) throws IOException, ServletException {
        try {
            String idUnidade = request.getParameter("id_unidade");
            String cpf = request.getParameter("cpf");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String data_contratacao = request.getParameter("data_contratacao");
            String senha = request.getParameter("senha");
            String cargo = request.getParameter("cargo");
            //todo trocar tudo para os atributos de Unidade

            System.out.println("Id de unidade: " + idUnidade);
            System.out.println("Cpf: " + cpf);
            System.out.println("Nome: " + nome);
            System.out.println("Email: " + email);
            System.out.println("Data de contratacao: " + data_contratacao);
            System.out.println("Senha: " + senha);
            System.out.println("Cargo: " + cargo);

            // 1. ADICIONAR O NULL CHECK AQUI:
            if (data_contratacao == null || data_contratacao.trim().isEmpty()) {
                // Se for nulo/vazio, trate como erro do cliente (Bad Request)
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Data de contratação é obrigatória.");
                return;
            }

            // 2. Tente fazer a conversão AGORA:
            int id_unidade = Integer.parseInt(idUnidade);
            //todo, retirar ou alterar para apenas ID
            LocalDate data_contratacaoDate = LocalDate.parse(data_contratacao, DateTimeFormatter.ISO_LOCAL_DATE); // Linha 113 agora segura contra null
            //todo fazer alterações necessárias

            Unidade Unidade = new Unidade();
            //todo ver os parametros do construtor
            UnidadeDao.cadastrar(Unidade);

            redirecionar(request, response);
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

    public void redirecionar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getContextPath() + "";
        //todo pegar a url
        response.sendRedirect(url);
    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws IOException, ServletException {
        try {
            List<Unidade> Unidades = UnidadeDao.buscarTodos();
            request.setAttribute("Unidades", Unidades);

            encaminhar(request, response, "");
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
        try {
            Analista analista = extrairAnalistaDoRequest(request);
            String novaSenha = request.getParameter("novaSenha");

            if (UnidadeDao.atualizar(analista, novaSenha)) {
                response.sendRedirect("Unidade?sucesso=Unidade atualizado com sucesso");
            } else {
                response.sendRedirect("Unidade?erro=Erro ao atualizar analista");
            }
        } catch (Exception e) {
            response.sendRedirect("Unidade?erro=" + e.getMessage());
        }
    }

    private void excluirAnalista(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int resultado = UnidadeDao.apagar(id);
        //todo checar o parametro de apagar

        if (resultado > 0) {
            response.sendRedirect("unidade?sucesso=Unidade excluído com sucesso");
        } else {
            response.sendRedirect("unidade?erro=Erro ao excluir unidade");
        }
    }

    private void buscarUnidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("tipo");
        String valor = request.getParameter("valor");
        List<Unidade> resultados = null;

        // CORREÇÃO: Trate 'tipo' nulo com uma string padrão ("erro_busca")
        switch (tipo != null ? tipo : "erro_busca") {
            case "id":
                resultados = UnidadeDao.buscarPorId(Integer.parseInt(valor));
                break;
            case "cpf":
                resultados = UnidadeDao.buscarPorCpf(valor);
                break;
            case "nome":
                resultados = UnidadeDao.buscarPorNome(valor);
                break;
            case "email":
                resultados = UnidadeDao.buscarPorEmail(valor);
                break;
            case "cargo":
                resultados = UnidadeDao.buscarPorCargo(valor);
                break;
            case "unidade":
                resultados = UnidadeDao.buscarPorIdUnidade(Integer.parseInt(valor));
                break;
            case "erro_busca":
                // Tratar erro ou apenas retornar lista vazia
                resultados = UnidadeDao.buscarTodos(); // Exibe todos se a busca falhar
                break;
                //todo checar e fazer a troca pelos daos de UNidade
        }

        request.setAttribute("Unidades", resultados);
        request.setAttribute("resultadoBusca", true);
        request.getRequestDispatcher("").forward(request, response);
        //todo caminho do Dispatcher
    }

    private Unidade extrairUnidadeDoRequest(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int idUnidade = Integer.parseInt(request.getParameter("idUnidade"));
        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        LocalDate dtContratacao = LocalDate.parse(request.getParameter("dtContratacao"));
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cargo = request.getParameter("cargo");
        String telefone = request.getParameter("telefone");
        //todo trocar para os atributos de Unidade

        return new Unidade(id, idUnidade, cpf, nome, dtContratacao, email, senha, cargo, telefone);
    }
}
