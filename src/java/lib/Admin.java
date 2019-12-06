package lib;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import model.Cliente;
import model.Pedido;

public abstract class Admin 
{
    public static boolean verificaAdmin(HttpServletRequest request)
    {
        Cliente admin = (Cliente) request.getSession().getAttribute("Admin");
        return admin != null && admin.getId() == -999;
    }
    
    public static GregorianCalendar capturaData(String d, boolean inicio)
    {
        if(d == null || d.isEmpty()) return null;
        else
        {
            Date date = Date.valueOf(d);
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, (inicio ? 0 : 23));
            cal.add(Calendar.MINUTE, (inicio ? 0 : 59));
            cal.add(Calendar.SECOND, (inicio ? 0 : 59));
            cal.add(Calendar.MILLISECOND, (inicio ? 0 : 999));
            return cal;
        }
    }
    
    public static String tituloRelatorio(GregorianCalendar inicio, GregorianCalendar fim)
    {
        if (inicio == null && fim == null)
            return "Listagem geral de pedidos";
        else
        {
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            if (inicio == null)
                return String.format("Pedidos realizados até %s", fmt.format(new Date(fim.getTimeInMillis())));
            else if (fim == null)
                return String.format("Pedidos realizados a partir de %s", fmt.format(new Date(inicio.getTimeInMillis())));
            else return String.format("Pedidos realizados entre %s e %s", fmt.format(new Date(inicio.getTimeInMillis())), fmt.format(new Date(fim.getTimeInMillis())));
        }
    }
    
    public static Integer vendidos(List<Pedido> lista)
    {
        int soma = 0;
        for (Pedido p : lista)
            soma += p.totalProdutos();
        return soma;
    }
    
    public static String faturamento(List<Pedido> lista)
    {
        double soma = 0.0;
        for (Pedido p : lista)
            soma += p.precoTotal();
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %1$,.2f", soma);
    }
    
    public static void uploadFoto(HttpServletRequest request, String nomeParte, String urlFoto) throws IOException, ServletException
    {
        ImageInputStream original = ImageIO.createImageInputStream(request.getPart(nomeParte).getInputStream());
        ImageOutputStream imagem = ImageIO.createImageOutputStream(new FileOutputStream(urlFoto));
        try
        {
            byte[] kb = new byte[(int) request.getPart(nomeParte).getSize()];
            original.read(kb);
            imagem.write(kb);
        }
        finally
        {
            original.close();
            imagem.close();
        }
    }
    
    public static String geraNome()
    {
        List<Character> c = caracteresAlfaNumericos();
        Random r = new Random();
        String nome = "";
        for (int i = 1; i <= 24; i++)
            nome += c.get(r.nextInt(c.size()));
        return nome;
    }
    
    private static List<Character> caracteresAlfaNumericos()
    {
        List<Character> c = new ArrayList<>();
        for (int i = 0; i <= 255 && c.size() < 52; i++)
            if (Character.isAlphabetic((char) i))
                c.add((char) i);
        return c;
    } 
    
    public static String capturaExtensao(String nomeArq)
    {
        String extensao = "";
        int i = nomeArq.length() - 1;
        while (i >= 0 && nomeArq.charAt(i) != '.')
            extensao = nomeArq.charAt(i--) + extensao;
        return extensao;
    }
}
