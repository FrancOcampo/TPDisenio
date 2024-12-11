
package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity 
@Table(name = "bedel") 
public class Bedel extends Usuario {
    
    private String turno;
    private boolean activo;

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
