package controlador;

import datos.EmpresaDAO;
import entidades.ClassEmpresa;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import negocio.EmpresaNegocio;
import negocio.MensajeFX;
import negocio.Variables;

public class FrmEmpresaController implements Initializable {

    private EmpresaNegocio CONTROL;  //instanciamos nuestra clase para realizar CRUD
    private int idRegistro;
    private String cifAnterior;
    private ClassEmpresa objetoEmpresa;
    private ObservableList<ClassEmpresa> items; //instanciamos un objeto tipo arrayList especial para JavaFX
    private EmpresaDAO datos;   //instanciamos la clase DAO la cual gestiona las acciones hacia nuestra BD
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private double[] posicion;    //posición de la ventana en eje X-Y

    @FXML
    private Label lblTextoFrm;
    @FXML
    private TextField txtCif;
    @FXML
    private TextField txtNombre;
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
        CONTROL = new EmpresaNegocio();  //instanciamos la clase Negocio
        lblTextoFrm.setText(Variables.getTextoFrm());  //Envíamos el texto de la variable como título del campo label de nuestra ventana

        if ("ELIMINAR EMPRESA".equals(Variables.getTextoFrm())) { //dependiendo de la acción a realizar (NUEVO/EDITAR/ELIMINAR) activamos/desactivamos botones
            campoEditable(false);
        } else {
            campoEditable(true);
        }
    }

    @FXML
    private void valorTecla(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtTelefono)) {
            if (" ".equals(event.getCharacter())) {
                txtTelefono.deletePreviousChar();
            } else {
                String caracter = event.getCharacter();
                this.compruebaInt(caracter, txtTelefono, 12);
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

        if (evt.equals(txtCif)) {
            if (" ".equals(event.getCharacter())) {
                txtCif.deletePreviousChar();
            } else {
                String caracter = event.getCharacter();
                this.compruebaString(caracter, txtCif, 9);
            }
        }

        if (evt.equals(txtNombre)) {
            String caracter = event.getCharacter();
            this.compruebaString(caracter, txtNombre, 30);
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
    private void cancelarFormulario(ActionEvent event) {
        Stage myStage = (Stage) this.txtCif.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void grabarFormulario(ActionEvent event) {
        if (comprobarDatos()) {
            guardarDatos();
        }
    }

    //Activamos o desactivamos los campos del formulario
    private void campoEditable(boolean valor) {
        txtCif.setEditable(valor);
        txtNombre.setEditable(valor);
        txtCalle.setEditable(valor);
        txtNumero.setEditable(valor);
        txtCodigoPostal.setEditable(valor);
        txtLocalidad.setEditable(valor);
        txtTelefono.setEditable(valor);
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
        if (txtCif.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'CIF' está vacío", "WARNING", posicionX_Y());
            txtCif.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo false y no continuo
        }
        if (txtNombre.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Nombre' está vacío", "WARNING", posicionX_Y());
            txtNombre.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo false y no continuo
        }
        if (txtCalle.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Calle' está vacío", "WARNING", posicionX_Y());
            txtCalle.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (txtNumero.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Número' está vacío", "WARNING", posicionX_Y());
            txtNumero.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (txtCodigoPostal.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Código Postal' está vacío", "WARNING", posicionX_Y());
            txtCodigoPostal.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (txtLocalidad.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Localidad' está vacío", "WARNING", posicionX_Y());
            txtLocalidad.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (txtTelefono.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Teléfono' está vacío", "WARNING", posicionX_Y());
            txtTelefono.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (!esNumerico(txtNumero.getText())) {
            MensajeFX.printTexto("El campo 'Número' tiene carácteres no permitidos", "WARNING", posicionX_Y());
            txtNumero.requestFocus();
            return false; //devuelvo false y no continuo
        }
        if (!esNumerico(txtCodigoPostal.getText())) {
            MensajeFX.printTexto("El campo 'Código Postal' tiene carácteres no permitidos", "WARNING", posicionX_Y());
            txtCodigoPostal.requestFocus();
            return false; //devuelvo false y no continuo
        }

        return true;  //si llega aquí es que todo está correcto
    }

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana de mensajes en la pantalla correctamente
    private double[] posicionX_Y() {
        double[] posicionn = new double[2];
        Stage myStage = (Stage) this.txtCif.getScene().getWindow();
        int frmX = 420 / 2; //tamaño ancho componente FrmAlumno
        int frmY = 500 / 2; //tamaño alto componente FrmAlumno
        int x = (int) (myStage.getWidth() / 2);
        int y = (int) (myStage.getHeight() / 2);
        posicionn[0] = myStage.getX() + (x - frmX);
        posicionn[1] = myStage.getY() + (y - frmY);

        return posicionn;
    }

    private void guardarDatos() {
        String respuesta;
        try {
            switch (Variables.getTextoFrm()) {
                case "CREAR EMPRESA":
                    respuesta = this.CONTROL.insertar(convertirStringObjeto());
                    if ("OK".equals(respuesta)) {
                        MensajeFX.printTexto("Empresa añadida correctamente", "INFO", posicionX_Y());
                        this.limpiar();
                        this.cerrarVentana();
                    } else {
                        MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "EDITAR EMPRESA":
                    respuesta = this.CONTROL.actualizar(convertirStringObjeto(), cifAnterior);
                    if ("OK".equals(respuesta)) {
                        MensajeFX.printTexto("Empresa editada correctamente", "INFO", posicionX_Y());
                        this.limpiar();
                        this.cerrarVentana();
                    } else {
                        MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "ELIMINAR EMPRESA":
                    if (MensajeFX.printTexto("¿Desea eliminar este registro?", "CONFIRM", posicionX_Y())) {
                        respuesta = this.CONTROL.eliminar(idRegistro);
                        if ("OK".equals(respuesta)) {
                            MensajeFX.printTexto("Empresa eliminada correctamente", "INFO", posicionX_Y());
                            this.limpiar();
                            this.cerrarVentana();
                        } else {
                            // Lo quito ya que el dao controla este mensaje también hay que revalorar MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                            this.cerrarVentana();
                        }
                    }
                    break;
            }

        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(FrmEmpresaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Convertirmos los campos textfield a tipo objeto ClassEmpresa
    private ClassEmpresa convertirStringObjeto() {
        this.objetoEmpresa = new ClassEmpresa();
        objetoEmpresa.setId(idRegistro);
        objetoEmpresa.setCif(txtCif.getText().strip().toUpperCase());
        objetoEmpresa.setNombre(txtNombre.getText().strip().toUpperCase());
        objetoEmpresa.setCalle(txtCalle.getText().strip().toUpperCase());
        objetoEmpresa.setNumero(Integer.parseInt(txtNumero.getText()));
        objetoEmpresa.setCp(Integer.parseInt(txtCodigoPostal.getText()));
        objetoEmpresa.setLocalidad(txtLocalidad.getText().strip().toUpperCase());
        objetoEmpresa.setTelefono(txtTelefono.getText().strip().toUpperCase());
        return objetoEmpresa;
    }

    //Método para limpiar todos los campos que forman parte del formulario
    private void limpiar() {
        idRegistro = 0;
        txtCif.setText("");
        txtNombre.setText("");
        txtCalle.setText("");
        txtLocalidad.setText("");
    }

    //Este método cierra la ventana que forme parte del componente capturado
    private void cerrarVentana() {
        Stage myStage = (Stage) this.txtCif.getScene().getWindow();
        myStage.close();
    }

    //Este método viene de EmpresaVistaController y nos pasa los datos de los campos que asignamos a los txtfield
    public void pasarDatos(ClassEmpresa objEmpresa) {
        idRegistro = objEmpresa.getId();
        txtCif.setText(objEmpresa.getCif());
        cifAnterior = objEmpresa.getCif();
        txtNombre.setText(objEmpresa.getNombre());
        txtCalle.setText(objEmpresa.getCalle());
        txtNumero.setText(String.valueOf(objEmpresa.getNumero()));
        txtCodigoPostal.setText(String.valueOf(objEmpresa.getCp()));
        txtLocalidad.setText(objEmpresa.getLocalidad());
        txtTelefono.setText(objEmpresa.getTelefono());
    }

}
