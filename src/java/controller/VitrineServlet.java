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
public class VitrineServlet extends HttpServlet 
{
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String destino = "/util/erro.jsp";
        Cliente c = Cliente.verificaUsuario(request);
        try
        {
            DataSource ds = new DataSource();
            ProdutoDAO dao = new ProdutoDAO(ds);
            List<Produto> lista = dao.read(false);
            ds.getConexao().close();
            request.setAttribute("ListaFull", lista);
            request.getSession().setAttribute("Cliente", c);
            destino = "/vitrine.jsp";   
        }
        catch (SQLException e)
        {
            RequestConf.erroSQL(e, request);
        }
        catch (Exception ex)
        {
            RequestConf.exception(ex, request);
        }
        finally
        {
            RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
            rd.forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    { processRequest(request, response); }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    { processRequest(request, response); }
}
