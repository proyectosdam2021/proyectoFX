package entidades;

import datos.interfaces.ClonarClase;
import java.sql.Date; //usamos el tipo SQL para mejorar la conversión con la BD

//implementamos la clase de tipo ClonarClase, que es la interface creada para copiar clases
//la opción de clonar es muy interesante cuado deseemos copiar clases
public class ClassAlumno implements ClonarClase {

    private int id;
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String calle;
    private int numero;
    private int cp;
    private String localidad;
    private String telefono;
    private Date fecha_nacimiento;
    private int id_empresa;

    @Override
    //este método se encarga de copiar la clase. Patrón Prototype
    public ClonarClase clonar() {
        ClassAlumno claseAlumno = null;
        try {
            claseAlumno = (ClassAlumno) clone();
        } catch (CloneNotSupportedException e) {
        }
        return claseAlumno;
    }

    public ClassAlumno() {
    }

    public ClassAlumno(int id, String dni, String nombre, String apellido1, String apellido2, String calle, int numero, int cp, String localidad, String telefono, Date fecha_nacimiento, int id_empresa) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
        this.localidad = localidad;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.id_empresa = id_empresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    @Override
    public String toString() {
        return "ClassAlumno{" + "id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", calle=" + calle + ", numero=" + numero + ", cp=" + cp + ", localidad=" + localidad + ", telefono=" + telefono + ", fecha_nacimiento=" + fecha_nacimiento + ", id_empresa=" + id_empresa + '}';
    }

}
