/*
 ******************************************************************
 *************** MODELO PATRÓN PROTOTYPE **************************
*******************************************************************
https://www.tutorialspoint.com/design_pattern/prototype_pattern.htm
 ******************************************************************
 ******************************************************************
 */
package datos.interfaces;

//creamos un método que devuelve la misma interface
//lo declaramos en nuestras clases entidades
//es ideal para copiar clases
public interface ClonarClase extends Cloneable {

    ClonarClase clonar();
}
