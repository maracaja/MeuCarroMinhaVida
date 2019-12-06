package controller;

import dao.DataSource;
import dao.ProdutoDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.RequestConf;
import model.Produto;

@SuppressWarnings("serial")
public class RelatorioProdutosServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/erro.jsp";
        try
        {
            DataSource ds = new DataSource();
            ProdutoDAO dao = new ProdutoDAO(ds);
            List<Produto> lista = dao.read(true);
            destino = "/adm/relatorioProdutos.jsp";
            request.setAttribute("ListaProdutos", lista);
        }
        catch (SQLException e)
        {
            RequestConf.erroSQL(e, request);
        }
        finally
        {
            RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
            rd.forward(request, response);
        }
    }
}
