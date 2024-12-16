
package excepciones;

public class NoExisteAulaException extends Exception {
    
    public NoExisteAulaException() {
        super("No existen aulas que cumplan con los criterios especificados.");
    }
}
