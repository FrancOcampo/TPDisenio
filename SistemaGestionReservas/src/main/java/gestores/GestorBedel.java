
package gestores;
import dtos.BedelDTO;
import model.Bedel;
import daos.BedelPostgreSQLDAO;
import dtos.BedelBusquedaDTO;
import dtos.BedelGeneralDTO;
import excepciones.PoliticasContraseniaException;
import excepciones.YaExisteUsuarioException;
import java.util.ArrayList;
import java.util.List;
import sistemasexternos.PoliticasContrasenia;

public class GestorBedel {
    
    private static GestorBedel instancia;
    
    private GestorBedel(){}
    
    public static GestorBedel obtenerInstancia() {
        if(instancia == null){
            instancia = new GestorBedel();
        }
        return instancia;
    }
    
    public boolean registrarBedel(BedelDTO bedelDTO) throws PoliticasContraseniaException, YaExisteUsuarioException {
        
            validarDatosRegistro(bedelDTO);
        
            Bedel bedel = new Bedel();
            bedel.setNombre(bedelDTO.getNombre());
            bedel.setApellido(bedelDTO.getApellido());
            bedel.setNombreUsuario(bedelDTO.getNombreUsuario());
            bedel.setContrasenia(bedelDTO.getContrasenia());
            bedel.setTurno(bedelDTO.getTurno());
            bedel.setActivo(true);
        
            BedelPostgreSQLDAO bedelPostgreSQLDAO = BedelPostgreSQLDAO.obtenerInstancia();
            
            return bedelPostgreSQLDAO.registrarBedel(bedel);
    }
    
    public boolean validarDatosRegistro(BedelDTO bedelDTO) throws YaExisteUsuarioException, PoliticasContraseniaException {
        
        BedelPostgreSQLDAO bedelPostgreSQLDAO = BedelPostgreSQLDAO.obtenerInstancia();
        
        // Se verifica que no exista un usuario con ese identificador
        if (bedelPostgreSQLDAO.obtenerBedel(bedelDTO) != null) throw new YaExisteUsuarioException();
        
        GestorServiciosExternos gse = GestorServiciosExternos.obtenerInstancia();
        PoliticasContrasenia politicasContrasenia = gse.obtenerPoliticasContrasenia();
        String contrasenia = bedelDTO.getContrasenia();
        List<String> mensajes = new ArrayList<>();
        
        // Se verifica que la contraseña cumpla con las políticas
        
        // Verificar longitud mínima
        if (contrasenia.length() < politicasContrasenia.getLongitudMinima()) {
            mensajes.add("- Debe tener al menos " + politicasContrasenia.getLongitudMinima() + " caracteres.");
        }
    
        // Si requiere al menos una mayúscula
        if (politicasContrasenia.getRequiereMayusculas() && !contrasenia.matches(".*[A-Z].*")) {
            mensajes.add("- Debe contener al menos una letra mayúscula.");
        }

        // Si requiere al menos una minúscula
        if (politicasContrasenia.getRequiereMinusculas() && !contrasenia.matches(".*[a-z].*")) {
            mensajes.add("- Debe contener al menos una letra minúscula.");
        }

        // Si requiere al menos un número
        if (politicasContrasenia.getRequiereNumeros() && !contrasenia.matches(".*\\d.*")) {
            mensajes.add("- Debe contener al menos un número.");
        }
        
        if (!mensajes.isEmpty()) {
            throw new PoliticasContraseniaException(mensajes);
        }

        // Si se llega hasta acá, no existe un usuario con ese identificador y la contraseña es válida
        return true;
    }
    
    private BedelBusquedaDTO mapBedel_a_BedelBusquedaDTO(Bedel bedel) {
        
        BedelBusquedaDTO bedelBusquedaDTO = new BedelBusquedaDTO();
        bedelBusquedaDTO.setNombreUsuario(bedel.getNombreUsuario());
        bedelBusquedaDTO.setNombre(bedel.getNombre());
        bedelBusquedaDTO.setApellido(bedel.getApellido());
        bedelBusquedaDTO.setTurno(bedel.getTurno());
        
        return bedelBusquedaDTO;
    }
    
    public List<BedelBusquedaDTO> buscarBedeles(BedelGeneralDTO bedelGeneralDTO) {
        
        BedelPostgreSQLDAO bedelPostgreSQLDAO = BedelPostgreSQLDAO.obtenerInstancia();
        
        List<Bedel> bedeles = bedelPostgreSQLDAO.buscarBedeles(bedelGeneralDTO);
        List<BedelBusquedaDTO> listaBedeles = new ArrayList<>();
        
        for (Bedel bedel : bedeles) {
            listaBedeles.add(mapBedel_a_BedelBusquedaDTO(bedel));
        }
        
        return listaBedeles;
    }
}
