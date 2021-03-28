package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class FrmEmpresaController implements Initializable {

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
        // TODO
    }

    @FXML
    private void valorTecla(KeyEvent event) {
    }

    @FXML
    private void cancelarAlumno(ActionEvent event) {
    }

    @FXML
    private void grabarAlumno(ActionEvent event) {
    }

}
