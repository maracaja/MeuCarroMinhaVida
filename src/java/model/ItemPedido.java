package model;

import java.util.Locale;

@SuppressWarnings("serial")
public final class ItemPedido implements java.io.Serializable
{
    private Produto produto;
    private double precoUnitario;
    private int quantidade;
    
    public ItemPedido(Produto pr, double preco, int qtd)
    {
        this.setProduto(pr);
        this.setPrecoUnitario(preco);
        this.setQuantidade(qtd);
    }
    
    public ItemPedido(ItemCarrinho item)
    {
        this(item.getProduto(), item.getProduto().getPreco(), item.getQtd());
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public double getPreco()
    {
        return this.getPrecoUnitario() * this.getQuantidade();
    }
    
    public String getPrecoString(boolean unitario)
    {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %1$,.2f", unitario ? this.getPrecoUnitario() : this.getPreco());
    }
}
