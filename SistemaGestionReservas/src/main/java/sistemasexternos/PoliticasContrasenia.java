
package sistemasexternos;

public class PoliticasContrasenia {
    
    private int longitudMinima;
    private boolean requiereMayusculas; 
    private boolean requiereMinusculas;
    private boolean requiereNumeros;
    
    public PoliticasContrasenia(int longitudMinima, boolean requiereMayusculas, boolean requiereMinusculas, boolean requiereNumeros) {
        this.longitudMinima = longitudMinima;
        this.requiereMayusculas = requiereMayusculas;
        this.requiereMinusculas = requiereMinusculas;
        this.requiereNumeros = requiereNumeros;
    }
   
    public int getLongitudMinima() { 
        return longitudMinima; 
    }
    
    // requiere al menos una mayúscula
    public boolean getRequiereMayusculas() { 
        return requiereMayusculas; 
    }
    
    // requiere al menos una minúscula
    public boolean getRequiereMinusculas() { 
        return requiereMinusculas; 
    }
    
    // requiere al menos un número
    public boolean getRequiereNumeros() { 
        return requiereNumeros; 
    }
}

    
