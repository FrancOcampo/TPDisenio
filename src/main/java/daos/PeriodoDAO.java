
package daos;

import dtos.PeriodoDTO;
import model.Periodo;

public interface PeriodoDAO {
    
    public Periodo obtenerPeriodo(PeriodoDTO periodoDTO);
}
