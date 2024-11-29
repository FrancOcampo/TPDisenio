
package daos;

import dtos.DatosBusquedaDTO;
import excepciones.ErrorException;
import java.util.List;
import model.Aula;

public interface AulaDAO {
    
    public List<Aula> obtenerOtrasAulas(List<Integer> listaIdAulasSolapadas, DatosBusquedaDTO datosBusquedaDTO) throws ErrorException;
    public Aula obtenerAula(String nombre, Class tipo) throws ErrorException;

}
