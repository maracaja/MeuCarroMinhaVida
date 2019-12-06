package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Carrinho;

@SuppressWarnings("serial")
public class AlteraItemServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        Carrinho cegonha = Carrinho.verificaCarrinho(request);
        if (!cegonha.getItens().isEmpty())
        {
            int idProd = Integer.parseInt(request.getParameter("id"));
            boolean aumenta = Boolean.parseBoolean(request.getParameter("acr"));
            cegonha.alteraItem(idProd, aumenta);
        }
        request.getSession().setAttribute("Carrinho", cegonha);
        response.sendRedirect("/cegonha");
    }
}
