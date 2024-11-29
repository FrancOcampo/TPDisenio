
package sistemasexternos;

import java.util.ArrayList;

public class SistemaCatedras {
    
    public static ArrayList<Catedra> catedras;
    
    public static ArrayList<Catedra> obtenerCatedras() {
        
        catedras = new ArrayList<>();
        completarLista();
        return catedras;
    }
    
    private static void completarLista() {
        
        Catedra catedra1 = new Catedra("Análisis Matemático I");
        Catedra catedra2 = new Catedra("Análisis Matemático II");
        Catedra catedra3 = new Catedra("Diseño de Sistemas");
        Catedra catedra4 = new Catedra("Algoritmos y Estructuras de Datos");
        Catedra catedra5 = new Catedra("Arquitectura de Computadoras");
        Catedra catedra6 = new Catedra("Análisis de Sistemas");
        Catedra catedra7 = new Catedra("Sistemas Operativos");
        Catedra catedra8 = new Catedra("Paradigmas de Programación");
        Catedra catedra9 = new Catedra("Comunicaciones de Datos");
        Catedra catedra10 = new Catedra("Bases de Datos");
        
        catedras.add(catedra1);
        catedras.add(catedra2);
        catedras.add(catedra3);
        catedras.add(catedra4);
        catedras.add(catedra5);
        catedras.add(catedra6);
        catedras.add(catedra7);
        catedras.add(catedra8);
        catedras.add(catedra9);
        catedras.add(catedra10);
        
    }
    
    
}
