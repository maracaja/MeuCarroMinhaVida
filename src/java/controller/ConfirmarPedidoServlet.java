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
import model.Carrinho;
import model.Cliente;
import model.Endereco;

@SuppressWarnings("serial")
public class ConfirmarPedidoServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        Cliente c = Cliente.verificaUsuario(request);
        Carrinho cegonha = Carrinho.verificaCarrinho(request);
        String destino = "/util/erro.jsp";
        if (c.getId() == -1)
        {
            RequestConf.requestLoginPadrao(request);
            destino = "/login.jsp";
        }
        else if (!cegonha.getItens().isEmpty())
        {
            if (c.getEnderecos().isEmpty())
            {
                try
                {
                    DataSource ds = new DataSource();
                    EnderecoDAO dao = new EnderecoDAO(ds);
                    List<Endereco> lista = dao.read(c.getId());
                    c.setEnderecos(lista);
                    ds.getConexao().close();
                    request.getSession().setAttribute("Cliente", c);
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
            destino = "/confirmarPedido.jsp";
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
        rd.forward(request, response);
    }
}
