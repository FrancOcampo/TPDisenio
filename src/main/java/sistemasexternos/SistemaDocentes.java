
package sistemasexternos;

import java.util.ArrayList;

public class SistemaDocentes {
    
    public static ArrayList<Docente> docentes;
    
    public static ArrayList<Docente> obtenerDocentes() {
        
        docentes = new ArrayList<>();
        completarLista();
        return docentes;
    }
    
    
    private static void completarLista() {
        
        Docente docente1 = new Docente("Juan", "González", "juangonzalez@gmail.com");
        Docente docente2 = new Docente("Rodolfo", "Pérez", "rodolfoperez@gmail.com");
        Docente docente3 = new Docente("Marcela", "Domínguez", "mdominguez@gmail.com");
        Docente docente4 = new Docente("Mirtha", "López", "lopezmirtha@gmail.com");
        Docente docente5 = new Docente("Pablo", "Sánchez", "pablo_sanchez@gmail.com");
        Docente docente6 = new Docente("Mauro", "Rodríguez", "mauro.rodriguez@gmail.com");
        Docente docente7 = new Docente("Sandra", "Fernández", "sfernandez@gmail.com");
        Docente docente8 = new Docente("Graciela", "Mendez", "mendezgraciela@gmail.com");
        Docente docente9 = new Docente("Cristian", "Impini", "cristianimpini@gmail.com");
        Docente docente10 = new Docente("Santiago", "Marnetto", "santiagomarnetto@gmail.com");
        
        docentes.add(docente1);
        docentes.add(docente2);
        docentes.add(docente3);
        docentes.add(docente4);
        docentes.add(docente5);
        docentes.add(docente6);
        docentes.add(docente7);
        docentes.add(docente8);
        docentes.add(docente9);
        docentes.add(docente10);
        
    }
}
