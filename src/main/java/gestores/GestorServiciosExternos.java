
package gestores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import sistemasexternos.Catedra;
import sistemasexternos.Docente;
import sistemasexternos.PoliticasContrasenia;
import sistemasexternos.SistemaCatedras;
import sistemasexternos.SistemaContrasenia;
import sistemasexternos.SistemaDocentes;

public class GestorServiciosExternos {
    
    private static GestorServiciosExternos instancia;
    
    private GestorServiciosExternos(){}
    
    public static GestorServiciosExternos obtenerInstancia() {
        if(instancia == null){
            instancia = new GestorServiciosExternos();
        }
        return instancia;
    }
    
    public PoliticasContrasenia obtenerPoliticasContrasenia() {
        
        return SistemaContrasenia.politicasContrasenia;
    }
    
    public ArrayList<Docente> listarDocentes() {
        
        ArrayList<Docente> docentes = SistemaDocentes.obtenerDocentes();
        
        Collections.sort(docentes, new Comparator<Docente>() {
            public int compare(Docente d1, Docente d2) {
                return d1.getApellido().compareTo(d2.getApellido());
            }
        });
        
        return docentes;
    }
    
    public ArrayList<Catedra> listarCatedras() {
        
        ArrayList<Catedra> catedras = SistemaCatedras.obtenerCatedras();
        
        Collections.sort(catedras, new Comparator<Catedra>() {
            public int compare(Catedra c1, Catedra c2) {
                return c1.getNombre().compareTo(c2.getNombre());
            }
        });
        
        return catedras;
    }
    
}
