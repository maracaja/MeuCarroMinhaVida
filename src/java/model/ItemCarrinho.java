package model;

public class ItemCarrinho 
{
    private Produto p;
    private int qtd = 1;
    
    public ItemCarrinho(Produto p)
    { setProduto(p); }
    
    private void setProduto(Produto p)
    { this.p = p; }
    
    public void aumenta()
    { qtd++; }
    
    public void aumenta(int q)
    { if (q > 0) qtd += q; }
    
    public void diminui()
    { if (getQtd() > 1) qtd--; }
    
    public int getQtd()
    { return qtd; }
    
    public Produto getProduto()
    { return p; }
    
    public double getPreco()
    { return getProduto().getPreco() * getQtd(); }
    
    public String getPrecoString()
    { return String.format("R$ %,.2f", this.getPreco()); }
}
