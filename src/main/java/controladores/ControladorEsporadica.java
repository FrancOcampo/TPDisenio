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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
                
            } catch(ErrorException e2) {
                ire.crearPopUpError();
    
            } catch(FechaException e3) {
                ire.crearPopUpAdvertencia("Hay campos inválidos o sin rellenar.");
                ire.getjLabel2().setText("<html>La fecha es anterior a la actual y/o<br>no corresponde a las fechas de cursado.<html>");
                ire.getjLabel2().setVisible(true);
                Border redBorder = new LineBorder(Color.RED, 2);
                ire.setCampoFecha(redBorder, true);
            }
        }
        else if (comando.equals("Confirmar")) {
            
            int row = iad.getjTable1().getSelectedRow();
            SimpleDateFormat formatoBD = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            
            try {
                if(row == -1) throw new DatosInvalidosException();
                else {
                    String aula = (String) iad.getModel().getValueAt(row, 0);
                    Object[] nuevaFila = { aula, busquedaAulaDTO.getTipo_aula(), reservaDTO.getNombre_catedra(), 
                                           formatoBD.format(busquedaAulaDTO.getFecha()), sdf.format((Time) busquedaAulaDTO.getHora_inicio()), sdf.format((Time) busquedaAulaDTO.getHora_fin()) };
                    ire.getModel().addRow(nuevaFila);
                    iad.dispose();
                }
                
            } catch(DatosInvalidosException e1) {
                iad.crearPopUpFila();
            }
        }
        else if(comando.equals("Finalizar reserva")) {
            
            if(ire.getModel().getRowCount() > 0) {
                int confirmacion = ire.confirmarContinuacion("¿Está seguro de que desea registrar la reserva?");
                if(confirmacion == JOptionPane.OK_OPTION) {
                    
                try {
                    if(subreservasRepetidas(ire.getjTable())) throw new DatosInvalidosException();
                    ArrayList<ReservaParcialDTO> reservasParcialesDTO = new ArrayList<>();
                    TableModel modelo = ire.getModel();
                    
                    for(int i = 0; i < modelo.getRowCount(); i++) {
                        
                        ReservaParcialDTO reservaParcialDTO = new ReservaParcialDTO();

                        reservaParcialDTO.setNombre_aula((String) modelo.getValueAt(i, 0)); 
                        reservaParcialDTO.setTipo_aula((String) modelo.getValueAt(i, 1));
                        reservaParcialDTO.setCurso((String) modelo.getValueAt(i, 2));
                        
                        Object valorFecha = modelo.getValueAt(i, 3);
                        
                        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                        java.util.Date utilDate = formato.parse((String) valorFecha); // Convierte String a java.util.Date
                        java.sql.Date fecha = new java.sql.Date(utilDate.getTime()); // Convierte java.util.Date a java.sql.Date

                        reservaParcialDTO.setFecha(fecha);
                        
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
                    
                    reservaDTO.setReservasParcialesDTO(reservasParcialesDTO);
                    GestorReserva.obtenerInstancia().registrarReserva(reservaDTO);
                    
                    ire.crearPopUpExito();
                    new InterfazIngresoDatosDocente().getControlador().completarDatos();
                    ire.dispose();
                    
                    }
                    } catch(DatosInvalidosException e1) {
                        ire.crearPopUpAdvertencia("La reserva contiene subreservas repetidas.");
                        
                    } catch(ParseException e2) {
                        ire.crearPopUpFracaso();
                          
                    } catch(ErrorException e3) {
                        ire.crearPopUpError();
    
                    } catch(ReservaInconsistenteException e4) {
                        ire.crearPopUpAdvertencia(e4.getMessage());
                        
                    } catch(OperacionException e5) {
                        ire.crearPopUpFracaso();
                    } 
            } 
        } else ire.crearPopUpAdvertencia("La reserva está vacía. Por favor, realice al menos una subreserva.");
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
           ire.getFecha().equals("") ||
           !verificarDiaFecha(ire.getCalendario().getDate())) valido = false;
        
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
        
        if(ire.getCampoCantidadAlumnos().getText().equals("") || !ire.getCampoCantidadAlumnos().getText().matches("\\d+") || Integer.parseInt(ire.getCampoCantidadAlumnos().getText()) <= 0) {
            ire.setCampoCantidadAlumnos(redBorder, visibilidad);
        }
        if(ire.getTipoAula().equals("")) {
            ire.setCampoAula(redBorder, visibilidad);
        }
        if(ire.getFecha().equals("")) {
            ire.setCampoFecha(redBorder, visibilidad);
        }
        if(!verificarDiaFecha(ire.getCalendario().getDate())) {
            ire.setCampoFecha(redBorder, visibilidad);
            ire.getjLabel2().setText("<html>La fecha no puede corresponder<br>al día domingo.<html>");
            ire.getjLabel2().setVisible(true);
        }
    }

    private Date convertirFecha(Date date) {
        
        SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
    
        try {
            return formatoBD.parse(formatoBD.format(date));
            
        } catch(ParseException e) {
            ire.crearPopUpError();
            return null;
        }
    }
    
    private Time getHoraAsTime(String hora) {
    
        try {
            
            LocalTime localTime = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));

            return Time.valueOf(localTime.atDate(java.time.LocalDate.now()).toLocalTime()); 
        
        } catch(Exception e) {
            ire.crearPopUpError();
            return null;
        }
    }
    
    private boolean verificarDiaFecha(Date fecha) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaString = sdf.format(fecha);

        // Convertir el String a LocalDate
        LocalDate fechaLocal = LocalDate.parse(fechaString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        return !fechaLocal.getDayOfWeek().equals(java.time.DayOfWeek.SUNDAY);
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
          ire.crearPopUpError();
          
        } catch(DatosInvalidosException e1) {
          ire.crearPopUpAdvertencia("Hay campos inválidos o sin rellenar.");  
          Border redBorder = new LineBorder(Color.RED, 2);
          ire.setCamposHora(redBorder, true);
          
        } catch(DuracionException e2) {
          ire.crearPopUpAdvertencia("La duración de la reserva no es múltiplo de 30 minutos.");
          Border redBorder = new LineBorder(Color.RED, 2);
          ire.setCamposHora(redBorder, true);
          
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
        
        if (!ire.getCampoCantidadAlumnos().getText().equals("")) {
            return true;
        }

        if (!ire.getTipoAula().equals("")) {
            return true;
        }
        
        LocalDate fechaSeleccionada = ire.getCalendario().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaActual = LocalDate.now();
        
        if (!fechaSeleccionada.equals(fechaActual)) {
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


