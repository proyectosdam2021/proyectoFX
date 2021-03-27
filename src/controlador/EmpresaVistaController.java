package controlador;

import datos.EmpresaDAO;
import entidades.ClassAlumno;
import entidades.ClassEmpresa;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import negocio.Variables;

public class EmpresaVistaController implements Initializable {

    private ObservableList<ClassEmpresa> items; //instanciamos un objeto tipo arrayList especial para JavaFX
    private EmpresaDAO datos;   //instanciamos la clase AlumnoDAO la cual gestiona las acciones hacia nuestra BD
    private ClassEmpresa copiaEmpresa;  //objeto donde guardar datos de la tabla
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private double[] posicion;    //posición de la ventana en eje X-Y

    @FXML
    private TextField txtFiltrarEmpresaTabla;
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
    private TableView<ClassEmpresa> tablaEmpresa;
    @FXML
    private TableColumn<ClassEmpresa, Integer> colId;
    @FXML
    private TableColumn<ClassEmpresa, String> ColCif;
    @FXML
    private TableColumn<ClassEmpresa, String> ColNombre;
    @FXML
    private TableColumn<ClassEmpresa, String> colCalle;
    @FXML
    private TableColumn<ClassEmpresa, Integer> colNumero;
    @FXML
    private TableColumn<ClassEmpresa, Integer> colCP;
    @FXML
    private TableColumn<ClassEmpresa, String> colLocalidad;
    @FXML
    private TableColumn<ClassEmpresa, String> colTelefono;
    @FXML
    private Label lblNumRegistros;

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
        this.cargarTabla(""); //cargamos la tabla de alumnos
    }

    @FXML
    private void txtPulsadoEnter(KeyEvent event) {
    }

    @FXML
    private void buscarAlumnoTabla(ActionEvent event) {
        offOnBotones(true);
        this.cargarTabla(txtFiltrarEmpresaTabla.getText());
    }

    @FXML
    private void limpiarAlumnoTabla(ActionEvent event) {
        this.limpiarVista();
        this.cargarTabla("");
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
    private void posicionTeclaTabla(KeyEvent event) {
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

    private void offOnBotones(boolean estado) {
        this.btnEditar.setDisable(estado);
        this.btnEliminar.setDisable(estado);
    }

    public void cargarTabla(String filtro) {
        //asignamos a cada columna de la tabla el valor de su campo referenciado en ClassEmpresa
        this.colId.setCellValueFactory(new PropertyValueFactory("id"));
        this.ColCif.setCellValueFactory(new PropertyValueFactory("cif"));
        this.ColNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colCalle.setCellValueFactory(new PropertyValueFactory("calle"));
        this.colNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        this.colCP.setCellValueFactory(new PropertyValueFactory("cp"));
        this.colLocalidad.setCellValueFactory(new PropertyValueFactory("localidad"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        
        items = datos.listar(filtro);  //llamamos al método "listar" dentro de la clase EmpresaDAO
        this.tablaEmpresa.refresh();  //refrescamos los datos de la tabla (sobre todo es interesante cuando actualizamos)
        this.tablaEmpresa.setItems(items); //mostramos las columnas de la tabla
        lblNumRegistros.setText("Mostrando " + Variables.getRegistrosMostrados() + " de un total de " + datos.total() + " registros");
    }

    private void limpiarVista() {
        //this.cambiarOpacidad(1);
        offOnBotones(true);
        this.txtFiltrarEmpresaTabla.setText("");
        Variables.setTextoFrm("");  //el texto superior que aparece al entrar en FrmEmrpesa
    }

    private void cargarFrmEmpresa() {
        try {
            //cargamos la vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FrmEmpresa.fxml"));
            //instanciamos y cargamos el FXML en el padre
            Parent root = loader.load();
            //instanciamos al controlador FrmAlumnoNuevo haciendo uso del nuevo método getController
           // FrmEmpresaController ctrFrmEmpresa = loader.getController();
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
            if (!"CREAR ALUMNO".equals(Variables.getTextoFrm())) {
           //     ctrFrmAlumno.pasarDatos(copiaAlumno);
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
