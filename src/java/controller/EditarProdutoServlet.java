package controller;

import dao.DataSource;
import dao.MarcaDAO;
import dao.ProdutoDAO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.Admin;
import lib.RequestConf;
import model.Marca;
import model.Produto;

@SuppressWarnings("serial")
public class EditarProdutoServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/404.jsp";
        if (Admin.verificaAdmin(request))
        {
            try
            {
                DataSource ds = new DataSource();
                MarcaDAO mdao = new MarcaDAO(ds);
                List<Marca> lista = mdao.read();
                int id = Integer.parseInt(request.getParameter("id"));
                ProdutoDAO pdao = new ProdutoDAO(ds);
                Produto p = (Produto) pdao.read(id).get(0);
                destino = "/adm/cadastroProduto.jsp";
                request.setAttribute("ListaMarcas", lista);
                request.setAttribute("Novo", Boolean.FALSE);
                request.setAttribute("Produto", p);
                ds.getConexao().close();
            }
            catch (Exception e)
            {
                destino = "/util/erro.jsp";
                RequestConf.exception(e, request);
            }
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String destino = "/util/404.jsp";
        if(Admin.verificaAdmin(request))
        {
            String url;
            String[] relativa = {null, null, null};
            for (int i = 1; i <= 3; i++)
            {
                if (request.getPart("foto" + i) != null && request.getPart("foto" + i).getSize() != 0)
                {
                    String nomeArq = Admin.geraNome();
                    String ext = Admin.capturaExtensao(request.getPart("foto" + i).getSubmittedFileName());
                    url = String.format("%s%s%s.%s", getServletContext().getRealPath("/img"), File.separator, nomeArq, ext);
                    relativa[i - 1] = String.format("img/%s.%s", nomeArq, ext);
                    Admin.uploadFoto(request, "foto" + i, url);
                }
            }
            Produto p = new Produto();
            p.setId(Integer.parseInt(request.getParameter("id")));
            p.setModelo(request.getParameter("modelo"));
            p.setAtivo(Boolean.parseBoolean(request.getParameter("ativo")));
            p.setMarca(Integer.parseInt(request.getParameter("marca")), "", "");
            p.setAno(Integer.parseInt(request.getParameter("ano")));
            p.setDescricao(request.getParameter("descricao"));
            p.setNovo(Boolean.parseBoolean(request.getParameter("novo")));
            p.setPreco(Double.parseDouble(request.getParameter("preco")));
            p.setUrlFoto1(relativa[0]);
            p.setUrlFoto2(relativa[1]);
            p.setUrlFoto3(relativa[2]);
            try
            {
                DataSource ds = new DataSource();
                ProdutoDAO dao = new ProdutoDAO(ds);
                dao.update(p);
                ds.getConexao().close();
                response.sendRedirect("/relatorioProdutos");
            }
            catch (Exception e)
            { 
                RequestConf.exception(e, request);
            }
        }
        else
        {
            RequestDispatcher rd = getServletContext().getRequestDispatcher(destino);
            rd.forward(request, response);
        }
    }
}
