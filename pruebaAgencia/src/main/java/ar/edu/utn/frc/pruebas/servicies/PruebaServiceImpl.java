package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.client.ApiClient;
import ar.edu.utn.frc.pruebas.dto.AgenciaDTO;
import ar.edu.utn.frc.pruebas.dto.NotificacionAlertaDTO;
import ar.edu.utn.frc.pruebas.models.*;
import ar.edu.utn.frc.pruebas.exceptions.PruebaException;
import ar.edu.utn.frc.pruebas.repositories.*;
import ar.edu.utn.frc.pruebas.servicies.interfaces.PruebaService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PruebaServiceImpl extends ServiceImpl<Prueba, Integer> implements PruebaService {
    private final PruebaRepository pruebaRepository;
    private final VehiculoRepository vehiculoRepository;
    private VehiculoServiceImpl vehiculoService;
    private final InteresadoRepository interesadoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PosicionRepository posicionRepository;
    private final IncidenteServiceImpl incidenteService;
    private final RestTemplate restTemplate;

    private static final double EARTH_RADIUS_KM = 6371.0;

    public PruebaServiceImpl(PruebaRepository pruebaRepository, VehiculoRepository vehiculoRepository,
                             InteresadoRepository interesadoRepository, EmpleadoRepository empleadoRepository,
                             PosicionRepository posicionRepository, IncidenteServiceImpl incidenteService, RestTemplate restTemplate, VehiculoServiceImpl vehiculoService) {
        this.pruebaRepository = pruebaRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.interesadoRepository = interesadoRepository;
        this.empleadoRepository = empleadoRepository;
        this.posicionRepository = posicionRepository;
        this.incidenteService = incidenteService;
        this.restTemplate = restTemplate;
        this.vehiculoService = vehiculoService;
    }

    public void add(Prueba prueba){
        this.pruebaRepository.save(prueba);
    }

    public void update(Prueba prueba){
        this.pruebaRepository.save(prueba);
    }

    public Prueba delete(Integer id){
        Prueba prueba = this.pruebaRepository.findById(id).orElseThrow();
        this.pruebaRepository.delete(prueba);
        return prueba;
    }

    public Prueba findById(Integer id){
        return this.pruebaRepository.findById(id).orElseThrow();
    }

    public List<Prueba> findAll(){
        return this.pruebaRepository.findAll();
    }

    public List<Prueba> listadoPruebasEnCurso() {
        return this.pruebaRepository.findAll()
                .stream()
                .filter(prueba -> prueba.getFechaFin().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void crearPrueba(Prueba p){
        Interesado interesado = interesadoRepository.findById(p.getInteresado().getId())
                .orElseThrow(() -> new PruebaException("Cliente no encontrado!"));

        if (interesado.getFechaVencimientoLicencia().isBefore(LocalDateTime.now())){
            throw new PruebaException("La licencia se encuentra vencida!");
        }

        if (interesado.getRestringido()){
            throw new PruebaException("El cliente se encuentra restringido!");
        }

        Vehiculo vehiculo = vehiculoService.findById(p.getVehiculo().getId());
                //.orElseThrow(() -> new PruebaException("Vehiculo no encontrado!"));

        boolean vehiculoEnPrueba = vehiculo.getPruebasVehiculo().stream()
                .anyMatch(v -> (v.getFechaFin().isAfter(LocalDateTime.now())));
        if (vehiculoEnPrueba){
            throw new PruebaException("El vehiculo tiene una prueba asignada!");
        }

        Empleado empleado = empleadoRepository.findById(p.getEmpleado().getLegajo())
                .orElseThrow(() -> new PruebaException("Empleado no encontrado!"));

        Prueba prueba = new Prueba(vehiculo, interesado, empleado, p.getFechaInicio(), p.getFechaFin());
        interesado.getPruebasInteresado().add(prueba);
        vehiculo.getPruebasVehiculo().add(prueba);
        empleado.getPruebasEmpleado().add(prueba);

        add(prueba);
    }

    public void agregarComentarios(int id, String comentarios){
        Prueba prueba = this.pruebaRepository.findById(id)
                .orElseThrow(() -> new PruebaException("Prueba no encontrada!"));

        prueba.setFechaFin(LocalDateTime.now());
        prueba.setComentarios(comentarios);

        update(prueba);
    }

    public NotificacionAlertaDTO verificarEnviarNotificacion(int idVehiculo){
        System.out.println("verif antes");
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new PruebaException("No se encontro el vehiculo"));
        List<Posicion> posiciones = vehiculo.getPosicionesVehiculo();
        List<Prueba> pruebas = vehiculo.getPruebasVehiculo();
        System.out.println("Encontrados: " + vehiculo + posiciones + pruebas );

        Posicion posicionVehiculo = posiciones.stream()
                .max(Comparator.comparing(Posicion::getFechaHora))
                .orElseThrow(() -> new NoSuchElementException("No tiene posiciones guardadas"));
        System.out.println("Posicion vehiculo: " + posicionVehiculo);

        Prueba prueba = pruebas.stream()
                .filter(p -> p.getFechaFin().isAfter(LocalDateTime.now()))
                .findFirst()
                .orElseThrow(() -> new PruebaException("El vehiculo no tiene una prueba activa"));

        System.out.println("Prueba: " + prueba);

        boolean estaEnLimite = evaluarPosicion(posicionVehiculo);
        System.out.println("esta en limite: " + estaEnLimite);

        if (!estaEnLimite){
            Incidente incidente = new Incidente(prueba, prueba.getEmpleado());
            incidenteService.add(incidente);
            System.out.println("Incidente creado: " + incidente);
            return enviarNotificacion(posicionVehiculo);
        }
        return null;
    }

    private NotificacionAlertaDTO enviarNotificacion(Posicion posicion){
        System.out.println("service antes" + posicion);
        NotificacionAlertaDTO notificacion = new NotificacionAlertaDTO(
                "Exceso de limite",
                "Peligro! El vehiculo ha ingresado a una zona peligrosa o ha excedido el radio permitido",
                posicion.getVehiculo().getId());

        String url = "http://localhost:8084/api/notificaciones";
        try {
            System.out.println("service: " + notificacion);
            restTemplate.postForObject(url, notificacion, String.class);
            return notificacion;
        } catch (Exception e) {
            System.err.println("Error al enviar la notificación: " + e.getMessage());
            return null;
        }
    }

    private boolean evaluarPosicion(Posicion posicion){
        Vehiculo vehiculo = vehiculoRepository.findById(posicion.getVehiculo().getId())
                .orElseThrow(() -> new PruebaException("Vehículo no encontrado"));

        Prueba pruebaActiva = vehiculo.getPruebasVehiculo().stream()
                .filter(p -> p.getFechaFin().isAfter(LocalDateTime.now()))
                .findFirst()
                .orElseThrow(() -> new PruebaException("El vehículo no tiene ninguna prueba activa"));

        return calcularDistancia(posicion.getLatitud(), posicion.getLongitud());
    }

    private boolean calcularDistancia(double latVehiculo, double lonVehiculo) {
        AgenciaDTO agencia = obtenerInformacionAgencia();
        double latitud =  agencia.getCoordenadasAgencia().getLat();
        double longitud = agencia.getCoordenadasAgencia().getLon();

        double lat1Rad = Math.toRadians(latVehiculo);
        double lon1Rad = Math.toRadians(lonVehiculo);
        double lat2Rad = Math.toRadians(latitud);
        double lon2Rad = Math.toRadians(longitud);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS_KM * c;

        return distance < 5.0;
    }

    private AgenciaDTO obtenerInformacionAgencia(){
        ApiClient apiClient = new ApiClient();
        return apiClient.getAgenciaInfo();
    }
}
