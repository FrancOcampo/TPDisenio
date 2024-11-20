
package controladores;

import daos.Conexion;
import dtos.AulaCompuestaDTO;
import dtos.AulaDisponibleDTO;
import dtos.AulaSolapadaDTO;
import dtos.BusquedaAulaDTO;
import dtos.ReservaDTO;
import excepciones.DatosInvalidosException;
import excepciones.DuracionException;
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
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ControladorPeriodica implements ActionListener {
    
    private InterfazReservaPeriodica irp;
    private InterfazAulasDisponibles iad;
    private InterfazAulasSolapadas ias;
    BusquedaAulaDTO busquedaAulaDTO;
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
                if(verificarHora(irp.getHoraInicio(), irp.getHoraFin())) {
                
                busquedaAulaDTO = new BusquedaAulaDTO();
                busquedaAulaDTO.setAlumnos(Integer.parseInt(irp.getCampoCantidadAlumnos().getText()));
                busquedaAulaDTO.setTipo_aula(irp.getTipoAula());
                busquedaAulaDTO.setTipo_reserva("Periódica");
                busquedaAulaDTO.setPeriodo(reservaDTO.getPeriodo());
                busquedaAulaDTO.setDia(irp.getDia());
                busquedaAulaDTO.setHora_inicio(getHoraAsTime(irp.getHoraInicio()));
                busquedaAulaDTO.setHora_fin(getHoraAsTime(irp.getHoraFin()));
                
                GestorReserva gr = GestorReserva.obtenerInstancia();
                AulaCompuestaDTO aulas = gr.disponerAulas(busquedaAulaDTO);
                
                if(aulas.getAulasDisponiblesDTO() != null) {
                    
                   iad = new InterfazAulasDisponibles();
                   iad.setControlador(this);
                   iad.getjLabel1().setText(reservaDTO.getNombre_catedra());
                   
                    for (AulaDisponibleDTO aula : aulas.getAulasDisponiblesDTO()) {
                       
                        Object[] row = { aula.getNombre_aula(), aula.getUbicacion(), aula.getCapacidad(), aula.getCaracteristicas() };
                        iad.getModel().addRow(row);
                    
                    }    
                }
                else {
                    
                    ias = new InterfazAulasSolapadas();
                    ias.setControlador(this);
                    
                    for (AulaSolapadaDTO aula : aulas.getAulasSolapadasDTO()) {
                        
                        Object[] row = { aula.getNombre_aula(), aula.getDocente(), aula.getCurso(), aula.getContacto(), aula.getHora_inicio(), aula.getHora_fin() };
                        ias.getModel().addRow(row);
                    }
                }
                
            }
                
            } catch(DatosInvalidosException e1) {
                irp.crearPopUpAdvertencia();
                marcarCampos();
            }
            
        }
        
        else if (comando.equals("Confirmar")) {
            
            int row = iad.getjTable1().getSelectedRow();
            
            try {
                if(row == -1) throw new DatosInvalidosException();
                else {
                    String aula = (String) iad.getModel().getValueAt(row, 0);
                    Object[] nuevaFila = { aula, busquedaAulaDTO.getTipo_aula(), reservaDTO.getNombre_catedra(), 
                                           busquedaAulaDTO.getDia(), busquedaAulaDTO.getHora_inicio(), busquedaAulaDTO.getHora_fin() };
                    irp.getModel().addRow(nuevaFila);
                    iad.dispose();
                }
                
            } catch(DatosInvalidosException e1) {
                iad.crearPopUpFila();
            }
            
        }
    
    }
    
    private boolean validarCampos() {
        
        boolean valido = true;
        
        if(irp.getCampoCantidadAlumnos().getText().equals("") ||
           !irp.getCampoCantidadAlumnos().getText().matches("\\d+") ||
           Integer.parseInt(irp.getCampoCantidadAlumnos().getText()) <= 0 ||
           irp.getTipoAula().equals("") ||
           irp.getDia().equals("")) valido = false;
        
        return valido;
    }
    
    private void marcarCampos() {
        
        Border redBorder = new LineBorder(Color.RED, 2);
        boolean visibilidad = true;
        
        if(irp.getCampoCantidadAlumnos().getText().equals("") || !irp.getCampoCantidadAlumnos().getText().matches("\\d+") || Integer.parseInt(irp.getCampoCantidadAlumnos().getText()) <= 0) {
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
    
    private boolean verificarHora(String horaInicio, String horaFin) {
        
        boolean valido = false;
        
        try {
            // Definir el formato de hora
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            
            // Parsear el String de horaInicio a Date
            Date date1 = sdf.parse(horaInicio);
            
            // Convertir Date a Time para horaInicio
            Time hora_inicio = new Time(date1.getTime());
            
            // Parsear el String de horaFin a Date
            Date date2 = sdf.parse(horaFin);
            
            // Convertir Date a Time para horaFin
            Time hora_fin = new Time(date2.getTime());
            
            // Comparar horaFin con horaInicio
            if (hora_fin.compareTo(hora_inicio) > 0) {
                
                LocalTime inicio = hora_inicio.toLocalTime();
                LocalTime fin = hora_fin.toLocalTime();

                Duration duracion = Duration.between(inicio, fin);
                long minutos = duracion.toMinutes();  // Convertir la duración a minutos

                if (minutos % 30 == 0) valido = true;
                else throw new DuracionException();
                
            } else throw new DatosInvalidosException();
            
            
        } catch (DatosInvalidosException e1) {
          irp.crearPopUpAdvertencia();  
          Border redBorder = new LineBorder(Color.RED, 2);
          irp.setCamposHora(redBorder, true);
          
        } catch (DuracionException e2) {
          irp.crearPopUpAdvertencia("La duración de la reserva no es múltiplo de 30 minutos.");
          Border redBorder = new LineBorder(Color.RED, 2);
          irp.setCamposHora(redBorder, true);
          
        } catch (Exception e) {
          e.printStackTrace();
        } 
        
        return valido;
    }
}


