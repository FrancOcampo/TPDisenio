
package daos;
import dtos.BedelDTO;
import dtos.BedelGeneralDTO;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.Bedel;

public class BedelPostgreSQLDAO implements GestorBedelDAO {

    private static BedelPostgreSQLDAO instancia;
    
    
    private BedelPostgreSQLDAO(){}
    
    public static BedelPostgreSQLDAO obtenerInstancia() {
        if(instancia == null){
           instancia = new BedelPostgreSQLDAO();
        }
        return instancia;
    }
    
    public boolean registrarBedel(Bedel bedel)  {
       EntityManager em = Conexion.getEntityManager();
       EntityTransaction transaccion = em.getTransaction();
       
        try {
            transaccion.begin(); 
            em.persist(bedel); 
            transaccion.commit(); 
            return true; 
            
        } catch (Exception e) {
            if (transaccion.isActive()) {
                transaccion.rollback(); 
            }
            e.printStackTrace();
            return false; 
            
        } finally {
            Conexion.closeEntityManager(); 
        }
    }
    
    public Bedel obtenerBedel(BedelDTO bedelDTO) {
       EntityManager em = Conexion.getEntityManager();
       EntityTransaction transaccion = em.getTransaction();
       Bedel bedel = null;

       try {
            String jpql = "SELECT b FROM Bedel b WHERE b.nombreUsuario = :nombreUsuario";
            TypedQuery<Bedel> query = em.createQuery(jpql, Bedel.class);
            query.setParameter("nombreUsuario", bedelDTO.getNombreUsuario());
            
            bedel = query.getResultList().isEmpty() ? null : query.getResultList().get(0);
            
        } catch(PersistenceException e) {
            e.printStackTrace(); 
            
        } finally {
           Conexion.closeEntityManager();
        }
       
       return bedel;
    }
    
    public void modificarBedel(Bedel bedel) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public List<Bedel> buscarBedeles(BedelGeneralDTO bedelGeneralDTO) {
        
        EntityManager em = Conexion.getEntityManager();
        EntityTransaction transaccion = em.getTransaction();
        List<Bedel> bedeles = null;
        
        try {
           
            String jpql = "SELECT b FROM Bedel b " +
                          "WHERE (:apellidoPrefix IS NULL OR b.apellido LIKE :apellidoPrefix) " +
                          "AND (:turnos IS NULL OR b.turno IN :turnos)";
            
            TypedQuery<Bedel> query = em.createQuery(jpql, Bedel.class);
            
            // Establecer el prefijo para el apellido
            String apellidoPrefix = bedelGeneralDTO.getApellido() != null ? bedelGeneralDTO.getApellido() + "%" : null;
            query.setParameter("apellidoPrefix", apellidoPrefix);

            // Establecer los turnos
            List<String> turnos = bedelGeneralDTO.getTurno();
            query.setParameter("turnos", turnos);

            bedeles = query.getResultList();
        
        } catch (PersistenceException e) {
            e.printStackTrace(); 
            
        } finally {
            Conexion.closeEntityManager();
        }

        return bedeles; 
    }

   
    
    
}
