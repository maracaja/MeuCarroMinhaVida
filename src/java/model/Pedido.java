package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressWarnings("serial")
public final class Pedido implements java.io.Serializable
{
    private int id;
    private GregorianCalendar dataHora;
    private Endereco entrega;
    private final List<ItemPedido> itens;
    
    public Pedido()
    {
        this.itens = new ArrayList<>();
    }
    
    public Pedido(Carrinho cegonha, Endereco entrega)
    {
        this();
        this.setEntrega(entrega);
        for (ItemCarrinho item : cegonha.getItens())
        {
            ItemPedido novo = new ItemPedido(item);
            this.adicionaItem(novo);
        }
        this.setDataHora();
    }

    public int getId() {
        return id;
    }
    
    public String getCodigo()
    {
        return String.format("%05d", this.getId());
    }

    public void setId(int id) {
        this.id = id;
    }

    public GregorianCalendar getDataHora() {
        return dataHora;
    }
    
    public String dataFormatada()
    {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return fmt.format(this.getDataHora().getTime());
    }

    public void setDataHora(GregorianCalendar dataHora)
    {
        this.dataHora = dataHora;
    }

    private void setDataHora() 
    {
        this.dataHora = new GregorianCalendar();
    }

    public Endereco getEntrega() {
        return entrega;
    }

    public void setEntrega(Endereco entrega) {
        this.entrega = entrega;
    }
    
    public List<ItemPedido> getItens() 
    {
        List<ItemPedido> result = itens;
        return result;
    }

    public void adicionaItem(ItemPedido item) 
    {
        this.itens.add(item);
    }
    
    public double precoTotal()
    {
        double soma = 0;
        for (ItemPedido item : this.getItens())
            soma += item.getPreco();
        return soma;
    }
    
    public int totalProdutos()
    {
        int soma = 0;
        for (ItemPedido item : this.getItens())
            soma += item.getQuantidade();
        return soma;
    }
    
    public String getPrecoString()
    {
        return String.format("R$ %,.2f", this.precoTotal());
    }   
}
