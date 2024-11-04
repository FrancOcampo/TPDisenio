
package daos;
import dtos.BedelDTO;
import dtos.BedelGeneralDTO;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
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
    
    public boolean registrarBedel(Bedel bedel) {
        
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
    
    public boolean modificarBedel(Bedel bedel) {
        
        EntityManager em = Conexion.getEntityManager();
        EntityTransaction transaccion = em.getTransaction();
        
        try {
            transaccion.begin();

            em.merge(bedel);

            transaccion.commit();
            return true;
            
        } catch (Exception e) {
            if (transaccion != null) {
                transaccion.rollback(); 
            }
            e.printStackTrace();
            return false;
            
        } finally {
            Conexion.closeEntityManager();
        }
        
    }

    public List<Bedel> buscarBedeles(BedelGeneralDTO bedelGeneralDTO) {
        
        EntityManager em = Conexion.getEntityManager();
        List<Bedel> bedeles = new ArrayList<>();
        
        try {
            String jpql = "SELECT b FROM Bedel b WHERE b.activo = true " +
                          "AND b.apellido LIKE :apellidoPrefix " +
                          "AND b.turno IN :turnos";

            TypedQuery<Bedel> query = em.createQuery(jpql, Bedel.class);

            query.setParameter("apellidoPrefix", bedelGeneralDTO.getApellido() + "%"); // Agregar % para buscar por prefijo
            query.setParameter("turnos", bedelGeneralDTO.getTurnos()); 

            bedeles = query.getResultList();
        
        } catch (PersistenceException e) {
            e.printStackTrace(); 
            
        } finally {
            Conexion.closeEntityManager();
        }

        return bedeles; 
    }
    
    public List<Bedel> buscarBedelesApellido(String apellido) {
        
        EntityManager em = Conexion.getEntityManager();
        List<Bedel> bedeles = new ArrayList<>();
        
        try {
            String jpql = "SELECT b FROM Bedel b WHERE b.activo = true " +
                          "AND b.apellido LIKE :apellidoPrefix ";

            TypedQuery<Bedel> query = em.createQuery(jpql, Bedel.class);

            query.setParameter("apellidoPrefix", apellido + "%"); // Agregar % para buscar por prefijo
            
            bedeles = query.getResultList();
        
        } catch (PersistenceException e) {
            e.printStackTrace(); 
            
        } finally {
            Conexion.closeEntityManager();
        }

        return bedeles; 
    }
    
    public List<Bedel> buscarBedelesTurno(ArrayList<String> turnos) {
        
        EntityManager em = Conexion.getEntityManager();
        List<Bedel> bedeles = new ArrayList<>();
        
        try {
            String jpql = "SELECT b FROM Bedel b WHERE b.activo = true " +
                          "AND b.turno IN :turnos";

            TypedQuery<Bedel> query = em.createQuery(jpql, Bedel.class);

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
