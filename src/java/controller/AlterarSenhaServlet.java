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
public class AlterarSenhaServlet extends HttpServlet 
{
    @Override // Entrada na página
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/alterarSenha.jsp";
        Cliente cli = Cliente.verificaUsuario(request);
        if(cli.getId() == -1)
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

    @Override //Registro de nova senha
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/erro.jsp";
        Cliente cli = Cliente.verificaUsuario(request);
        try 
        {
            int id = Senha.testaLogin(cli.getEmail(), request.getParameter("senhaAtual"));
            if(id == -1) request.setAttribute("Aviso", "Senha inválida!");
            else 
            {
                request.setAttribute("Aviso", "");
                DataSource ds = new DataSource();
                ClienteDAO dao = new ClienteDAO(ds);
                dao.updateSenha(cli, request.getParameter("senhaNova"));
                ds.getConexao().close();
                destino = "/alterarSenha.jsp";
                request.setAttribute("Alterado", Boolean.TRUE);
            }
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
}
