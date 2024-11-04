
package gestores;

import sistemasexternos.PoliticasContrasenia;
import sistemasexternos.SistemaContrasenia;

public class GestorServiciosExternos {
    
    private SistemaContrasenia sistemaContrasenia = new SistemaContrasenia();
    
    private static GestorServiciosExternos instancia;
    
    private GestorServiciosExternos(){}
    
    public static GestorServiciosExternos obtenerInstancia() {
        if(instancia == null){
            instancia = new GestorServiciosExternos();
        }
        return instancia;
    }
    
    public PoliticasContrasenia obtenerPoliticasContrasenia() {
        
        return sistemaContrasenia.politicasContrasenia;
    }
    
}
