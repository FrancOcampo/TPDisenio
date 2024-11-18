
package dtos;

import java.util.ArrayList;
import java.util.Date;

public class ReservaDTO {
    
    private int id_reserva;
    private int id_docente;
    private int id_catedra;
    private String nombre_docente;
    private String email_docente;
    private String nombre_catedra;
    private Date fecha_reserva;
    private String tipo_reserva;
    private ArrayList<ReservaParcialDTO> reservasParcialesDTO;
    private int id_bedel;
    private String periodo;
    
    public ReservaDTO() {}

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public int getId_docente() {
        return id_docente;
    }

    public void setId_docente(int id_docente) {
        this.id_docente = id_docente;
    }

    public int getId_catedra() {
        return id_catedra;
    }

    public void setId_catedra(int id_catedra) {
        this.id_catedra = id_catedra;
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

    public Date getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(Date fecha_reserva) {
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
