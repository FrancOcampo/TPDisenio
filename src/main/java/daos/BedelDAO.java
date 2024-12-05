
package daos;
import model.Bedel;
import dtos.BedelDTO;
import dtos.BedelGeneralDTO;
import excepciones.ErrorException;
import excepciones.OperacionException;
import java.util.List;

public interface BedelDAO {
    
    public void registrarBedel(Bedel bedel) throws OperacionException;
    public void modificarBedel(Bedel bedel) throws OperacionException;
    public List<Bedel> buscarBedeles(BedelGeneralDTO bedelDTO) throws ErrorException;
    public Bedel obtenerBedel(BedelDTO bedelDTO);
    public Bedel obtenerBedel(int id_bedel);
    
}
