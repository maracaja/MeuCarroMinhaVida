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
import model.Endereco;

@SuppressWarnings("serial")
public class CadastraUsuarioServlet extends HttpServlet 
{
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  
    {
        String destino = "/util/erro.jsp";
        try
        {
            Cliente c = new Cliente();
            Endereco e = new Endereco();
            c.setNome(request.getParameter("nome"));
            c.setCpf(request.getParameter("cpf"));
            c.setDataNasc(Date.valueOf(request.getParameter("dataNasc")));
            c.setEmail(request.getParameter("email"));
            c.setSenha(request.getParameter("senha"));
            e.setMarcador(request.getParameter("marcador"));
            e.setCep(request.getParameter("cep"));
            e.setLogradouro(request.getParameter("logradouro"));
            e.setNumero(request.getParameter("numero"));
            e.setComplemento(request.getParameter("complemento"));
            e.setBairro(request.getParameter("bairro"));
            e.setCidade(request.getParameter("cidade"));
            e.setUf(request.getParameter("uf"));
            DataSource ds = new DataSource();
            ClienteDAO cdao = new ClienteDAO(ds);
            cdao.create(c, e);
            ds.getConexao().close();
            request.setAttribute("Cadastrado", Boolean.TRUE);
            request.setAttribute("Aviso", "");
            destino = "/login.jsp";
        }
        catch (SQLException e)
        {
            if(e.getErrorCode() == 1062) // Registro duplicado
            {
                destino = "/cadastro.jsp";
                String msg = String.format("Já existe um usuário com o %s informado", e.getMessage().contains("cpf") ? "CPF" : "e-mail");
                request.setAttribute("Aviso", msg);
            }
            else RequestConf.erroSQL(e, request);
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
