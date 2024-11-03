
package excepciones;

import java.util.List;

public class PoliticasContraseniaException extends Exception {
    private List<String> mensajes;

    public PoliticasContraseniaException(List<String> mensajes) {
        super("Errores de validación de contraseña");
        this.mensajes = mensajes;
    }

    public List<String> getMensajes() {
        return mensajes;
    }
}
