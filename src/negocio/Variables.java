package negocio;

public class Variables {

    //en estas variables globales vamos a guardar los datos necesarios para ciertas acciones a realizar
    private static int registrosMostrados = 0; //almacenamos en número de registros mostrados en las busquedas de las tablas
    private static String textoFrm;   //almacenamos el texto del título que aparece cuando se abre Frm
    private static int conectadoBD = 0; //guardamos si se ha conectado a la BD
    private static int alumnoCreaEmpresa = 0;  //guardamos si valor 0 para no crear, y 1 para si crear

    //Creamos los Getter y Setter de los datos a compartir en la aplicación
    public static int getRegistrosMostrados() {
        return registrosMostrados;
    }

    public static void setRegistrosMostrados(int registrosMostrados) {
        Variables.registrosMostrados = registrosMostrados;
    }

    public static String getTextoFrm() {
        return textoFrm;
    }

    public static void setTextoFrm(String textoFrm) {
        Variables.textoFrm = textoFrm;
    }

    public static int getConectadoBD() {
        return conectadoBD;
    }

    public static void setConectadoBD(int conectadoBD) {
        Variables.conectadoBD = conectadoBD;
    }

    public static int getAlumnoCreaEmpresa() {
        return alumnoCreaEmpresa;
    }

    public static void setAlumnoCreaEmpresa(int alumnoCreaEmpresa) {
        Variables.alumnoCreaEmpresa = alumnoCreaEmpresa;
    }

}
