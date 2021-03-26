package datos;

import database.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datos.interfaces.CrudInterface;
import entidades.ClassAlumno;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import negocio.Variables;

public class AlumnoDAO implements CrudInterface<ClassAlumno> {

    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public AlumnoDAO() {
        CON = Conexion.getInstacia();
    }

    @Override
    public ObservableList<ClassAlumno> listar(String texto) {
        Variables.setRegistrosMostrados(0);
        //Creamos un ObservablearrayList de tipo List donde guardar los datos de nuestra tabla
        ObservableList<ClassAlumno> registros = FXCollections.observableArrayList(); //Especial para javaFX
        String filtra = texto.toUpperCase();
        // Creamos nuestra instrucción SQL, donde se pueda mostrar por cualquiera de los campos
        String SQL = "SELECT * FROM alumno WHERE nombre LIKE '" + filtra + "%'"
                + " OR apellido1 LIKE '%" + filtra + "%' OR apellido2 LIKE '%" + filtra + "%'"
                + " OR id LIKE  '" + filtra + "%' OR dni LIKE '" + filtra + "%'"
                + " OR calle LIKE '%" + filtra + "%' OR localidad LIKE '%" + filtra + "%'"
                + " OR telefono LIKE '%" + filtra + "%' OR fecha_nacimiento LIKE '%" + filtra + "%'";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new ClassAlumno(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                                              rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getDate(11), rs.getInt(12)));
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registros;
    }

    @Override
    public boolean insertar(ClassAlumno obj) {
        resp = false;
        String SQL = "INSERT INTO alumno (id,dni,nombre,apellido1,apellido2,calle,numero,cp,localidad,telefono,"
                + "fecha_nacimiento) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, obj.getId());
            ps.setString(2, obj.getDni());
            ps.setString(3, obj.getNombre());
            ps.setString(4, obj.getApellido1());
            ps.setString(5, obj.getApellido2());
            ps.setString(6, obj.getCalle());
            ps.setInt(7, obj.getNumero());
            ps.setInt(8, obj.getCp());
            ps.setString(9, obj.getLocalidad());
            ps.setString(10, obj.getTelefono());
            ps.setDate(11, (Date) obj.getFecha_nacimiento());
            //ps.setInt(12, null);

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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean actualizar(ClassAlumno obj) {
        resp = false;
        String SQL = "UPDATE alumno SET dni=?,nombre=?,apellido1=?,apellido2=?,calle=?,numero=?,cp=?,"
                + "localidad=?,telefono=?,fecha_nacimiento=?,id_empresa=? WHERE id=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getDni());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getApellido1());
            ps.setString(4, obj.getApellido2());
            ps.setString(5, obj.getCalle());
            ps.setInt(6, obj.getNumero());
            ps.setInt(7, obj.getCp());
            ps.setString(8, obj.getLocalidad());
            ps.setString(9, obj.getTelefono());
            ps.setDate(10, (Date) obj.getFecha_nacimiento());
            ps.setInt(11, obj.getId_empresa());
            ps.setInt(12, obj.getId());

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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    public ClassAlumno devuelveAlumno(ClassAlumno obj) {
        String SQL = "SELECT * FROM alumno WHERE id=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, obj.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                obj.setId(rs.getInt(1));
                obj.setDni(rs.getString(2));
                obj.setNombre(rs.getString(3));
                obj.setApellido1(rs.getString(4));
                obj.setApellido2(rs.getString(5));
                obj.setCalle(rs.getString(6));
                obj.setNumero(rs.getInt(7));
                obj.setCp(rs.getInt(8));
                obj.setLocalidad(rs.getString(9));
                obj.setTelefono(rs.getString(10));
                obj.setFecha_nacimiento(rs.getDate(11));
                obj.setId_empresa(rs.getInt(12));
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj;
    }

    @Override
    public boolean eliminar(ClassAlumno obj) {
        resp = false;
        String SQL = "DELETE FROM alumno WHERE id=?";
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public int total() {
        int totalResistros = 0;
        String SQL = "SELECT COUNT(id) FROM alumno";
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return totalResistros;
    }

    @Override
    public boolean existe(String texto) {
        resp = false;
        String SQL = "SELECT * FROM alumno WHERE dni=?";
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

}
