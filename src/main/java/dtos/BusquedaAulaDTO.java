
package dtos;

import java.sql.Time;
import java.time.LocalDate;

public class BusquedaAulaDTO {
    
    private int alumnos;
    private String tipo_aula; 
    private String tipo_reserva;
    private LocalDate fecha;
    private String periodo; 
    private String dia;
    private Time hora_inicio;
    private Time hora_fin;

    public int getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(int alumnos) {
        this.alumnos = alumnos;
    }

    public String getTipo_aula() {
        return tipo_aula;
    }

    public void setTipo_aula(String tipo_aula) {
        this.tipo_aula = tipo_aula;
    }

    public String getTipo_reserva() {
        return tipo_reserva;
    }

    public void setTipo_reserva(String tipo_reserva) {
        this.tipo_reserva = tipo_reserva;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
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
    
    
}
