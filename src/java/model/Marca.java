package model;

@SuppressWarnings("serial")
 public class Marca implements java.io.Serializable
{
    private int id;
    private String nomeMarca;
    private String urlLogo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String marca) {
        this.nomeMarca = marca;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }
}
