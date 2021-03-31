package negocio;

import datos.AlumnoDAO;
import entidades.ClassAlumno;
import java.sql.SQLException;

public class AlumnoNegocio {

    private final AlumnoDAO DATOS;
    private ClassAlumno objeto;

    public AlumnoNegocio() {
        this.DATOS = new AlumnoDAO();
        this.objeto = new ClassAlumno();
        Variables.setRegistrosMostrados(0);
    }

    public String insertar(ClassAlumno objeto) throws SQLException {
        //comprobamos si existe el DNI a insertar
        if (DATOS.existe(objeto.getDni())) {
            return "El registro con ese DNI ya existe";
        } else {

            if (DATOS.insertar(objeto)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }
    }

    public String actualizar(ClassAlumno objeto, String dniAnterior) throws SQLException {
        //si el nuevo DNI es igual al anterior permitimos la actualización
        if (objeto.getDni().equals(dniAnterior)) {
            if (DATOS.actualizar(objeto)) {
                return "OK";
            } else {
                return "Error en la actualización";
            }
        } else {
            if (DATOS.existe(objeto.getDni())) {
                return "El registro con ese DNI ya existe";
            } else {
                if (DATOS.actualizar(objeto)) {
                    return "OK";
                } else {
                    return "Error en la actualización";
                }
            }
        }
    }

    public String eliminar(int id) throws SQLException {
        objeto.setId(id);
        if (DATOS.eliminar(objeto)) {
            return "OK";
        } else {
            return "Error al eliminar registro";
        }
    }

    public ClassAlumno devolverAlumno(int id) throws SQLException {
        objeto = new ClassAlumno();
        objeto.setId(id);
        return DATOS.devuelveAlumno(objeto);
    }

    public int total() throws SQLException {
        return DATOS.total();
    }

    public int totalMostrados() {
        return Variables.getRegistrosMostrados();
    }

    public boolean existe(String dni) throws SQLException {
        if (DATOS.existe(dni)) {
            return true;
        } else {
            return false;
        }
    }

}
