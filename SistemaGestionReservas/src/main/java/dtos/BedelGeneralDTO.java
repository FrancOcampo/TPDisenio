
package dtos;

import java.util.ArrayList;

public class BedelGeneralDTO {
    
    private String apellido;
    private ArrayList<String> turnos;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public ArrayList<String> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<String> turno) {
        this.turnos = turno;
    }
    
}
