package com.proyectoacademia;

import controlador.PrincipalVistaController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import negocio.MensajeFX;
        
public class ProyectoAcademia extends Application {
    
    private static Scene scene;   //donde se produce la acción con los elementos creados
    private static Stage stage;   //el marco de la ventana actual

    @Override
    public void init() {  //Primer método que se ejecuta al instanciar la clase
        //Usado para validaciones con bases de datos
        //Cargar configuración inicial
        //NO vale para cargar componentes de nuestra interfaz gráfica
        System.out.println("Método init()");
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Método start()");
        System.out.println("Java version: " + System.getProperty("java.version") + "\nJavaFX version: " + System.getProperty("javafx.version"));
        //cargamos la vista FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PrincipalVista.fxml"));
        //instanciamos y cargamos el FXML en el padre
        Parent root = loader.load();
        //instanciamos el controlador haciendo uso del nuevo método getController
        PrincipalVistaController ctrPrincipal = loader.getController();
        scene = new Scene(root);//creamos la nueva escena padre
        stage = new Stage();    //creamos la nueva ventana
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene); //establecemos la escena
        //Cargamos el resto de componentes de la vista
        stage.setTitle("Proyecto Academia");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/icons8_java_duke_50px.png")));  //Cargamos un nuevo icono en la ventana
        //stage.setResizable(false); //no permitimos que la ventana cambie de tamaño
        stage.show(); //mostramos la ventana
        //este código es para controlar el evento al pulsar la X y confirmar el cierre de la aplicación
        double[] posicionxy = new double[2]; 
        int frmX = 420 / 2; //tamaño ancho componente
        int frmY = 400; //tamaño alto componente
        int x = (int) (stage.getWidth() / 2);
        int y = (int) (stage.getHeight() / 2);
        posicionxy[0] = stage.getX() + (x - frmX);
        posicionxy[1] = stage.getY() + (y - frmY);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                if (MensajeFX.printTexto("¿Desea salir de la aplicación?", "CONFIRM", posicionxy)) {
                } else {
                    we.consume();
                }
            }
        });
    }
    
    @Override
    public void stop() { //Creamos procesos de finalización
        System.out.println("Método stop()");
    }
    
    public void cerrar() {
        Platform.exit(); //Es ideal para cuando se cierre la aplicación se ejecute el proceso stop()
        //Es decir no tendremos que usar la función System.exit(0) ya que debemos sustituirlo por el nuevo Platform.exit()
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
