package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Carrinho;

@SuppressWarnings("serial")
public class AdicionarCarrinhoServlet extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        Carrinho cegonha = Carrinho.verificaCarrinho(request);
        int p = Integer.parseInt(request.getParameter("prod"));
        int qtd = Integer.parseInt(request.getParameter("quantidade"));
        cegonha.adicionar(p, qtd);
        request.getSession().setAttribute("Carrinho", cegonha);
        response.sendRedirect("/cegonha");
    }
}
