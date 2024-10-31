
package daos;
import model.Bedel;
import dtos.BedelDTO;
import excepciones.YaExisteUsuarioException;
import java.util.List;

public interface GestorBedelDAO {
    public boolean registrarBedel(Bedel bedel) throws YaExisteUsuarioException;
    public void modificarBedel(Bedel bedel);
    public List<BedelDTO> buscarBedeles(BedelDTO bedelDTO);
    public Bedel obtenerBedel(BedelDTO bedelDTO);
}
