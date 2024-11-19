
package model;

import java.util.Date;

public class Periodo {
    
    private int id_periodo;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String tipo;
    private int anio_lectivo;
    
    public int getId_periodo() {
        return id_periodo;
    }

    public void setId_periodo(int id_periodo) {
        this.id_periodo = id_periodo;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAnio_lectivo() {
        return anio_lectivo;
    }

    public void setAnio_lectivo(int anio_lectivo) {
        this.anio_lectivo = anio_lectivo;
    }
    
}
