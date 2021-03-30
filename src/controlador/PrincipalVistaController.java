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
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import negocio.MensajeFX;

public class PrincipalVistaController implements Initializable {

    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private VBox ventana; //creamos un objeto de tipo VBox (Todas nuestras ventanas hijas comienzan por este tipo de elemento)
    private double[] posicion;    //posición de la ventana en eje X-Y
    @FXML
    private VBox vboxPrincipal;
    @FXML
    private Button mnuSalir;

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

    @FXML
    private void mnuiEmpresas(ActionEvent event) {
        cargarEmpresa();
    }

    @FXML
    private void mnuiProfesor(ActionEvent event) {
    }

    @FXML
    private void mnuiCursos(ActionEvent event) {
    }

    @FXML
    private void mnuiSalir(ActionEvent event) {
        if (MensajeFX.printTexto("¿Desea salir de la aplicación?", "CONFIRM", posicionX_Y())) {
            Platform.exit(); //Es ideal para cuando se cierre la aplicación se ejecute el proceso stop()
        }
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

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana de mensajes en la pantalla correctamente
    private double[] posicionX_Y() {
        double[] posicionxy = new double[2];
        //creamos una nueva ventana temporal capturando de cualquier btn/lbl la escena y ventana
        //se entiende que los btn o lbl forman parte de la ventana que deseamos obtener datos
        Stage myStage = (Stage) this.mnuSalir.getScene().getWindow();
        int frmX = 420 / 2; //tamaño ancho componente
        int frmY = 400; //tamaño alto componente
        int x = (int) (myStage.getWidth() / 2);
        int y = (int) (myStage.getHeight() / 2);
        posicionxy[0] = myStage.getX() + (x - frmX);
        posicionxy[1] = myStage.getY() + (y - frmY);
        return posicionxy;
    }

}
