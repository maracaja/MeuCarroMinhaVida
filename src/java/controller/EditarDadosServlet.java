package controller;

import dao.ClienteDAO;
import dao.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.RequestConf;
import model.Cliente;

@SuppressWarnings("serial")
public class EditarDadosServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String destino = "/editarDados.jsp";
        Cliente cli = Cliente.verificaUsuario(request);
        if (cli.getId() == -1)
        {
            destino = "/login.jsp";
            RequestConf.requestLoginPadrao(request);
        }
        else 
        {
            request.setAttribute("Alterado", Boolean.FALSE);
            request.setAttribute("Aviso", "");
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/erro.jsp";
        Cliente cli = Cliente.verificaUsuario(request);
        try 
        {
            if (cli.getId() != -1)
            {
                String strChave = cli.getEmail();
                cli.setNome(request.getParameter("nome"));
                cli.setDataNasc(Date.valueOf(request.getParameter("dataNasc")));
                cli.setEmail(request.getParameter("email"));
                DataSource ds = new DataSource();
                ClienteDAO dao = new ClienteDAO(ds);
                dao.update(cli, strChave);
                ds.getConexao().close();
                destino = "/editarDados.jsp";
                request.setAttribute("Alterado", Boolean.TRUE);
                request.setAttribute("Aviso", "");
                request.getSession().setAttribute("Cliente", cli);
            }
            else 
            {
                destino = "/login.jsp";
                RequestConf.requestLoginPadrao(request);
            }
        }
        catch (SQLException e) 
        {
            RequestConf.erroSQL(e, request);
        }
        catch (Exception e)
        {
            RequestConf.exception(e, request);
        }
        finally
        {
            RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
            rd.forward(request, response);
        }
    }
}
