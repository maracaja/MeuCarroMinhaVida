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
public class CegonhaServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getSession().setAttribute("Cliente", Cliente.verificaUsuario(request));
        request.getSession().setAttribute("Carrinho", Carrinho.verificaCarrinho(request));
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/cegonha.jsp");
        rd.forward(request, response);
    } 
}
