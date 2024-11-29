
package excepciones;

public class DuracionException extends Exception {
    
    public DuracionException() {
        super("La duración no es múltiplo de 30 minutos.");
    }
}
