
package daos;
import dtos.BedelDTO;
import excepciones.YaExisteUsuarioException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManager;
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

       TypedQuery<Bedel> query = em.createQuery("SELECT b FROM Bedel b WHERE b.nombreUsuario = :nombreUsuario", Bedel.class);
       query.setParameter("nombreUsuario", bedelDTO.getNombreUsuario());
            
        if (!query.getResultList().isEmpty()) return query.getResultList().getFirst();
        else return null;
    }

    public void modificarBedel(Bedel bedel) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public List<BedelDTO> buscarBedeles(BedelDTO bedelDTO) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

   
    
    
}
