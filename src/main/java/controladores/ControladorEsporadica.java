package controladores;

import daos.Conexion;
import dtos.AulaCompuestaDTO;
import dtos.AulaDisponibleDTO;
import dtos.AulaSolapadaDTO;
import dtos.BusquedaAulaDTO;
import dtos.ReservaDTO;
import dtos.ReservaParcialDTO;
import excepciones.DatosInvalidosException;
import excepciones.ErrorException;
import excepciones.FechaException;
import excepciones.OperacionException;
import excepciones.ReservaInconsistenteException;
import gestores.GestorReserva;
import interfaces.InterfazAulasDisponibles;
import interfaces.InterfazAulasSolapadas;
import interfaces.InterfazIngresoDatosDocente;
import interfaces.InterfazMainBedel;
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
import java.util.Date;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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
                if(!validarCampos()) throw new DatosInvalidosException("Hay campos inválidos o sin rellenar.");
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
                        
                        Time horaInicio = aula.getHora_inicio();
                        Time horaFin = aula.getHora_fin();

                        int horaInicioHoras = horaInicio.getHours();
                        int horaInicioMinutos = horaInicio.getMinutes();
                        int horaFinHoras = horaFin.getHours();
                        int horaFinMinutos = horaFin.getMinutes();

                        // Formatear a "hh:mm"
                        String horaInicioFormateada = String.format("%02d:%02d", horaInicioHoras, horaInicioMinutos);
                        String horaFinFormateada = String.format("%02d:%02d", horaFinHoras, horaFinMinutos);

                        Object[] row = { 
                            aula.getNombre_aula(), 
                            aula.getDocente(), 
                            aula.getCurso(), 
                            aula.getContacto(), 
                            horaInicioFormateada,  
                            horaFinFormateada      
                        };

                        ias.getModel().addRow(row);
                    }
                }
            }
                
            } catch(DatosInvalidosException e1) {
                ire.crearPopUpAdvertencia(e1.getMessage());
                marcarCampos();
                
            } catch(ErrorException e2) {
                ire.crearPopUpError(e2.getMessage());
    
            } catch(FechaException e3) {
                ire.crearPopUpAdvertencia(e3.getMessage());
                ire.getjLabel2().setText("<html>La fecha es anterior a la actual y/o<br>no corresponde con las fechas de cursado.<html>");
                ire.getjLabel2().setVisible(true);
                Border redBorder = new LineBorder(Color.RED, 2);
                ire.setCampoFecha(redBorder, true);
            }
        }
        else if (comando.equals("Confirmar")) {
            
            int row = iad.getjTable1().getSelectedRow();
            DateTimeFormatter formatoBD = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            
            try {
                if(row == -1) throw new DatosInvalidosException("Por favor, seleccione un aula.");
                else {
                    int confirmacion = ire.confirmarContinuacion("¿Está seguro de que desea agregar la subreserva?");
                    if(confirmacion == JOptionPane.OK_OPTION) {
                        
                        String aula = (String) iad.getModel().getValueAt(row, 0);
                        Object[] nuevaFila = { aula, busquedaAulaDTO.getTipo_aula(), reservaDTO.getNombre_catedra(), 
                                               busquedaAulaDTO.getFecha().format(formatoBD), sdf.format((Time) busquedaAulaDTO.getHora_inicio()), sdf.format((Time) busquedaAulaDTO.getHora_fin()) };

                        if(subreservasRepetidas(ire.getjTable(), nuevaFila)) throw new ReservaInconsistenteException("La reserva no puede contener subreservas superpuestas.");
                        ire.getModel().addRow(nuevaFila);
                        iad.dispose();
                    }
                }
                
            } catch(DatosInvalidosException e1) {
                iad.crearPopUpAdvertencia(e1.getMessage());
                
            } catch(ReservaInconsistenteException e2) {
                ire.crearPopUpAdvertencia(e2.getMessage());
            }
        }
        else if(comando.equals("Finalizar reserva")) {
            
            if(ire.getModel().getRowCount() > 0) {
                int confirmacion = ire.confirmarContinuacion("¿Está seguro de que desea registrar la reserva?");
                if(confirmacion == JOptionPane.OK_OPTION) {
                    
                try {
                    ArrayList<ReservaParcialDTO> reservasParcialesDTO = new ArrayList<>();
                    TableModel modelo = ire.getModel();
                    
                    for(int i = 0; i < modelo.getRowCount(); i++) {
                        
                        ReservaParcialDTO reservaParcialDTO = new ReservaParcialDTO();

                        reservaParcialDTO.setNombre_aula((String) modelo.getValueAt(i, 0)); 
                        reservaParcialDTO.setTipo_aula((String) modelo.getValueAt(i, 1));
                        reservaParcialDTO.setCurso((String) modelo.getValueAt(i, 2));
                        
                        Object valorFecha = modelo.getValueAt(i, 3);
                        
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
                        LocalDate fecha = LocalDate.parse((String) valorFecha, formato);

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
                    }
                    
                    reservaDTO.setReservasParcialesDTO(reservasParcialesDTO);
                    GestorReserva.obtenerInstancia().registrarReserva(reservaDTO);
                    
                    ire.crearPopUpExito();
                    new InterfazIngresoDatosDocente().getControlador().completarDatos();
                    ire.dispose();
                    
                     
                    } catch(ParseException e1) {
                        ire.crearPopUpError("Ocurrió un error. Vuelva a intentarlo.");
                          
                    } catch(ErrorException e2) {
                        ire.crearPopUpError(e2.getMessage());
    
                    } catch(ReservaInconsistenteException e3) {
                        ire.crearPopUpAdvertencia(e3.getMessage());
                        
                    } catch(OperacionException e4) {
                        ire.crearPopUpError(e4.getMessage());
                    } 
            } 
        } else ire.crearPopUpAdvertencia("La reserva está vacía. Por favor, realice al menos una subreserva.");
        }
        else if(comando.equals("Cancelar")) {
            
            int confirmacion = ire.confirmarContinuacion("¿Está seguro de que desea cancelar la reserva?");
            if(confirmacion == JOptionPane.OK_OPTION) {
                new InterfazMainBedel();
                ire.dispose();
            }
        }
        else if(comando.equals("CancelarAD")) {
            iad.dispose();
        }
        else if(comando.equals("Continuar")) {
            ias.dispose();
        }
        else if(comando.equals("Eliminar subreserva")) {
            try {
                int row = ire.getjTable().getSelectedRow(); 
                
                if(row == -1) throw new DatosInvalidosException("Seleccione una subreserva.");
                else ire.getModel().removeRow(row);
                
                } catch(DatosInvalidosException e1) {
                    ire.crearPopUpAdvertencia(e1.getMessage());
                }
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
    
    private boolean subreservasRepetidas(JTable tabla, Object[] fila) {
        int columnas = tabla.getColumnCount();
        int filas = tabla.getRowCount();

        // Recorrer cada fila de la tabla
        for (int i = 0; i < filas; i++) {
            boolean sonIguales = true;

            // Comparar cada elemento de la fila
            for (int j = 0; j < columnas-1; j++) {
                Object valorTabla = tabla.getValueAt(i, j);
                Object valorFilaBuscada = fila[j];

                // Si estamos en la columna 4 
                if (j == 4) {
                        // Compara hora inicio y hora fin de la tabla con hora inicio y hora fin de la fila a insertar
                        if (!sonHorasSuperpuestas(tabla.getValueAt(i, j), tabla.getValueAt(i, j+1), fila[4], fila[5])) {
                            sonIguales = false;
                            break;
                        }
                        
                } else {
                    if (!Objects.equals(valorTabla, valorFilaBuscada)) {
                        sonIguales = false;
                        break; 
                    }
                }
            }

            if (sonIguales) {
                return true; 
            }
        }
        return false; 
    }
    
    private boolean sonHorasSuperpuestas(Object horaInicio1, Object horaFin1, Object horaInicio2, Object horaFin2) {
        if (horaInicio1 instanceof String && horaFin1 instanceof String && horaInicio2 instanceof String && horaFin2 instanceof String) {
            LocalTime inicio1 = LocalTime.parse((String) horaInicio1);
            LocalTime fin1 = LocalTime.parse((String) horaFin1);
            LocalTime inicio2 = LocalTime.parse((String) horaInicio2);
            LocalTime fin2 = LocalTime.parse((String) horaFin2);

            return (inicio1.isBefore(fin2) && fin1.isAfter(inicio2));
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

    private LocalDate convertirFecha(Date date) {
    
        try {
            
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();                     // Convierte a LocalDate, sin la parte de la hora

            return localDate; 
            
        } catch(Exception e) {
            ire.crearPopUpError("Ocurrió un error. Vuelva a intentarlo.");
            return null;
        }
    }
    
    private Time getHoraAsTime(String hora) {
    
        try {
            
            LocalTime localTime = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));

            return Time.valueOf(localTime.atDate(java.time.LocalDate.now()).toLocalTime()); 
        
        } catch(Exception e) {
            ire.crearPopUpError("Ocurrió un error. Vuelva a intentarlo.");
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
                else throw new DatosInvalidosException("La duración de la reserva debe ser múltiplo de 30 minutos.");
                
            } else throw new DatosInvalidosException("Hay campos inválidos o sin rellenar.");
            
        } catch(ParseException e) {
          ire.crearPopUpError("Ocurrió un error. Vuelva a intentarlo.");
          
        } catch(DatosInvalidosException e1) {
          ire.crearPopUpAdvertencia(e1.getMessage());  
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

}


