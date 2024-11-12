package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.models.Empleado;
import ar.edu.utn.frc.pruebas.repositories.EmpleadoRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.EmpleadoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl extends ServiceImpl<Empleado, Integer> implements EmpleadoService {
    private final EmpleadoRepository empleRepository; // Repositorio de empleados para acceder a la base de datos de empleados de la agencia de viajes.

    public EmpleadoServiceImpl(EmpleadoRepository empleRepository) {
        this.empleRepository = empleRepository;
    } // Constructor de la clase EmpleadoServiceImpl que recibe un repositorio de empleados. No retorna nada.

    /**
     * Metodo que elimina un empleado de la base de datos de empleados de la agencia de viajes.
     * Recibe un id de un empleado, busca al empleado con ese id en la base de datos de empleados de la agencia de viajes,
     * lo elimina de la base de datos y devuelve al empleado eliminado.
     *
     * @param id Identificador del empleado a eliminar.
     * @return El empleado eliminado.
     */
    public Empleado delete(Integer id){
        Empleado empleado = this.empleRepository.findById(id).orElseThrow();
        this.empleRepository.delete(empleado);
        return empleado;
    }

    /**
     * Metodo que actualiza un empleado en la base de datos de empleados de la agencia de viajes.
     * Recibe un empleado y lo actualiza en la base de datos de empleados de la agencia de viajes.
     *
     * @param empleado El empleado a actualizar.
     */
    public void update(Empleado empleado){
        this.empleRepository.save(empleado);
    }

    /**
     * Metodo que devuelve una lista con todos los empleados de la base de datos de empleados de la agencia de viajes.
     *
     * @return Lista de todos los empleados.
     */
    public List<Empleado> findAll(){
        return this.empleRepository.findAll();
    }

    /**
     * Metodo que agrega un empleado a la base de datos de empleados de la agencia de viajes.
     * Recibe un empleado y lo guarda en la base de datos de empleados.
     * No retorna nada.
     *
     * @param empleado El empleado a agregar.
     */
    public void add(Empleado empleado){
        this.empleRepository.save(empleado);
    }

    /**
     * Metodo que busca un empleado en la base de datos de empleados de la agencia de viajes.
     * Recibe un id de un empleado, busca al empleado con ese id en la base de datos de empleados de la agencia de viajes y lo devuelve.
     *
     * @param id Identificador del empleado a buscar.
     * @return El empleado encontrado.
     */
    public Empleado findById(Integer id){
        return this.empleRepository.findById(id).orElseThrow();
    }
}