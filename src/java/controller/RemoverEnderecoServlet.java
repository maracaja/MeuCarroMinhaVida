package controller;

import dao.DataSource;
import dao.EnderecoDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.RequestConf;
import model.Cliente;
import model.Endereco;

@SuppressWarnings("serial")
public class RemoverEnderecoServlet extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        int id = Integer.parseInt(request.getParameter("remove"));
        Cliente c = (Cliente) request.getSession().getAttribute("Cliente");
        if (c.getId() != -1)
        {
            Endereco end = c.temEndereco(id);
            if(end != null)
            {           
                try 
                {
                    DataSource ds = new DataSource();
                    EnderecoDAO dao = new EnderecoDAO(ds);
                    dao.delete(end);
                    c.removeEndereco(id);
                    ds.getConexao().close();
                } 
                catch (SQLException e) 
                {
                    RequestConf.erroSQL(e, request);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/util/erro.jsp");
                    rd.forward(request, response);
                }
            }
            response.sendRedirect("/meusEnderecos");
        }
        else
        {
            RequestConf.requestLoginPadrao(request);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }              
    }
}
