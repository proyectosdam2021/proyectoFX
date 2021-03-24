package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author telev
 */
public class AlumnoVistaController implements Initializable {

    @FXML
    private TextField txtFiltrarAlumnoTabla;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<?> tablaAlumno;
    @FXML
    private TableColumn<?, ?> colRegistro;
    @FXML
    private TableColumn<?, ?> ColDni;
    @FXML
    private TableColumn<?, ?> ColNombre;
    @FXML
    private TableColumn<?, ?> colApellido1;
    @FXML
    private TableColumn<?, ?> colApellido2;
    @FXML
    private Label lblRegistros;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pulsoEnter(KeyEvent event) {
    }

    @FXML
    private void buscarAlumnoTabla(ActionEvent event) {
    }

    @FXML
    private void limpiarAlumnoTabla(ActionEvent event) {
    }

    @FXML
    private void nuevoAlumnoTabla(ActionEvent event) {
    }

    @FXML
    private void editarAlumnoTabla(ActionEvent event) {
    }

    @FXML
    private void eliminarAlumnoTabla(ActionEvent event) {
    }

    @FXML
    private void posicionRatonTabla(MouseEvent event) {
    }

    @FXML
    private void posicionTeclaTabla(KeyEvent event) {
    }
    
}
