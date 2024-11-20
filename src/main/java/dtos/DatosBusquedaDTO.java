
package dtos;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class DatosBusquedaDTO {
    
    private int alumnos;
    private String tipo_aula;
    private Class<?> tipoAula;
    private List<Date> listaFechas; 
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

    public List<Date> getListaFechas() {
        return listaFechas;
    }

    public void setListaFechas(List<Date> listaFechas) {
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
