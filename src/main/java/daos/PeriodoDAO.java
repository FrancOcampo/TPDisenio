
package daos;

import dtos.PeriodoDTO;
import modelo.Periodo;

public interface PeriodoDAO {
    
    public Periodo obtenerPeriodo(PeriodoDTO periodoDTO);
}
