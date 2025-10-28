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
        String acao = request.getParameter("acao");

        switch (acao != null ? acao : "listar") {
            case "editar": // ADICIONE ESTE CASO
                carregarEnderecoParaEdicao(request, response);
                break;
            case "buscar":
                buscarTodos(request, response, acao, "buscar_todos");
                break;
            case "filtrar":
                filtrarEnderecos(request, response);
                break;
            case "listar":
            default:
                buscarTodos(request, response, acao, "buscar_todos");
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
            case "atualizar": // ATUALIZE ESTE CASO
                atualizarEndereco(request, response);
                break;
            case "excluir":
                excluirEndereco(request, response);
                break;
        }
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
                errorMessage = "Erro ao cadastrar endereço no banco de dados.";
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
            errorMessage = "Erro ao cadastrar endereço no banco de dados.";
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

    private void carregarEnderecoParaEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("Carregando endereço para edição - ID: " + id);

            List<Endereco> enderecos = enderecoDAO.buscarPorId(id);
            System.out.println("Quantidade de endereços encontrados: " + (enderecos != null ? enderecos.size() : "null"));

            if (enderecos != null && !enderecos.isEmpty()) {
                Endereco endereco = enderecos.get(0);
                System.out.println("Endereço encontrado: " + endereco.getRua() + ", " + endereco.getNumero());
                request.setAttribute("endereco", endereco);
                RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Addresses/editar_endereco.jsp");
                rd.forward(request, response);
            } else {
                System.out.println("Nenhum endereço encontrado para ID: " + id);
                String errorMessage = URLEncoder.encode("Endereço não encontrado.", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de parse no ID");
            String errorMessage = URLEncoder.encode("ID inválido.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode("Erro ao carregar endereço para edição.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos&error=" + errorMessage);
        }
    }

    private void atualizarEndereco(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String errorMessage = null;

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String cep = request.getParameter("cep");
            String rua = request.getParameter("rua");
            String numeroStr = request.getParameter("numero");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String pais = request.getParameter("pais");

            System.out.println("Atualizando endereço - ID: " + id + ", CEP: " + cep +
                    ", Rua: " + rua + ", Número: " + numeroStr + ", Bairro: " + bairro +
                    ", Cidade: " + cidade + ", Estado: " + estado + ", País: " + pais);

            // Remove caracteres não numéricos do CEP
            String cepNumeros = cep.replaceAll("[^0-9]", "");

            // Validações
            if (!cepNumeros.matches("\\d{8}")) {
                errorMessage = "CEP deve ter exatamente 8 dígitos numéricos.";
            } else if (rua == null || rua.trim().length() < 2 || rua.trim().length() > 100) {
                errorMessage = "Rua deve ter entre 2 e 100 caracteres.";
            } else if (numeroStr == null || numeroStr.trim().isEmpty()) {
                errorMessage = "Número é obrigatório.";
            } else if (bairro == null || bairro.trim().length() < 2 || bairro.trim().length() > 50) {
                errorMessage = "Bairro deve ter entre 2 e 50 caracteres.";
            } else if (cidade == null || cidade.trim().length() < 2 || cidade.trim().length() > 50) {
                errorMessage = "Cidade deve ter entre 2 e 50 caracteres.";
            } else if (estado == null || estado.trim().length() != 2) {
                errorMessage = "Estado deve ter exatamente 2 caracteres (UF).";
            } else if (pais == null || pais.trim().length() < 2 || pais.trim().length() > 50) {
                errorMessage = "País deve ter entre 2 e 50 caracteres.";
            } else {
                try {
                    int numero = Integer.parseInt(numeroStr);

                    if (numero < 1 || numero > 99999) {
                        errorMessage = "Número deve estar entre 1 e 99999.";
                    } else {
                        // Lista de UFs válidas do Brasil
                        String[] ufsValidas = {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG",
                                "PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
                        boolean ufValida = false;
                        for (String uf : ufsValidas) {
                            if (uf.equalsIgnoreCase(estado.trim())) {
                                ufValida = true;
                                break;
                            }
                        }

                        if (!ufValida) {
                            errorMessage = "UF inválida. Use uma sigla de estado válida do Brasil.";
                        } else {
                            int resultado = enderecoDAO.alterarEndereco(id, cepNumeros, rua.trim(), numero,
                                    bairro.trim(), cidade.trim(), estado.trim().toUpperCase(), pais.trim());

                            if (resultado > 0) {
                                response.sendRedirect(request.getContextPath() + "/servlet-enderecos?acao_principal=buscar&sub_acao=buscar_todos");
                                return;
                            } else {
                                errorMessage = "Erro ao atualizar endereço no banco de dados.";
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    errorMessage = "Número deve ser um valor numérico válido.";
                }
            }
        } catch (NumberFormatException e) {
            errorMessage = "ID inválido.";
        } catch (Exception e) {
            errorMessage = "Ocorreu um erro inesperado: " + e.getMessage();
            e.printStackTrace();
        }

        // Se houve erro, recarrega a página de edição com a mensagem de erro
        request.setAttribute("errorMessage", errorMessage);
        carregarEnderecoParaEdicao(request, response);
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

    private void filtrarEnderecos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String filtroId = request.getParameter("filtro_id");
            String filtroCep = request.getParameter("filtro_cep");
            String filtroRua = request.getParameter("filtro_rua");
            String filtroNumero = request.getParameter("filtro_numero");
            String filtroBairro = request.getParameter("filtro_bairro");
            String filtroCidade = request.getParameter("filtro_cidade");
            String filtroEstado = request.getParameter("filtro_estado");
            String filtroPais = request.getParameter("filtro_pais");

            List<Endereco> enderecos;

            // Verifica se pelo menos um filtro foi preenchido
            boolean algumFiltro = (filtroId != null && !filtroId.trim().isEmpty()) ||
                    (filtroCep != null && !filtroCep.trim().isEmpty()) ||
                    (filtroRua != null && !filtroRua.trim().isEmpty()) ||
                    (filtroNumero != null && !filtroNumero.trim().isEmpty()) ||
                    (filtroBairro != null && !filtroBairro.trim().isEmpty()) ||
                    (filtroCidade != null && !filtroCidade.trim().isEmpty()) ||
                    (filtroEstado != null && !filtroEstado.trim().isEmpty()) ||
                    (filtroPais != null && !filtroPais.trim().isEmpty());

            if (algumFiltro) {
                // Busca com filtros
                enderecos = enderecoDAO.filtrarEnderecosMultiplos(filtroId, filtroCep, filtroRua, filtroNumero,
                        filtroBairro, filtroCidade, filtroEstado, filtroPais);
            } else {
                // Se nenhum filtro, busca todos
                enderecos = enderecoDAO.buscarTodos();
            }

            request.setAttribute("enderecos", enderecos);

            // Mantém os parâmetros do filtro para mostrar no formulário
            request.setAttribute("filtroId", filtroId);
            request.setAttribute("filtroCep", filtroCep);
            request.setAttribute("filtroRua", filtroRua);
            request.setAttribute("filtroNumero", filtroNumero);
            request.setAttribute("filtroBairro", filtroBairro);
            request.setAttribute("filtroCidade", filtroCidade);
            request.setAttribute("filtroEstado", filtroEstado);
            request.setAttribute("filtroPais", filtroPais);

            RequestDispatcher rd = request.getRequestDispatcher("/html/Restricted-area/Pages/Addresses/processar_addresses.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Erro ao filtrar endereços: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            buscarTodos(request, response, "buscar", "buscar_todos");
        }
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

    }
}
