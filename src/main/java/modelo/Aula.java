
package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Usamos la estrategia JOINED
public abstract class Aula {
    
    @Id
    protected int id_aula;
    protected int nro_aula;
    protected String nombre;
    protected int capacidad;
    protected boolean habilitada;
    protected int piso;
    protected boolean canion;
    protected String tipo_pizarron;
    protected boolean ventiladores;
    protected boolean aire_acondicionado;

    public int getId_aula() {
        return id_aula;
    }

    public void setId_aula(int id_aula) {
        this.id_aula = id_aula;
    }

    public int getNumeroAula() {
        return nro_aula;
    }

    public void setNumeroAula(int numero_aula) {
        this.nro_aula = numero_aula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean habilitada() {
        return habilitada;
    }

    public void setHabilitada(boolean habilitada) {
        this.habilitada = habilitada;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public boolean canion() {
        return canion;
    }

    public void setCanion(boolean canion) {
        this.canion = canion;
    }

    public String getTipoPizarron() {
        return tipo_pizarron;
    }

    public void setTipoPizarron(String tipoPizarron) {
        this.tipo_pizarron = tipoPizarron;
    }

    public boolean ventiladores() {
        return ventiladores;
    }

    public void setVentiladores(boolean ventiladores) {
        this.ventiladores = ventiladores;
    }

    public boolean aireAcondicionado() {
        return aire_acondicionado;
    }

    public void setAireAcondicionado(boolean aireAcondicionado) {
        this.aire_acondicionado = aireAcondicionado;
    }
    
}
