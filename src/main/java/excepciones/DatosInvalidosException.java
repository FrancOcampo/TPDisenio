
package excepciones;

public class DatosInvalidosException extends Exception {
    
    public DatosInvalidosException(){}
    
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
