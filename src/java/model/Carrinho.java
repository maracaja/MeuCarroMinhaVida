package model;

import dao.DataSource;
import dao.ProdutoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

public class Carrinho 
{
    private final ArrayList<ItemCarrinho> lista;
    private double total;
    
    public Carrinho()
    {
        lista = new ArrayList<>();
        total = 0.0;
    }
    
    public void adicionar(Produto p, int qtd)
    {
        boolean add = false;
        for(ItemCarrinho ic : lista)
        {
            if (ic.getProduto().getId() == p.getId())
            {
                ic.aumenta(qtd);
                total += ic.getProduto().getPreco() * qtd;
                add = true;
                break;
            }
        }
        if (!add)
        {
            ItemCarrinho item = new ItemCarrinho(p);
            if(qtd > 1) item.aumenta(qtd - 1);
            lista.add(item);
            total += p.getPreco() * qtd;
        }
    }
    
    public void adicionar(int idProduto, int qtd)
    {
        try
        {
            DataSource ds = new DataSource();
            ProdutoDAO dao = new ProdutoDAO(ds);
            List<Object> result = dao.read(idProduto);
            adicionar((Produto) result.get(0), qtd);
            ds.getConexao().close();
        }
        catch (SQLException e)
        {
            System.out.printf("\nErro (Carrinho): %s\n", e.toString());
        }
    }
    
    public void remover(int idProduto)
    {
        ItemCarrinho item = null;
        for (ItemCarrinho ic : lista)
        {
            if (ic.getProduto().getId() == idProduto)
            {
                item = ic;
                break;
            }
        }
        if (item != null)
        {
            total -= item.getProduto().getPreco() * item.getQtd();
            lista.remove(item);
        }
    }
    
    public void alteraItem(int idProduto, boolean aumenta)
    {
        ItemCarrinho item = null;
        for (ItemCarrinho ic : lista)
        {
            if (ic.getProduto().getId() == idProduto)
            {
                item = ic;
                break;
            }
        }
        if(item != null)
        {
            if (aumenta) 
            {
                item.aumenta();
                total += item.getProduto().getPreco();
            }
            else if(item.getQtd() > 1)
            {
                item.diminui();
                total -= item.getProduto().getPreco();
            }
        }
    }
    
    public double getTotal()
    { return total; }
    
    public String getTotalString()
    { return String.format(Locale.forLanguageTag("pt-BR"), "R$ %1$,.2f", getTotal()); }
    
    public ArrayList<ItemCarrinho> getItens()
    {
        ArrayList<ItemCarrinho> result = this.lista;
        return result; 
    }
    
    public static Carrinho verificaCarrinho(HttpServletRequest request)
    {
        Carrinho c = (Carrinho) request.getSession().getAttribute("Carrinho");
        if (c == null) c = new Carrinho();
        request.getSession().setAttribute("Carrinho", c);
        return c;
    }
}
