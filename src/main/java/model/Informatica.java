
package model;

import jakarta.persistence.Entity;

@Entity
public class Informatica extends Aula {
    
    private int cantidad_pc;

    public int getCantidadPC() {
        return cantidad_pc;
    }

    public void setCantidadPC(int cantidadPC) {
        this.cantidad_pc = cantidadPC;
    }
    
}
