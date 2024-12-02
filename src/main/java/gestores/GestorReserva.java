
package gestores;

import daos.AulaPostgreSQLDAO;
import daos.BedelPostgreSQLDAO;
import daos.PeriodoPostgreSQLDAO;
import daos.ReservaPostgreSQLDAO;
import dtos.AulaCompuestaDTO;
import dtos.AulaDisponibleDTO;
import dtos.AulaSolapadaDTO;
import dtos.BusquedaAulaDTO;
import dtos.DatosBusquedaDTO;
import dtos.PeriodoDTO;
import dtos.ReservaDTO;
import dtos.ReservaParcialDTO;
import excepciones.ErrorException;
import excepciones.FechaException;
import excepciones.OperacionException;
import excepciones.ReservaInconsistenteException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Aula;
import model.Informatica;
import model.Multimedios;
import model.Periodo;
import model.ReservaParcial;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import model.Bedel;
import model.Reserva;
import model.SinRecursosAdicionales;

public class GestorReserva {
    
    private static GestorReserva instancia;
    
    private GestorReserva(){}
    
    public static GestorReserva obtenerInstancia() {
        if(instancia == null){
            instancia = new GestorReserva();
        }
        return instancia;
    }
    
    public AulaCompuestaDTO disponerAulas(BusquedaAulaDTO busquedaAulaDTO) throws FechaException, ErrorException {
        
        AulaCompuestaDTO aulaCompuestaDTO = new AulaCompuestaDTO();
        PeriodoDTO periodoDTO = new PeriodoDTO();
        periodoDTO.setTipo_periodo(busquedaAulaDTO.getPeriodo());
        LocalDate fechaActual = LocalDate.now();
        int anio = fechaActual.getYear();
        periodoDTO.setAnio_lectivo(anio);
        
        Periodo periodo = PeriodoPostgreSQLDAO.obtenerInstancia().obtenerPeriodo(periodoDTO);
        
        List<LocalDate> listaFechas = new ArrayList<>();
        
        if(busquedaAulaDTO.getTipo_reserva().equals("Periódica")) {
            listaFechas.addAll(calcularFechas(busquedaAulaDTO.getDia(), periodo));
        }
        else if(busquedaAulaDTO.getTipo_reserva().equals("Esporádica")) {
            
            if(validarFecha(busquedaAulaDTO.getFecha(), periodo)) {
                listaFechas.add(busquedaAulaDTO.getFecha());
            }
            else throw new FechaException();
        }
        
        DatosBusquedaDTO datosBusquedaDTO = new DatosBusquedaDTO();
        datosBusquedaDTO.setAlumnos(busquedaAulaDTO.getAlumnos());
        datosBusquedaDTO.setListaFechas(listaFechas);
        datosBusquedaDTO.setHora_inicio(busquedaAulaDTO.getHora_inicio());
        datosBusquedaDTO.setHora_fin(busquedaAulaDTO.getHora_fin());
        
        Class<?> tipoAula = null;

        switch (busquedaAulaDTO.getTipo_aula()) {
            case "Multimedios":
                tipoAula = Multimedios.class;
                break;
            case "Informática":
                tipoAula = Informatica.class;
                break;
            case "Sin recursos adicionales":
                tipoAula = SinRecursosAdicionales.class;
                break;
        }
        
        datosBusquedaDTO.setTipoAula(tipoAula);
        
        ReservaPostgreSQLDAO reservaPostgreSQLDAO = ReservaPostgreSQLDAO.obtenerInstancia();
        
        List<ReservaParcial> reservasParcialesSolapadas = reservaPostgreSQLDAO.obtener_RP_solapadas(datosBusquedaDTO);
        List<Integer> idAulas = new ArrayList<>();
        
        if(!reservasParcialesSolapadas.isEmpty()) {

             for (ReservaParcial reservaParcial : reservasParcialesSolapadas) {
                 idAulas.add(reservaParcial.getAula().getId_aula()); 
             }
        }
        else idAulas.add(0);

        AulaPostgreSQLDAO aulaPostgreSQLDAO = AulaPostgreSQLDAO.obtenerInstancia();

        List<Aula> aulasDisponibles = aulaPostgreSQLDAO.obtenerOtrasAulas(idAulas, datosBusquedaDTO);

        if(!aulasDisponibles.isEmpty()) {

            List<AulaDisponibleDTO> aulasDisponiblesDTO = new ArrayList<>();

            for(Aula aula : aulasDisponibles) {
                AulaDisponibleDTO aulaDisponibleDTO = map_Aula_a_AulaDisponibleDTO(aula);
                aulasDisponiblesDTO.add(aulaDisponibleDTO);
            }

            aulaCompuestaDTO.setAulasDisponiblesDTO(aulasDisponiblesDTO);

        }
        else {
            List<AulaSolapadaDTO> aulasSolapadasDTO = new ArrayList<>();
            List<ReservaParcial> rpMenosSolapadas = listaRpMenosSolapadas(reservasParcialesSolapadas, busquedaAulaDTO.getHora_inicio(), busquedaAulaDTO.getHora_fin());

            for(ReservaParcial rpSolapada : rpMenosSolapadas) {

                AulaSolapadaDTO aulaSolapadaDTO = new AulaSolapadaDTO();
                aulaSolapadaDTO.setNombre_aula(rpSolapada.getAula().getNombre());
                Reserva reserva = reservaPostgreSQLDAO.obtenerReserva(rpSolapada.getId_reserva());
                aulaSolapadaDTO.setDocente(reserva.getNombre_docente());
                aulaSolapadaDTO.setContacto(reserva.getEmail_docente());
                aulaSolapadaDTO.setCurso(reserva.getNombre_catedra());
                aulaSolapadaDTO.setHora_inicio(rpSolapada.getHora_inicio());
                aulaSolapadaDTO.setHora_fin(rpSolapada.getHora_fin());

                aulasSolapadasDTO.add(aulaSolapadaDTO);
            }

            aulaCompuestaDTO.setAulasSolapadasDTO(aulasSolapadasDTO);
        }

        return aulaCompuestaDTO;

    }
    
    public void verificarPeriodo(ReservaDTO reservaDTO) throws FechaException {
        
        PeriodoDTO periodoDTO = new PeriodoDTO();
        periodoDTO.setTipo_periodo(reservaDTO.getPeriodo());
        LocalDate fechaActual = LocalDate.now();
        int anio = fechaActual.getYear();
        periodoDTO.setAnio_lectivo(anio);
        
        Periodo periodo = PeriodoPostgreSQLDAO.obtenerInstancia().obtenerPeriodo(periodoDTO);
        
        if(fechaActual.isAfter(periodo.getFecha_fin())) {
            throw new FechaException();
        }
    }
   
    private List<LocalDate> calcularFechas(String dia, Periodo periodo) {
        
        List<LocalDate> fechas = new ArrayList<>();
        DayOfWeek diaSemana;
        
        // Mapeo de días en español a DayOfWeek
        switch (dia.toLowerCase()) {
            case "lunes":
                diaSemana = DayOfWeek.MONDAY;
                break;
            case "martes":
                diaSemana = DayOfWeek.TUESDAY;
                break;
            case "miércoles":
                diaSemana = DayOfWeek.WEDNESDAY;
                break;
            case "jueves":
                diaSemana = DayOfWeek.THURSDAY;
                break;
            case "viernes":
                diaSemana = DayOfWeek.FRIDAY;
                break;
            case "sábado":
                diaSemana = DayOfWeek.SATURDAY;
                break;
            default:
                throw new IllegalArgumentException("Día no válido: " + dia);
        }

        LocalDate currentDate = periodo.getFecha_inicio();
        
        // Ajustamos la fecha de inicio al primer día correcto dentro del rango
        while (currentDate.getDayOfWeek() != diaSemana) {
            currentDate = currentDate.plusDays(1);
        }
        
        // Iteramos desde la fecha ajustada hasta la fecha de fin
        while (!currentDate.isAfter(periodo.getFecha_fin())) {
            
            fechas.add(currentDate);
            
            // Avanzar al siguiente día del mismo tipo
            currentDate = currentDate.plusWeeks(1); // Sumamos 7 días
        }
        
        return fechas;
    }
    
    private boolean validarFecha(LocalDate fecha, Periodo periodo) {
        
        LocalDate fechaActual = LocalDate.now(); // Fecha actual

        // Compara la fecha con el inicio y fin del período, además de verificar que sea posterior a la fecha actual
        return (fecha.isAfter(periodo.getFecha_inicio()) && 
                fecha.isBefore(periodo.getFecha_fin()) &&
                fecha.isAfter(fechaActual));
}
    
    private AulaDisponibleDTO map_Aula_a_AulaDisponibleDTO(Aula aula) {
        
        AulaDisponibleDTO aulaDisponibleDTO = new AulaDisponibleDTO();
        aulaDisponibleDTO.setCapacidad(aula.getCapacidad());
        aulaDisponibleDTO.setCaracteristicas(obtenerCaracteristicasAula(aula));
        aulaDisponibleDTO.setNombre_aula(aula.getNombre());
        aulaDisponibleDTO.setUbicacion("Piso " + aula.getPiso());
        
        return aulaDisponibleDTO;
        
    }
    
    private String obtenerCaracteristicasAula(Aula aula) {
       
        StringBuilder caracteristicas = new StringBuilder();

        caracteristicas.append("Con cañón: ").append(aula.canion() ? "SÍ" : "NO").append(".\n");

        caracteristicas.append("Tipo de pizarrón: ").append(aula.getTipoPizarron().toLowerCase()).append(".\n");
        
        caracteristicas.append("Con ventiladores: ").append(aula.ventiladores() ? "SÍ" : "NO").append(".\n");

        caracteristicas.append("Con aire acondicionado: ").append(aula.aireAcondicionado() ? "SÍ" : "NO").append(".\n");

        if (aula instanceof Informatica) {
            Informatica aulaInformatica = (Informatica) aula;
            caracteristicas.append("Cantidad de PCs: ").append(aulaInformatica.getCantidadPC()).append(".\n");
        }

        if (aula instanceof Multimedios) {
            Multimedios aulaMultimedios = (Multimedios) aula;
            caracteristicas.append("Con televisor: ").append(aulaMultimedios.televisor() ? "SÍ" : "NO").append(".\n");
            caracteristicas.append("Con computadora: ").append(aulaMultimedios.computadora() ? "SÍ" : "NO").append(".\n");
        }

        // Eliminar el último salto de línea si existe
        if (caracteristicas.length() > 0 && caracteristicas.charAt(caracteristicas.length() - 1) == '\n') {
            caracteristicas.setLength(caracteristicas.length() - 1);
        }

        return caracteristicas.toString();
    }

    private List<ReservaParcial> listaRpMenosSolapadas(List<ReservaParcial> rpSolapadas, Time hora_inicio, Time hora_fin) {
        
        // Lista para almacenar las reservas con su solapamiento calculado
        Map<ReservaParcial, Integer> reservasConSolapamiento = new HashMap<>();

        for (ReservaParcial reserva_parcial : rpSolapadas) {

            Time horaInicioReserva = reserva_parcial.getHora_inicio();
            Time horaFinReserva = reserva_parcial.getHora_fin();

            // Convertir las horas a milisegundos para poder comparar
            long inicioReserva = horaInicioReserva.getTime();
            long finReserva = horaFinReserva.getTime();
            long inicioBusqueda = hora_inicio.getTime();
            long finBusqueda = hora_fin.getTime();

            // Calcular el solapamiento entre los intervalos de tiempo
            long inicioSolapamiento = Math.max(inicioReserva, inicioBusqueda);  // El mayor de los inicios
            long finSolapamiento = Math.min(finReserva, finBusqueda); // El menor de los finales

            // Calculamos la cantidad de minutos de solapamiento
            int solapamiento = 0;
                solapamiento = (int) ((finSolapamiento - inicioSolapamiento) / 60000);  // Convertimos milisegundos a minutos

            // Guardar el solapamiento calculado para cada reserva
            reservasConSolapamiento.put(reserva_parcial, solapamiento);
        }

        // Ordenar las reservas por el solapamiento, de menor a mayor
        List<Map.Entry<ReservaParcial, Integer>> listaOrdenada = new ArrayList<>(reservasConSolapamiento.entrySet());
        listaOrdenada.sort(Comparator.comparingInt(Map.Entry::getValue));

        // Lista para devolver las 3 reservas con menos solapamiento
        List<ReservaParcial> reservasMenosSolapadas = new ArrayList<>();
        for (int i = 0; i < Math.min(3, listaOrdenada.size()); i++) {
            reservasMenosSolapadas.add(listaOrdenada.get(i).getKey());
        }

        return reservasMenosSolapadas;
    }
    
    public void registrarReserva(ReservaDTO reservaDTO) throws ErrorException, ReservaInconsistenteException, OperacionException {
        
        AulaPostgreSQLDAO aulaPostgreSQLDAO = AulaPostgreSQLDAO.obtenerInstancia();
        List<ReservaParcial> reservasParciales = new ArrayList<>();
        List<ReservaParcialDTO> reservasParcialesDTO = new ArrayList<>();
        
        if(reservaDTO.getTipo_reserva().equals("Periódica")) {
            
            PeriodoDTO periodoDTO = new PeriodoDTO();
            periodoDTO.setTipo_periodo(reservaDTO.getPeriodo());
            LocalDate fechaActual = LocalDate.now();
            int anio = fechaActual.getYear();
            periodoDTO.setAnio_lectivo(anio);

            Periodo periodo = PeriodoPostgreSQLDAO.obtenerInstancia().obtenerPeriodo(periodoDTO);

            reservasParcialesDTO = calcularRP(reservaDTO.getReservasParcialesDTO(), periodo);
            
        }
        else if(reservaDTO.getTipo_reserva().equals("Esporádica")) {
            reservasParcialesDTO = reservaDTO.getReservasParcialesDTO();
        }
            
        for(ReservaParcialDTO rpDTO : reservasParcialesDTO) {
                
            ReservaParcial reservaParcial = new ReservaParcial();
                
            reservaParcial.setHora_inicio(rpDTO.getHora_inicio());
            reservaParcial.setHora_fin(rpDTO.getHora_fin());
            reservaParcial.setDuracion(rpDTO.getDuracion()); 
            reservaParcial.setFecha(rpDTO.getFecha());
            
            Class<?> tipoAula = null;

            switch (rpDTO.getTipo_aula()) {
                case "Multimedios":
                    tipoAula = Multimedios.class;
                    break;
                case "Informática":
                    tipoAula = Informatica.class;
                    break;
                case "Sin recursos adicionales":
                    tipoAula = SinRecursosAdicionales.class;
                    break;
            }
            
            Aula aula = aulaPostgreSQLDAO.obtenerAula(rpDTO.getNombre_aula(), tipoAula);
                
            reservaParcial.setAula(aula);
                
            reservasParciales.add(reservaParcial);
        }            
            
        verificarDisponibilidad(reservasParciales, reservaDTO.getTipo_reserva());
        
        Reserva reserva = new Reserva();
        reserva.setNombre_docente(reservaDTO.getNombre_docente());
        reserva.setNombre_catedra(reservaDTO.getNombre_catedra());
        reserva.setEmail_docente(reservaDTO.getEmail_docente());
        reserva.setTipo_reserva(reservaDTO.getTipo_reserva());
        reserva.setFecha_reserva(reservaDTO.getFecha_reserva());
        reserva.setReservasParciales(reservasParciales);
        Bedel bedel = BedelPostgreSQLDAO.obtenerInstancia().obtenerBedel(reservaDTO.getId_bedel());
        reserva.setBedel(bedel);
        
        PeriodoDTO periodoDTO = new PeriodoDTO();
        periodoDTO.setTipo_periodo(reservaDTO.getPeriodo());
        LocalDate fechaActual = LocalDate.now();
        int anio = fechaActual.getYear();
        periodoDTO.setAnio_lectivo(anio);

        Periodo periodo = PeriodoPostgreSQLDAO.obtenerInstancia().obtenerPeriodo(periodoDTO);
        
        reserva.setPeriodo(periodo);
        
        ReservaPostgreSQLDAO.obtenerInstancia().registrarReserva(reserva);

    }
   
    // Método para calcular reservas parciales DTO con las fechas del día en el período
    private ArrayList<ReservaParcialDTO> calcularRP(List<ReservaParcialDTO> reservasParcialesDTO, Periodo periodo) {
        
        ArrayList<ReservaParcialDTO> rpCalculadas = new ArrayList<>();
        
        for(ReservaParcialDTO rpDTO : reservasParcialesDTO) {
            
            String dia = rpDTO.getDia();
            DayOfWeek diaSemana = null;

            // Mapeo de días en español a DayOfWeek dentro del método
            switch (dia.toLowerCase()) {
                case "lunes":
                    diaSemana = DayOfWeek.MONDAY;
                    break;
                case "martes":
                    diaSemana = DayOfWeek.TUESDAY;
                    break;
                case "miércoles":
                    diaSemana = DayOfWeek.WEDNESDAY;
                    break;
                case "jueves":
                    diaSemana = DayOfWeek.THURSDAY;
                    break;
                case "viernes":
                    diaSemana = DayOfWeek.FRIDAY;
                    break;
                case "sábado":
                    diaSemana = DayOfWeek.SATURDAY;
                    break;
            }

            LocalDate currentDate = periodo.getFecha_inicio();

            // Ajustamos la fecha de inicio al primer día correcto dentro del rango
            while (currentDate.getDayOfWeek() != diaSemana) {
                currentDate = currentDate.plusDays(1);
            }

            // Iteramos desde la fecha ajustada hasta la fecha de fin
            while (!currentDate.isAfter(periodo.getFecha_fin())) {
                
                ReservaParcialDTO reservaParcialDTO = new ReservaParcialDTO();
                reservaParcialDTO.setNombre_aula(rpDTO.getNombre_aula());
                reservaParcialDTO.setTipo_aula(rpDTO.getTipo_aula());
                reservaParcialDTO.setCurso(rpDTO.getCurso());
                reservaParcialDTO.setFecha(currentDate);
                reservaParcialDTO.setHora_inicio(rpDTO.getHora_inicio());
                reservaParcialDTO.setHora_fin(rpDTO.getHora_fin());
                reservaParcialDTO.setDuracion(rpDTO.getDuracion());
                
                rpCalculadas.add(reservaParcialDTO);
                
                currentDate = currentDate.plusWeeks(1); // Sumamos 7 días
            }
        }
        
        return rpCalculadas;
    }

    private void verificarDisponibilidad(List<ReservaParcial> reservasParciales, String tipo_reserva) throws ErrorException, ReservaInconsistenteException {
        
        ReservaPostgreSQLDAO reservaPostgreSQLDAO = ReservaPostgreSQLDAO.obtenerInstancia();
        
        List<ReservaParcial> reservasConConflicto;
        
        Set<String> reservasPeriodicasConConflicto = new HashSet<>();
        
        reservasConConflicto = reservaPostgreSQLDAO.verificarDisponibilidad(reservasParciales);
        
        if (!reservasConConflicto.isEmpty()) {
            
            Set<ReservaParcial> reservasSolapadas = new HashSet<>();

            for (ReservaParcial reserva : reservasParciales) {
                for (ReservaParcial rp : reservasConConflicto) {
                    
                    if (rp.getAula().getId_aula() == reserva.getAula().getId_aula() &&
                        rp.getFecha().equals(reserva.getFecha())) {

                        // Verificar si los horarios se solapan
                        boolean solapan = rp.getHora_inicio().before(reserva.getHora_fin()) && 
                                          rp.getHora_fin().after(reserva.getHora_inicio());

                        if (solapan) reservasSolapadas.add(reserva);  
                    }
                }
            }
    
            StringBuilder mensaje = new StringBuilder("<html>Conflictos detectados con las siguientes reservas:<br>");
            
            for (ReservaParcial rp : reservasSolapadas) {
                
                LocalTime inicio = rp.getHora_inicio().toLocalTime(); 
                LocalTime fin = rp.getHora_fin().toLocalTime();

                // Formatear a "HH:mm"
                String horaInicio = inicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                String horaFin = fin.format(DateTimeFormatter.ofPattern("HH:mm"));
                
                if(tipo_reserva.equals("Esporádica")) {
                    mensaje.append("Aula: ").append(rp.getAula().getNombre())
                            .append(", fecha: ").append(rp.getFecha())
                            .append(", hora inicio: ").append(horaInicio)
                            .append(", hora fin: ").append(horaFin)
                            .append("<br>");
                } 
                else if(tipo_reserva.equals("Periódica")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", new Locale("es", "ES"));
                    String dia = rp.getFecha().format(formatter);

                    dia = dia.substring(0, 1).toUpperCase() + dia.substring(1).toLowerCase();

                    String reservaTexto = "Aula: " + rp.getAula().getNombre() +
                                          ", día: " + dia +
                                          ", hora inicio: " + horaInicio +
                                          ", hora fin: " + horaFin;

                    // Solo agregar al mensaje si aún no se ha agregado
                    if (reservasPeriodicasConConflicto.add(reservaTexto)) {
                        mensaje.append(reservaTexto).append("<br>");
                    }
                }
            }
            
            mensaje.append("</html>");
            
            throw new ReservaInconsistenteException(mensaje.toString());
           }
        }
    }
