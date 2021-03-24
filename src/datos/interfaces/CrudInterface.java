package datos.interfaces;

import javafx.collections.ObservableList;

public interface CrudInterface<T> {

    public ObservableList<T> listar(String texto); //similar al List. Especial para JavaFX

    public boolean insertar(T obj);

    public boolean actualizar(T obj);

    public boolean eliminar(T obj);

    public int total();

    public boolean existe(String texto);
}
