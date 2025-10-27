package org.example.eitruck.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.eitruck.Dao.*;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private AdministradorDAO administradorDAO;
    private AnalistaDAO analistaDAO;
    private SegmentoDAO segmentoDAO;
    private UnidadeDAO unidadeDAO;
    private EnderecoDAO enderecoDAO;
    private TipoOcorrenciaDAO tipoOcorrenciaDAO;

    @Override
    public void init() throws ServletException {
        // Inicializar os DAOs uma vez
        this.administradorDAO = new AdministradorDAO();
        this.analistaDAO = new AnalistaDAO();
        this.segmentoDAO = new SegmentoDAO();
        this.unidadeDAO = new UnidadeDAO();
        this.enderecoDAO = new EnderecoDAO();
        this.tipoOcorrenciaDAO = new TipoOcorrenciaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obter as contagens
            int totalAdministradores = administradorDAO.numeroRegistros();
            int totalAnalistas = analistaDAO.numeroRegistros();
            int totalSegmentos = segmentoDAO.numeroRegistros();
            int totalUnidades = unidadeDAO.numeroRegistros();
            int totalEnderecos = enderecoDAO.numeroRegistros();
            int totalOcorrencias = tipoOcorrenciaDAO.numeroRegistros();

            // Adicionar os atributos na request
            request.setAttribute("totalAdministradores", totalAdministradores);
            request.setAttribute("totalAnalistas", totalAnalistas);
            request.setAttribute("totalSegmentos", totalSegmentos);
            request.setAttribute("totalUnidades", totalUnidades);
            request.setAttribute("totalEnderecos", totalEnderecos);
            request.setAttribute("totalOcorrencias", totalOcorrencias);

            // Encaminhar para o JSP
            request.getRequestDispatcher("/html/Restricted-area/Pages/Dashboard/dashboard.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Em caso de erro, definir valores padrão
            request.setAttribute("totalAdministradores", 0);
            request.setAttribute("totalAnalistas", 0);
            request.setAttribute("totalSegmentos", 0);
            request.setAttribute("totalUnidades", 0);
            request.setAttribute("totalEnderecos", 0);
            request.setAttribute("totalOcorrencias", 0);
            request.setAttribute("error", "Erro ao carregar dados do dashboard");

            request.getRequestDispatcher("/html/Restricted-area/Pages/Dashboard/dashboard.jsp")
                    .forward(request, response);
        }
    }
}