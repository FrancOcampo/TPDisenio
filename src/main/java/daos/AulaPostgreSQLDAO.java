
package daos;

import dtos.DatosBusquedaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import model.Aula;

public class AulaPostgreSQLDAO implements AulaDAO {
    
    private static AulaPostgreSQLDAO instancia;
    
    private AulaPostgreSQLDAO(){}
    
    public static AulaPostgreSQLDAO obtenerInstancia() {
        if(instancia == null){
           instancia = new AulaPostgreSQLDAO();
        }
        return instancia;
    }
    
    public List<Aula> obtenerOtrasAulas(List<Integer> listaIdAulasSolapadas, DatosBusquedaDTO datosBusquedaDTO) {
        
        EntityManager em = Conexion.getEntityManager();
        List<Aula> otrasAulas = new ArrayList<>(); 
        
        try {
            String queryStr = "SELECT a FROM Aula a " +
                              "WHERE a.id_aula NOT IN :listaIdAulasSolapadas " +  
                              "AND a.tipoAula = :tipoAula " +                     
                              "AND a.capacidad >= :alumnos";                      

            TypedQuery<Aula> query = em.createQuery(queryStr, Aula.class);

            query.setParameter("listaIdAulasSolapadas", listaIdAulasSolapadas);  
            query.setParameter("tipoAula", datosBusquedaDTO.getTipo_aula());     
            query.setParameter("alumnos", datosBusquedaDTO.getAlumnos());        

            otrasAulas = query.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();  
        } finally {
            Conexion.closeEntityManager();  
        }

        return otrasAulas;
    }

}
