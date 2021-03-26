package controlador;

import datos.AlumnoDAO;
import entidades.ClassAlumno;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import negocio.AlumnoNegocio;
import negocio.MensajeFX;
import negocio.Variables;

public class FrmAlumnoController implements Initializable {

    private AlumnoNegocio CONTROL;  //instanciamos nuestra clase para realizar CRUD
    private int idRegistro;
    private String dniAnterior;
    private ClassAlumno objeto;
    private ObservableList<ClassAlumno> items; //instanciamos un objeto tipo arrayList especial para JavaFX
    private AlumnoDAO datos;   //instanciamos la clase AlumnoDAO la cual gestiona las acciones hacia nuestra BD
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
    private ImageView btnCancelar;
    @FXML
    private ImageView bntAceptar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CONTROL = new AlumnoNegocio();  //instanciamos la clase AlumnoNegocio
        lblTextoFrm.setText(Variables.getTextoFrm());  //Envíamos el texto de la variable como título del campo label de nuestra ventana
        if ("ELIMINAR ALUMNO".equals(Variables.getTextoFrm())) { //dependiendo de la acción a realizar (NUEVO/EDITAR/ELIMINAR) activamos/desactivamos botones
            campoEditable(false);
        } else {
            campoEditable(true);
        }
    }

    @FXML
    private void cancelarAlumno(ActionEvent event) {
        Stage myStage = (Stage) this.txtDni.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void grabarAlumno(ActionEvent event) {
        if (comprobarDatos()) {
            guardarDatos();
        }
    }

    //En este método controlamos no se introduzcan espacios
    @FXML
    private void valorTecla(KeyEvent event) {
        Object evt = event.getSource();
        if (evt.equals(txtDni)) {
            if (" ".equals(event.getCharacter())) {
                txtDni.deletePreviousChar();
            }
        }
        if (evt.equals(txtTelefono)) {
            if (" ".equals(event.getCharacter())) {
                txtTelefono.deletePreviousChar();
            }
        }
        if (evt.equals(txtCodigoPostal)) {
            if (" ".equals(event.getCharacter())) {
                txtCodigoPostal.deletePreviousChar();
            }
        }

    }

    private boolean comprobarDatos() {
        //Comprobamos los campos no estén vacíos
        if (txtDni.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'DNI' está vacío", "WARNING", posicionX_Y());
            txtDni.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtNombre.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Nombre' está vacío", "WARNING", posicionX_Y());
            txtNombre.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtApellido1.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Apellido1' está vacío", "WARNING", posicionX_Y());
            txtApellido1.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        if (txtCalle.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Calle' está vacío", "WARNING", posicionX_Y());
            txtCalle.requestFocus();
            return false; //devuelvo el código y no continuo
        }

        if (txtNumero.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Número' está vacío", "WARNING", posicionX_Y());
            txtNumero.requestFocus();
            return false; //devuelvo el código y no continuo
        }

        if (txtCodigoPostal.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Código Postal' está vacío", "WARNING", posicionX_Y());
            txtCodigoPostal.requestFocus();
            return false; //devuelvo el código y no continuo
        }

        if (txtLocalidad.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Localidad' está vacío", "WARNING", posicionX_Y());
            txtLocalidad.requestFocus();
            return false; //devuelvo el código y no continuo
        }

        if (txtTelefono.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Teléfono' está vacío", "WARNING", posicionX_Y());
            txtTelefono.requestFocus();
            return false; //devuelvo el código y no continuo
        }

        if (txtFechaNac.getValue() == null) {
            MensajeFX.printTexto("El campo 'Fecha de nacimiento' está vacío", "WARNING", posicionX_Y());
            txtFechaNac.requestFocus();
            return false; //devuelvo el código y no continuo
        }

        //Comprobamos los campos no excendan del tañano permitido
        if (txtApellido2.getText().length() > 21) {
            MensajeFX.printTexto("El campo 'Apellido2' excede del tamaño de 21 carácteres", "WARNING", posicionX_Y());
            txtApellido2.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        if (txtApellido1.getText().length() > 21) {
            MensajeFX.printTexto("El campo 'Apellido1' excede del tamaño de 21 carácteres", "WARNING", posicionX_Y());
            txtApellido1.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        if (txtNombre.getText().length() > 26) {
            MensajeFX.printTexto("El campo 'Nombre' excede del tamaño de 26 carácteres", "WARNING", posicionX_Y());
            txtNombre.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtDni.getText().length() > 14) {
            MensajeFX.printTexto("El campo 'Dni' excede del tamaño de 14 carácteres", "WARNING", posicionX_Y());
            txtDni.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }

        return true;  //si llega aquí es que todo está correcto
    }

    private void guardarDatos() {
        String respuesta;
        try {
            switch (Variables.getTextoFrm()) {
                case "CREAR ALUMNO":
                    respuesta = this.CONTROL.insertar(convertirStringObjeto());
                    if ("OK".equals(respuesta)) {
                        MensajeFX.printTexto("Alumno añadido correctamente", "INFO", posicionX_Y());
                        this.limpiar();
                        this.cerrarVentana();
                    } else {
                        MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "EDITAR ALUMNO":
                    respuesta = this.CONTROL.actualizar(convertirStringObjeto(), dniAnterior);
                    if ("OK".equals(respuesta)) {
                        MensajeFX.printTexto("Alumno editado correctamente", "INFO", posicionX_Y());
                        this.limpiar();
                        this.cerrarVentana();
                    } else {
                        MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "ELIMINAR ALUMNO":
                    if (MensajeFX.printTexto("¿Desea eliminar este registro?", "CONFIRM", posicionX_Y())) {
                        respuesta = this.CONTROL.eliminar(idRegistro);
                        if ("OK".equals(respuesta)) {
                            MensajeFX.printTexto("Alumno eliminado correctamente", "INFO", posicionX_Y());
                            this.limpiar();
                            this.cerrarVentana();
                        } else {
                            MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                        }
                    }
                    break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(FrmAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana de mensajes en la pantalla correctamente
    private double[] posicionX_Y() {
        double[] posicionn = new double[2];
        Stage myStage = (Stage) this.txtDni.getScene().getWindow();
        int frmX = 420 / 2; //tamaño ancho componente FrmAlumno
        int frmY = 500 / 2; //tamaño alto componente FrmAlumno
        int x = (int) (myStage.getWidth() / 2);
        int y = (int) (myStage.getHeight() / 2);
        posicionn[0] = myStage.getX() + (x - frmX);
        posicionn[1] = myStage.getY() + (y - frmY);

        return posicionn;
    }

    private void limpiar() {
        idRegistro = 0;
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
    }

    private void cerrarVentana() {
        Stage myStage = (Stage) this.txtDni.getScene().getWindow();
        myStage.close();
    }

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
        Stage myStage = (Stage) this.lblTextoFrm.getScene().getWindow();
        int frmX = 420 / 2; //tamaño ancho componente FrmAlumno
        int frmY = 500 / 2; //tamaño alto componente FrmAlumno
        int x = (int) (myStage.getWidth() / 2);
        int y = (int) (myStage.getHeight() / 2);
        posicionxy[0] = myStage.getX() + (x - frmX);
        posicionxy[1] = myStage.getY() + (y - frmY);
        return posicionxy;
    }

    private ClassAlumno convertirStringObjeto() {
        this.objeto = new ClassAlumno();
        objeto.setId(idRegistro);
        objeto.setDni(txtDni.getText().strip().toUpperCase());
        objeto.setNombre(txtNombre.getText().strip().toUpperCase());
        objeto.setApellido1(txtApellido1.getText().strip().toUpperCase());
        objeto.setApellido2(txtApellido2.getText().strip().toUpperCase());
        objeto.setCalle(txtCalle.getText().strip().toUpperCase());
        objeto.setNumero(Integer.parseInt(txtNumero.getText()));
        objeto.setCp(Integer.parseInt(txtCodigoPostal.getText()));
        objeto.setLocalidad(txtLocalidad.getText().strip().toUpperCase());
        objeto.setTelefono(txtTelefono.getText().strip().toUpperCase());
        objeto.setFecha_nacimiento(java.sql.Date.valueOf(txtFechaNac.getValue()));  //convertimos un campo datepicker en Date SQL
        return objeto;
    }

    //Este método viene de AlumnoVistaController y nos pasa los datos de los campos a editar/eliminar
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
    }

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
    }

}
