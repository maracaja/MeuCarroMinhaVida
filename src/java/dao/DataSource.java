package dao;

import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException;

public final class DataSource 
{
    private String nomeHost;
    private int porta;
    private String bd;
    private String usuario;
    private String senha;
    private Connection conexao;

    public DataSource() throws SQLException
    {
        setNomeHost("127.0.0.1");
        setPorta(3306);
        setBD("ecommerce");
        setUsuario("comercio");
        setSenha("Projeto2019");
        try
        {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = String.format("jdbc:mysql://%s:%d/%s?useTimezone=true&serverTimezone=UTC", getNomeHost(), getPorta(), getBD());
            setConexao(DriverManager.getConnection(url, getUsuario(), getSenha()));
        }
        catch(SQLException e)
        {
            System.out.printf("\nErro de conexão com o Banco de Dados: %s\n", e.getMessage());
            throw e;
        }
    }
    
    private void setNomeHost(String host) {
        this.nomeHost = host;
    }

    private void setPorta(int porta) {
        this.porta = porta;
    }

    private void setBD(String bd) {
        this.bd = bd;
    }

    private void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    private void setSenha(String senha) {
        this.senha = senha;
    }

    private void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
    
    public Connection getConexao() {
        return conexao;
    }

    private String getNomeHost() {
        return nomeHost;
    }

    private int getPorta() {
        return porta;
    }

    private String getBD() {
        return bd;
    }

    private String getUsuario() {
        return usuario;
    }

    private String getSenha() {
        return senha;
    }
}
