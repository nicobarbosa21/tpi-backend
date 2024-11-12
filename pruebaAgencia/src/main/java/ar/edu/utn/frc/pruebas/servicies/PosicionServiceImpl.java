package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.models.Posicion;
import ar.edu.utn.frc.pruebas.repositories.PosicionRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.PosicionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosicionServiceImpl extends ServiceImpl<Posicion, Integer> implements PosicionService {
    private final PosicionRepository positionRepository; // Repositorio de posiciones para acceder a la base de datos de posiciones de la agencia de viajes.

    public PosicionServiceImpl(PosicionRepository positionRepository) {
        this.positionRepository = positionRepository;
    } // Constructor de la clase PosicionServiceImpl que recibe un repositorio de posiciones.

    /**
     * Metodo que actualiza una posicion en la base de datos de posiciones de la agencia de viajes.
     * Recibe una posicion y la actualiza en la base de datos de posiciones de la agencia de viajes.
     *
     * @param posicion La posicion a actualizar.
     */
    public void update(Posicion posicion) {
        this.positionRepository.save(posicion);
    }

    /**
     * Metodo que agrega una posicion a la base de datos de posiciones de la agencia de viajes.
     * Recibe una posicion y la guarda en la base de datos de posiciones.
     *
     * @param posicion La posicion a agregar.
     */
    public void add(Posicion posicion) {
        this.positionRepository.save(posicion);
    }

    /**
     * Metodo que elimina una posicion de la base de datos de posiciones de la agencia de viajes.
     * Recibe un id de una posicion, busca a la posicion con ese id en la base de datos de posiciones de la agencia de viajes,
     * la elimina de la base de datos y devuelve a la posicion eliminada.
     *
     * @param id Identificador de la posicion a eliminar.
     * @return La posicion eliminada.
     */
    public Posicion delete(Integer id) {
        Posicion posicion = this.positionRepository.findById(id).orElseThrow();
        this.positionRepository.delete(posicion);
        return posicion;
    }

    /**
     * Metodo que devuelve una lista con todas las posiciones de la base de datos de posiciones de la agencia de viajes.
     *
     * @return Lista de todas las posiciones.
     */
    public List<Posicion> findAll() {
        return this.positionRepository.findAll();
    }

    /**
     * Metodo que busca una posicion en la base de datos de posiciones de la agencia de viajes.
     * Recibe un id de una posicion, busca a la posicion con ese id en la base de datos de posiciones de la agencia de viajes y la devuelve.
     *
     * @param id Identificador de la posicion a buscar.
     * @return La posicion encontrada.
     */
    public Posicion findById(Integer id) {
        return this.positionRepository.findById(id).orElseThrow();
    }
}
