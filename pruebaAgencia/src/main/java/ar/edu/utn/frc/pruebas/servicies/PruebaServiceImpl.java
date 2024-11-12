package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.servicioExterno.ApiExterna;
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
    private final PruebaRepository RepositoryPrueba; // Repositorio para acceder a los datos de pruebas.
    private final VehiculoRepository RepositoryVehiculo; // Repositorio para acceder a los datos de vehículos.
    private VehiculoServiceImpl ServiceVehiculo; // Servicio para operaciones de vehículos.
    private final InteresadoRepository RepositoryInteresado; // Repositorio para acceder a los datos de interesados.
    private final EmpleadoRepository RepositoryEmpleado; // Repositorio para acceder a los datos de empleados.
    private final PosicionRepository RepositoryPosition; // Repositorio para acceder a los datos de posiciones.
    private final IncidenteServiceImpl ServiceIncident; // Servicio para operaciones de incidentes.
    private final RestTemplate TemplateRe; // RestTemplate para realizar solicitudes HTTP.

    private static final double EARTH_RADIUS_KM = 6371.0; // Radio de la Tierra en kilómetros.

    public PruebaServiceImpl(PruebaRepository RepositoryPrueba, VehiculoRepository RepositoryVehiculo,
                             InteresadoRepository RepositoryInteresado, EmpleadoRepository RepositoryEmpleado,
                             PosicionRepository RepositoryPosition, IncidenteServiceImpl ServiceIncident, RestTemplate TemplateRe, VehiculoServiceImpl ServiceVehiculo) {
        this.RepositoryPrueba = RepositoryPrueba;
        this.RepositoryVehiculo = RepositoryVehiculo;
        this.RepositoryInteresado = RepositoryInteresado;
        this.RepositoryEmpleado = RepositoryEmpleado;
        this.RepositoryPosition = RepositoryPosition;
        this.ServiceIncident = ServiceIncident;
        this.TemplateRe = TemplateRe;
        this.ServiceVehiculo = ServiceVehiculo;
    } // Constructor de la clase PruebaServiceImpl.

    /**
     * Metodo para eliminar una prueba de la base de datos.
     * @param id El ID de la prueba a eliminar.
     * @return La prueba eliminada.
     */
    public Prueba delete(Integer id){
        Prueba prueba = this.RepositoryPrueba.findById(id).orElseThrow();
        this.RepositoryPrueba.delete(prueba);
        return prueba;
    }

    /**
     * Metodo para agregar una prueba a la base de datos.
     * @param prueba La prueba a agregar.
     */
    public void add(Prueba prueba){
        this.RepositoryPrueba.save(prueba);
    }

    /**
     * Metodo para actualizar una prueba en la base de datos.
     * @param prueba La prueba a actualizar.
     */
    public void update(Prueba prueba){
        this.RepositoryPrueba.save(prueba);
    }

    /**
     * Metodo para encontrar todas las pruebas en la base de datos.
     * @return Lista de todas las pruebas.
     */
    public List<Prueba> findAll(){
        return this.RepositoryPrueba.findAll();
    }

    /**
     * Metodo para encontrar una prueba por su ID.
     * @param id El ID de la prueba a encontrar.
     * @return La prueba encontrada.
     */
    public Prueba findById(Integer id){
        return this.RepositoryPrueba.findById(id).orElseThrow();
    }

    /**
     * Metodo para listar todas las pruebas en curso.
     * @return Lista de pruebas en curso.
     */
    public List<Prueba> listadoPruebasEnCurso() {
        return this.RepositoryPrueba.findAll()
                .stream()
                .filter(prueba -> prueba.getFechaFin().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    /**
     * Metodo para crear una nueva prueba.
     * @param pb La prueba a crear.
     */
    public void crearPrueba(Prueba pb){
        Interesado interesado = RepositoryInteresado.findById(pb.getInteresado().getId())
                .orElseThrow(() -> new PruebaException("El cliente no fue encontrado"));

        if (interesado.getFechaVencimientoLicencia().isBefore(LocalDateTime.now())){
            throw new PruebaException("Oh no! La licencia del cliente esta vencida");
        }

        if (interesado.getRestringido()){
            throw new PruebaException("El cliente esta en calidad de restringido");
        }

        Vehiculo vehiculo = ServiceVehiculo.findById(pb.getVehiculo().getId());

        boolean vehiculoEnPrueba = vehiculo.getPruebasVehiculo().stream()
                .anyMatch(v -> (v.getFechaFin().isAfter(LocalDateTime.now())));
        if (vehiculoEnPrueba){
            throw new PruebaException("El vehiculo posee una prueba en calidad de asignada!");
        }

        Empleado empleado = RepositoryEmpleado.findById(pb.getEmpleado().getLegajo())
                .orElseThrow(() -> new PruebaException("El empleado no fue encontrado"));

        Prueba prueba = new Prueba(vehiculo, interesado, empleado, pb.getFechaInicio(), pb.getFechaFin());
        interesado.getPruebasInteresado().add(prueba);
        vehiculo.getPruebasVehiculo().add(prueba);
        empleado.getPruebasEmpleado().add(prueba);

        add(prueba);
    }

    /**
     * Metodo para verificar y enviar una notificación si un vehículo excede el límite permitido.
     * @param idDelVehiculo El ID del vehículo a verificar.
     * @return El DTO de alerta de notificación si se envía una notificación, null en caso contrario.
     */
    public NotificacionAlertaDTO verificarEnviarNotificacion(int idDelVehiculo){
        System.out.println("verif antes");
        Vehiculo vehiculo = RepositoryVehiculo.findById(idDelVehiculo)
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
                .orElseThrow(() -> new PruebaException("El vehiculo no posee una prueba en calidad de activa"));

        System.out.println("Prueba: " + prueba);

        boolean estaEnLimite = evaluarPosicion(posicionVehiculo);
        System.out.println("esta en limite: " + estaEnLimite);

        if (!estaEnLimite){
            Incidente incidente = new Incidente(prueba, prueba.getEmpleado());
            ServiceIncident.add(incidente);
            System.out.println("Incidente creado: " + incidente);
            return enviarNotificacion(posicionVehiculo);
        }
        return null;
    }

    /**
     * Metodo para agregar comentarios a una prueba.
     * @param id El ID de la prueba a actualizar.
     * @param cmts Los comentarios a agregar.
     */
    public void agregarComentarios(int id, String cmts){
        Prueba prueba = this.RepositoryPrueba.findById(id)
                .orElseThrow(() -> new PruebaException("La prueba no ah sido encontrada"));

        prueba.setFechaFin(LocalDateTime.now());
        prueba.setComentarios(cmts);

        update(prueba);
    }

    /**
     * Metodo para enviar una alerta de notificación.
     * @param posicion La posición a usar para la notificación.
     * @return El DTO de alerta de notificación si se envía la notificación, null en caso contrario.
     */
    private NotificacionAlertaDTO enviarNotificacion(Posicion posicion){
        System.out.println("service antes" + posicion);
        NotificacionAlertaDTO notificacion = new NotificacionAlertaDTO(
                "Exceso de limite",
                "CUIDADO!! El vehiculo ha excedido el limite permitido",
                posicion.getVehiculo().getId());

        String url = "http://localhost:8084/api/notificaciones";
        try {
            System.out.println("service: " + notificacion);
            TemplateRe.postForObject(url, notificacion, String.class);
            return notificacion;
        } catch (Exception e) {
            System.err.println("Error al enviar la notificación: " + e.getMessage());
            return null;
        }
    }

    /**
     * Metodo para obtener la información de la agencia.
     * @return La información de la agencia.
     */
    private AgenciaDTO obtenerInformacionAgencia(){
        ApiExterna apiExterna = new ApiExterna();
        return apiExterna.getAgenciaInfo();
    }

    /**
     * Metodo para evaluar si la posición de un vehículo está dentro del límite permitido.
     * @param posicion La posición a evaluar.
     * @return True si el vehículo está dentro del límite permitido, false en caso contrario.
     */
    private boolean evaluarPosicion(Posicion posicion){
        Vehiculo vehiculo = RepositoryVehiculo.findById(posicion.getVehiculo().getId())
                .orElseThrow(() -> new PruebaException("El vehículo no fue encontrado"));

        Prueba pruebaActiva = vehiculo.getPruebasVehiculo().stream()
                .filter(p -> p.getFechaFin().isAfter(LocalDateTime.now()))
                .findFirst()
                .orElseThrow(() -> new PruebaException("El vehículo no tiene posee prueba en calidad de activa"));

        return calcularDistancia(posicion.getLatitud(), posicion.getLongitud());
    }

    /**
     * Metodo para calcular la distancia entre la posición de un vehículo y la posición de la agencia.
     * @param latVehiculo La latitud de la posición del vehículo.
     * @param lonVehiculo La longitud de la posición del vehículo.
     * @return True si la distancia está dentro del límite permitido, false en caso contrario.
     */
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
}