package controlador;

import datos.AlumnoDAO;
import entidades.ClassAlumno;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import negocio.AlumnoNegocio;
import negocio.EmpresaNegocio;
import negocio.MensajeFX;
import negocio.Variables;

public class AlumnoVistaController implements Initializable {

    private ObservableList<ClassAlumno> items; //instanciamos un objeto tipo arrayList especial para JavaFX
    private AlumnoDAO datos;   //instanciamos la clase AlumnoDAO la cual gestiona las acciones hacia nuestra BD
    private ClassAlumno copiaAlumno;  //objeto donde guardar datos de la tabla
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private double[] posicion;    //posición de la ventana en eje X-Y
    private AlumnoNegocio CONTROL;
    private EmpresaNegocio ControlEmpresa;

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
        offOnBotones(true);
        datos = new AlumnoDAO();  //instanciamos un objeto para hacer consultas a la BD
        this.cargarTabla(""); //cargamos la tabla de alumnos
    }

    @FXML
    private void buscarAlumnoTabla(ActionEvent event) {
        offOnBotones(true);
        this.cargarTabla(txtFiltrarAlumnoTabla.getText());
    }

    @FXML
    private void limpiarAlumnoTabla(ActionEvent event) {
        this.limpiarVista();
        this.cargarTabla("");
    }

    @FXML
    private void nuevoAlumnoTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.setTextoFrm("CREAR ALUMNO");  //Se usará posteriormente en el controlador FrmAlumno
        this.cargarFrmAlumno();
    }

    @FXML
    private void editarAlumnoTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.setTextoFrm("EDITAR ALUMNO");  //Se usará posteriormente en el controlador FrmAlumno
        this.cargarFrmAlumno();
    }

    @FXML
    private void eliminarAlumnoTabla(ActionEvent event) {
        String respuesta;
        CONTROL = new AlumnoNegocio();
        ControlEmpresa = new EmpresaNegocio();
        try {
            if (MensajeFX.printTexto("¿Desea eliminar este registro?", "CONFIRM", obtenPosicionX_Y())) {
                respuesta = this.CONTROL.eliminar(copiaAlumno.getId());
                if ("OK".equals(respuesta)) {
                    MensajeFX.printTexto("Alumno eliminado correctamente", "INFO", obtenPosicionX_Y());
                    cargarTabla("");
                    ControlEmpresa.eliminar(copiaAlumno.getId_empresa()); //Eliminamos también la empresa asociada
                } else {
                    MensajeFX.printTexto("El Alumno no se ha podido eliminar", "ERROR", obtenPosicionX_Y());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void posicionRatonTabla(MouseEvent event
    ) {
        //cuando pulsamos con el ratón en algún registro de la tabla capturamos la información de la fila
        ClassAlumno claseAlumno = tablaAlumno.getSelectionModel().getSelectedItem();
        if (claseAlumno != null) {  //si no es NULL capturamos los datos de la fila
            copiaAlumno = (ClassAlumno) claseAlumno.clonar();
        }
        offOnBotones(false);
        //si se pulsa ENTER en algún registro de la tabla y el objeto no es nulo
        if (event.getClickCount() == 2 && (claseAlumno != null)) {
            Variables.setTextoFrm("EDITAR ALUMNO");  //Lo procesamos como Editar
            this.cargarFrmAlumno();
        }
    }

    @FXML
    private void posicionTeclaTabla(KeyEvent event
    ) {
        //cuando nos desplazamos con el cursor por la tabla capturamos la información de la fila
        ClassAlumno claseAlumno = tablaAlumno.getSelectionModel().getSelectedItem();
        if (claseAlumno != null) {  //si no es NULL capturamos los datos de la fila
            copiaAlumno = (ClassAlumno) claseAlumno.clonar();
        }
        offOnBotones(false);
        //si se pulsa ENTER en algún registro de la tabla y el objeto no es nulo
        if (event.getCode().equals(KeyCode.ENTER) && (claseAlumno != null)) {
            Variables.setTextoFrm("EDITAR ALUMNO");  //Lo procesamos como Editar
            this.cargarFrmAlumno();
        }
    }

    @FXML
    private void txtPulsadoEnter(KeyEvent event) {

        //keyPressed: cuando se pulsa ENTER en la caja de textoBuscar hacemos la acción de buscar
        Object evt = event.getSource();
        if (evt.equals(txtFiltrarAlumnoTabla) && txtFiltrarAlumnoTabla.getText().length() >= 2) {
            final int segundos = 3;
            Timeline lineadetiempo = new Timeline(new KeyFrame(Duration.seconds(segundos), (ActionEvent event1) -> {
                                                           cargarTabla(txtFiltrarAlumnoTabla.getText());
                                                       }));
            lineadetiempo.setCycleCount(1);
            lineadetiempo.play();
            System.out.println("Pulsando tecla");
            if (event.getCode().equals(KeyCode.ENTER)) {
                this.cargarTabla(txtFiltrarAlumnoTabla.getText());
            }
        }
    }

    private void offOnBotones(boolean estado) {
        this.btnEditar.setDisable(estado);
        this.btnEliminar.setDisable(estado);
    }

    public void cargarTabla(String filtro) {
        //asignamos a cada columna de la tabla el valor de su campo referenciado en ClassAlumno
        PropertyValueFactory<ClassAlumno, Integer> valorCol1 = new PropertyValueFactory<>("id");
        this.colId.setCellValueFactory(valorCol1);
        PropertyValueFactory<ClassAlumno, String> valorCol2 = new PropertyValueFactory<>("dni");
        this.ColDni.setCellValueFactory(valorCol2);
        PropertyValueFactory<ClassAlumno, String> valorCol3 = new PropertyValueFactory<>("nombre");
        this.ColNombre.setCellValueFactory(valorCol3);
        PropertyValueFactory<ClassAlumno, String> valorCol4 = new PropertyValueFactory<>("apellido1");
        this.colApellido1.setCellValueFactory(valorCol4);
        PropertyValueFactory<ClassAlumno, String> valorCol5 = new PropertyValueFactory<>("apellido2");
        this.colApellido2.setCellValueFactory(valorCol5);
        PropertyValueFactory<ClassAlumno, String> valorCol6 = new PropertyValueFactory<>("calle");
        this.colCalle.setCellValueFactory(valorCol6);
        PropertyValueFactory<ClassAlumno, Integer> valorCol7 = new PropertyValueFactory<>("numero");
        this.colNumero.setCellValueFactory(valorCol7);
        PropertyValueFactory<ClassAlumno, Integer> valorCol8 = new PropertyValueFactory<>("cp");
        this.colCP.setCellValueFactory(valorCol8);
        PropertyValueFactory<ClassAlumno, String> valorCol9 = new PropertyValueFactory<>("localidad");
        this.colLocalidad.setCellValueFactory(valorCol9);
        PropertyValueFactory<ClassAlumno, String> valorCol0 = new PropertyValueFactory<>("telefono");
        this.colTelefono.setCellValueFactory(valorCol0);
        PropertyValueFactory<ClassAlumno, Date> valorCol11 = new PropertyValueFactory<>("fecha_nacimiento");
        this.colFechaNac.setCellValueFactory(valorCol11);
        PropertyValueFactory<ClassAlumno, Integer> valorCol12 = new PropertyValueFactory<>("id_empresa");
        this.colIdEmpresa.setCellValueFactory(valorCol12);

        items = datos.listar(filtro);  //llamamos al método "listar" dentro de la clase AlumnoDAO
        this.tablaAlumno.refresh();  //refrescamos los datos de la tabla (sobre todo es interesante cuando actualizamos)
        this.tablaAlumno.setItems(items); //mostramos las columnas de la tabla
        lblNumRegistros.setText("Mostrando " + Variables.getRegistrosMostrados() + " de un total de " + datos.total() + " registros");
    }

    private void limpiarVista() {
        //this.cambiarOpacidad(1);
        offOnBotones(true);
        this.txtFiltrarAlumnoTabla.setText("");
        Variables.setTextoFrm("");  //el texto superior que aparece al entrar en FrmAlumno
    }

    private void cargarFrmAlumno() {
        try {
            //cargamos la vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FrmAlumno.fxml"));
            //instanciamos y cargamos el FXML en el padre
            Parent root = loader.load();
            //instanciamos al controlador FrmAlumnoNuevo haciendo uso del nuevo método getController
            FrmAlumnoController ctrFrmAlumno = loader.getController();
            scene = new Scene(root); //creamos la nueva escena que viene del padre
            stage = new Stage();    //creamos la nueva ventana
            stage.setTitle("Crud de Alumnos"); //ponemos un título
            stage.initModality(Modality.APPLICATION_MODAL);  //hacemos que la escena nueva tome el foco y no permita cambiarse de ventana
            stage.setScene(scene); //establecemos la escena
            this.ventanaPosicion(); //posicionamos la nueva ventana
            this.cambiarOpacidad(0.5); //cambiamos la opacidad de la ventana anterior
            stage.setResizable(false); //no permitimos que la ventana cambie de tamaño
            stage.initStyle(StageStyle.UTILITY); //desactivamos maximinar y minimizar
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/icons8_java_duke_50px.png")));
            //Pasamos los datos a la nueva ventana FrmAlumno mientras sea distinto a CREAR ALUMNO (se usará para EDITAR/ELIMINAR)
            if ("EDITAR ALUMNO".equals(Variables.getTextoFrm())) {
                ctrFrmAlumno.pasarDatos(copiaAlumno);
            }
            stage.showAndWait(); //mostramos la nueva ventana y esperamos
            //El programa continua en esta línea cuando la nueva ventana se cierre
            this.cambiarOpacidad(1);
            this.limpiarVista();
            this.cargarTabla("");

        } catch (IOException ex) {
            System.err.println("Error en cargarFrmAlumno " + ex);
        }
    }

    public void ventanaPosicion() {
        posicion = obtenPosicionX_Y();
        stage.setX(posicion[0]);
        stage.setY(posicion[1]);
    }

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana correctamente
    public double[] obtenPosicionX_Y() {
        double[] posicionxy = new double[2];
        //creamos una nueva ventana temporal capturando de cualquier btn/lbl la escena y ventana
        //se entiende que los btn o lbl forman parte de la ventana que deseamos obtener datos
        Stage myStage = (Stage) this.lblNumRegistros.getScene().getWindow();
        int frmX = 840 / 2; //tamaño ancho componente FrmAlumno
        int frmY = 700 / 2; //tamaño alto componente FrmAlumno
        int x = (int) (myStage.getWidth() / 2);
        int y = (int) (myStage.getHeight() / 2);
        posicionxy[0] = myStage.getX() + (x - frmX);
        posicionxy[1] = myStage.getY() + (y - frmY);
        return posicionxy;
    }

    public void cambiarOpacidad(double valor) {
        Stage myStage = (Stage) this.lblNumRegistros.getScene().getWindow();
        myStage.setOpacity(valor);
    }

}
