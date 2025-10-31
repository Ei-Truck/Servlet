package org.example.eitruck.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Atributos HTTP
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // URLs públicas que não precisam de autenticação
        boolean isPublicResource = requestURI.startsWith(contextPath + "/StyleCss/") ||
            requestURI.startsWith(contextPath + "/html/Landing-page/") ||
            requestURI.startsWith(contextPath + "/JS/") ||
            requestURI.startsWith(contextPath + "/image/") ||
            requestURI.startsWith(contextPath + "/js/") ||
            requestURI.equals(contextPath + "/login.jsp") ||
            requestURI.equals(contextPath + "/login") ||
            requestURI.equals(contextPath + "/index.html") ||
            requestURI.startsWith(contextPath + "/Erro.jsp/") ||
            requestURI.equals(contextPath + "/");

        // Verifica se o usuário está logado
        boolean isLoggedIn = (session != null && session.getAttribute("nomeAdimin") != null);

        if (isPublicResource || isLoggedIn) {
            // Permite o acesso a recursos públicos ou usuários logados
            chain.doFilter(request, response);
        }
        else {
            // Redireciona para a página de login se não estiver autenticado
            httpResponse.sendRedirect(contextPath + "/login.jsp");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {} // Inicialização do filtro

    @Override
    public void destroy() {} // Cleanup do filtro
}