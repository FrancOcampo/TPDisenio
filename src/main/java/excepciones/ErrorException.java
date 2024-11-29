
package excepciones;

public class ErrorException extends Exception {
    
    public ErrorException() {
        super("Ocurrió un error. Vuelva a intentarlo.");
    }
}
