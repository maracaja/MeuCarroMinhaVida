package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Marca;

/**
 * Esta classe tem o objetivo apenas de gerar a lista de marcas para o select de cadastro de produtos
 * @author Maracajá
 */

public class MarcaDAO 
{
    private final DataSource dataSource;
    
    public MarcaDAO(DataSource ds)
    {
        this.dataSource = ds;
    }
    
    public List<Marca> read() throws Exception
    {
        String sql = "SELECT id, marca FROM Marca ORDER BY Marca";
        List<Marca> result = new ArrayList<>();
        Marca m;
        try (PreparedStatement ps = dataSource.getConexao().prepareStatement(sql); ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                m = new Marca();
                m.setId(rs.getInt("id"));
                m.setNomeMarca(rs.getString("marca"));
                result.add(m);
            }
        }
        catch (Exception e)
        {
            System.out.printf("\nErro ao recuperar informações do banco de dados: %s\n", e.toString());
            throw e;
        }
        return result;
    }
}
