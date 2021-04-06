package controlador;

import entidades.ClassAlumno;
import entidades.ClassEmpresa;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import negocio.AlumnoNegocio;
import negocio.EmpresaNegocio;
import negocio.MensajeFX;
import negocio.Variables;

public class FrmAlumnoController implements Initializable {

    private AlumnoNegocio CONTROL;  //instanciamos nuestra clase para realizar CRUD
    private EmpresaNegocio ControlEmpresa; // instanciamos la clase Empresa Negocio
    private int idRegistro;
    private int idEmpresa;
    private String dniAnterior;
    private ClassAlumno objetoAlumno;
    private ClassEmpresa objetoEmpresa;
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private double[] posicion;    //posición de la ventana en eje X-Y

    @FXML
    private Label lblTextoFrm;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtApellido2;
    @FXML
    private TextField txtCalle;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtCodigoPostal;
    @FXML
    private TextField txtLocalidad;
    @FXML
    private TextField txtTelefono;
    @FXML
    private DatePicker txtFechaNac;
    @FXML
    private TextField txtAlumnoCif;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtCifEmpresa;
    @FXML
    private TextField txtNombreEmpresa;
    @FXML
    private TextField txtCalleEmpresa;
    @FXML
    private TextField txtNumeroEmpresa;
    @FXML
    private TextField txtCPostalEmpresa;
    @FXML
    private TextField txtLocalidadEmpresa;
    @FXML
    private TextField txtTelefonoEmpresa;
    @FXML
    private Button btnCancelarEmpresa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CONTROL = new AlumnoNegocio();  //instanciamos la clase AlumnoNegocio
        ControlEmpresa = new EmpresaNegocio();
        lblTextoFrm.setText(Variables.getTextoFrm());  //Envíamos el texto de la variable como título del campo label de nuestra ventana
        campoEditable(true);
        campoEditableEmpresa(false);
    }

    @FXML
    private void cancelarAlumno(ActionEvent event) {  //cerramos el FrmAlumno
        Stage myStage = (Stage) this.txtDni.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void grabarAlumno(ActionEvent event) {
        if (comprobarDatos()) {
            if (!txtAlumnoCif.getText().isEmpty()) {  //si el textfield del alumno cif NO está vacio realizamos lo siguiente...
                if (!validaCif()) { //si no se valida es CIF entonces la empresa no existe
                    editaEmpresa();     //permitimos que se inserten los datos en los campos de empresa
                }
                if (comprobarDatosEmpresa()) {  //si los datos de empresa son correctos
                    if (MensajeFX.printTexto("¿Los datos de empresa son correctos?", "CONFIRM", obtenPosicionX_Y())) {
                        guardarDatosEmpresa();  //guardamos datos de la empresa del alumno
                        hacerPausa(500);  //realizamos una pausa de 500ms
                        guardarDatos(); //guardamos datos del alumno con el id_empresa asociado
                    }
                }
            } else //solo si el textfield del alumno cif está vacio..
            {
                guardarDatos();  //guardamos datos del alumno sin empresa
            }
        }
    }

    //En este método controlamos el texto introducido en los campos textfield
    @FXML
    private void valorTecla(KeyEvent event) {
        Object evt = event.getSource();  //capturamos el evento cuando se pulsa una tecla en los campos que tenemos el evento activado

        if (evt.equals(txtTelefono)) {                  //si el evento es el campo teléfono
            if (" ".equals(event.getCharacter())) {      //si el caracter que se introduce es un "espacio en blanco"
                txtTelefono.deletePreviousChar();         //eliminamos el caracter (solo si trata de un "espacio")
            } else {                                          //sino es un "espacio" en blanco..
                String caracter = event.getCharacter();       //guardamos en un string el valor del caracter
                this.compruebaInt(caracter, txtTelefono, 12); //llamamos al método de tipo entero, pasando el string(caracter), campo textfield, y tamaño máximo permitido
            }
        }

        if (evt.equals(txtCodigoPostal)) {
            if (" ".equals(event.getCharacter())) {
                txtCodigoPostal.deletePreviousChar();
            } else {
                String caracter = event.getCharacter();
                this.compruebaInt(caracter, txtCodigoPostal, 11);
            }
        }

        if (evt.equals(txtNumero)) {
            if (" ".equals(event.getCharacter())) {
                txtNumero.deletePreviousChar();
            } else {
                String caracter = event.getCharacter();
                this.compruebaInt(caracter, txtNumero, 11);
            }
        }

        if (evt.equals(txtDni)) {    //si el evento es el campo dni
            if (" ".equals(event.getCharacter())) {  //si el caracter que se introduce es un "espacio en blanco"
                txtDni.deletePreviousChar();           //eliminamos el caracter (solo si trata de un "espacio")
            } else {                                         //sino es un "espacio" en blanco..
                String caracter = event.getCharacter();         //guardamos en un string el valor del caracter
                this.compruebaString(caracter, txtDni, 9);      //llamamos al método de tipo string, pasando el string(caracter), campo textfield, y tamaño máximo permitido
            }
        }

        if (evt.equals(txtAlumnoCif) && txtAlumnoCif.isEditable()) {  //si el evento es campo alumnoCif y alumnoCif es editable
            if (" ".equals(event.getCharacter())) {                     //si el caracter que se introduce es un "espacio en blanco"
                txtAlumnoCif.deletePreviousChar();                      //eliminamos el caracter (solo si trata de un "espacio")
            } else {                                                    //sino es un "espacio" en blanco..
                limpiarEmpresa();                                       // limpiamos los campos textfield de empresa 
                String caracter = event.getCharacter();                 //guardamos en un string el valor del caracter
                this.compruebaString(caracter, txtAlumnoCif, 9);       //llamamos al método de tipo string, pasando el string(caracter), campo textfield, y tamaño máximo permitido 
            }
        }

        if (evt.equals(txtNombre)) {
            String caracter = event.getCharacter();
            this.compruebaString(caracter, txtNombre, 30);
        }

        if (evt.equals(txtApellido1)) {
            String caracter = event.getCharacter();
            this.compruebaString(caracter, txtApellido1, 50);
        }

        if (evt.equals(txtApellido2)) {
            String caracter = event.getCharacter();
            this.compruebaString(caracter, txtApellido2, 50);
        }

        if (evt.equals(txtCalle)) {
            String caracter = event.getCharacter();
            this.compruebaString(caracter, txtCalle, 100); //le pasamos caracter, campo y tamaño máximo
        }

        if (evt.equals(txtLocalidad)) {
            String caracter = event.getCharacter();
            this.compruebaString(caracter, txtLocalidad, 30); //le pasamos caracter, campo y tamaño máximo
        }
    }

    @FXML
    private void valorTeclaEmpresa(KeyEvent event) {
        Object evt = event.getSource();
        if (evt.equals(txtTelefonoEmpresa) && txtTelefonoEmpresa.isEditable()) {
            if (" ".equals(event.getCharacter())) {
                txtTelefonoEmpresa.deletePreviousChar();
            } else {
                String caracter = event.getCharacter();
                this.compruebaInt(caracter, txtTelefonoEmpresa, 12);
            }
        }

        if (evt.equals(txtCPostalEmpresa) && txtCPostalEmpresa.isEditable()) {
            if (" ".equals(event.getCharacter())) {
                txtCPostalEmpresa.deletePreviousChar();
            } else {
                String caracter = event.getCharacter();
                this.compruebaInt(caracter, txtCPostalEmpresa, 11);
            }
        }

        if (evt.equals(txtNumeroEmpresa) && txtNumeroEmpresa.isEditable()) {
            if (" ".equals(event.getCharacter())) {
                txtNumeroEmpresa.deletePreviousChar();
            } else {
                String caracter = event.getCharacter();
                this.compruebaInt(caracter, txtNumeroEmpresa, 11);
            }
        }

        if (evt.equals(txtCifEmpresa) && txtCifEmpresa.isEditable()) {
            if (" ".equals(event.getCharacter())) {
                txtCifEmpresa.deletePreviousChar();
            } else {
                String caracter = event.getCharacter();
                this.compruebaString(caracter, txtCifEmpresa, 9);
            }
        }

        if (evt.equals(txtNombreEmpresa) && txtNombreEmpresa.isEditable()) {
            String caracter = event.getCharacter();
            this.compruebaString(caracter, txtNombreEmpresa, 30);
        }

        if (evt.equals(txtCalleEmpresa) && txtCalleEmpresa.isEditable()) {
            String caracter = event.getCharacter();
            this.compruebaString(caracter, txtCalleEmpresa, 100); //le pasamos caracter, campo y tamaño máximo
        }

        if (evt.equals(txtLocalidadEmpresa) && txtLocalidadEmpresa.isEditable()) {
            String caracter = event.getCharacter();
            this.compruebaString(caracter, txtLocalidadEmpresa, 30); //le pasamos caracter, campo y tamaño máximo
        }
    }

    @FXML
    private void cancelarEmpresa(ActionEvent event) {
        limpiarEmpresa();
        campoEditableEmpresa(false);
        txtAlumnoCif.setEditable(true);
        txtAlumnoCif.setText("");
    }

    private boolean validaDni() {
        try {
            if (CONTROL.existe(txtDni.getText())) {  //si Existe el dni (usamos los métodos de la clase AlumnoNegocio = CONTROL)
                MensajeFX.printTexto("El campo 'DNI' ya existe", "WARNING", obtenPosicionX_Y());
                txtDni.requestFocus();
                return false;
            }
        } catch (SQLException ex) {
            System.err.println("Error en validaDni :" + ex);
        }
        return true;
    }

    private boolean validaCif() {
        try {
            if (ControlEmpresa.existe(txtAlumnoCif.getText())) {  //si Existe el cif de la empresa del alumno (usamos los métodos de la clase EmpresaNegocio = ControlEmpresa)
                objetoEmpresa = ControlEmpresa.cargarEmpresa(txtAlumnoCif.getText());  //metemos en objeto empresa los datos de la empresa que cargada
                campoEditableEmpresa(false);  //no permitimos que se puedan editarr los campos de empresa
                mostrarDatosEmpresa(objetoEmpresa);  //mostramos en los textfield los datos de la empresa cargada
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Error en validaCif :" + ex);
        }
        return false;
    }

    private void guardarDatos() {
        String respuesta;  //vamos a guardar la respuesta de tipo string que nos devolverá al llamar con el método CONTROL
        try {
            switch (Variables.getTextoFrm()) { //desde el método anterior (AlumnoVistaController) envíamos el tipo de operación a realizar
                case "CREAR ALUMNO":
                    objetoAlumno = convertirStringObjeto();  //guardamos en el objeto de tipo alumno los datos de los textfield mediante un método de convertir
                    respuesta = this.CONTROL.insertar(objetoAlumno); //procemos insertar del objeto alumno y guardamos la respuesta
                    if ("OK".equals(respuesta)) {   //si la respuesta es OK mostramos un mensaje de información
                        MensajeFX.printTexto("Alumno añadido correctamente", "INFO", obtenPosicionX_Y());  //mensaje compuesto del texto, tipo de mensaje, y posición en la ventana
                        this.limpiar();
                        this.cerrarVentana();
                    } else {  //si la respuesta NO es OK
                        MensajeFX.printTexto(respuesta, "ERROR", obtenPosicionX_Y());  //mostramos el mensaje de error de la respuesta
                    }
                    break;
                case "EDITAR ALUMNO":
                    objetoAlumno = convertirStringObjeto();
                    respuesta = this.CONTROL.actualizar(objetoAlumno, dniAnterior);
                    if ("OK".equals(respuesta)) {
                        MensajeFX.printTexto("Alumno editado correctamente", "INFO", obtenPosicionX_Y());
                        this.limpiar();
                        this.limpiarEmpresa();
                        this.cerrarVentana();
                    } else {  //si la respuesta NO es OK
                        MensajeFX.printTexto(respuesta, "ERROR", obtenPosicionX_Y());  //mostramos el mensaje de error de la respuesta
                    }
                    break;
            }

        } catch (SQLException ex) {
            System.err.println("Error en guardarDatos :" + ex);
        }
    }

    private void guardarDatosEmpresa() {
        String respuesta;
        try {
            objetoEmpresa = convertirStringObjetoEmpresa(); //guardamos el objeto de tipo empresa los datos de los campos textfield mediante un método convertir
            if (objetoEmpresa.getId() == 0) {                //si el id empresa es 0 = significa que es una nueva empresa
                respuesta = this.ControlEmpresa.insertar(objetoEmpresa);  //realizamos la inserción de la nueva empresa y guardamos la respuesta
                if ("OK".equals(respuesta)) {
                    MensajeFX.printTexto("Empresa añadida correctamente", "INFO", obtenPosicionX_Y());
                } else {
                    MensajeFX.printTexto(respuesta, "ERROR", obtenPosicionX_Y());
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en guardarDatosEmpresa :" + ex);
        }
    }

    //Convertirmos los campos textfield a tipo objetoAlumno ClassAlumno
    private ClassAlumno convertirStringObjeto() {
        this.objetoAlumno = new ClassAlumno();
        objetoAlumno.setId(idRegistro);
        objetoAlumno.setDni(txtDni.getText().strip().toUpperCase());
        objetoAlumno.setNombre(txtNombre.getText().strip().toUpperCase());
        objetoAlumno.setApellido1(txtApellido1.getText().strip().toUpperCase());
        objetoAlumno.setApellido2(txtApellido2.getText().strip().toUpperCase());
        objetoAlumno.setCalle(txtCalle.getText().strip().toUpperCase());
        objetoAlumno.setNumero(Integer.parseInt(txtNumero.getText()));
        objetoAlumno.setCp(Integer.parseInt(txtCodigoPostal.getText()));
        objetoAlumno.setLocalidad(txtLocalidad.getText().strip().toUpperCase());
        objetoAlumno.setTelefono(txtTelefono.getText().strip().toUpperCase());
        objetoAlumno.setFecha_nacimiento(java.sql.Date.valueOf(txtFechaNac.getValue()));  //convertimos un campo datepicker en Date SQL
        try {
            if (txtAlumnoCif.getText().isEmpty()) { //si textfield de alumnoCif está vacío 
                objetoAlumno.setId_empresa(0);          //guardamos en el objeto id_empresa un 0 (no se va a guardar empresa)
            } else if (!txtAlumnoCif.getText().isEmpty() && objetoEmpresa.getId() == 0) {  //si el campo alumnocif NO está vacío y el campo id empresa es 0
                objetoAlumno.setId_empresa(ControlEmpresa.ultimoRegistro());  //guardamos en idempresa del último registro introducido **cambiar por una búsqueda por CIF**, al crear la nueva empresa este proceso es una lectura casi inmediata después de guardar la empresa
            } else {  //sino .. (significa que ya existe la empresa y solo requerimos el id_empresa)
                objetoAlumno.setId_empresa(objetoEmpresa.getId());
            }
        } catch (SQLException ex) {
            System.err.println("Error en convertirStringObeto :" + ex);
        }

        return objetoAlumno;
    }

    //Convertirmos los campos textfield a tipo objetoAlumno ClassEmpresa
    private ClassEmpresa convertirStringObjetoEmpresa() {
        this.objetoEmpresa = new ClassEmpresa();
        ClassEmpresa objetocopiaEmpresa = new ClassEmpresa();  //creamos un nuevo objeto de tipo empresa
        objetoEmpresa.setCif(txtCifEmpresa.getText().strip());
        objetoEmpresa.setNombre(txtNombreEmpresa.getText().strip());
        objetoEmpresa.setCalle(txtCalleEmpresa.getText().strip());
        objetoEmpresa.setNumero(Integer.parseInt(txtNumeroEmpresa.getText()));
        objetoEmpresa.setCp(Integer.parseInt(txtCPostalEmpresa.getText()));
        objetoEmpresa.setLocalidad(txtLocalidadEmpresa.getText().strip());
        objetoEmpresa.setTelefono(txtTelefonoEmpresa.getText().strip());
        objetocopiaEmpresa = ControlEmpresa.cargarEmpresa(txtAlumnoCif.getText());  //guardamos en el objeto copia la empresa a través del método y el parámetro alumno cif empresa
        if (objetocopiaEmpresa.getId() == 0) {  //si la empresa no existe le ponemos al ID valor 0 
            objetoEmpresa.setId(0);    //establecemos el id empresa a 0 (para que lo grabe como nueva empresa)
        } else {                                 // sino..
            objetoEmpresa.setId(objetocopiaEmpresa.getId());  //establecemos en el id empresa el id del objeto copia que habíamos cargado
        }

        return objetoEmpresa;
    }

    //Este método viene de AlumnoVistaController y nos pasa los datos de los campos que asignamos a los txtfield
    public void pasarDatos(ClassAlumno objAlumno) {
        idRegistro = objAlumno.getId();
        txtDni.setText(objAlumno.getDni());
        dniAnterior = objAlumno.getDni();
        txtNombre.setText(objAlumno.getNombre());
        txtApellido1.setText(objAlumno.getApellido1());
        txtApellido2.setText(objAlumno.getApellido2());
        txtCalle.setText(objAlumno.getCalle());
        txtNumero.setText(String.valueOf(objAlumno.getNumero()));
        txtCodigoPostal.setText(String.valueOf(objAlumno.getCp()));
        txtLocalidad.setText(objAlumno.getLocalidad());
        txtTelefono.setText(objAlumno.getTelefono());
        txtFechaNac.setValue(objAlumno.getFecha_nacimiento().toLocalDate());
        idEmpresa = objAlumno.getId_empresa();
        cargarDatosEmpresa(idEmpresa);
    }

    public void mostrarDatosEmpresa(ClassEmpresa objEmpresa) {
        idEmpresa = objEmpresa.getId();
        txtCifEmpresa.setText(objEmpresa.getCif());
        txtNombreEmpresa.setText(objEmpresa.getNombre());
        txtCalleEmpresa.setText(objEmpresa.getCalle());
        txtNumeroEmpresa.setText(String.valueOf(objEmpresa.getNumero()));
        txtCPostalEmpresa.setText(String.valueOf(objEmpresa.getCp()));
        txtLocalidadEmpresa.setText(objEmpresa.getLocalidad());
        txtTelefonoEmpresa.setText(objEmpresa.getTelefono());
        txtAlumnoCif.setText(objetoEmpresa.getCif());
        //objetocopiaEmpresa = (ClassEmpresa) objetoEmpresa.clonar();
    }

    private void cargarDatosEmpresa(int idEmpresa) {
        objetoEmpresa = new ClassEmpresa();
        objetoEmpresa = ControlEmpresa.cargarEmpresaId(idEmpresa);
        mostrarDatosEmpresa(objetoEmpresa);
        if (txtAlumnoCif.getText() == null) {
            txtAlumnoCif.setText("");
        }
    }

    private void editaEmpresa() {
        campoEditableEmpresa(true);
        txtCifEmpresa.setText(txtAlumnoCif.getText());
        txtCifEmpresa.setEditable(false);
        txtAlumnoCif.setEditable(false);
        txtNombreEmpresa.requestFocus();
    }

    //Activamos o desactivamos los campos del formulario
    private void campoEditable(boolean valor) {
        txtDni.setEditable(valor);
        txtNombre.setEditable(valor);
        txtApellido1.setEditable(valor);
        txtApellido2.setEditable(valor);
        txtCalle.setEditable(valor);
        txtNumero.setEditable(valor);
        txtCodigoPostal.setEditable(valor);
        txtLocalidad.setEditable(valor);
        txtTelefono.setEditable(valor);
        txtFechaNac.setEditable(valor);
        txtAlumnoCif.setEditable(valor);
    }

    private void campoEditableEmpresa(boolean valor) {
        txtCifEmpresa.setEditable(valor);
        txtNombreEmpresa.setEditable(valor);
        txtCalleEmpresa.setEditable(valor);
        txtNumeroEmpresa.setEditable(valor);
        txtCPostalEmpresa.setEditable(valor);
        txtLocalidadEmpresa.setEditable(valor);
        txtTelefonoEmpresa.setEditable(valor);
    }

    //Método para campos de tipo String, convertiendo a mayúscula y comprobando tamaño
    private void compruebaString(String caracter, TextField txtCampo, int tamanio) {
        char palabra = caracter.charAt(0);
        if (txtCampo.getLength() > tamanio) {
            txtCampo.deletePreviousChar();
            txtCampo.end();
        } else if (palabra >= 'a') {
            caracter = caracter.toUpperCase();
            txtCampo.deletePreviousChar();
            txtCampo.setText(txtCampo.getText() + caracter);
            txtCampo.end();
        }
    }

    //Método para campos de tipo Int, permitiendo solo números y comprobando tamaño
    private void compruebaInt(String caracter, TextField txtCampo, int tamanio) {
        char palabra = caracter.charAt(0);
        if (txtCampo.getLength() > tamanio) {
            txtCampo.deletePreviousChar();
            txtCampo.end();
        } else if (palabra >= '0' && palabra <= '9') {
            txtCampo.deletePreviousChar();
            txtCampo.setText(txtCampo.getText() + caracter);
            txtCampo.end();
        } else if (palabra >= 'a' || palabra >= 'A') {
            txtCampo.deletePreviousChar();
            txtCampo.end();
        }
    }

    private static boolean esNumerico(String valor) {
        try {
            if (valor != null) {
                Integer.parseInt(valor);
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    //Método donde comprobamos los campos no estén vacíos
    private boolean comprobarDatos() {
        if ("CREAR ALUMNO".equals(lblTextoFrm.getText())) {
            if (!validaDni()) {
                txtDni.requestFocus(); //llevo el 'foco' al campo
                return false;
            }
        }
        if (txtDni.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'DNI' está vacío", "WARNING", obtenPosicionX_Y());
            txtDni.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo false y no continuo
        }
        if (txtNombre.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Nombre' está vacío", "WARNING", obtenPosicionX_Y());
            txtNombre.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo false y no continuo
        }
        if (txtApellido1.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Apellido1' está vacío", "WARNING", obtenPosicionX_Y());
            txtApellido1.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (txtCalle.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Calle' está vacío", "WARNING", obtenPosicionX_Y());
            txtCalle.requestFocus();
            return false; //devuelvo false y no continuo
        }

        if (txtNumero.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Número' está vacío", "WARNING", obtenPosicionX_Y());
            txtNumero.requestFocus();
            return false; //devuelvo false y no continuo
        }

        if (txtCodigoPostal.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Código Postal' está vacío", "WARNING", obtenPosicionX_Y());
            txtCodigoPostal.requestFocus();
            return false; //devuelvo false y no continuo
        }

        if (txtLocalidad.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Localidad' está vacío", "WARNING", obtenPosicionX_Y());
            txtLocalidad.requestFocus();
            return false; //devuelvo false y no continuo
        }

        if (txtTelefono.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Teléfono' está vacío", "WARNING", obtenPosicionX_Y());
            txtTelefono.requestFocus();
            return false; //devuelvo false y no continuo
        }

        if (txtFechaNac.getValue() == null) {
            MensajeFX.printTexto("El campo 'Fecha de nacimiento' es incorrecto", "WARNING", obtenPosicionX_Y());
            txtFechaNac.requestFocus();
            return false; //devuelvo false y no continuo
        }

        if (!esNumerico(txtNumero.getText())) {
            MensajeFX.printTexto("El campo 'Número' tiene carácteres no permitidos", "WARNING", obtenPosicionX_Y());
            txtNumero.requestFocus();
            return false; //devuelvo false y no continuo
        }

        if (!esNumerico(txtCodigoPostal.getText())) {
            MensajeFX.printTexto("El campo 'Código Postal' tiene carácteres no permitidos", "WARNING", obtenPosicionX_Y());
            txtCodigoPostal.requestFocus();
            return false; //devuelvo false y no continuo
        }

        return true;  //si llega aquí es que todo está correcto
    }

    private boolean comprobarDatosEmpresa() {
        if (txtCifEmpresa.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'CIF' está vacío", "WARNING", obtenPosicionX_Y());
            txtCifEmpresa.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo false y no continuo
        }
        if (txtNombreEmpresa.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Nombre Empresa' está vacío", "WARNING", obtenPosicionX_Y());
            txtNombreEmpresa.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo false y no continuo
        }
        if (txtCalleEmpresa.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Calle Empresa' está vacío", "WARNING", obtenPosicionX_Y());
            txtCalleEmpresa.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (txtNumeroEmpresa.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Número Empresa' está vacío", "WARNING", obtenPosicionX_Y());
            txtNumeroEmpresa.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (txtCPostalEmpresa.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Código Postal Empresa' está vacío", "WARNING", obtenPosicionX_Y());
            txtCPostalEmpresa.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (txtLocalidadEmpresa.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Localidad Empresa' está vacío", "WARNING", obtenPosicionX_Y());
            txtLocalidadEmpresa.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (txtTelefonoEmpresa.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Teléfono Empresa' está vacío", "WARNING", obtenPosicionX_Y());
            txtTelefonoEmpresa.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (!esNumerico(txtNumeroEmpresa.getText())) {
            MensajeFX.printTexto("El campo 'Número Empresa' tiene carácteres no permitidos", "WARNING", obtenPosicionX_Y());
            txtNumeroEmpresa.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (!esNumerico(txtCPostalEmpresa.getText())) {
            MensajeFX.printTexto("El campo 'Código Postal Empresa' tiene carácteres no permitidos", "WARNING", obtenPosicionX_Y());
            txtCPostalEmpresa.requestFocus();
            return false; //devuelvo false y no continuo
        }

        return true;  //si llega aquí es que todo está correcto
    }

    //Método para limpiar todos los campos que forman parte del formulario
    private void limpiar() {
        idRegistro = 0;
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        txtCalle.setText("");
        txtLocalidad.setText("");
        txtCodigoPostal.setText("");
        txtNumero.setText("");
        txtAlumnoCif.setText("");
    }

    private void limpiarEmpresa() {
        idEmpresa = 0;
        txtCifEmpresa.setText("");
        txtNombreEmpresa.setText("");
        txtCalleEmpresa.setText("");
        txtLocalidadEmpresa.setText("");
        txtTelefonoEmpresa.setText("");
        txtNumeroEmpresa.setText("");
        txtCPostalEmpresa.setText("");
    }

    //Este método cierra la ventana que forme parte del componente capturado
    private void cerrarVentana() {
        Stage myStage = (Stage) this.txtDni.getScene().getWindow();
        myStage.close();
    }

    //Establecemos la posición de la ventana actual 
    private void ventanaPosicion() {
        posicion = obtenPosicionX_Y();
        stage.setX(posicion[0]);
        stage.setY(posicion[1]);
    }

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana correctamente
    private double[] obtenPosicionX_Y() {
        double[] posicionxy = new double[2];
        //creamos una nueva ventana temporal capturando de cualquier btn/lbl la escena y ventana
        //se entiende que los btn o lbl forman parte de la ventana que deseamos obtener datos
        Stage myStage = (Stage) this.txtDni.getScene().getWindow();
        int frmX = 420 / 2; //tamaño ancho componente FrmAlumno
        int frmY = 50; //posición Y
        int x = (int) (myStage.getWidth() / 2);  //guardamos el ancho eje X
        int y = (int) myStage.getY();  //guardamos la posición Y
        posicionxy[0] = myStage.getX() + (x - frmX);
        posicionxy[1] = y - frmY;
        return posicionxy;
    }

    private void hacerPausa(int valor) {
        try {
            Thread.sleep(valor);
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
