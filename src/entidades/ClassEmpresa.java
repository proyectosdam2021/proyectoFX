package entidades;

import datos.interfaces.ClonarClase;

//implementamos la clase de tipo ClonarClase, que es la interface creada para copiar clases
//la opción de clonar es muy interesante cuado deseemos copiar clases
public class ClassEmpresa implements ClonarClase {

    private int id;
    private String cif;
    private String nombre;
    private String calle;
    private int numero;
    private int cp;
    private String localidad;
    private String telefono;

    @Override
    //este método se encarga de copiar la clase. Patrón Prototype
    public ClonarClase clonar() {
        ClassEmpresa claseEmpresa = null;
        try {
            claseEmpresa = (ClassEmpresa) clone();
        } catch (CloneNotSupportedException e) {
        }
        return claseEmpresa;
    }

    public ClassEmpresa() {
    }

    public ClassEmpresa(int id, String cif, String nombre, String calle, int numero, int cp, String localidad, String telefono) {
        this.id = id;
        this.cif = cif;
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
        this.localidad = localidad;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "ClassEmpresa{" + "id=" + id + ", cif=" + cif + ", nombre=" + nombre + ", calle=" + calle + ", numero=" + numero + ", cp=" + cp + ", localidad=" + localidad + ", telefono=" + telefono + '}';
    }

}
