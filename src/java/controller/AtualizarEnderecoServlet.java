package controller;

import dao.DataSource;
import dao.EnderecoDAO;
import dao.PedidoDAO;
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
import model.Pedido;

@SuppressWarnings("serial")
public class AtualizarEnderecoServlet extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        Cliente c = Cliente.verificaUsuario(request);
        if (c.getId() != -1)
        {
            int id = Integer.parseInt(request.getParameter("id"));
            Endereco end = c.temEndereco(id);
            if (end != null)
            {
                try 
                {
                    end.setCep(request.getParameter("cep"));
                    end.setMarcador(request.getParameter("marcador"));
                    end.setLogradouro(request.getParameter("logradouro"));
                    end.setNumero(request.getParameter("numero"));
                    end.setComplemento(request.getParameter("complemento"));
                    end.setBairro(request.getParameter("bairro"));
                    end.setCidade(request.getParameter("cidade"));
                    end.setUf(request.getParameter("uf"));
                    DataSource ds = new DataSource();
                    if (c.getPedidos().isEmpty())
                    {
                        PedidoDAO pdao = new PedidoDAO(ds);
                        c.setPedidos(pdao.read(c.getId()));
                    }
                    boolean enderecoUsado = false;
                    for (Pedido p : c.getPedidos())
                    {
                        if (p.getEntrega().getId() == id)
                        {
                            enderecoUsado = true;
                            break;
                        }
                    }
                    EnderecoDAO edao = new EnderecoDAO(ds);
                    if (enderecoUsado)
                    {
                        edao.delete(end);
                        end.setId(edao.create(end, c.getId()));
                    }
                    else edao.update(end);
                    ds.getConexao().close();
                    c.removeEndereco(id);
                    c.adicionaEndereco(end);
                    request.getSession().setAttribute("Cliente", c);
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
