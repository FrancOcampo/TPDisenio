
package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Objects;

@Entity  
@Table(name = "reserva_parcial")  
public class ReservaParcial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int id_reserva_parcial;
    private int duracion;
    private Time hora_inicio;
    private Time hora_fin;
    private LocalDate fecha;
    
    @ManyToOne
    @JoinColumn(name = "id_aula")
    private Aula aula; 
    
    @ManyToOne
    @JoinColumn(name = "id_reserva")  
    private Reserva reserva;  
  
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReservaParcial rp = (ReservaParcial) obj;
        return Objects.equals(reserva, rp.reserva) &&
               Objects.equals(hora_inicio, rp.hora_inicio) &&
               Objects.equals(hora_fin, rp.hora_fin) &&
               Objects.equals(aula, rp.aula);
    }
    
    public int hashCode() {
        return Objects.hash(reserva, hora_inicio, hora_fin, aula);
    }
    
}
