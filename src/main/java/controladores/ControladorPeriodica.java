
package controladores;

import daos.Conexion;
import dtos.AulaCompuestaDTO;
import dtos.AulaDisponibleDTO;
import dtos.AulaSolapadaDTO;
import dtos.BusquedaAulaDTO;
import dtos.ReservaDTO;
import dtos.ReservaParcialDTO;
import excepciones.DatosInvalidosException;
import excepciones.DuracionException;
import excepciones.ErrorException;
import excepciones.FechaException;
import excepciones.OperacionException;
import excepciones.ReservaInconsistenteException;
import gestores.GestorReserva;
import interfaces.InterfazAulasDisponibles;
import interfaces.InterfazAulasSolapadas;
import interfaces.InterfazIngresoDatosDocente;
import interfaces.InterfazReservaPeriodica;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ControladorPeriodica implements ActionListener {
    
    private InterfazReservaPeriodica irp;
    private InterfazAulasDisponibles iad;
    private InterfazAulasSolapadas ias;
    private BusquedaAulaDTO busquedaAulaDTO;
    private ReservaDTO reservaDTO;
    
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
                busquedaAulaDTO.setTipo_reserva(reservaDTO.getTipo_reserva());
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
                irp.crearPopUpAdvertencia("Hay campos inválidos o sin rellenar.");
                marcarCampos();
                
            } catch(ErrorException e2) {
                irp.crearPopUpError();
                
            } catch(FechaException e3) {}
        }
        else if (comando.equals("Confirmar")) {
            
            int row = iad.getjTable1().getSelectedRow();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            
            try {
                if(row == -1) throw new DatosInvalidosException();
                else {
                    String aula = (String) iad.getModel().getValueAt(row, 0);
                    Object[] nuevaFila = { aula, busquedaAulaDTO.getTipo_aula(), reservaDTO.getNombre_catedra(), 
                                           busquedaAulaDTO.getDia(), sdf.format((Time) busquedaAulaDTO.getHora_inicio()), sdf.format((Time) busquedaAulaDTO.getHora_fin()) };
                    irp.getModel().addRow(nuevaFila);
                    iad.dispose();
                }
                
            } catch(DatosInvalidosException e1) {
                iad.crearPopUpFila();
            }
            
        }
        else if(comando.equals("Finalizar reserva")) {
            
            if(irp.getModel().getRowCount() > 0) {
                int confirmacion = irp.confirmarContinuacion("¿Está seguro de que desea registrar la reserva?");
                if(confirmacion == JOptionPane.OK_OPTION) {
                    
                try {
                    if(subreservasRepetidas(irp.getjTable())) throw new DatosInvalidosException();
                    
                    ArrayList<ReservaParcialDTO> reservasParcialesDTO = new ArrayList<>();
                    TableModel modelo = irp.getModel();
                    
                    for(int i = 0; i < modelo.getRowCount(); i++) {
                        
                        ReservaParcialDTO reservaParcialDTO = new ReservaParcialDTO();

                        reservaParcialDTO.setNombre_aula((String) modelo.getValueAt(i, 0)); 
                        reservaParcialDTO.setTipo_aula((String) modelo.getValueAt(i, 1));
                        reservaParcialDTO.setCurso((String) modelo.getValueAt(i, 2));
                        reservaParcialDTO.setDia((String) modelo.getValueAt(i, 3)); 
                        
                        Object valorHoraInicio = modelo.getValueAt(i, 4);
                        Object valorHoraFin = modelo.getValueAt(i, 5);
                        
                        String horaInicioString = (String) valorHoraInicio;  // "HH:mm"
                        String horaFinString = (String) valorHoraFin;        // "HH:mm"

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
                        Date horaInicioDate = sdf.parse(horaInicioString);
                        Date horaFinDate = sdf.parse(horaFinString);

                        Time horaInicio = new Time(horaInicioDate.getTime());
                        Time horaFin = new Time(horaFinDate.getTime());

                        reservaParcialDTO.setHora_inicio(horaInicio);
                        reservaParcialDTO.setHora_fin(horaFin);
                        
                        long duracion = calcularDuracion(reservaParcialDTO.getHora_inicio(), reservaParcialDTO.getHora_fin());
                        reservaParcialDTO.setDuracion((int)duracion);
                        
                        reservasParcialesDTO.add(reservaParcialDTO);
                    }
                    
                    reservaDTO.setReservasParcialesDTO(reservasParcialesDTO);
                    GestorReserva.obtenerInstancia().registrarReserva(reservaDTO);
                    
                    irp.crearPopUpExito();
                    new InterfazIngresoDatosDocente().getControlador().completarDatos();
                    irp.dispose();
                    
                    } catch(DatosInvalidosException e1) {
                        irp.crearPopUpAdvertencia("La reserva contiene subreservas repetidas.");
                        
                    } catch(ParseException e2) {
                        irp.crearPopUpFracaso();
                        
                    } catch(ErrorException e3) {
                        irp.crearPopUpError();
                        
                    } catch(ReservaInconsistenteException e4) {
                        irp.crearPopUpAdvertencia(e4.getMessage());
                        
                    } catch(OperacionException e5) {
                        irp.crearPopUpFracaso();
                    } 
            }
        } else irp.crearPopUpAdvertencia("La reserva está vacía. Por favor, realice al menos una subreserva.");
        }
        else if(comando.equals("Cancelar")) {
            
            if(hayCambios()) {
                int confirmacion = irp.confirmarContinuacion("Hay cambios sin guardar. ¿Desea continuar?");
                if(confirmacion == JOptionPane.OK_OPTION) {
                    new InterfazIngresoDatosDocente().getControlador().setearDatos(reservaDTO);
                    irp.dispose();
                }
            }
            else {
                new InterfazIngresoDatosDocente().getControlador().setearDatos(reservaDTO);
                irp.dispose();
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
        
        if(irp.getCampoCantidadAlumnos().getText().equals("") ||
           !irp.getCampoCantidadAlumnos().getText().matches("\\d+") ||
           Integer.parseInt(irp.getCampoCantidadAlumnos().getText()) <= 0 ||
           irp.getTipoAula().equals("") ||
           irp.getDia().equals("")) valido = false;
        
        return valido;
    }
    
    private boolean subreservasRepetidas(JTable tabla) throws DatosInvalidosException {

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object[] fila1 = new Object[modelo.getColumnCount()];
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                fila1[j] = modelo.getValueAt(i, j);
            }

            for (int k = i + 1; k < modelo.getRowCount(); k++) {
                Object[] fila2 = new Object[modelo.getColumnCount()];
                for (int j = 0; j < modelo.getColumnCount(); j++) {
                    fila2[j] = modelo.getValueAt(k, j);
                }

                // Verificar si las filas son iguales
                if (Arrays.equals(fila1, fila2)) {
                    return true;  
                }
            }
        }

        return false;  
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
        
        } catch(Exception e) {
            irp.crearPopUpError();
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
                
                long minutos = calcularDuracion(hora_inicio, hora_fin);

                if (minutos % 30 == 0) valido = true;
                else throw new DuracionException();
                
            } else throw new DatosInvalidosException();
           
            
        } catch(ParseException e) {
          irp.crearPopUpError();
          
        } catch(DatosInvalidosException e1) {
          irp.crearPopUpAdvertencia("Hay campos inválidos o sin rellenar.");  
          Border redBorder = new LineBorder(Color.RED, 2);
          irp.setCamposHora(redBorder, true);
          
        } catch(DuracionException e2) {
          irp.crearPopUpAdvertencia("La duración de la reserva no es múltiplo de 30 minutos.");
          Border redBorder = new LineBorder(Color.RED, 2);
          irp.setCamposHora(redBorder, true);
          
        } 
        
        return valido;
    }
    
    private long calcularDuracion(Time hora_inicio, Time hora_fin) {
        
        LocalTime inicio = hora_inicio.toLocalTime(); 
        LocalTime fin = hora_fin.toLocalTime();
        
        // Calcular la duración entre las horas
        Duration duracion = Duration.between(inicio, fin);
        return duracion.toMinutes();  
    }
    
    public boolean hayCambios() {
        
        if (!irp.getCampoCantidadAlumnos().getText().equals("")) {
            return true;
        }

        if (!irp.getTipoAula().equals("")) {
            return true;
        }
        
        if (!irp.getDia().equals("")) {
            return true;
        }

        if (!irp.getHoraInicio().equals("08:00")) {
            return true;
        }

        if (!irp.getHoraFin().equals("08:30")) {
            return true;
        }

        if(irp.getModel().getRowCount() > 0) {
            return true;
        }

        // Si ninguno de los valores cambió
        return false;
    }
}


