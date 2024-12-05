
package dtos;

import java.util.List;

public class BedelGeneralDTO {
    
    private String apellido;
    private List<String> turnos;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<String> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<String> turno) {
        this.turnos = turno;
    }
    
}
