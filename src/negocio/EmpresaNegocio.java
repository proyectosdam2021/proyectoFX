package negocio;

import datos.EmpresaDAO;
import entidades.ClassEmpresa;
import java.sql.SQLException;

public class EmpresaNegocio {

    private final EmpresaDAO DATOS;
    private final ClassEmpresa objeto;

    public EmpresaNegocio() {
        this.DATOS = new EmpresaDAO();
        this.objeto = new ClassEmpresa();
        Variables.setRegistrosMostrados(0);
    }

    public String insertar(ClassEmpresa objeto) throws SQLException {
        //comprobamos si existe el CIF a insertar
        if (DATOS.existe(objeto.getCif())) {
            return "El registro con ese CIF ya existe";
        } else {
            if (DATOS.insertar(objeto)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }
    }

    public String actualizar(ClassEmpresa objeto, String cifAnterior) throws SQLException {
        //si el nuevo CIF es igual al anterior permitimos la actualización
        if (objeto.getCif().equals(cifAnterior)) {
            if (DATOS.actualizar(objeto)) {
                return "OK";
            } else {
                return "Error en la actualización";
            }
        } else {
            if (DATOS.existe(objeto.getCif())) {
                return "El registro con ese CIF ya existe";
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

    public boolean existe(String cif) throws SQLException {
        return DATOS.existe(cif);
    }

    public int total() throws SQLException {
        return DATOS.total();
    }

    public int totalMostrados() {
        return Variables.getRegistrosMostrados();
    }
}
