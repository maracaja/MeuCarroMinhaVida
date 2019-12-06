package controller;

import dao.ClienteDAO;
import dao.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.RequestConf;
import lib.Senha;
import model.Cliente;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/erro.jsp";
        try
        {
            int id = Senha.testaLogin(request.getParameter("email").toLowerCase(), request.getParameter("senha"));
            if (id == -999)
            {
                destino = "/admin.jsp";
                Cliente admin = new Cliente();
                admin.setId(-999);
                request.getSession().setAttribute("Admin", admin);
            } 
            else if (id != -1)
            {
                DataSource ds = new DataSource();
                ClienteDAO dao = new ClienteDAO(ds);
                Cliente cli = dao.read(id);
                if(cli != null)
                {
                    request.getSession().setAttribute("Cliente", cli);
                    destino = "/loja";
                }
                else
                {
                    cli = Cliente.verificaUsuario(request);
                    request.getSession().setAttribute("Cliente", cli);
                    destino = "/login.jsp";
                    RequestConf.requestLoginPadrao(request);
                }
                ds.getConexao().close();
            }
            else
            {
                request.setAttribute("Aviso", "E-mail/senha inválido(a)");
                request.setAttribute("Cadastrado", Boolean.FALSE);
                destino = "/login.jsp";
            }
        }
        catch(SQLException e)
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
}
