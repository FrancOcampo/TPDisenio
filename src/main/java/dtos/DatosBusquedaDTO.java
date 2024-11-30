
package dtos;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class DatosBusquedaDTO {
    
    private int alumnos;
    private Class<?> tipoAula;
    private List<LocalDate> listaFechas; 
    private Time hora_inicio;
    private Time hora_fin;

    public int getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(int alumnos) {
        this.alumnos = alumnos;
    }
    
    public List<LocalDate> getListaFechas() {
        return listaFechas;
    }

    public void setListaFechas(List<LocalDate> listaFechas) {
        this.listaFechas = listaFechas;
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

    public Class<?> getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(Class<?> tipoAula) {
        this.tipoAula = tipoAula;
    }
    
    
}
