
package gestores;

import daos.AulaPostgreSQLDAO;
import daos.PeriodoPostgreSQLDAO;
import daos.ReservaPostgreSQLDAO;
import dtos.AulaCompuestaDTO;
import dtos.AulaDisponibleDTO;
import dtos.AulaSolapadaDTO;
import dtos.BusquedaAulaDTO;
import dtos.DatosBusquedaDTO;
import dtos.PeriodoDTO;
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
    
    public AulaCompuestaDTO disponerAulas(BusquedaAulaDTO busquedaAulaDTO) {
        
        AulaCompuestaDTO aulaCompuestaDTO = new AulaCompuestaDTO();
        PeriodoDTO periodoDTO = new PeriodoDTO();
        periodoDTO.setTipo_periodo(busquedaAulaDTO.getPeriodo());
        LocalDate fechaActual = LocalDate.now();
        int anio = fechaActual.getYear();
        periodoDTO.setAnio_lectivo(anio);
        
        Periodo periodo = PeriodoPostgreSQLDAO.obtenerInstancia().obtenerPeriodo(periodoDTO);
        
        List<Date> listaFechas = new ArrayList<>();
        listaFechas.addAll(calcularFechas(busquedaAulaDTO.getDia(), periodo));
        
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
            caracteristicas.append("Con cañón, ");
        }
        if (aula.getTipoPizarron() != null && !aula.getTipoPizarron().isEmpty()) {
            caracteristicas.append("Tipo de pizarrón: ").append(aula.getTipoPizarron()).append(", ");
        }
        if (aula.ventiladores()) {
            caracteristicas.append("Con ventiladores, ");
        }
        if (aula.aireAcondicionado()) {
            caracteristicas.append("Con aire acondicionado, ");
        }

        if (aula instanceof Informatica) {
            Informatica aulaInformatica = (Informatica) aula;
            caracteristicas.append("Cantidad de PCs: ").append(aulaInformatica.getCantidadPC()).append(", ");

        }

        if (aula instanceof Multimedios) {
            Multimedios aulaMultimedios = (Multimedios) aula;
            if (aulaMultimedios.televisor()) {
                caracteristicas.append("Con televisor, ");
            }
            if (aulaMultimedios.computadora()) {
                caracteristicas.append("Con computadora, ");
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


}
