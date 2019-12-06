package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import model.Cliente;
import model.Endereco;
import model.ItemPedido;
import model.Pedido;
import model.Produto;

/**
 * As operações update e delete não serão implementadas devido à lógica de negócio:
 * Não há motivo para apagar o registro de um pedido nem alterá-lo, uma vez que foi finalizado
 * @author Maracajá
 */

public class PedidoDAO
{
    private final DataSource dataSource;
    
    public PedidoDAO(DataSource ds)
    {
        this.dataSource = ds;
    }

    public void create(Pedido p, Cliente c) throws SQLException
    {
        // Cria o pedido
        String sql = "INSERT INTO Pedido VALUES (NULL, ?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            Timestamp t = new Timestamp(p.getDataHora().getTimeInMillis());
            ps.setTimestamp(1, t);
            ps.setInt(2, c.getId());
            ps.setInt(3, p.getEntrega().getId());
            int result = ps.executeUpdate();
            if (result == 0)
                throw new RuntimeException("Erro na inserção de dados");
            try (ResultSet rs = ps.getGeneratedKeys())
            {
                if(rs.next()) p.setId(rs.getInt(1));
            }
            // Cria os itens do pedido
            String args = "";
            for (int i = 0; i < p.getItens().size(); i++)
                args = String.format("%s%s (?, ?, ?, ?)", args, i != 0 ? "," : "");
            sql = String.format("INSERT INTO ItemPedido VALUES%s", args); 
            try (PreparedStatement ps2 = dataSource.getConexao().prepareStatement(sql))
            {
                for (int i = 0; i < p.getItens().size(); i++)
                {
                    ps2.setInt(4*i + 1, p.getId());
                    ps2.setInt(4*i + 2, p.getItens().get(i).getProduto().getId());
                    ps2.setDouble(4*i + 3, p.getItens().get(i).getPrecoUnitario());
                    ps2.setInt(4*i + 4, p.getItens().get(i).getQuantidade());
                }
                result = ps2.executeUpdate();
                if (result == 0)
                    throw new RuntimeException("Erro na inserção de dados");
            }
        } 
        catch (Exception e) 
        {
            System.out.printf("Erro ao inserir informações no banco de dados: %s\n", e.toString());
            throw e;
        }
    }

    public List<Pedido> read(int idcli) throws SQLException 
    {
        String sql1 = "SELECT p.id, p.dataHora, e.id AS id_endereco, e.cep, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.uf FROM Pedido AS p INNER JOIN Endereco AS e ON p.entrega = e.id WHERE id_cliente = ? ORDER BY p.id";
        String sql2 = "SELECT i.id_pedido AS codigo_pedido, p.id, p.ativo, p.marca AS id_marca, m.marca, p.modelo, p.ano, p.novo, p.descricao, p.urlFoto1, p.urlFoto2, p.urlFoto3, m.urlLogo, i.precoUnit, i.quantidade FROM Produto AS p LEFT JOIN Marca AS m ON p.marca = m.id INNER JOIN ItemPedido AS i ON i.id_produto = p.id WHERE i.id_pedido IN (SELECT id FROM Pedido WHERE id_cliente = ? ORDER BY id) ORDER BY codigo_pedido";
        List<Pedido> result = new ArrayList<>();
        try (PreparedStatement ps1 = dataSource.getConexao().prepareStatement(sql1); PreparedStatement ps2 = dataSource.getConexao().prepareStatement(sql2))
        {
            ps1.setInt(1, idcli);
            ps2.setInt(1, idcli);
            try (ResultSet rs1 = ps1.executeQuery(); ResultSet rs2 = ps2.executeQuery())
            {
                Pedido p;
                Endereco end;
                Produto prod;
                ItemPedido it = null;
                boolean mesmoPedido;
                while (rs1.next())
                {
                    p = new Pedido();
                    end = new Endereco();
                    p.setId(rs1.getInt("id"));
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(rs1.getTimestamp("dataHora").getTime());
                    p.setDataHora(cal);
                    end.setId(rs1.getInt("id_endereco"));
                    end.setCep(rs1.getString("cep"));
                    end.setLogradouro(rs1.getString("logradouro"));
                    end.setNumero(rs1.getString("numero"));
                    end.setComplemento(rs1.getString("complemento"));
                    end.setBairro(rs1.getString("bairro"));
                    end.setCidade(rs1.getString("cidade"));
                    end.setUf(rs1.getString("uf"));
                    p.setEntrega(end);
                    if (it != null)
                        p.adicionaItem(it);
                    mesmoPedido = true;
                    while (mesmoPedido && rs2.next())
                    {
                        prod = new Produto();
                        prod.setId(rs2.getInt("id"));
                        prod.setAtivo(rs2.getBoolean("ativo"));
                        prod.setMarca(rs2.getInt("id_marca"), rs2.getString("marca"), rs2.getString("urlLogo"));
                        prod.setModelo(rs2.getString("modelo"));
                        prod.setAno(rs2.getInt("ano"));
                        prod.setDescricao(rs2.getString("descricao"));
                        prod.setNovo(rs2.getBoolean("novo"));
                        prod.setUrlFoto1(rs2.getString("urlFoto1"));
                        prod.setUrlFoto2(rs2.getString("urlFoto2"));
                        prod.setUrlFoto3(rs2.getString("urlFoto3"));
                        it = new ItemPedido(prod, rs2.getDouble("precoUnit"), rs2.getInt("quantidade"));
                        if (rs2.getInt("codigo_pedido") != p.getId())
                            mesmoPedido = false;
                        else p.adicionaItem(it);
                    }
                    result.add(p);
                }
            }
        }
        catch (Exception e)
        {
            System.out.printf("Erro ao recuperar informações do banco de dados: %s\n", e.toString());
            throw e;
        }
        return result;
    }
    
    public List<Pedido> read(GregorianCalendar inicio, GregorianCalendar fim) throws Exception
    {   
        String sql = String.format("SELECT id, dataHora FROM Pedido%s", completaConsulta(inicio, fim));
        String sql2 = String.format("SELECT i.id_pedido AS codigo_pedido, p.id, p.ativo, p.marca AS id_marca, m.marca, p.modelo, p.ano, p.novo, i.precoUnit, i.quantidade FROM Produto AS p LEFT JOIN Marca AS m ON p.marca = m.id INNER JOIN ItemPedido AS i ON i.id_produto = p.id WHERE i.id_pedido IN (SELECT id FROM Pedido%s) ORDER BY codigo_pedido", completaConsulta(inicio,fim));
        List<Pedido> result = new ArrayList<>();
        try (PreparedStatement ps1 = dataSource.getConexao().prepareStatement(sql); PreparedStatement ps2 = dataSource.getConexao().prepareStatement(sql2))
        {
            if (inicio != null && fim != null)
            {
                ps1.setTimestamp(1, new Timestamp(inicio.getTimeInMillis()));
                ps2.setTimestamp(1, new Timestamp(inicio.getTimeInMillis()));
                ps1.setTimestamp(2, new Timestamp(fim.getTimeInMillis()));
                ps2.setTimestamp(2, new Timestamp(fim.getTimeInMillis()));
            }
            else if (inicio != null || fim != null)
            {
                if (inicio == null)
                {
                    ps1.setTimestamp(1, new Timestamp(fim.getTimeInMillis()));
                    ps2.setTimestamp(1, new Timestamp(fim.getTimeInMillis()));
                }
                else
                {
                    ps1.setTimestamp(1, new Timestamp(inicio.getTimeInMillis()));
                    ps2.setTimestamp(1, new Timestamp(inicio.getTimeInMillis()));
                }
            }
            try (ResultSet rs1 = ps1.executeQuery(); ResultSet rs2 = ps2.executeQuery())
            {
                Pedido p;
                Produto prod;
                ItemPedido it = null;
                boolean mesmoPedido;
                while (rs1.next())
                {
                    p = new Pedido();
                    p.setId(rs1.getInt("id"));
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(rs1.getTimestamp("dataHora").getTime());
                    p.setDataHora(cal);
                    if (it != null)
                        p.adicionaItem(it);
                    mesmoPedido = true;
                    while (mesmoPedido && rs2.next())
                    {
                        prod = new Produto();
                        prod.setId(rs2.getInt("id"));
                        prod.setAtivo(rs2.getBoolean("ativo"));
                        prod.setMarca(rs2.getInt("id_marca"), rs2.getString("marca"), null);
                        prod.setModelo(rs2.getString("modelo"));
                        prod.setAno(rs2.getInt("ano"));
                        prod.setNovo(rs2.getBoolean("novo"));
                        it = new ItemPedido(prod, rs2.getDouble("precoUnit"), rs2.getInt("quantidade"));
                        if (rs2.getInt("codigo_pedido") != p.getId())
                            mesmoPedido = false;
                        else p.adicionaItem(it);
                    }
                    result.add(p);
                }
            }
        }
        catch (Exception e)
        {
            System.out.printf("Erro ao recuperar informações do banco de dados: %s\n", e.toString());
            throw e;
        }
        return result;
    }
    
    private static String completaConsulta(GregorianCalendar inicio, GregorianCalendar fim)
    {
        String args = "";
        if (inicio == null && fim == null)
            return args;
        else
        {
            args = " WHERE ";
            if (inicio == null)
                return args + "dataHora <= ?";
            else if (fim == null)
                return args + "dataHora >= ?";
            else return args + "dataHora >= ? AND dataHora <= ?";
        }
    }
}