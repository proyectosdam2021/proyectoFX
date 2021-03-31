package datos;

import database.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import datos.interfaces.CrudInterface;
import entidades.ClassEmpresa;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import negocio.Variables;

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
        Variables.setRegistrosMostrados(0);
        //Creamos un ObservablearrayList de tipo List donde guardar los datos de nuestra tabla
        ObservableList<ClassEmpresa> registros = FXCollections.observableArrayList(); //Especial para javaFX
        String filtra = texto.toUpperCase();
        // Creamos nuestra instrucción SQL, donde se pueda mostrar por cualquiera de los campos
        String SQL = "SELECT * FROM empresa WHERE nombre LIKE '" + filtra + "%'"
                + " OR id LIKE  '" + filtra + "%' OR cif LIKE '" + filtra + "%'"
                + " OR calle LIKE '%" + filtra + "%' OR localidad LIKE '%" + filtra + "%'"
                + " OR telefono LIKE '%" + filtra + "%'";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new ClassEmpresa(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                               rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
                Variables.setRegistrosMostrados(Variables.getRegistrosMostrados() + 1); //guardamos el total de registros mostrados
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                //Para el correcto funcionamiento del Pool de conexiones
                //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
                //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
                //y deberíamos verificar que conexion no es null antes de intentar cerrar
                if (CON != null) {
                    CON.desconectar();
                }
                if (ps != null) {
                    ps.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registros;
    }

    @Override
    public boolean insertar(ClassEmpresa obj) {
        resp = false;
        String SQL = "INSERT INTO empresa (id,cif,nombre,calle,numero,cp,localidad,telefono) VALUES (?,?,?,?,?,?,?,?)";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, 0);
            ps.setString(2, obj.getCif());
            ps.setString(3, obj.getNombre());
            ps.setString(4, obj.getCalle());
            ps.setInt(5, obj.getNumero());
            ps.setInt(6, obj.getCp());
            ps.setString(7, obj.getLocalidad());
            ps.setString(8, obj.getTelefono());

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                //Para el correcto funcionamiento del Pool de conexiones
                //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
                //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
                //y deberíamos verificar que conexion no es null antes de intentar cerrar
                if (CON != null) {
                    CON.desconectar();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean actualizar(ClassEmpresa obj) {
        resp = false;
        String SQL = "UPDATE empresa SET cif=?,nombre=?,calle=?,numero=?,cp=?,"
                + "localidad=?,telefono=? WHERE id=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getCif());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getCalle());
            ps.setInt(4, obj.getNumero());
            ps.setInt(5, obj.getCp());
            ps.setString(6, obj.getLocalidad());
            ps.setString(7, obj.getTelefono());
            ps.setInt(8, obj.getId());

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                //Para el correcto funcionamiento del Pool de conexiones
                //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
                //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
                //y deberíamos verificar que conexion no es null antes de intentar cerrar
                if (CON != null) {
                    CON.desconectar();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean eliminar(ClassEmpresa obj) {
        resp = false;
        String SQL = "DELETE FROM empresa WHERE id=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, obj.getId());
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                //Para el correcto funcionamiento del Pool de conexiones
                //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
                //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
                //y deberíamos verificar que conexion no es null antes de intentar cerrar
                if (CON != null) {
                    CON.desconectar();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public int total() {
        int totalResistros = 0;
        String SQL = "SELECT COUNT(id) FROM empresa";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                totalResistros = rs.getInt("COUNT(id)");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                //Para el correcto funcionamiento del Pool de conexiones
                //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
                //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
                //y deberíamos verificar que conexion no es null antes de intentar cerrar
                if (CON != null) {
                    CON.desconectar();
                }
                if (ps != null) {
                    ps.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return totalResistros;
    }

    @Override
    public boolean existe(String texto) {
        resp = false;
        String SQL = "SELECT * FROM empresa WHERE cif=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, texto);
            rs = ps.executeQuery();
            rs.last();
            if (rs.getRow() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                //Para el correcto funcionamiento del Pool de conexiones
                //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
                //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
                //y deberíamos verificar que conexion no es null antes de intentar cerrar
                if (CON != null) {
                    CON.desconectar();
                }
                if (ps != null) {
                    ps.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    //Este método nos valdrá para conocer el nº máximo del registro de la tabla
    public int ultimoRegistro() {
        int ultimoRegistro = -1;
        String SQL = "SELECT MAX(id) FROM empresa";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                ultimoRegistro = rs.getInt("MAX(id)");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                //Para el correcto funcionamiento del Pool de conexiones
                //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
                //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
                //y deberíamos verificar que conexion no es null antes de intentar cerrar
                if (CON != null) {
                    CON.desconectar();
                }
                if (ps != null) {
                    ps.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ultimoRegistro;
    }

}
