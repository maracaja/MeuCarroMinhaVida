package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.RequestConf;
import model.Cliente;
import model.Endereco;

@SuppressWarnings("serial")
public class TelaEnderecoServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String destino = "/alterarEndereco.jsp";
        Cliente c = Cliente.verificaUsuario(request);
        if (c.getId() != -1)
        {
            request.setAttribute("Novo", Boolean.TRUE);
            request.setAttribute("Endereco", new Endereco());
        }
        else
        {
            destino = "/login.jsp";
            RequestConf.requestLoginPadrao(request);
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/alterarEndereco.jsp";
        int id = Integer.parseInt(request.getParameter("edit"));
        Cliente c = Cliente.verificaUsuario(request);
        if (c.getId() != -1)
        {
            Endereco end = c.temEndereco(id);
            if (end != null)
            {
                request.setAttribute("Novo", Boolean.FALSE);
                request.setAttribute("Endereco", end);
            }
            else response.sendRedirect("/meusEnderecos");
        }
        else
        {
            destino = "/login.jsp";
            RequestConf.requestLoginPadrao(request);
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
        rd.forward(request, response);
    }
}
