package controller;

import dao.DataSource;
import dao.PedidoDAO;
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
import model.Pedido;

@SuppressWarnings("serial")
public class MeusPedidosServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/meusPedidos.jsp";
        Cliente c = Cliente.verificaUsuario(request);
        if(c.getId() != -1)
        {
            if(c.getPedidos().isEmpty())
            {
                try
                {
                    DataSource ds = new DataSource();
                    PedidoDAO dao = new PedidoDAO(ds);
                    List<Pedido> p = dao.read(c.getId());
                    c.setPedidos(p);
                    ds.getConexao().close();
                    request.getSession().setAttribute("Cliente", c);
                }
                catch (SQLException e)
                {
                    destino = "/util/erro.jsp";
                    RequestConf.erroSQL(e, request);
                }
            }
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
