package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrincipalVistaController implements Initializable {

    @FXML
    private VBox vboxPrincipal;
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private VBox ventana; //creamos un objeto de tipo VBox (Todas nuestras ventanas hijas comienzan por este tipo de elemento)

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void mnuiAlumno(ActionEvent event) {
        cargarAlumno();
    }

    private void mnuiEmpresa(ActionEvent event) {
        cargarEmpresa();
    }

    @FXML
    private void mnuiProfesor(ActionEvent event) {
    }


    @FXML
    private void mnuiSalir(ActionEvent event) {
        //AQUÍ AÑADIR LA FUNCIONALIDAD DE SI QUIERE SALIR PARA QUE NO SALGA DIRECTO
        Platform.exit(); //Es ideal para cuando se cierre la aplicación se ejecute el proceso stop()
    }

    private void cargarAlumno() {
        try {
            ventana = FXMLLoader.load(getClass().getResource("/vista/AlumnoVista.fxml"));
            vboxPrincipal.getChildren().setAll(ventana);
            vboxPrincipal.setVisible(true);

        } catch (IOException ex) {
            System.err.println("Error la carga de alumno" + ex);
        }
    }

    private void cargarEmpresa() {
        try {
            ventana = FXMLLoader.load(getClass().getResource("/vista/EmpresaVista.fxml"));
            vboxPrincipal.getChildren().setAll(ventana);
            vboxPrincipal.setVisible(true);

        } catch (IOException ex) {
            System.err.println("Error la carga de empresa" + ex);
        }
    }

    private void cargarProfesor() {
        try {
            ventana = FXMLLoader.load(getClass().getResource("/vista/ProfesorVista.fxml"));
            vboxPrincipal.getChildren().setAll(ventana);
            vboxPrincipal.setVisible(true);

        } catch (IOException ex) {
            System.err.println("Error la carga de profesor" + ex);
        }
    }

    private void cargarCurso() {
        try {
            ventana = FXMLLoader.load(getClass().getResource("/vista/CursoVista.fxml"));
            vboxPrincipal.getChildren().setAll(ventana);
            vboxPrincipal.setVisible(true);

        } catch (IOException ex) {
            System.err.println("Error la carga de curso" + ex);
        }
    }

    @FXML
    private void mnuiCursos(ActionEvent event) {
    }

    @FXML
    private void mnuiEmpresas(ActionEvent event) {
    }

}
