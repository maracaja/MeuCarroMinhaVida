package controller;

import dao.DataSource;
import dao.PedidoDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.RequestConf;
import model.Cliente;
import model.Pedido;

@SuppressWarnings("serial")
public class DetalhePedidoServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/erro.jsp";
        Cliente c = Cliente.verificaUsuario(request);
        if (c.getId() != -1)
        {
            int id = Integer.parseInt(request.getParameter("id"));
            Pedido p = null;
            if (c.getPedidos().isEmpty())
            {
                try 
                {
                    DataSource ds = new DataSource();
                    PedidoDAO dao = new PedidoDAO(ds);
                    List<Pedido> lista = dao.read(c.getId());
                    ds.getConexao().close();
                    c.setPedidos(lista);
                    request.setAttribute("Cliente", c);
                } 
                catch (SQLException e) 
                {
                    RequestConf.erroSQL(e, request);
                }
            }
            for (Pedido ped : c.getPedidos())
            {
                if (ped.getId() == id)
                {
                    p = ped;
                    break;
                }
            }
            if(p != null)
            {
                destino = "/detalhePedido.jsp";
                request.setAttribute("Pedido", p);
            }
            else destino = "/util/404.jsp";
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
