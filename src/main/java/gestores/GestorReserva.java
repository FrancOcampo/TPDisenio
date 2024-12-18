
package gestores;

import daos.AulaPostgreSQLDAO;
import daos.BedelPostgreSQLDAO;
import daos.PeriodoPostgreSQLDAO;
import daos.ReservaPostgreSQLDAO;
import dtos.AulaCompuestaDTO;
import dtos.AulaDisponibleDTO;
import dtos.AulaSolapadaDTO;
import dtos.BedelDTO;
import dtos.BusquedaAulaDTO;
import dtos.DatosBusquedaDTO;
import dtos.PeriodoDTO;
import dtos.ReservaDTO;
import dtos.ReservaParcialDTO;
import excepciones.FechaException;
import excepciones.OperacionException;
import excepciones.ReservaInconsistenteException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import model.Aula;
import model.Informatica;
import model.Multimedios;
import model.Periodo;
import model.ReservaParcial;
import java.util.Map;
import java.util.HashMap;
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
    
    public AulaCompuestaDTO disponerAulas(BusquedaAulaDTO busquedaAulaDTO) throws FechaException {
        
        AulaCompuestaDTO aulaCompuestaDTO = new AulaCompuestaDTO();
        PeriodoDTO periodoDTO = new PeriodoDTO();
        periodoDTO.setTipo_periodo(busquedaAulaDTO.getPeriodo());
        LocalDate fechaActual = LocalDate.now();
        int anio = fechaActual.getYear();
        periodoDTO.setAnio_lectivo(anio);
        
        Periodo periodo = PeriodoPostgreSQLDAO.obtenerInstancia().obtenerPeriodo(periodoDTO);
        
        List<Date> listaFechas = new ArrayList<>();
        
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
        datosBusquedaDTO.setTipo_aula(busquedaAulaDTO.getTipo_aula());
        datosBusquedaDTO.setListaFechas(listaFechas);
        datosBusquedaDTO.setHora_inicio(busquedaAulaDTO.getHora_inicio());
        datosBusquedaDTO.setHora_fin(busquedaAulaDTO.getHora_fin());
        
        ReservaPostgreSQLDAO reservaPostgreSQLDAO = ReservaPostgreSQLDAO.obtenerInstancia();
        
        Class<?> tipoAula = null;

        switch (datosBusquedaDTO.getTipo_aula()) {
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
        
        List<ReservaParcial> reservasParcialesSolapadas = reservaPostgreSQLDAO.obtener_RP_solapadas(datosBusquedaDTO);
        List<Integer> idAulas = new ArrayList<>();
        
       if(!reservasParcialesSolapadas.isEmpty()) {
           
            for (ReservaParcial reservaParcial : reservasParcialesSolapadas) {
                idAulas.add(reservaParcial.getAula().getId_aula()); // Asumiendo que 'getAula()' devuelve el objeto Aula
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
        
        if(fechaActual.isAfter(periodo.getFecha_fin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
            throw new FechaException();
        }
    }
   
    private List<Date> calcularFechas(String dia, Periodo periodo) {
        
        List<Date> fechas = new ArrayList<>();
        DayOfWeek diaSemana;
        
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
            default:
                throw new IllegalArgumentException("Día no válido: " + dia);
        }

        // Convertir la fecha de inicio de tipo Date a LocalDate
        LocalDate currentDate = periodo.getFecha_inicio().toInstant()
                                       .atZone(ZoneId.systemDefault())
                                       .toLocalDate();
        
        // Convertir la fecha de fin de tipo Date a LocalDate
        LocalDate fechaFin = periodo.getFecha_fin().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();
        
        // Ajustamos la fecha de inicio al primer día correcto dentro del rango
        while (currentDate.getDayOfWeek() != diaSemana) {
            currentDate = currentDate.plusDays(1);
        }
        
        // Iteramos desde la fecha ajustada hasta la fecha de fin
        while (!currentDate.isAfter(fechaFin)) {
            // Convertir LocalDate a Date y agregarlo al listado
            fechas.add(java.sql.Date.valueOf(currentDate));
            // Avanzar al siguiente día del mismo tipo
            currentDate = currentDate.plusWeeks(1); // Sumamos 7 días
        }
        
        return fechas;
    }
    
    private boolean validarFecha(Date fecha, Periodo periodo) {
        
        Date fechaActual = new Date(); // Fecha actual

        return (fecha.after(periodo.getFecha_inicio()) && 
                fecha.before(periodo.getFecha_fin()) &&
                fecha.after(fechaActual));
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

        if (aula.canion()) {
            caracteristicas.append("Con cañón. ");
        }
        if (aula.getTipoPizarron() != null && !aula.getTipoPizarron().isEmpty()) {
            caracteristicas.append("Tipo de pizarrón: ").append(aula.getTipoPizarron()).append(". ");
        }
        if (aula.ventiladores()) {
            caracteristicas.append("Con ventiladores. ");
        }
        if (aula.aireAcondicionado()) {
            caracteristicas.append("Con aire acondicionado. ");
        }

        if (aula instanceof Informatica) {
            Informatica aulaInformatica = (Informatica) aula;
            caracteristicas.append("Cantidad de PCs: ").append(aulaInformatica.getCantidadPC()).append(". ");

        }

        if (aula instanceof Multimedios) {
            Multimedios aulaMultimedios = (Multimedios) aula;
            if (aulaMultimedios.televisor()) {
                caracteristicas.append("Con televisor. ");
            }
            if (aulaMultimedios.computadora()) {
                caracteristicas.append("Con computadora. ");
            }
        }

        if (caracteristicas.length() > 0) {
            caracteristicas.setLength(caracteristicas.length() - 2);
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
    
    public void registrarReserva(ReservaDTO reservaDTO) throws ReservaInconsistenteException, OperacionException {
        
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
            
        verificarDisponibilidad(reservasParciales);
        
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

            // Convertir la fecha de inicio de tipo Date a LocalDate
            LocalDate currentDate = periodo.getFecha_inicio().toInstant()
                                           .atZone(ZoneId.systemDefault())
                                           .toLocalDate();

            // Convertir la fecha de fin de tipo Date a LocalDate
            LocalDate fechaFin = periodo.getFecha_fin().toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate();

            // Ajustamos la fecha de inicio al primer día correcto dentro del rango
            while (currentDate.getDayOfWeek() != diaSemana) {
                currentDate = currentDate.plusDays(1);
            }

            // Iteramos desde la fecha ajustada hasta la fecha de fin
            while (!currentDate.isAfter(fechaFin)) {
                
                ReservaParcialDTO reservaParcialDTO = new ReservaParcialDTO();
                reservaParcialDTO.setNombre_aula(rpDTO.getNombre_aula());
                reservaParcialDTO.setTipo_aula(rpDTO.getTipo_aula());
                reservaParcialDTO.setCurso(rpDTO.getCurso());
                reservaParcialDTO.setFecha(java.sql.Date.valueOf(currentDate));
                reservaParcialDTO.setHora_inicio(rpDTO.getHora_inicio());
                reservaParcialDTO.setHora_fin(rpDTO.getHora_fin());
                reservaParcialDTO.setDuracion(rpDTO.getDuracion());
                
                rpCalculadas.add(reservaParcialDTO);
                
                currentDate = currentDate.plusWeeks(1); // Sumamos 7 días
            }
        }
        
        return rpCalculadas;
    }

    private void verificarDisponibilidad(List<ReservaParcial> reservasParciales) throws ReservaInconsistenteException {
        
        ReservaPostgreSQLDAO reservaPostgreSQLDAO = ReservaPostgreSQLDAO.obtenerInstancia();
        
        List<ReservaParcial> reservasConConflicto;
        
        reservasConConflicto = reservaPostgreSQLDAO.verificarDisponibilidad(reservasParciales);
        
        if (!reservasConConflicto.isEmpty()) {
               StringBuilder mensaje = new StringBuilder("Conflictos detectados en las siguientes reservas:\n");
               for (ReservaParcial rp : reservasConConflicto) {
                   mensaje.append("Aula: ").append(rp.getAula().getNombre())
                          .append(", fecha: ").append(rp.getFecha())
                          .append(", hora inicio: ").append(rp.getHora_inicio())
                          .append(", hora fin: ").append(rp.getHora_fin())
                          .append("\n");
               }
               throw new ReservaInconsistenteException(mensaje.toString());
           }
        
    }
    
 }
