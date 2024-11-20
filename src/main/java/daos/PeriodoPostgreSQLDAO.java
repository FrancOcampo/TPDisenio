
package daos;

import dtos.PeriodoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Periodo;

public class PeriodoPostgreSQLDAO implements PeriodoDAO {
    
    private static PeriodoPostgreSQLDAO instancia;
    
    private PeriodoPostgreSQLDAO(){}
    
    public static PeriodoPostgreSQLDAO obtenerInstancia() {
        if(instancia == null){
           instancia = new PeriodoPostgreSQLDAO();
        }
        return instancia;
    }
    
    public Periodo obtenerPeriodo(PeriodoDTO periodoDTO) {
        
    EntityManager em = Conexion.getEntityManager();
    EntityTransaction transaccion = em.getTransaction();
    
    try {
        
        String jpql = "SELECT p FROM Periodo p WHERE p.tipo = :tipo_periodo AND p.anio_lectivo = :anio_lectivo";
        Periodo periodo = em.createQuery(jpql, Periodo.class)
                             .setParameter("tipo_periodo", periodoDTO.getTipo_periodo())
                             .setParameter("anio_lectivo", periodoDTO.getAnio_lectivo())
                             .getSingleResult(); 
        
        return periodo; 
        
    } catch (Exception e) {
        e.printStackTrace(); 
        return null;
        
    } finally {
        Conexion.closeEntityManager(); 
    }
      
  }
    
    

}
    