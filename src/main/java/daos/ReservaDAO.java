
package daos;

import dtos.DatosBusquedaDTO;
import excepciones.ErrorException;
import excepciones.OperacionException;
import java.util.List;
import model.Reserva;
import model.ReservaParcial;

public interface ReservaDAO {
    
    public List<ReservaParcial> obtener_RP_solapadas(DatosBusquedaDTO datosBusquedaDTO) throws ErrorException;
    public Reserva obtenerReserva(int id) throws ErrorException;
    public List<ReservaParcial> verificarDisponibilidad(List<ReservaParcial> reservasParciales) throws ErrorException;
    public void registrarReserva(Reserva reserva) throws OperacionException;
    
}
