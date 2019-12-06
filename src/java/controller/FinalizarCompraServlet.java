package controller;

import dao.DataSource;
import dao.PedidoDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.RequestConf;
import model.Carrinho;
import model.Cliente;
import model.Endereco;
import model.Pedido;

@SuppressWarnings("serial")
public class FinalizarCompraServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect("/meusPedidos");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/erro.jsp";
        Cliente cli = Cliente.verificaUsuario(request);
        Carrinho cegonha = Carrinho.verificaCarrinho(request);
        if (cli.getId() == -1)
        {
            destino = "/login.jsp";
            RequestConf.requestLoginPadrao(request);
        }
        else if (cegonha.getItens().isEmpty()) 
            destino = "/vitrine.jsp";
        else
        {
            int id = Integer.parseInt(request.getParameter("enderecoEntrega"));
            Endereco entrega = null;
            for (Endereco end : cli.getEnderecos())
            {
                if (end.getId() == id)
                {
                    entrega = end;
                    break;
                }
            }
            if (entrega != null)
            {
                Pedido p = new Pedido(cegonha, entrega);
                try
                {
                    DataSource ds = new DataSource();
                    PedidoDAO dao = new PedidoDAO(ds);
                    dao.create(p, cli);
                    if (!cli.getPedidos().isEmpty())
                    {
                        cli.adicionaPedido(p);
                        request.getSession().setAttribute("Cliente", cli);
                    }
                    cegonha = new Carrinho();
                    ds.getConexao().close();
                    request.getSession().setAttribute("Carrinho", cegonha);
                    destino = "/confirmacao.jsp";
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
            else request.setAttribute("erroInfo", "Erro ao cadastrar pedido: falha ao registrar endereço de entrega");
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
        rd.forward(request, response);
    }
}
