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
import model.Cliente;
import model.Produto;

@SuppressWarnings("serial")
public class DetalheProdutoServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/erro.jsp";
        Cliente c = Cliente.verificaUsuario(request);
        try
        {
            DataSource ds = new DataSource();
            ProdutoDAO dao = new ProdutoDAO(ds);
            List<Object> result = dao.read(Integer.parseInt(request.getParameter("id")));
            ds.getConexao().close();
            if(!result.isEmpty())
            {
                destino = "/detalhes.jsp";
                request.setAttribute("Produto", (Produto) result.get(0));
            }
            else destino = "/util/404.jsp";
        }
        catch (SQLException e)
        {
            RequestConf.erroSQL(e, request);
        }
        catch (NumberFormatException ex)
        {
            RequestConf.exception(ex, request);
        }
        finally
        {
            request.setAttribute("Cliente", c);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
            rd.forward(request, response);
        }
    }    
}
