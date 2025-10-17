package org.example.eitruck.Servlet;

import org.example.eitruck.Dao.AdministradorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;

@WebServlet(name = "VerificarAdImin", value = "nove forms")
public class VerificarAdImin extends HttpServlet {
    private final AdministradorDAO admin=new AdministradorDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email=req.getParameter("email");
        String senha=req.getParameter("senha");
        String resultado=admin.ehAdimin(email, senha);
        if (resultado!=null){
            HttpSession sessionNome= req.getSession();
            sessionNome.setAttribute("nomeAdimin", resultado);
            req.getRequestDispatcher("/src/main/webapp/html/Restricted-area/Pages/addresses.html").forward(req, resp);//aqui vai o caminho da area restrita, no lugar vazio

            //todo trocar no forms de cpf para email
            //todo colocar valor de recebimento = "nomeAdmin"
            //
        }


    }


}
