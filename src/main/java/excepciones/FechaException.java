
package excepciones;

public class FechaException extends Exception {
    
    public FechaException() {
        super("La fecha no está dentro de las fechas de cursado.");
    }
}
