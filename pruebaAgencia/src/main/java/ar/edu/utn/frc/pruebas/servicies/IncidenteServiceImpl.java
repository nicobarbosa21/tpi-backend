package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.models.Incidente;
import ar.edu.utn.frc.pruebas.models.Prueba;
import ar.edu.utn.frc.pruebas.repositories.IncidenteRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.IncidenteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidenteServiceImpl extends ServiceImpl<Incidente, Integer> implements IncidenteService {
    private final IncidenteRepository incidenteRepository; // Repositorio de incidentes para acceder a la base de datos de incidentes de la agencia de viajes.

    public IncidenteServiceImpl(IncidenteRepository incidenteRepository) {
        this.incidenteRepository = incidenteRepository;
    } // Constructor de la clase IncidenteServiceImpl que recibe un repositorio de incidentes.

    /**
     * Metodo que agrega un incidente a la base de datos de incidentes de la agencia de viajes.
     * Recibe un incidente y lo guarda en la base de datos de incidentes.
     *
     * @param incidente El incidente a agregar.
     */
    @Override
    public void add(Incidente incidente) {
        this.incidenteRepository.save(incidente);
    }

    /**
     * Metodo que devuelve una lista con todas las pruebas de la base de datos de incidentes de la agencia de viajes.
     *
     * @return Lista de todas las pruebas.
     */
    public List<Prueba> listadoPruebas() {
        return this.incidenteRepository.findAll()
                .stream()
                .map(Incidente::getPrueba)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Metodo que busca un incidente en la base de datos de incidentes de la agencia de viajes.
     * Recibe un id de un incidente, busca al incidente con ese id en la base de datos de incidentes de la agencia de viajes y lo devuelve.
     *
     * @param id Identificador del incidente a buscar.
     * @return El incidente encontrado.
     */
    public Incidente findById(Integer id) {
        return this.incidenteRepository.findById(id).orElseThrow();
    }

    /**
     * Metodo que actualiza un incidente en la base de datos de incidentes de la agencia de viajes.
     * Recibe un incidente y lo actualiza en la base de datos de incidentes de la agencia de viajes.
     *
     * @param incidente El incidente a actualizar.
     */
    @Override
    public void update(Incidente incidente) {
        this.incidenteRepository.save(incidente);
    }

    /**
     * Metodo que devuelve una lista con todos los incidentes de la base de datos de incidentes de la agencia de viajes.
     *
     * @return Lista de todos los incidentes.
     */
    public List<Incidente> findAll() {
        return this.incidenteRepository.findAll();
    }

    /**
     * Metodo que elimina un incidente de la base de datos de incidentes de la agencia de viajes.
     * Recibe un id de un incidente, busca al incidente con ese id en la base de datos de incidentes de la agencia de viajes,
     * lo elimina de la base de datos y devuelve al incidente eliminado.
     *
     * @param id Identificador del incidente a eliminar.
     * @return El incidente eliminado.
     */
    public Incidente delete(Integer id) {
        Incidente incidente = this.incidenteRepository.findById(id).orElseThrow();
        this.incidenteRepository.delete(incidente);
        return incidente;
    }

    /**
     * Metodo que devuelve una lista con todas las pruebas de la base de datos de incidentes de la agencia de viajes
     * que fueron realizadas por un empleado.
     *
     * @param idEmpleado Identificador del empleado.
     * @return Lista de pruebas realizadas por el empleado.
     */
    public List<Prueba> listadoPruebasEmpleado(int idEmpleado) {
        List<Prueba> pruebas = listadoPruebas();
        return pruebas.stream()
                .filter(p -> p.getEmpleado().getLegajo() == idEmpleado)
                .collect(Collectors.toList());
    }
}