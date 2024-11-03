
package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity // Marca la clase como una entidad persistente
@Table(name = "bedel") // Nombre de la tabla en la base de datos
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
