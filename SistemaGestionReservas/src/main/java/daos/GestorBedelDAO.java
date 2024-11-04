
package daos;
import model.Bedel;
import dtos.BedelDTO;
import dtos.BedelGeneralDTO;
import java.util.ArrayList;
import java.util.List;

public interface GestorBedelDAO {
    public boolean registrarBedel(Bedel bedel);
    public void modificarBedel(Bedel bedel);
    public List<Bedel> buscarBedeles(BedelGeneralDTO bedelDTO);
    public List<Bedel> buscarBedelesApellido(String apellido);
    public List<Bedel> buscarBedelesTurno(ArrayList<String> turnos);
    public Bedel obtenerBedel(BedelDTO bedelDTO);
    
}
