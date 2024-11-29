
package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity // Marca la clase como una entidad persistente
@Table(name = "reserva") // Nombre de la tabla en la base de datos
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_reserva;
    
    private String nombre_docente;
    private String email_docente;
    private String nombre_catedra;
    private Date fecha_reserva;
    private String tipo_reserva;
    
    @OneToMany(mappedBy = "id_reserva", cascade = CascadeType.ALL)
    private List<ReservaParcial> reservasParciales;
    
    @ManyToOne
    @JoinColumn(name = "id_periodo") // Columna que referencia al periodo
    private Periodo periodo;
    
    @ManyToOne
    @JoinColumn(name = "id_bedel") // Columna que referencia al bedel
    private Bedel bedel;

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

    public List<ReservaParcial> getReservasParciales() {
        return reservasParciales;
    }

    public void setReservasParciales(List<ReservaParcial> reservasParciales) {
        this.reservasParciales = reservasParciales;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Bedel getBedel() {
        return bedel;
    }

    public void setBedel(Bedel bedel) {
        this.bedel = bedel;
    }
    
}
