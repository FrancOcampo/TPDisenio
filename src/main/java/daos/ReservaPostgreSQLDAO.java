
package daos;

import dtos.DatosBusquedaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import model.Reserva;
import model.ReservaParcial;

public class ReservaPostgreSQLDAO implements ReservaDAO {
    
    private static ReservaPostgreSQLDAO instancia;
    
    private ReservaPostgreSQLDAO(){}
    
    public static ReservaPostgreSQLDAO obtenerInstancia() {
        if(instancia == null){
           instancia = new ReservaPostgreSQLDAO();
        }
        return instancia;
    }
    
    public List<ReservaParcial> obtener_RP_solapadas(DatosBusquedaDTO datosBusquedaDTO) {
        
        List<ReservaParcial> reservasParciales = new ArrayList<>();  
        try {
            
            EntityManager em = Conexion.getEntityManager();

            String queryStr = "SELECT rp FROM ReservaParcial rp " +
                              "JOIN rp.reserva r " + 
                              "JOIN rp.aula a " +    
                              "WHERE a.tipoAula = :tipoAula " + 
                              "AND rp.fecha IN :fechas " + 
                              "AND r.alumnos = :alumnos " + 
                              "AND rp.horaInicio < :horaFin " + 
                              "AND rp.horaFin > :horaInicio"; 

            TypedQuery<ReservaParcial> query = em.createQuery(queryStr, ReservaParcial.class);
            query.setParameter("tipoAula", datosBusquedaDTO.getTipo_aula());
            query.setParameter("fechas", datosBusquedaDTO.getListaFechas());
            query.setParameter("alumnos", datosBusquedaDTO.getAlumnos());
            query.setParameter("horaInicio", datosBusquedaDTO.getHora_inicio());
            query.setParameter("horaFin", datosBusquedaDTO.getHora_fin());

            reservasParciales = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();  
        } finally {
            Conexion.closeEntityManager(); 
        }

        return reservasParciales;  
    }

    
    public Reserva obtenerReserva(int id) {
        
        EntityManager em = Conexion.getEntityManager();
        
        String queryStr = "SELECT r FROM Reserva r WHERE r.idReserva = :id";
        
        TypedQuery<Reserva> query = em.createQuery(queryStr, Reserva.class);
        query.setParameter("id", id);  

        Reserva reserva = null;
        
        try {
            
            reserva = query.getSingleResult();  
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        finally {
            Conexion.closeEntityManager(); 
        }

        return reserva;
    }
  }

