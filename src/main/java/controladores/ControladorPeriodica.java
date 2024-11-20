
package controladores;

import daos.Conexion;
import dtos.AulaCompuestaDTO;
import dtos.AulaDisponibleDTO;
import dtos.AulaSolapadaDTO;
import dtos.BusquedaAulaDTO;
import dtos.ReservaDTO;
import excepciones.DatosInvalidosException;
import gestores.GestorReserva;
import interfaces.InterfazAulasDisponibles;
import interfaces.InterfazAulasSolapadas;
import interfaces.InterfazReservaPeriodica;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ControladorPeriodica implements ActionListener {
    
    private InterfazReservaPeriodica irp;
    ReservaDTO reservaDTO;
    
    public ControladorPeriodica() {}
    
    public ControladorPeriodica(InterfazReservaPeriodica irp) {
        
        this.irp = irp;
        irp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        irp.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Cerrar conexión y salir
                Conexion.closeEntityManagerFactory();
                irp.dispose();
            }
        });
    }
    
    public void setDatos(ReservaDTO reservaDTO) {
        this.reservaDTO = reservaDTO;
    }
    
    public void actionPerformed(ActionEvent e) {
        
        String comando = e.getActionCommand();
        
        if(comando.equals("Confirmar día")) {
            
            try {
                irp.desmarcarCampos();
                if(!validarCampos()) throw new DatosInvalidosException();
                
                BusquedaAulaDTO busquedaAulaDTO = new BusquedaAulaDTO();
                busquedaAulaDTO.setAlumnos(Integer.parseInt(irp.getCampoCantidadAlumnos().getText()));
                busquedaAulaDTO.setTipo_aula(irp.getTipoAula());
                busquedaAulaDTO.setTipo_reserva("Periódica");
                busquedaAulaDTO.setDia(irp.getDia());
                busquedaAulaDTO.setHora_inicio(getHoraAsTime(irp.getHoraInicio()));
                busquedaAulaDTO.setHora_fin(getHoraAsTime(irp.getHoraFin()));
                
                GestorReserva gr = GestorReserva.obtenerInstancia();
                AulaCompuestaDTO aulas = gr.disponerAulas(busquedaAulaDTO);
                
                if(aulas.getAulasDisponiblesDTO() != null) {
                    
                   InterfazAulasDisponibles iad = new InterfazAulasDisponibles();
                   iad.setControlador(this);
                   
                    for (AulaDisponibleDTO aula : aulas.getAulasDisponiblesDTO()) {
                       
                        Object[] row = { aula.getNombre_aula(), aula.getUbicacion(), aula.getCapacidad(), aula.getCaracteristicas() };
                        iad.getModel().addRow(row);
                    
                    }    
                }
                else {
                    
                    InterfazAulasSolapadas ias = new InterfazAulasSolapadas();
                    ias.setControlador(this);
                    
                    for (AulaSolapadaDTO aula : aulas.getAulasSolapadasDTO()) {
                        
                        Object[] row = { aula.getNombre_aula(), aula.getDocente(), aula.getCurso(), aula.getContacto(), aula.getHora_inicio(), aula.getHora_fin() };
                        ias.getModel().addRow(row);
                    }
                }
                
            } catch(DatosInvalidosException e1) {
                irp.crearPopUpAdvertencia();
                marcarCampos();
            }
            
        }
        
        else if (comando.equals("Confirmar")) {
            
        }
    
    }
    
    private boolean validarCampos() {
        
        boolean valido = true;
        
        if(irp.getCampoCantidadAlumnos().getText().equals("") ||
           !irp.getCampoCantidadAlumnos().getText().matches("\\d+") ||
           irp.getTipoAula().equals("") ||
           irp.getDia().equals("")) valido = false;
        
        return valido;
    }
    
    private void marcarCampos() {
        
        Border redBorder = new LineBorder(Color.RED, 2);
        boolean visibilidad = true;
        
        if(irp.getCampoCantidadAlumnos().getText().equals("") || !irp.getCampoCantidadAlumnos().getText().matches("\\d+")) {
            irp.setCampoCantidadAlumnos(redBorder, visibilidad);
        }
        if(irp.getTipoAula().equals("")) {
            irp.setCampoAula(redBorder, visibilidad);
        }
        if(irp.getDia().equals("")) {
            irp.setCampoDia(redBorder, visibilidad);
        }
    }
    
    private Time getHoraAsTime(String hora) {
    
        try {
            
        LocalTime localTime = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));
        
        return Time.valueOf(localTime.atDate(java.time.LocalDate.now()).toLocalTime()); 
        
        } catch (Exception e) {
            
        e.printStackTrace();
        return null;
        
        }
    }
    
    
}
