package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class Cliente implements java.io.Serializable
{
    private int id;
    private String cpf;
    private String nome;
    private Date dataNasc;
    private String email;
    private String senha;
    private final List<Endereco> enderecos;
    private final List<Pedido> pedidos;
    
    public Cliente()
    {
        this.enderecos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }
    
    public String primeiroNome()
    {
        String prenome = "";
        int i = 0;
        while(!Character.isAlphabetic(this.getNome().charAt(i))) i++;
        for (; i < this.getNome().length() && !Character.isSpaceChar(this.getNome().charAt(i)); i++) 
            prenome += this.getNome().charAt(i);
        return prenome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Endereco> getEnderecos() 
    {
        List<Endereco> result = this.enderecos;
        return result;
    }
    
    public void setEnderecos(List<Endereco> lista)
    {
        if (lista != null)
            for (Endereco end : lista)
                this.adicionaEndereco(end);
    }

    public void adicionaEndereco(Endereco end) 
    {
        this.enderecos.add(end);
    }
    
    public void removeEndereco(int id)
    {
        Endereco endereco = temEndereco(id);
        if (endereco != null)
            this.enderecos.remove(endereco);
    }
    
    public Endereco temEndereco(int id)
    {
        for (Endereco addr : this.getEnderecos())
        {
            if (addr.getId() == id)
            {
                return addr;
            }
        }
        return null;
    }
    
    public List<Pedido> getPedidos() 
    {
        List<Pedido> result = this.pedidos;
        return result;
    }
    
    public void setPedidos(List<Pedido> lista)
    {
        if (lista != null)            
            for (Pedido p : lista)
                this.adicionaPedido(p);
    }
    
    public void adicionaPedido(Pedido p) 
    {
        this.pedidos.add(p);
    }
    
    public static Cliente verificaUsuario(HttpServletRequest request)
    {
        Cliente c = (Cliente) request.getSession().getAttribute("Cliente");
        if (c == null)
        {
            c = new Cliente();
            c.setId(-1);
        }
        request.getSession().setAttribute("Cliente", c);
        return c;
    }
}
