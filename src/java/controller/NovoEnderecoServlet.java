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
public class NovoEnderecoServlet extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        Cliente c = Cliente.verificaUsuario(request);
        if (c.getId() != -1)
        {
            try 
            {
                Endereco end = new Endereco();
                end.setCep(request.getParameter("cep"));
                end.setMarcador(request.getParameter("marcador"));
                end.setLogradouro(request.getParameter("logradouro"));
                end.setNumero(request.getParameter("numero"));
                end.setComplemento(request.getParameter("complemento"));
                end.setBairro(request.getParameter("bairro"));
                end.setCidade(request.getParameter("cidade"));
                end.setUf(request.getParameter("uf"));
                DataSource ds = new DataSource();
                EnderecoDAO dao = new EnderecoDAO(ds);
                int id = dao.create(end, c.getId());
                end.setId(id);
                c.adicionaEndereco(end);
                ds.getConexao().close();
                request.getSession().setAttribute("Cliente", c);
                response.sendRedirect("/meusEnderecos");
            } 
            catch (SQLException e) 
            {
                RequestConf.erroSQL(e, request);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/util/erro.jsp");
                rd.forward(request, response);
            }
        }
        else
        {
            RequestConf.requestLoginPadrao(request);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }
    }
}
