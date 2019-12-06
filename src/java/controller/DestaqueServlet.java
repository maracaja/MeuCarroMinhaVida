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

@SuppressWarnings("serial")
public class DestaqueServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/erro.jsp";
        int[] destaques = {1, 2, 4, 6}; // Destaques da vitrine escolhidos arbitrariamente (pode ser substituído por outra lógica)
        try
        {
            DataSource ds = new DataSource();
            ProdutoDAO dao = new ProdutoDAO(ds);
            List<Object> lista = dao.read(destaques);
            ds.getConexao().close();
            request.setAttribute("ListaDestaques", lista);
            request.getSession().setAttribute("Cliente", Cliente.verificaUsuario(request));
            destino = "/destaques.jsp";
        }
        catch (SQLException e)
        {
            RequestConf.erroSQL(e, request);
        }
        catch(Exception ex)
        {
            RequestConf.exception(ex, request);
        }
        finally
        {
            RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
            rd.forward(request, response);
        }
    }
}
