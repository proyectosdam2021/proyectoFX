package controlador;

import entidades.ClassAlumno;
import java.net.URL;
import java.util.Date;
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
    private Label lblNumRegistros;
    @FXML
    private TableView<ClassAlumno> tablaAlumno;
    @FXML
    private TableColumn<ClassAlumno, Integer> colId;
    @FXML
    private TableColumn<ClassAlumno, String> ColDni;
    @FXML
    private TableColumn<ClassAlumno, String> ColNombre;
    @FXML
    private TableColumn<ClassAlumno, String> colApellido1;
    @FXML
    private TableColumn<ClassAlumno, String> colApellido2;
    @FXML
    private TableColumn<ClassAlumno, String> colCalle;
    @FXML
    private TableColumn<ClassAlumno, Integer> colNumero;
    @FXML
    private TableColumn<ClassAlumno, Integer> colCP;
    @FXML
    private TableColumn<ClassAlumno, String> colLocalidad;
    @FXML
    private TableColumn<ClassAlumno, String> colTelefono;
    @FXML
    private TableColumn<ClassAlumno, Date> colFechaNac;
    @FXML
    private TableColumn<ClassAlumno, Integer> colIdEmpresa;

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

    @FXML
    private void txtPulsadoEnter(KeyEvent event) {
    }

}
