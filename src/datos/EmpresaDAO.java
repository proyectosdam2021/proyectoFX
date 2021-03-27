package datos;

import database.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import datos.interfaces.CrudInterface;
import entidades.ClassEmpresa;
import javafx.collections.ObservableList;

public class EmpresaDAO implements CrudInterface<ClassEmpresa> {

    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public EmpresaDAO() {
        CON = Conexion.getInstacia();
    }

    @Override
    public ObservableList<ClassEmpresa> listar(String texto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insertar(ClassEmpresa obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(ClassEmpresa obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(ClassEmpresa obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int total() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existe(String texto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

   
}
