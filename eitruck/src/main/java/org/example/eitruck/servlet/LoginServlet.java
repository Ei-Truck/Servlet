package org.example.eitruck.servlet;

import org.example.eitruck.Dao.AdministradorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final AdministradorDAO admin = new AdministradorDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        // Verifica se já existe uma sessão
        HttpSession existingSession = req.getSession(false);
        if (existingSession != null && existingSession.getAttribute("nomeAdimin") != null) {
            // Já está logado, redireciona para o dashboard
            resp.sendRedirect(req.getContextPath() + "/html/Restricted-area/Pages/Dashboard/dashboard.jsp");
            return;
        }

        // Verifica login com o DAO
        String resultado = admin.ehAdmin(email, senha);

        if (resultado != null) {
            // Login válido → cria sessão e redireciona
            HttpSession session = req.getSession();
            session.setAttribute("nomeAdimin", resultado);
            session.setMaxInactiveInterval(30 * 60); // 30 minutos de inatividade

            // Redireciona para a tela de carregamento
            resp.sendRedirect(req.getContextPath() + "/html/Restricted-area/loading-screen.jsp");
        } else {
            // Login inválido → volta para a mesma página de login
            req.setAttribute("erroLogin", "E-mail ou senha incorretos!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Se já estiver logado, redireciona para o dashboard
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("nomeAdimin") != null) {
            resp.sendRedirect(req.getContextPath() + "/html/Restricted-area/Pages/Dashboard/dashboard.jsp");
            return;
        }

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}