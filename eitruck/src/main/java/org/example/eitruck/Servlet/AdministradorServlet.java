package org.example.eitruck.Servlet;

import org.example.eitruck.Dao.AdministradorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AdministradorServlet", value = "/login")
public class AdministradorServlet extends HttpServlet {

    private final AdministradorDAO admin = new AdministradorDAO();

    // Método POST: chamado quando o formulário é enviado
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        // Verifica login com o DAO
        String resultado = admin.ehAdimin(email, senha);

        if (resultado != null) {
            // Login válido → cria sessão e redireciona para área restrita
            HttpSession sessionNome = req.getSession();
            sessionNome.setAttribute("nomeAdimin", resultado);

            // Encaminha para a tela de carregamento (corrigido o caminho)
            req.getRequestDispatcher("../../../webapp/html/Restricted-area/loading-screen.html").forward(req, resp);

        } else {
            // Login inválido → retorna à página de login com mensagem de erro
            req.setAttribute("erroLogin", "E-mail ou senha incorretos!");
            req.getRequestDispatcher("src/main/webapp/html/Erro.jsp").forward(req, resp);
        }
    }

    // Método GET: chamado se o usuário acessar /login diretamente
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Encaminha o usuário para a página de login
        req.getRequestDispatcher("/html/Restricted-area/login.jsp").forward(req, resp);
    }
}
