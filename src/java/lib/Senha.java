package lib;

import dao.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Senha
{
    private static final int MIN = 32;
    private static final int MAX = 126;
    private static final int N = MAX - MIN + 1;
    
    private static class Dim
    {
        int lin;
        int col;
    }
    
    private static Dim dimensoes(int n)
    {
        int maior = 0;
        Dim result = new Dim();
        for (int i = 1; i <= Math.sqrt(n); i++)
            if(n % i == 0) maior = i;
        result.lin = maior;
        result.col = n / maior;
        return result;
    }
    
    private static int numChar(char c)
    { return numChar((int) c); }
    
    private static int numChar(int n)
    {
        if(n < MIN) return n + N;
        else if(n > MAX) return n - N;
        else return n;
    }
    
    private static int chave(int k)
    { return (k >= 0 ? k : N + k) % N; }
    
    private static int chave(String s)
    {
        int soma = 0;
        for (int i = 0; i < s.length(); i++)
            soma += numChar(s.charAt(i));
        return chave(soma);
    }
    
    public static String cripto(String email, String senha, boolean paraBD)
    {
        if (senha.length() != 0)
        {
            Dim dm = dimensoes(senha.length());
            char[][] m = new char[dm.lin][dm.col];
            String text = "";
            int key = chave(email), x, y;
            if (paraBD)
            {
                for (int i = 0; i < senha.length(); i++)
                    text += (char) (numChar(senha.charAt(i) + chave(key + i)));
                senha = "";
                for (x = 0; x < dm.lin; x++)
                    for (y = 0; y < dm.col; y++)
                        m[x][y] = text.charAt(x * dm.col + y);
                for (y = dm.col - 1; y >= 0; y--)
                    for (x = dm.lin - 1; x >= 0; x--)
                        senha += m[x][y];
            }
            else
            {
                for (y = dm.col - 1; y >= 0; y--)
                    for (x = dm.lin - 1; x >= 0; x--)
                        m[x][y] = senha.charAt(senha.length() - 1 - y*dm.lin - x);
                for (x = 0; x < dm.lin; x++)
                    for (y = 0; y < dm.col; y++)
                        text += m[x][y];
                senha = "";
                for (int i = 0; i < text.length(); i++)
                    senha += (char) (numChar(text.charAt(i) + chave(-key - i)));
            }
        }
        return senha;
    }
    
    // Retorna o id do usuário, se corresponder
    public static int testaLogin(String email, String senha) throws SQLException
    {
        String sql;
        try
        {
            DataSource ds = new DataSource();
            if (email.equalsIgnoreCase("admin@admin"))
            {
                sql = "SELECT * FROM SenhaAdmin WHERE senha = ?";
                PreparedStatement ps = ds.getConexao().prepareStatement(sql);
                ps.setString(1, cripto(email, senha, true));
                ResultSet rs = ps.executeQuery();
                if(rs.next()) return -999;
            }
            else
            {
                sql = "SELECT id FROM Cliente WHERE email = ? AND senha = ?";
                PreparedStatement ps = ds.getConexao().prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, cripto(email, senha, true));
                ResultSet rs = ps.executeQuery();
                if(rs.next()) return rs.getInt(1);
            }
            ds.getConexao().close();
        }
        catch (SQLException e)
        {
            System.out.printf("\nErro ao recuperar informações de login: %s\n", e.toString());
            throw e;
        }
        return -1;
    }
}