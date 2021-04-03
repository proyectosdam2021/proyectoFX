package controlador;

import datos.EmpresaDAO;
import entidades.ClassEmpresa;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import negocio.Variables;

public class EmpresaVistaController implements Initializable {

    private ObservableList<ClassEmpresa> items; //instanciamos un objeto tipo arrayList especial para JavaFX
    private EmpresaDAO datos;   //instanciamos la clase DAO la cual gestiona las acciones hacia nuestra BD
    private ClassEmpresa copiaEmpresa;  //objeto donde guardar datos de la tabla
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private double[] posicion;    //posición de la ventana en eje X-Y

    @FXML // fx:id="txtFiltrarEmpresaTabla"
    private TextField txtFiltrarEmpresaTabla; // Value injected by FXMLLoader
    @FXML // fx:id="btnBuscar"
    private Button btnBuscar; // Value injected by FXMLLoader
    @FXML // fx:id="btnLimpiar"
    private Button btnLimpiar; // Value injected by FXMLLoader
    @FXML // fx:id="btnEditar"
    private Button btnEditar; // Value injected by FXMLLoader
    @FXML // fx:id="btnEliminar"
    private Button btnEliminar; // Value injected by FXMLLoader
    @FXML // fx:id="tablaEmpresa"
    private TableView<ClassEmpresa> tablaEmpresa; // Value injected by FXMLLoader
    @FXML // fx:id="colId"
    private TableColumn<ClassEmpresa, Integer> colId; // Value injected by FXMLLoader
    @FXML // fx:id="colCif"
    private TableColumn<ClassEmpresa, String> colCif; // Value injected by FXMLLoader
    @FXML // fx:id="ColNombre"
    private TableColumn<ClassEmpresa, String> ColNombre; // Value injected by FXMLLoader
    @FXML // fx:id="colCalle"
    private TableColumn<ClassEmpresa, String> colCalle; // Value injected by FXMLLoader
    @FXML // fx:id="colNumero"
    private TableColumn<ClassEmpresa, Integer> colNumero; // Value injected by FXMLLoader
    @FXML // fx:id="colCP"
    private TableColumn<ClassEmpresa, Integer> colCP; // Value injected by FXMLLoader
    @FXML // fx:id="colLocalidad"
    private TableColumn<ClassEmpresa, String> colLocalidad; // Value injected by FXMLLoader
    @FXML // fx:id="colTelefono"
    private TableColumn<ClassEmpresa, String> colTelefono; // Value injected by FXMLLoader
    @FXML // fx:id="lblNumRegistros"
    private Label lblNumRegistros; // Value injected by FXMLLoader

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        offOnBotones(true);
        datos = new EmpresaDAO();  //instanciamos un objeto para hacer consultas a la BD
        this.cargarTabla(""); //cargamos la tabla
    }

    @FXML
    private void buscarEmpresaTabla(ActionEvent event) {
        offOnBotones(true);
        this.cargarTabla(txtFiltrarEmpresaTabla.getText());
    }

    @FXML
    private void limpiarEmpresaTabla(ActionEvent event) {
        this.limpiarVista();
        this.cargarTabla("");
    }

    @FXML
    private void editarEmpresaTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.setTextoFrm("EDITAR EMPRESA");  //Se usará posteriormente en el controlador Frm
        this.cargarFrmEmpresa();
    }

    @FXML
    private void eliminarEmpresaTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.setTextoFrm("ELIMINAR EMPRESA");  //Se usará posteriormente en el controlador FrmAlumno
        this.cargarFrmEmpresa();
    }

    @FXML
    void posicionRatonTabla(MouseEvent event) {
        //cuando pulsamos con el ratón en algún registro de la tabla capturamos la información de la fila
        ClassEmpresa claseEmpresa = tablaEmpresa.getSelectionModel().getSelectedItem();
        if (claseEmpresa != null) {  //si no es NULL capturamos los datos de la fila
            copiaEmpresa = (ClassEmpresa) claseEmpresa.clonar();
        }
        offOnBotones(false);
        //si se pulsa ENTER en algún registro de la tabla y el objeto no es nulo
        if (event.getClickCount() == 2 && (claseEmpresa != null)) {
            Variables.setTextoFrm("EDITAR EMPRESA");  //Lo procesamos como Editar
            this.cargarFrmEmpresa();
        }
    }

    @FXML
    void posicionTeclaTabla(KeyEvent event) {
        //cuando nos desplazamos con el cursor por la tabla capturamos la información de la fila
        ClassEmpresa claseEmpresa = tablaEmpresa.getSelectionModel().getSelectedItem();
        if (claseEmpresa != null) {  //si no es NULL capturamos los datos de la fila
            copiaEmpresa = (ClassEmpresa) claseEmpresa.clonar();
        }
        offOnBotones(false);
        //si se pulsa ENTER en algún registro de la tabla y el objeto no es nulo
        if (event.getCode().equals(KeyCode.ENTER) && (claseEmpresa != null)) {
            Variables.setTextoFrm("EDITAR EMPRESA");  //Lo procesamos como Editar
            this.cargarFrmEmpresa();
        }
    }

    @FXML
    void txtPulsadoEnter(KeyEvent event) {
        //keyPressed: cuando se pulsa ENTER en la caja de textoBuscar hacemos la acción de buscar
        Object evt = event.getSource();
        if (evt.equals(txtFiltrarEmpresaTabla) && txtFiltrarEmpresaTabla.getText().length() >= 2) {
            final int segundos = 3;
            Timeline lineadetiempo = new Timeline(new KeyFrame(Duration.seconds(segundos), (ActionEvent event1) -> {
                cargarTabla(txtFiltrarEmpresaTabla.getText());
            }));
            lineadetiempo.setCycleCount(1);
            lineadetiempo.play();
            System.out.println("Pulsando tecla");

            if (event.getCode().equals(KeyCode.ENTER)) {
                this.cargarTabla(txtFiltrarEmpresaTabla.getText());
            }
        }
    }

    private void offOnBotones(boolean estado) {
        this.btnEditar.setDisable(estado);
        this.btnEliminar.setDisable(estado);
    }

    public void cargarTabla(String filtro) {
        //asignamos a cada columna de la tabla el valor de su campo referenciado en ClassEmpresa
        PropertyValueFactory<ClassEmpresa, Integer> valorCol1 = new PropertyValueFactory<>("id");
        this.colId.setCellValueFactory(valorCol1);
        PropertyValueFactory<ClassEmpresa, String> valorCol2 = new PropertyValueFactory<>("cif");
        this.colCif.setCellValueFactory(valorCol2);
        PropertyValueFactory<ClassEmpresa, String> valorCol3 = new PropertyValueFactory<>("nombre");
        this.ColNombre.setCellValueFactory(valorCol3);
        PropertyValueFactory<ClassEmpresa, String> valorCol4 = new PropertyValueFactory<>("calle");
        this.colCalle.setCellValueFactory(valorCol4);
        PropertyValueFactory<ClassEmpresa, Integer> valorCol5 = new PropertyValueFactory<>("numero");
        this.colNumero.setCellValueFactory(valorCol5);
        PropertyValueFactory<ClassEmpresa, Integer> valorCol6 = new PropertyValueFactory<>("cp");
        this.colCP.setCellValueFactory(valorCol6);
        PropertyValueFactory<ClassEmpresa, String> valorCol7 = new PropertyValueFactory<>("localidad");
        this.colLocalidad.setCellValueFactory(valorCol7);
        PropertyValueFactory<ClassEmpresa, String> valorCol8 = new PropertyValueFactory<>("telefono");
        this.colTelefono.setCellValueFactory(valorCol8);

        items = datos.listar(filtro);  //llamamos al método "listar" dentro de la clase AlumnoDAO
        this.tablaEmpresa.refresh();  //refrescamos los datos de la tabla (sobre todo es interesante cuando actualizamos)
        this.tablaEmpresa.setItems(items); //mostramos las columnas de la tabla
        lblNumRegistros.setText("Mostrando " + Variables.getRegistrosMostrados() + " de un total de " + datos.total() + " registros");
    }

    private void limpiarVista() {
        //this.cambiarOpacidad(1);
        offOnBotones(true);
        this.txtFiltrarEmpresaTabla.setText("");
        Variables.setTextoFrm("");  //el texto superior que aparece al entrar en FrmAlumno
    }

    private void cargarFrmEmpresa() {
        try {
            //cargamos la vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FrmEmpresa.fxml"));
            //instanciamos y cargamos el FXML en el padre
            Parent root = loader.load();
            //instanciamos al controlador FrmAlumnoNuevo haciendo uso del nuevo método getController
            FrmEmpresaController ctrFrmEmpresa = loader.getController();
            scene = new Scene(root); //creamos la nueva escena que viene del padre
            stage = new Stage();    //creamos la nueva ventana
            stage.setTitle("Crud de Empresa"); //ponemos un título
            stage.initModality(Modality.APPLICATION_MODAL);  //hacemos que la escena nueva tome el foco y no permita cambiarse de ventana
            stage.setScene(scene); //establecemos la escena
            this.ventanaPosicion(); //posicionamos la nueva ventana
            this.cambiarOpacidad(0.5); //cambiamos la opacidad de la ventana anterior
            stage.setResizable(false); //no permitimos que la ventana cambie de tamaño
            stage.initStyle(StageStyle.UTILITY); //desactivamos maximinar y minimizar
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/icons8_java_duke_50px.png")));
            //Pasamos los datos a la nueva ventana FrmAlumno mientras sea distinto a CREAR ALUMNO (se usará para EDITAR/ELIMINAR)
            if (!"CREAR EMPRESA".equals(Variables.getTextoFrm())) {
                ctrFrmEmpresa.pasarDatos(copiaEmpresa);
            }
            stage.showAndWait(); //mostramos la nueva ventana y esperamos
            //El programa continua en esta línea cuando la nueva ventana se cierre
            this.cambiarOpacidad(1);
            this.limpiarVista();
            this.cargarTabla("");

        } catch (IOException ex) {
            System.err.println("Error en el inicio validado " + ex);
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
        int frmX = 420 / 2; //tamaño ancho componente FrmAlumno
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
