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

        // Verifica login com o DAO
        String resultado = admin.ehAdimin(email, senha);

        if (resultado != null) {
            // Login válido → cria sessão e redireciona
            HttpSession sessionNome = req.getSession();
            sessionNome.setAttribute("nomeAdimin", resultado);

            // Redireciona para a tela de carregamento
            resp.sendRedirect(req.getContextPath() + "/html/Restricted-area/loading-screen.html");
        } else {
            // Login inválido → volta para a mesma página de login
            req.setAttribute("erroLogin", "E-mail ou senha incorretos!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/Restricted-area/login.jsp").forward(req, resp);
    }
}