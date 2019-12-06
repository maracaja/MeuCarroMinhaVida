package model;

@SuppressWarnings("serial")
public class Produto implements java.io.Serializable
{  
    private int id;
    private boolean ativo;
    private Marca marca;
    private String modelo;
    private int ano;
    private boolean novo;
    private String descricao;
    private double preco;
    private String urlFoto1;
    private String urlFoto2;
    private String urlFoto3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public int getIdMarca(){
        return marca.getId();
    }

    public String getMarca() {
        return marca.getNomeMarca();
    }
    
    public String getLogoMarca() {
        return marca.getUrlLogo();
    }

    public void setMarca(int id, String nome, String logo) 
    {
        this.marca = new Marca();
        this.marca.setId(id);
        this.marca.setNomeMarca(nome);
        this.marca.setUrlLogo(logo);
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }
    
    public String getPrecoString()
    {
        return String.format("R$ %,.2f", getPreco());
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getUrlFoto1() {
        return urlFoto1;
    }

    public void setUrlFoto1(String urlFoto1) {
        this.urlFoto1 = urlFoto1;
    }

    public String getUrlFoto2() {
        return urlFoto2;
    }

    public void setUrlFoto2(String urlFoto2) {
        this.urlFoto2 = urlFoto2;
    }

    public String getUrlFoto3() {
        return urlFoto3;
    }

    public void setUrlFoto3(String urlFoto3) {
        this.urlFoto3 = urlFoto3;
    }   
}
