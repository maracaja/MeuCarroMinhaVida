package controller;

import dao.DataSource;
import dao.PedidoDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.Admin;
import lib.RequestConf;
import model.Pedido;

@SuppressWarnings("serial")
public class RelatorioPedidosServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String destino = "/util/404.jsp";
        if (Admin.verificaAdmin(request))
        { 
            destino = "/adm/relatorioPedidos.jsp";
            request.setAttribute("Aviso", "");
            request.setAttribute("ListaPedidos", new ArrayList<>());
            request.setAttribute("Titulo", "");
            request.setAttribute("Vendidos", 0);
            request.setAttribute("Faturamento", "");
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/404.jsp";
        if(Admin.verificaAdmin(request))
        {
            GregorianCalendar inicio = Admin.capturaData(request.getParameter("inicio"), true);
            GregorianCalendar fim = Admin.capturaData(request.getParameter("final"), false);
            boolean ambos = (inicio != null && fim != null);
            if (ambos && fim.before(inicio))
            {
                destino = "/adm/relatorioPedidos.jsp";
                request.setAttribute("Aviso", "Data final não pode ser menor que a data inicial");
                request.setAttribute("ListaPedidos", new ArrayList<>());
                request.setAttribute("Titulo", "");
                request.setAttribute("Vendidos", 0);
                request.setAttribute("Faturamento", "");
            }
            else
            {
                try
                {
                    DataSource ds = new DataSource();
                    PedidoDAO dao = new PedidoDAO(ds);
                    List<Pedido> lista = dao.read(inicio, fim);
                    destino = "/adm/relatorioPedidos.jsp";
                    request.setAttribute("Aviso", "");
                    request.setAttribute("ListaPedidos", lista);
                    request.setAttribute("Titulo", Admin.tituloRelatorio(inicio, fim));
                    request.setAttribute("Vendidos", Admin.vendidos(lista));
                    request.setAttribute("Faturamento", Admin.faturamento(lista));
                    ds.getConexao().close();
                }
                catch (SQLException e)   
                {
                    RequestConf.erroSQL(e, request);
                }
                catch (Exception ex)
                {
                    RequestConf.exception(ex, request);
                }
            }
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
        rd.forward(request, response);    
    }
}