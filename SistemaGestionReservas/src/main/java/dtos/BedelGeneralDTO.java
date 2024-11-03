
package dtos;

import java.util.ArrayList;

public class BedelGeneralDTO {
    
    private String apellido;
    private ArrayList<String> turno;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public ArrayList<String> getTurno() {
        return turno;
    }

    public void setTurno(ArrayList<String> turno) {
        this.turno = turno;
    }
    
}
