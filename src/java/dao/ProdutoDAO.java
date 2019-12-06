package dao;

import model.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Não está prevista a operação DELETE para esta classe:
 * Os produtos se tornarão apenas inativos, não aparecendo disponíveis para venda, mas manterão seu registro no banco de dados
 * @author Maracajá
 */

public class ProdutoDAO
{
    private final DataSource dataSource;
    
    public ProdutoDAO(DataSource ds)
    {
        this.dataSource = ds;
    }
    
    public void create(Object o) throws SQLException
    {
        Produto p = (Produto) o;
        String sql = "INSERT INTO Produto VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            ps.setBoolean(1, p.isAtivo());
            ps.setInt(2, p.getIdMarca());
            ps.setString(3, p.getModelo());
            ps.setInt(4, p.getAno());
            ps.setBoolean(5, p.isNovo());
            ps.setString(6, p.getDescricao());
            ps.setDouble(7, p.getPreco());
            ps.setString(8, p.getUrlFoto1());
            ps.setString(9, p.getUrlFoto2());
            ps.setString(10, p.getUrlFoto3());
            int result = ps.executeUpdate();
            if (result == 0)
                throw new RuntimeException("Erro ao inserir dados"); 
        }
        catch (Exception e)
        {
            System.out.printf("Erro ao adicionar informações no banco de dados: %s", e.toString());
            throw e;
        }
    }

    public void update(Object o) throws Exception
    {
        Produto p = (Produto) o;
        String[] fotoNula = new String[3];
        fotoNula[0] = String.format("%s, urlFoto1 = ?%s", (p.getUrlFoto1() == null ? " /*" : ""), (p.getUrlFoto1() == null ? "*/" : ""));
        fotoNula[1] = String.format("%s, urlFoto2 = ?%s", (p.getUrlFoto2() == null ? " /*" : ""), (p.getUrlFoto2() == null ? "*/" : ""));
        fotoNula[2] = String.format("%s, urlFoto3 = ?%s", (p.getUrlFoto3() == null ? " /*" : ""), (p.getUrlFoto3() == null ? "*/" : ""));
        String sql = String.format("UPDATE Produto SET ativo = ?, marca = ?, modelo = ?, ano = ?, novo = ?, descricao = ?, preco = ?%s%s%s WHERE id = ?", fotoNula[0], fotoNula[1], fotoNula[2]);
        System.out.println(sql);
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql))
        {
            int cont = 1;
            ps.setBoolean(cont, p.isAtivo()); cont++;
            ps.setInt(cont, p.getIdMarca()); cont++;
            ps.setString(cont, p.getModelo()); cont++;
            ps.setInt(cont, p.getAno()); cont++;
            ps.setBoolean(cont, p.isNovo()); cont++;
            ps.setString(cont, p.getDescricao()); cont++;
            ps.setDouble(cont, p.getPreco()); cont++;
            if(p.getUrlFoto1() != null) {ps.setString(cont, p.getUrlFoto1()); cont++;}
            if(p.getUrlFoto2() != null) {ps.setString(cont, p.getUrlFoto2()); cont++;}
            if(p.getUrlFoto3() != null) {ps.setString(cont, p.getUrlFoto3()); cont++;}
            ps.setInt(cont, p.getId());
            int result = ps.executeUpdate();
            if (result == 0)
                throw new RuntimeException("Erro ao inserir dados"); 
        }
        catch (Exception e)
        {
            System.out.printf("Erro ao atualizar informações no banco de dados: %s", e.toString());
            throw e;
        } 
    }

    public List<Produto> read(boolean todos) throws SQLException
    {
        List<Produto> result = null;
        try
        { 
            String sql = String.format("SELECT p.ativo, p.id, p.marca AS id_marca, m.marca, p.modelo, p.ano, p.novo, p.descricao, p.preco, p.urlFoto1, p.urlFoto2, p.urlFoto3, m.urlLogo FROM Produto AS p LEFT JOIN Marca AS m ON p.marca = m.id%s", todos ? " ORDER BY m.marca" : " WHERE p.ativo = true");
            try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql); ResultSet rs = ps.executeQuery()) 
            {
                result = new ArrayList<>();
                while (rs.next())
                {
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setAtivo(rs.getBoolean("ativo"));
                    p.setMarca(rs.getInt("id_marca"), rs.getString("marca"), rs.getString("urlLogo"));
                    p.setModelo(rs.getString("modelo"));
                    p.setAno(rs.getInt("ano"));
                    p.setNovo(rs.getBoolean("novo"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setPreco(rs.getDouble("preco"));
                    p.setUrlFoto1(rs.getString("urlFoto1"));
                    p.setUrlFoto2(rs.getString("urlFoto2"));
                    p.setUrlFoto3(rs.getString("urlFoto3"));
                    result.add(p);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.printf("\nErro ao recuperar informações do Banco de Dados: %s\n", e.getMessage());
            throw e;
        }
        return result;
    }
    
    public List<Object> read(int id) throws SQLException
    {
        List<Object> result = null;
        try
        {
            String sql = "SELECT p.id, p.ativo, p.marca AS id_marca, m.marca, p.modelo, p.ano, p.novo, p.descricao, p.preco, p.urlFoto1, p.urlFoto2, p.urlFoto3, m.urlLogo FROM Produto AS p LEFT JOIN Marca AS m ON p.marca = m.id WHERE p.id = ?";
            try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql)) 
            {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    result = new ArrayList<>();
                    while(rs.next())
                    {
                        Produto p = new Produto();
                        p.setId(rs.getInt("id"));
                        p.setAtivo(rs.getBoolean("ativo"));
                        p.setMarca(rs.getInt("id_marca"), rs.getString("marca"), rs.getString("urlLogo"));
                        p.setModelo(rs.getString("modelo"));
                        p.setAno(rs.getInt("ano"));
                        p.setNovo(rs.getBoolean("novo"));
                        p.setDescricao(rs.getString("descricao"));
                        p.setPreco(rs.getDouble("preco"));
                        p.setUrlFoto1(rs.getString("urlFoto1"));
                        p.setUrlFoto2(rs.getString("urlFoto2"));
                        p.setUrlFoto3(rs.getString("urlFoto3"));
                        result.add(p);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            System.out.printf("\nErro ao recuperar informações do Banco de Dados: %s\n", e.getMessage());
            throw e;
        }
        return result;
    }
    
    public List<Object> read(int[] ids) throws SQLException
    {
        List<Object> result = null;
        try
        {
            String args = "";
            for (int i = 0; i < ids.length; i++)
                args = String.format("%s?%s", args, (i != ids.length - 1 ? ", " : ""));
            String sql = String.format("SELECT p.id, p.marca AS id_marca, m.marca, p.modelo, p.ano, p.novo, p.descricao, p.preco, p.urlFoto1, p.urlFoto2, p.urlFoto3, m.urlLogo FROM Produto AS p LEFT JOIN Marca AS m ON p.marca = m.id WHERE p.id IN (%s) AND p.ativo = true", args);
            try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql)) 
            {
                for (int i = 1; i <= ids.length; i++)
                    ps.setInt(i, ids[i - 1]);
                try (ResultSet rs = ps.executeQuery())
                {
                    result = new ArrayList<>();
                    while(rs.next())
                    {
                        Produto p = new Produto();
                        p.setId(rs.getInt("id"));
                        p.setAtivo(true);
                        p.setMarca(rs.getInt("id_marca"), rs.getString("marca"), rs.getString("urlLogo"));
                        p.setModelo(rs.getString("modelo"));
                        p.setAno(rs.getInt("ano"));
                        p.setNovo(rs.getBoolean("novo"));
                        p.setDescricao(rs.getString("descricao"));
                        p.setPreco(rs.getDouble("preco"));
                        p.setUrlFoto1(rs.getString("urlFoto1"));
                        p.setUrlFoto2(rs.getString("urlFoto2"));
                        p.setUrlFoto3(rs.getString("urlFoto3"));
                        result.add(p);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            System.out.printf("\nErro ao recuperar informações do Banco de Dados: %s\n", e.getMessage());
            throw e;
        }
        return result;
    }
}
