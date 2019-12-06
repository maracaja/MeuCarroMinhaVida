package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.RequestConf;
import model.Cliente;

@SuppressWarnings("serial")
public class TelaLoginServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/login.jsp";
        Cliente cli = (Cliente) request.getSession().getAttribute("Cliente");
        if(cli == null)
        {
            cli = new Cliente();
            cli.setId(-1);
        }
        if(cli.getId() != -1) destino = "/index.jsp";
        else RequestConf.requestLoginPadrao(request);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
        rd.forward(request, response);
    }
}
