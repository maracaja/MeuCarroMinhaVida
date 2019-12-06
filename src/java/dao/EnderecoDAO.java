package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Endereco;

public class EnderecoDAO
{
    private final DataSource dataSource;
    
    public EnderecoDAO(DataSource ds)
    {
        this.dataSource = ds;
    }

    public int create(Object o) throws SQLException
    {
        Endereco end = (Endereco) o;
        String sql = "INSERT INTO Endereco VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, end.getCep());
            ps.setString(2, end.getLogradouro());
            ps.setString(3, end.getNumero());
            ps.setString(4, end.getComplemento());
            ps.setString(5, end.getBairro());
            ps.setString(6, end.getCidade());
            ps.setString(7, end.getUf());
            int resultBD = ps.executeUpdate();
            if(resultBD != 0)
            {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) end.setId(rs.getInt(1));
            }    
            else throw new RuntimeException("Erro: dados não puderam ser salvos.");
        }
        catch (Exception e) 
        {
            System.out.printf("\nErro ao inserir informações no banco de dados: %s\n", e.getMessage());
            throw e;
        }
        return end.getId();
    }
    
    public int create(Endereco end, int idcli) throws SQLException
    {
        int idend = create(end);
        String sql = "INSERT INTO EnderecosClientes VALUES (?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            ps.setInt(1, idcli);
            ps.setInt(2, idend);
            ps.setString(3, end.getMarcador());
            int result = ps.executeUpdate();
            if(result == 0)
                throw new RuntimeException("Erro: dados não puderam ser salvos");
        }
        catch (Exception e)
        {
            System.out.printf("\nErro ao inserir informações no banco de dados: %s\n", e.getMessage());
            throw e;
        }
        return idend;
    }

    public void delete(Object o) throws SQLException 
    {
        Endereco end = (Endereco) o;
        String sql = "DELETE FROM EnderecosClientes WHERE id_endereco = ?";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            ps.setInt(1, end.getId());
            int result = ps.executeUpdate();
            if (result == 0)
                throw new RuntimeException("Erro na remoção de dados cadastrais");
        }
        catch (Exception e)
        {
            System.out.printf("\nErro ao excluir informações do banco de dados: %s\n", e.getMessage());
            throw e;
        }
    }

    public void update(Object o) throws SQLException 
    {
        Endereco end = (Endereco) o;
        String sql = "UPDATE Endereco SET cep = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            ps.setString(1, end.getCep());
            ps.setString(2, end.getLogradouro());
            ps.setString(3, end.getNumero());
            ps.setString(4, end.getComplemento());
            ps.setString(5, end.getBairro());
            ps.setString(6, end.getCidade());
            ps.setString(7, end.getUf());
            ps.setInt(8, end.getId());
            int result = ps.executeUpdate();
            if (result == 0)
                throw new RuntimeException("Erro na atualização de dados cadastrais");
            sql = "UPDATE EnderecosClientes SET marcador = ? WHERE id_endereco = ?";
            try (PreparedStatement ps2 = dataSource.getConexao().prepareStatement(sql))
            {
                ps2.setString(1, end.getMarcador());
                ps2.setInt(2, end.getId());
                result = ps2.executeUpdate();
                if (result == 0)
                    throw new RuntimeException("Erro na atualização de dados cadastrais");
            }
        }
        catch (Exception e)
        {
            System.out.printf("\nErro ao atualizar informações no banco de dados: %s\n", e.getMessage());
            throw e;
        }
    }
    
    public List<Endereco> read(int idcli) throws SQLException 
    {
        List<Endereco> result = new ArrayList<>();
        String sql = "SELECT ec.id_endereco, ec.marcador, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.uf, e.cep FROM EnderecosClientes AS ec INNER JOIN Endereco AS e ON e.id = ec.id_endereco WHERE ec.id_cliente = ?";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            ps.setInt(1, idcli);
            try(ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    Endereco end = new Endereco();
                    end.setId(rs.getInt("id_endereco"));
                    end.setMarcador(rs.getString("marcador"));
                    end.setCep(rs.getString("cep"));
                    end.setLogradouro(rs.getString("logradouro"));
                    end.setNumero(rs.getString("numero"));
                    end.setComplemento(rs.getString("complemento"));
                    end.setBairro(rs.getString("bairro"));
                    end.setCidade(rs.getString("cidade"));
                    end.setUf(rs.getString("uf"));
                    result.add(end);
                }
            }
        }
        catch(Exception e)
        {
            System.out.printf("\nErro ao recuperar informações do Banco de Dados: %s\n", e.getMessage());
            throw e;
        }
        return result;
    }
}
