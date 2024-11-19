
package model;

public abstract class Aula {
    
    protected int id_aula;
    protected int numero_aula;
    protected String nombre;
    protected int capacidad;
    protected boolean habilitada;
    protected int piso;
    protected boolean canion;
    protected String tipoPizarron;
    protected boolean ventiladores;
    protected boolean aireAcondicionado;

    public int getId_aula() {
        return id_aula;
    }

    public void setId_aula(int id_aula) {
        this.id_aula = id_aula;
    }

    public int getNumero_aula() {
        return numero_aula;
    }

    public void setNumero_aula(int numero_aula) {
        this.numero_aula = numero_aula;
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
        return tipoPizarron;
    }

    public void setTipoPizarron(String tipoPizarron) {
        this.tipoPizarron = tipoPizarron;
    }

    public boolean ventiladores() {
        return ventiladores;
    }

    public void setVentiladores(boolean ventiladores) {
        this.ventiladores = ventiladores;
    }

    public boolean aireAcondicionado() {
        return aireAcondicionado;
    }

    public void setAireAcondicionado(boolean aireAcondicionado) {
        this.aireAcondicionado = aireAcondicionado;
    }
    
}
