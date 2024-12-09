
package daos;

import dtos.DatosBusquedaDTO;
import excepciones.ErrorException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import modelo.Aula;

public class AulaPostgreSQLDAO implements AulaDAO {
    
    private static AulaPostgreSQLDAO instancia;
    
    private AulaPostgreSQLDAO(){}
    
    public static AulaPostgreSQLDAO obtenerInstancia() {
        if(instancia == null){
           instancia = new AulaPostgreSQLDAO();
        }
        return instancia;
    }
    
    public List<Aula> obtenerOtrasAulas(List<Integer> listaIdAulasSolapadas, DatosBusquedaDTO datosBusquedaDTO) throws ErrorException {
        
        EntityManager em = Conexion.getEntityManager();
        List<Aula> otrasAulas = new ArrayList<>(); 
        
        try {
            String queryStr = "SELECT a FROM Aula a " +
                              "WHERE a.id_aula NOT IN :listaIdAulasSolapadas " + 
                              "AND a.habilitada = true " +
                              "AND TYPE(a) = :tipoAula " +                     
                              "AND a.capacidad >= :alumnos";                      

            TypedQuery<Aula> query = em.createQuery(queryStr, Aula.class);

            query.setParameter("listaIdAulasSolapadas", listaIdAulasSolapadas);  
            query.setParameter("tipoAula", datosBusquedaDTO.getTipoAula());     
            query.setParameter("alumnos", datosBusquedaDTO.getAlumnos());        

            otrasAulas = query.getResultList();
            
        } catch(Exception e) {
            throw new ErrorException();  
        } finally {
            Conexion.closeEntityManager();  
        }

        return otrasAulas;
    }
    
    public Aula obtenerAula(String nombre, Class tipo) throws ErrorException {
        
        EntityManager em = Conexion.getEntityManager();
        Aula aula = null;

        try {
            String queryStr = "SELECT a FROM Aula a " +
                              "WHERE a.nombre = :nombre " +
                              "AND TYPE(a) = :tipo";

            TypedQuery<Aula> query = em.createQuery(queryStr, Aula.class);

            query.setParameter("nombre", nombre);
            query.setParameter("tipo", tipo);

            aula = query.getSingleResult();

        } catch(Exception e) {
            throw new ErrorException();   
        } finally {
            Conexion.closeEntityManager();
        }

        return aula;
    }


}
