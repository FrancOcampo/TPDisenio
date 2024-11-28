
package daos;

import dtos.DatosBusquedaDTO;
import java.util.List;
import model.Aula;

public interface AulaDAO {
    
    public List<Aula> obtenerOtrasAulas(List<Integer> listaIdAulasSolapadas, DatosBusquedaDTO datosBusquedaDTO);
    public Aula obtenerAula(String nombre, Class tipo);

}
