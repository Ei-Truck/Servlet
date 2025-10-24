package org.example.eitruck.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Invalida a sessão
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Redireciona para a página de login
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}