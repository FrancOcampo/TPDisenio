
package excepciones;

public class NoExisteBedelException extends Exception {
    
    public NoExisteBedelException() {
        super("No existen bedeles que cumplan con ese criterio.");
    }
}
