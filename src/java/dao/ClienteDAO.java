package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import lib.Senha;
import model.Cliente;
import model.Endereco;

/**
 * Pela lógica de negócio adotada, não é implementada a operação DELETE para clientes
 * @author Maracajá
 */

public class ClienteDAO
{
    private final DataSource dataSource;
    
    public ClienteDAO(DataSource ds)
    {
        this.dataSource = ds;
    }

    public int create(Object o) throws SQLException
    {
        Cliente c = (Cliente) o;
        String sql = "INSERT INTO Cliente VALUES (NULL, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setDate(3, c.getDataNasc());
            ps.setString(4, c.getEmail().toLowerCase());
            ps.setString(5, Senha.cripto(c.getEmail().toLowerCase(), c.getSenha(), true));
            int result = ps.executeUpdate();
            if (result == 0)
                throw new RuntimeException("Erro ao inserir dados: Registros duplicados");
            try (ResultSet rs = ps.getGeneratedKeys())
            {
                if(rs.next()) c.setId(rs.getInt(1));
            }
        }
        catch (Exception e)
        {
            System.out.printf("\nErro ao inserir informações no banco de dados: %s\n", e.getMessage());
            throw e;
        }
        return c.getId();
    }
    
    public void create(Object oc, Object oe) throws SQLException
    {
        int cid = this.create(oc);
        EnderecoDAO enddao = new EnderecoDAO(dataSource);
        enddao.create((Endereco) oe, cid);
    }

    public void update(Object o, String strChave) throws SQLException, Exception
    {
        Cliente c = (Cliente) o;
        String sql = "UPDATE Cliente SET nome = ?, dataNasc = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            ps.setString(1, c.getNome());
            ps.setDate(2, c.getDataNasc());
            ps.setString(3, c.getEmail().toLowerCase());
            ps.setString(4, Senha.cripto(c.getEmail().toLowerCase(), readSenha(c.getId(), strChave), true));
            ps.setInt(5, c.getId());
            int result = ps.executeUpdate();
            if (result == 0)
                throw new RuntimeException("Erro ao cadastrar atualização de dados cadastrais");
        }
        catch (Exception e)
        {
            System.out.printf("\nErro ao atualizar informações no banco de dados: %s\n", e.getMessage());
            throw e;
        }
    }
    
    public void updateSenha(Cliente cli, String senha) throws SQLException
    {
        String sql = "UPDATE Cliente SET senha = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            ps.setString(1, Senha.cripto(cli.getEmail(), senha, true));
            ps.setInt(2, cli.getId());
            int result = ps.executeUpdate();
            if(result == 0)
                throw new RuntimeException("Erro ao gravar nova senha no banco de dados");
        }
        catch (Exception e)
        {
            System.out.printf("\nErro ao atualizar informações no banco de dados: %s\n", e.getMessage());
            throw e;
        }        
    }
    
    private String readSenha(int id, String email) throws SQLException
    {
        String sql = "SELECT senha FROM Cliente WHERE id = ?";
        String senhaBD = "";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery())
            {
                if(rs.next()) senhaBD = rs.getString(1);
            }
        }
        catch(Exception e)
        {
            System.out.printf("\nErro ao recuperar informações do Banco de Dados: %s\n", e.getMessage());
            throw e;
        }
        return Senha.cripto(email, senhaBD, false);
    }
    
    public Cliente read(int id) throws SQLException, Exception
    {
        List<Object> result = new ArrayList<>();
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery())
            {
                while(rs.next())
                {   // Não irá carregar as informações de senha
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCpf(rs.getString("cpf"));
                    GregorianCalendar cal = new GregorianCalendar();
                    c.setDataNasc(rs.getDate("dataNasc", cal));
                    c.setEmail(rs.getString("email"));
                    result.add(c);
                }
            }
        }
        catch (Exception e)
        {
            System.out.printf("\nErro ao recuperar informações do Banco de Dados: %s\n", e.getMessage());
            throw e;
        }
        if(result.size() > 0) return (Cliente) result.get(0);
        else throw new Exception("Cliente não encontrado!");
    }
}