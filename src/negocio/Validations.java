package negocio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AlmeidaGonzálezJoséCarlos
 */
public class Validations {

    public static boolean validarNIF(String nif) {

        boolean correcto;
        Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher matcher = pattern.matcher(nif);
        if (matcher.matches()) {
            String letra = matcher.group(2);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int index = Integer.parseInt(matcher.group(1));
            index = index % 23;
            String reference = letras.substring(index, index + 1);
            if (reference.equalsIgnoreCase(letra)) {
                correcto = true;
            } else {
                correcto = false;
            }
        } else {
            correcto = false;
        }
        return correcto;
        
    }

    public static boolean compruebapatron(String patron, String datosacomprobar) {
        Pattern p = Pattern.compile(patron);
        Matcher m = p.matcher(datosacomprobar);
        System.out.println("Patrón " + patron + " Datos a comrpobar: " + datosacomprobar + " Resultado: " + m.matches());
        return m.matches();
    }
}
