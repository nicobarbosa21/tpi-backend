package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.models.Modelo;
import ar.edu.utn.frc.pruebas.repositories.ModeloRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.ModeloService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloServiceImpl extends ServiceImpl<Modelo, Integer> implements ModeloService {
    private final ModeloRepository modeRepository; // Repositorio de modelos para acceder a la base de datos de modelos de la agencia de viajes.

    public ModeloServiceImpl(ModeloRepository modeRepository) {
        this.modeRepository = modeRepository;
    } // Constructor de la clase ModeloServiceImpl que recibe un repositorio de modelos.

    /**
     * Metodo que elimina un modelo de la base de datos de modelos de la agencia de viajes.
     * Recibe un id de un modelo, busca al modelo con ese id en la base de datos de modelos de la agencia de viajes,
     * lo elimina de la base de datos y devuelve al modelo eliminado.
     *
     * @param id Identificador del modelo a eliminar.
     * @return El modelo eliminado.
     */
    public Modelo delete(Integer id) {
        Modelo modelo = this.modeRepository.findById(id).orElseThrow();
        this.modeRepository.delete(modelo);
        return modelo;
    }

    /**
     * Metodo que agrega un modelo a la base de datos de modelos de la agencia de viajes.
     * Recibe un modelo y lo guarda en la base de datos de modelos.
     *
     * @param modelo El modelo a agregar.
     */
    public void add(Modelo modelo) {
        this.modeRepository.save(modelo);
    }

    /**
     * Metodo que actualiza un modelo en la base de datos de modelos de la agencia de viajes.
     * Recibe un modelo y lo actualiza en la base de datos de modelos de la agencia de viajes.
     *
     * @param modelo El modelo a actualizar.
     */
    public void update(Modelo modelo) {
        this.modeRepository.save(modelo);
    }

    /**
     * Metodo que devuelve una lista con todos los modelos de la base de datos de modelos de la agencia de viajes.
     *
     * @return Lista de todos los modelos.
     */
    public List<Modelo> findAll() {
        return this.modeRepository.findAll();
    }

    /**
     * Metodo que busca un modelo en la base de datos de modelos de la agencia de viajes.
     * Recibe un id de un modelo, busca al modelo con ese id en la base de datos de modelos de la agencia de viajes y lo devuelve.
     *
     * @param id Identificador del modelo a buscar.
     * @return El modelo encontrado.
     */
    public Modelo findById(Integer id) {
        return this.modeRepository.findById(id).orElseThrow();
    }
}