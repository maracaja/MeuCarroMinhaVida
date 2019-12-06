package lib;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

public abstract class RequestConf 
{
    public static void requestLoginPadrao(HttpServletRequest request)
    {
        request.setAttribute("Cadastrado", Boolean.FALSE);
        request.setAttribute("Aviso", "");
    }
    
    public static void erroSQL(SQLException e, HttpServletRequest request)
    {
        System.out.printf("\nErro (Banco de Dados): %s\n", e.getMessage());
        request.setAttribute("erroInfo", String.format("Erro %d: %s", e.getErrorCode(), e.getMessage()));
    }
    
    public static void exception(Exception e, HttpServletRequest request)
    {
        System.out.printf("\nErro: %s\n", e.getMessage());
        request.setAttribute("erroInfo", String.format("Erro: %s", e.toString()));
    }
}
