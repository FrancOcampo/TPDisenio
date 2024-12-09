
package daos;

import dtos.DatosBusquedaDTO;
import excepciones.ErrorException;
import excepciones.OperacionException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import modelo.Reserva;
import modelo.ReservaParcial;

public class ReservaPostgreSQLDAO implements ReservaDAO {
    
    private static ReservaPostgreSQLDAO instancia;
    
    private ReservaPostgreSQLDAO(){}
    
    public static ReservaPostgreSQLDAO obtenerInstancia() {
        if(instancia == null){
           instancia = new ReservaPostgreSQLDAO();
        }
        return instancia;
    }
    
    public List<ReservaParcial> obtener_RP_solapadas(DatosBusquedaDTO datosBusquedaDTO) throws ErrorException {
        
        List<ReservaParcial> reservasParciales = new ArrayList<>();  
        
        try {
            EntityManager em = Conexion.getEntityManager();

            String queryStr =   "SELECT rp FROM ReservaParcial rp " +
                                "JOIN rp.aula a " +  
                                "JOIN rp.reserva r " +  
                                "WHERE TYPE(a) = :tipoAula " +  
                                "AND a.capacidad >= :alumnos " +
                                "AND rp.fecha IN :fechas " + 
                                "AND rp.hora_inicio < :horaFin " + 
                                "AND rp.hora_fin > :horaInicio"; 

            TypedQuery<ReservaParcial> query = em.createQuery(queryStr, ReservaParcial.class);
            query.setParameter("tipoAula", datosBusquedaDTO.getTipoAula());
            query.setParameter("fechas", datosBusquedaDTO.getListaFechas());
            query.setParameter("alumnos", datosBusquedaDTO.getAlumnos());
            query.setParameter("horaInicio", datosBusquedaDTO.getHora_inicio());
            query.setParameter("horaFin", datosBusquedaDTO.getHora_fin());

            reservasParciales = query.getResultList();

        } catch(Exception e) {
            throw new ErrorException();
            
        } finally {
            Conexion.closeEntityManager(); 
        }

        return reservasParciales;  
    }

    public Reserva obtenerReserva(int id) throws ErrorException {
        
        EntityManager em = Conexion.getEntityManager();
        
        String queryStr = "SELECT r FROM Reserva r WHERE r.id_reserva = :id";
        
        TypedQuery<Reserva> query = em.createQuery(queryStr, Reserva.class);
        query.setParameter("id", id);  

        Reserva reserva = null;
        
        try {
            reserva = query.getSingleResult();  
            
        } catch(Exception e) {
            throw new ErrorException();
            
        } finally {
            Conexion.closeEntityManager(); 
        }

        return reserva;
    }

    public List<ReservaParcial> verificarDisponibilidad(List<ReservaParcial> reservasParciales) throws ErrorException {
        
        EntityManager em = Conexion.getEntityManager();
        List<ReservaParcial> reservasConConflicto = new ArrayList<>();

        try {
            for (ReservaParcial reserva : reservasParciales) {
                
                String queryStr = "SELECT rp FROM ReservaParcial rp " +
                                  "WHERE rp.aula.id_aula = :idAula " +
                                  "AND rp.fecha = :fecha " +
                                  "AND rp.hora_inicio < :horaFin " + // Termina después de que comienza otra
                                  "AND rp.hora_fin > :horaInicio";  // Comienza antes de que termine otra

                TypedQuery<ReservaParcial> query = em.createQuery(queryStr, ReservaParcial.class);
                query.setParameter("idAula", reserva.getAula().getId_aula());
                query.setParameter("fecha", reserva.getFecha());
                query.setParameter("horaInicio", reserva.getHora_inicio());
                query.setParameter("horaFin", reserva.getHora_fin());

                reservasConConflicto.addAll(query.getResultList());
            }
            
        } catch(Exception e) {
            throw new ErrorException();
            
        } finally {
            Conexion.closeEntityManager();
        }

        return reservasConConflicto; 
    }

    
    public void registrarReserva(Reserva reserva) throws OperacionException {
        
       EntityManager em = Conexion.getEntityManager();
       EntityTransaction transaccion = em.getTransaction();

       try {
            transaccion.begin();
            
            List<ReservaParcial> reservasParciales = reserva.getReservasParciales();
            reserva.setReservasParciales(null); 
    
            em.persist(reserva);

            for (ReservaParcial reservaParcial : reservasParciales) {
                reservaParcial.setReserva(reserva); 
                em.persist(reservaParcial); 
            }
            
            reserva.setReservasParciales(reservasParciales);
            
            em.merge(reserva); 

            transaccion.commit();
           
       } catch(Exception e) {
           if (transaccion.isActive()) {
               transaccion.rollback();
           }
           throw new OperacionException("Error al registrar la reserva. Por favor, vuelva a intentarlo.");
           
       } finally {
            Conexion.closeEntityManager();
       }
   }
    
}

