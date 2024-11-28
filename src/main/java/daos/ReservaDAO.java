
package daos;

import dtos.DatosBusquedaDTO;
import excepciones.OperacionException;
import java.util.List;
import model.Reserva;
import model.ReservaParcial;

public interface ReservaDAO {
    
    public List<ReservaParcial> obtener_RP_solapadas(DatosBusquedaDTO datosBusquedaDTO);
    public Reserva obtenerReserva(int id);
    public List<ReservaParcial> verificarDisponibilidad(List<ReservaParcial> reservasParciales);
    public void registrarReserva(Reserva reserva) throws OperacionException;
    
}
