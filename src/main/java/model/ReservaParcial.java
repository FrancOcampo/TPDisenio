
package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Time;
import java.util.Date;

@Entity  // Marca la clase como una entidad persistente
@Table(name = "reserva_parcial")  // Nombre de la tabla en la base de datos
public class ReservaParcial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Asumiendo que el ID se genera automáticamente
    private int id_reserva_parcial;
    
    private int duracion;
    private Time hora_inicio;
    private Time hora_fin;
    private Date fecha;
    private int id_reserva;
    
    // Relación ManyToOne con la entidad Aula
    @ManyToOne
    @JoinColumn(name = "id_aula", referencedColumnName = "id_aula")
    private Aula aula;  // El objeto de la clase Aula que representará la relación
  
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

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }
    
    
}
