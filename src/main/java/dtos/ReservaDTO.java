
package dtos;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReservaDTO {
    
    private int id_reserva;
    private String nombre_docente;
    private String email_docente;
    private String nombre_catedra;
    private LocalDate fecha_reserva;
    private String tipo_reserva;
    private ArrayList<ReservaParcialDTO> reservasParcialesDTO;
    private String periodo;
    private int id_bedel;
    
    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getNombre_docente() {
        return nombre_docente;
    }

    public void setNombre_docente(String nombre_docente) {
        this.nombre_docente = nombre_docente;
    }

    public String getEmail_docente() {
        return email_docente;
    }

    public void setEmail_docente(String email_docente) {
        this.email_docente = email_docente;
    }

    public String getNombre_catedra() {
        return nombre_catedra;
    }

    public void setNombre_catedra(String nombre_catedra) {
        this.nombre_catedra = nombre_catedra;
    }

    public LocalDate getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(LocalDate fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public String getTipo_reserva() {
        return tipo_reserva;
    }

    public void setTipo_reserva(String tipo_reserva) {
        this.tipo_reserva = tipo_reserva;
    }

    public ArrayList<ReservaParcialDTO> getReservasParcialesDTO() {
        return reservasParcialesDTO;
    }

    public void setReservasParcialesDTO(ArrayList<ReservaParcialDTO> reservasParcialesDTO) {
        this.reservasParcialesDTO = reservasParcialesDTO;
    }

    public int getId_bedel() {
        return id_bedel;
    }

    public void setId_bedel(int id_bedel) {
        this.id_bedel = id_bedel;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
}
