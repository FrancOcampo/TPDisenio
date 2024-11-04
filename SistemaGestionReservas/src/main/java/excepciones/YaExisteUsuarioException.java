
package excepciones;

public class YaExisteUsuarioException extends Exception {
    
    public YaExisteUsuarioException() {
        super("Ya existe un usuario con ese identificador.");
    }
}
