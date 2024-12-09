
package daos;
import dtos.BedelDTO;
import dtos.BedelGeneralDTO;
import excepciones.ErrorException;
import excepciones.OperacionException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import modelo.Bedel;

public class BedelPostgreSQLDAO implements BedelDAO {

    private static BedelPostgreSQLDAO instancia;
    
    private BedelPostgreSQLDAO(){}
    
    public static BedelPostgreSQLDAO obtenerInstancia() {
        if(instancia == null){
           instancia = new BedelPostgreSQLDAO();
        }
        return instancia;
    }
    
    public void registrarBedel(Bedel bedel) throws OperacionException {
        
       EntityManager em = Conexion.getEntityManager();
       EntityTransaction transaccion = em.getTransaction();
       
        try {
            transaccion.begin(); 
            em.persist(bedel); 
            transaccion.commit(); 
            
        } catch(Exception e) {
            if (transaccion.isActive()) {
                transaccion.rollback(); 
            }
            throw new OperacionException("Error al registrar el bedel. Por favor, vuelva a intentarlo."); 
            
        } finally {
            Conexion.closeEntityManager(); 
        }
    }
    
    public Bedel obtenerBedel(BedelDTO bedelDTO) {
        
       EntityManager em = Conexion.getEntityManager();
       EntityTransaction transaccion = em.getTransaction();

       TypedQuery<Bedel> query = em.createQuery("SELECT b FROM Bedel b WHERE b.nombreUsuario = :nombreUsuario", Bedel.class);
       query.setParameter("nombreUsuario", bedelDTO.getNombreUsuario());
            
        try {
            return query.getSingleResult();
            
        } catch(NoResultException e) {
            return null; // Retorna null si no se encuentra el bedel
        }
    }
    
    public void modificarBedel(Bedel bedel) throws OperacionException {
        
        EntityManager em = Conexion.getEntityManager();
        EntityTransaction transaccion = em.getTransaction();
        
        try {
            transaccion.begin();

            em.merge(bedel);

            transaccion.commit();
            
        } catch(Exception e) {
            if (transaccion != null) {
                transaccion.rollback(); 
            }
            throw new OperacionException("Error al guardar los cambios. Por favor, vuelva a intentarlo.");
            
        } finally {
            Conexion.closeEntityManager();
        }
        
    }

    public List<Bedel> buscarBedeles(BedelGeneralDTO bedelGeneralDTO) throws ErrorException {
        
        EntityManager em = Conexion.getEntityManager();
        List<Bedel> bedeles = new ArrayList<>();
        
        try {
            String jpql = "SELECT b FROM Bedel b WHERE b.activo = true " +
                          "AND (:apellidoPrefix IS NULL OR LOWER(b.apellido) LIKE LOWER(:apellidoPrefix || '%') " +
                          "OR LOWER(b.apellido) LIKE LOWER('% ' || :apellidoPrefix || '%')) " +
                          "AND (:turnos IS NULL OR b.turno IN :turnos)";

            TypedQuery<Bedel> query = em.createQuery(jpql, Bedel.class);

            query.setParameter("apellidoPrefix", bedelGeneralDTO.getApellido() + "%"); // Agregar % para buscar por prefijo
            query.setParameter("turnos", bedelGeneralDTO.getTurnos()); 

            bedeles = query.getResultList();
        
        } catch(PersistenceException e) {
            e.printStackTrace();
            throw new ErrorException();
            
        } finally {
            Conexion.closeEntityManager();
        }

        return bedeles; 
    }
    
    public Bedel obtenerBedel(int id_bedel) {
    
        EntityManager em = Conexion.getEntityManager();
        EntityTransaction transaccion = em.getTransaction();

        TypedQuery<Bedel> query = em.createQuery("SELECT b FROM Bedel b WHERE b.id_usuario = :idBedel", Bedel.class);
        query.setParameter("idBedel", id_bedel);

        try {
            return query.getSingleResult(); 
            
        } catch(NoResultException e) {
            return null;  
        }
    }

}
