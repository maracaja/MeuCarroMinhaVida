package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Carrinho;
import model.Cliente;

@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getSession(true);
        Cliente cli = new Cliente();
        Carrinho cegonha = new Carrinho();
        cli.setId(-1);
        request.getSession().setAttribute("Cliente", cli);
        request.getSession().setAttribute("Carrinho", cegonha);
        if (request.getSession().getAttribute("Admin") != null)
            request.getSession().removeAttribute("Admin");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
}
