package controladores;

import daos.Conexion;
import dtos.AulaCompuestaDTO;
import dtos.AulaDisponibleDTO;
import dtos.AulaSolapadaDTO;
import dtos.BusquedaAulaDTO;
import dtos.ReservaDTO;
import excepciones.DatosInvalidosException;
import excepciones.DuracionException;
import excepciones.FechaException;
import gestores.GestorReserva;
import interfaces.InterfazAulasDisponibles;
import interfaces.InterfazAulasSolapadas;
import interfaces.InterfazIngresoDatosDocente;
import interfaces.InterfazReservaEsporadica;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ControladorEsporadica implements ActionListener {
    
    private InterfazReservaEsporadica ire;
    private InterfazAulasDisponibles iad;
    private InterfazAulasSolapadas ias;
    private BusquedaAulaDTO busquedaAulaDTO;
    private ReservaDTO reservaDTO;
    
    public ControladorEsporadica() {}
    
    public ControladorEsporadica(InterfazReservaEsporadica ire) {
        
        this.ire = ire;
        ire.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ire.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Cerrar conexión y salir
                Conexion.closeEntityManagerFactory();
                ire.dispose();
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
                ire.desmarcarCampos();
                if(!validarCampos()) throw new DatosInvalidosException();
                if(verificarHora(ire.getHoraInicio(), ire.getHoraFin())) {
                    
                busquedaAulaDTO = new BusquedaAulaDTO();
                busquedaAulaDTO.setAlumnos(Integer.parseInt(ire.getCampoCantidadAlumnos().getText()));
                busquedaAulaDTO.setTipo_aula(ire.getTipoAula());
                busquedaAulaDTO.setTipo_reserva(reservaDTO.getTipo_reserva());
                busquedaAulaDTO.setPeriodo(reservaDTO.getPeriodo());
                busquedaAulaDTO.setFecha(convertirFecha(ire.getCalendario().getDate()));
                busquedaAulaDTO.setHora_inicio(getHoraAsTime(ire.getHoraInicio()));
                busquedaAulaDTO.setHora_fin(getHoraAsTime(ire.getHoraFin()));    
             
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
                ire.crearPopUpAdvertencia("Hay campos inválidos o sin rellenar.");
                marcarCampos();
                
            } catch(FechaException e2) {
                ire.crearPopUpAdvertencia("La fecha es anterior a la actual y/o no corresponde a las fechas de cursado.");
                Border redBorder = new LineBorder(Color.RED, 2);
                ire.setCampoFecha(redBorder, true);
            }
        }
        else if(comando.equals("Registrar reserva")) {
            if(ire.getModel().getRowCount() > 0) {
                int confirmacion = ire.confirmarContinuacion("¿Está seguro de que desea registrar la reserva?");
                if(confirmacion == JOptionPane.OK_OPTION) {
                    //llamar al metodo registrarReserva() que debería tirar una excepción en caso de inconsistencia
                    ire.crearPopUpExito();
                    ire.setearCamposEnBlanco();
                }
            }
            else ire.crearPopUpAdvertencia("La reserva está vacía. Por favor, realice al menos una subreserva.");
        }
        else if(comando.equals("Cancelar")) {
            
            if(hayCambios()) {
                int confirmacion = ire.confirmarContinuacion("Hay cambios sin guardar. ¿Desea continuar?");
                if(confirmacion == JOptionPane.OK_OPTION) {
                    new InterfazIngresoDatosDocente().getControlador().setearDatos(reservaDTO);
                    ire.dispose();
                }
            }
            else {
                new InterfazIngresoDatosDocente().getControlador().setearDatos(reservaDTO);
                ire.dispose();
            }
        }
        else if(comando.equals("CancelarAD")) {
            iad.dispose();
        }
        else if(comando.equals("Continuar")) {
            ias.dispose();
        }
    
    }
    
    private boolean validarCampos() {
        
        boolean valido = true;
        
        if(ire.getCampoCantidadAlumnos().getText().equals("") ||
           !ire.getCampoCantidadAlumnos().getText().matches("\\d+") ||
           Integer.parseInt(ire.getCampoCantidadAlumnos().getText()) <= 0 ||
           ire.getTipoAula().equals("") ||
           ire.getFecha().equals("")) valido = false;
        
        return valido;
    }
    
    private void marcarCampos() {
        
        Border redBorder = new LineBorder(Color.RED, 2);
        boolean visibilidad = true;
        
        if(ire.getCampoCantidadAlumnos().getText().equals("") || !ire.getCampoCantidadAlumnos().getText().matches("\\d+") || Integer.parseInt(ire.getCampoCantidadAlumnos().getText()) <= 0) {
            ire.setCampoCantidadAlumnos(redBorder, visibilidad);
        }
        if(ire.getTipoAula().equals("")) {
            ire.setCampoAula(redBorder, visibilidad);
        }
        if(ire.getFecha().equals("")) {
            ire.setCampoFecha(redBorder, visibilidad);
        }
    }

    private Date convertirFecha(Date date) {
        
        SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
    
        try {
            return formatoBD.parse(formatoBD.format(date));
            
        } catch (ParseException e) {
            throw new RuntimeException("Error al convertir la fecha", e);
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
          ire.crearPopUpAdvertencia("Hay campos inválidos o sin rellenar.");  
          Border redBorder = new LineBorder(Color.RED, 2);
          ire.setCamposHora(redBorder, true);
          
        } catch (DuracionException e2) {
          ire.crearPopUpAdvertencia("La duración de la reserva no es múltiplo de 30 minutos.");
          Border redBorder = new LineBorder(Color.RED, 2);
          ire.setCamposHora(redBorder, true);
          
        } catch (Exception e) {
          e.printStackTrace();
        } 
        
        return valido;
    }       
    
    public boolean hayCambios() {
        
        if (!ire.getCampoCantidadAlumnos().getText().equals("")) {
            return true;
        }

        if (!ire.getTipoAula().equals("")) {
            return true;
        }
        
        if (!ire.getFecha().equals("")) {
            return true;
        }

        if (!ire.getHoraInicio().equals("08:00")) {
            return true;
        }

        if (!ire.getHoraFin().equals("08:30")) {
            return true;
        }

        if(ire.getModel().getRowCount() > 0) {
            return true;
        }

        // Si ninguno de los valores cambió
        return false;
    }
    
                
    
            


}


