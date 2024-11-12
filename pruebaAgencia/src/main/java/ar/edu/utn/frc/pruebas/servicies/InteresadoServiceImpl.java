package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.exceptions.PruebaException;
import ar.edu.utn.frc.pruebas.models.Interesado;
import ar.edu.utn.frc.pruebas.models.Posicion;
import ar.edu.utn.frc.pruebas.models.Prueba;
import ar.edu.utn.frc.pruebas.repositories.InteresadoRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.InteresadoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InteresadoServiceImpl extends ServiceImpl<Interesado, Integer> implements InteresadoService {
    private final InteresadoRepository interRepository; // Repositorio de interesados para acceder a la base de datos de interesados de la agencia de viajes.
    private PosicionServiceImpl posicionService; // Servicio de posiciones para acceder a la base de datos de posiciones de la agencia de viajes.

    public InteresadoServiceImpl(InteresadoRepository interRepository, PosicionServiceImpl posicionService) {
        this.interRepository = interRepository;
        this.posicionService = posicionService;
    } // Constructor de la clase InteresadoServiceImpl que recibe un repositorio de interesados y un servicio de posiciones.

    /**
     * Metodo que agrega un interesado a la base de datos de interesados de la agencia de viajes.
     * Recibe un interesado y lo guarda en la base de datos de interesados.
     *
     * @param interesado El interesado a agregar.
     */
    @Transactional
    public void add(Interesado interesado) {
        System.out.println("service: " + interesado);
        this.interRepository.save(interesado);
    }

    /**
     * Metodo que devuelve una lista con todos los interesados de la base de datos de interesados de la agencia de viajes.
     *
     * @return Lista de todos los interesados.
     */
    public List<Interesado> findAll() {
        return this.interRepository.findAll();
    }

    /**
     * Metodo que actualiza un interesado en la base de datos de interesados de la agencia de viajes.
     * Recibe un interesado y lo actualiza en la base de datos de interesados de la agencia de viajes.
     *
     * @param interesado El interesado a actualizar.
     */
    public void update(Interesado interesado) {
        this.interRepository.save(interesado);
    }

    /**
     * Metodo que guarda la posicion de un vehiculo en la base de datos de posiciones de la agencia de viajes.
     * Recibe un id de un interesado, busca al interesado con ese id en la base de datos de interesados de la agencia de viajes,
     * busca la prueba activa del interesado, genera una posicion con la latitud y longitud de un vehiculo y la fecha y hora actual
     * y la guarda en la base de datos de posiciones de la agencia de viajes.
     *
     * @param idDelInteresado Identificador del interesado.
     */
    public void guardarPosicionVehiculo(int idDelInteresado) {
        Interesado interesado = findById(idDelInteresado);

        List<Prueba> pruebas = interesado.getPruebasInteresado();

        Prueba prueba = pruebas.stream()
                .filter(p -> p.getFechaFin().isAfter(LocalDateTime.now()))
                .findFirst()
                .orElse(null);

        if (prueba == null) {
            throw new PruebaException("Se ha detectado que el interesado no posee una prueba activa");
        } else {
            double latitud = 42 + Math.random();
            double longitud = 1 + Math.random();

            Posicion position = new Posicion(prueba.getVehiculo(), LocalDateTime.now(), latitud, longitud);
            posicionService.add(position);
        }
    }

    /**
     * Metodo que elimina un interesado de la base de datos de interesados de la agencia de viajes.
     * Recibe un id de un interesado, busca al interesado con ese id en la base de datos de interesados de la agencia de viajes,
     * lo elimina de la base de datos y devuelve al interesado eliminado.
     *
     * @param id Identificador del interesado a eliminar.
     * @return El interesado eliminado.
     */
    public Interesado delete(Integer id) {
        Interesado interesado = this.interRepository.findById(id).orElseThrow();
        this.interRepository.delete(interesado);
        return interesado;
    }


    /**
     * Metodo que busca un interesado en la base de datos de interesados de la agencia de viajes.
     * Recibe un id de un interesado, busca al interesado con ese id en la base de datos de interesados de la agencia de viajes y lo devuelve.
     *
     * @param id Identificador del interesado a buscar.
     * @return El interesado encontrado.
     */
    public Interesado findById(Integer id) {
        return this.interRepository.findById(id).orElseThrow();
    }
}