package org.example.eitruck.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.eitruck.Dao.AdministradorDAO;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private final AdministradorDAO admin = new AdministradorDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        String resultado = admin.ehAdmin(email, senha);

        if (resultado != null) {
            HttpSession sessionNome = req.getSession();
            sessionNome.setAttribute("nomeAdimin", resultado);

            // Verifique se este caminho está correto
            req.getRequestDispatcher("/html/Restricted-area/loading-screen.html").forward(req, resp);

        } else {
            // CORREÇÃO: Redirecionar de volta para a página de login
            req.setAttribute("erroLogin", "E-mail ou senha incorretos!");
            req.getRequestDispatcher("/html/Restricted-area/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/Restricted-area/login.jsp").forward(req, resp);
    }
}

//package org.example.eitruck.Servlet;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.example.eitruck.Dao.AdministradorDAO;
//
//import java.io.IOException;
//
//@WebServlet(name = "LoginServlet", value = "/login")
//public class LoginServlet extends HttpServlet {
//    private final AdministradorDAO admin = new AdministradorDAO();
//
//    // Método POST: chamado quando o formulário é enviado
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String email = req.getParameter("email");
//        String senha = req.getParameter("senha");
//
//        // Verifica login com o DAO
//        String resultado = admin.ehAdmin(email, senha);
//
//        if (resultado != null) {
//            // Login válido → cria sessão e redireciona para área restrita
//            HttpSession sessionNome = req.getSession();
//            sessionNome.setAttribute("nomeAdimin", resultado);
//
//            // Encaminha para a tela de carregamento (corrigido o caminho)
//            req.getRequestDispatcher("/html/Restricted-area/loading-screen.html").forward(req, resp);
//
//        } else {
//            // Login inválido → retorna à página de login com mensagem de erro
//            req.setAttribute("erroLogin", "E-mail ou senha incorretos!");
//            req.getRequestDispatcher("/html/Erro.jsp").forward(req, resp);
//        }
//    }
//
//    // Método GET: chamado se o usuário acessar /login diretamente
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Encaminha o usuário para a página de login
//        req.getRequestDispatcher("/html/Restricted-area/login.jsp").forward(req, resp);
//    }
//}
