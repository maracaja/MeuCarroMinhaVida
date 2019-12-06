package controller;

import dao.DataSource;
import dao.EnderecoDAO;
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
import model.Endereco;

@SuppressWarnings("serial")
public class MeusEnderecosServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/erro.jsp";
        Cliente c = Cliente.verificaUsuario(request);
        if(c.getId() != -1)
        {
            if (c.getEnderecos().isEmpty())
            {
                try 
                {
                    DataSource ds = new DataSource();
                    EnderecoDAO dao = new EnderecoDAO(ds);
                    List<Endereco> end = dao.read(c.getId());
                    c.setEnderecos(end);
                    ds.getConexao().close();
                } 
                catch (SQLException e) 
                {
                    RequestConf.erroSQL(e, request);
                }
            }
            destino = "/meusEnderecos.jsp";
            request.getSession().setAttribute("Cliente", c);
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