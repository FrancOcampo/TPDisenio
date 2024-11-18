
package model;

import java.sql.Time;
import java.util.Date;

public class ReservaParcial {
    
    private int id_reserva_parcial;
    private int duracion;
    private Time hora_inicio;
    private Time hora_fin;
    private Date fecha;
    
    public ReservaParcial() {}

    public int getId_reserva_parcial() {
        return id_reserva_parcial;
    }

    public void setId_reserva_parcial(int id_reserva_parcial) {
        this.id_reserva_parcial = id_reserva_parcial;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Time getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Time getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(Time hora_fin) {
        this.hora_fin = hora_fin;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
