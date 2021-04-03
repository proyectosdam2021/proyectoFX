//JAVA FX//
package negocio;

import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MensajeFX {

    private final String INFO = "INFO";
    private final String WARNING = "WARNING";
    private final String ERROR = "ERROR";
    private final String CONFIRM = "CONFIRM";
    private final String TIEMPO = "TIEMPO";
    private static final int segundos = 3;

    public MensajeFX() {
    }

    public static boolean printTexto(String mensaje, String tipo, double[] posicion) {
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);

        Timeline lineadetiempo = new Timeline(new KeyFrame(Duration.seconds(segundos), (ActionEvent event) -> {
            alertInfo.hide();
        }));
        lineadetiempo.setCycleCount(1);
        lineadetiempo.play();

        //ponemos las coordenadas a las ventanas de mensajes
        posicion[0] = posicion[0] - 5;  //ajustamos la posición en eje X
        posicion[1] = posicion[1] + 50; //ajustamos la posición en el eje Y
        alertInfo.setX(posicion[0]);   //seteamos la posición en eje X de la ventana de mensaje
        alertInfo.setY(posicion[1]);   //seteamos la posición en el eje Y de la ventana de mensaje
        alertWarning.setX(posicion[0]);
        alertWarning.setY(posicion[1]);
        alertError.setX(posicion[0]);
        alertError.setY(posicion[1]);
        alertConfirm.setX(posicion[0]);
        alertConfirm.setY(posicion[1]);
        alertInfo.initStyle(StageStyle.UNDECORATED);
        alertInfo.initModality(Modality.WINDOW_MODAL);

        switch (tipo) {
            case "INFO":
                alertInfo.setHeaderText(null);
                alertInfo.setTitle("Información del sistema");
                alertInfo.setContentText(mensaje);
                alertInfo.show();
                return true;

            case "WARNING":
                alertWarning.setHeaderText(null);
                alertWarning.setTitle("Advertencia del sistema");
                alertWarning.setContentText(mensaje);
                alertWarning.showAndWait();
                return true;

            case "ERROR":
                alertError.setHeaderText(null);
                alertError.setTitle("Error del sistema");
                alertError.setContentText(mensaje);
                alertError.showAndWait();
                return true;

            case "CONFIRM":
                alertConfirm.setHeaderText(null);
                alertConfirm.setTitle("Confirmación");
                alertConfirm.setContentText(mensaje);
                Optional<ButtonType> action = alertConfirm.showAndWait();  //usamos la clase Optional<ButtonType> para guardar el resultado de lo que hemos seleccionado
                // Si hemos pulsado en aceptar
                if (action.get() == ButtonType.OK) {
                    return true;
                } else {
                    return false;
                }
        }
        return true;
    }

}
