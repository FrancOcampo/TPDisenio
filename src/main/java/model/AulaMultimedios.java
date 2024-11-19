
package model;

public class AulaMultimedios extends Aula {
    
    private boolean televisor;
    private boolean computadora;

    public boolean televisor() {
        return televisor;
    }

    public void setTelevisor(boolean televisor) {
        this.televisor = televisor;
    }

    public boolean computadora() {
        return computadora;
    }

    public void setComputadora(boolean computadora) {
        this.computadora = computadora;
    }
    
}
