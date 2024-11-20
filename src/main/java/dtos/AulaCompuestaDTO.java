
package dtos;

import java.util.List;

public class AulaCompuestaDTO {
    
    private List<AulaDisponibleDTO> aulasDisponiblesDTO;
    private List<AulaSolapadaDTO> aulasSolapadasDTO;

    public List<AulaDisponibleDTO> getAulasDisponiblesDTO() {
        return aulasDisponiblesDTO;
    }

    public void setAulasDisponiblesDTO(List<AulaDisponibleDTO> aulasDisponiblesDTO) {
        this.aulasDisponiblesDTO = aulasDisponiblesDTO;
    }

    public List<AulaSolapadaDTO> getAulasSolapadasDTO() {
        return aulasSolapadasDTO;
    }

    public void setAulasSolapadasDTO(List<AulaSolapadaDTO> aulasSolapadasDTO) {
        this.aulasSolapadasDTO = aulasSolapadasDTO;
    }
    
    
}
