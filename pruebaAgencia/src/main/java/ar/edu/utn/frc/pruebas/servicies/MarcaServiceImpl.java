package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.models.Marca;
import ar.edu.utn.frc.pruebas.repositories.MarcaRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.MarcaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl extends ServiceImpl<Marca, Integer> implements MarcaService {
    private final MarcaRepository marcRepository; // Repositorio de marcas para acceder a la base de datos de marcas de la agencia de viajes.

    public MarcaServiceImpl(MarcaRepository marcRepository) {
        this.marcRepository = marcRepository;
    } // Constructor de la clase MarcaServiceImpl que recibe un repositorio de marcas.

    /**
     * Metodo que agrega una marca a la base de datos de marcas de la agencia de viajes.
     * Recibe una marca y la guarda en la base de datos de marcas.
     *
     * @param marca La marca a agregar.
     */
    public void add(Marca marca) {
        this.marcRepository.save(marca);
    }

    /**
     * Metodo que elimina una marca de la base de datos de marcas de la agencia de viajes.
     * Recibe un id de una marca, busca a la marca con ese id en la base de datos de marcas de la agencia de viajes,
     * la elimina de la base de datos y devuelve a la marca eliminada.
     *
     * @param id Identificador de la marca a eliminar.
     * @return La marca eliminada.
     */
    public Marca delete(Integer id) {
        Marca marca = this.marcRepository.findById(id).orElseThrow();
        this.marcRepository.delete(marca);
        return marca;
    }

    /**
     * Metodo que actualiza una marca en la base de datos de marcas de la agencia de viajes.
     * Recibe una marca y la actualiza en la base de datos de marcas de la agencia de viajes.
     *
     * @param marca La marca a actualizar.
     */
    public void update(Marca marca) {
        this.marcRepository.save(marca);
    }

    /**
     * Metodo que busca una marca en la base de datos de marcas de la agencia de viajes.
     * Recibe un id de una marca, busca a la marca con ese id en la base de datos de marcas de la agencia de viajes y la devuelve.
     *
     * @param id Identificador de la marca a buscar.
     * @return La marca encontrada.
     */
    public Marca findById(Integer id) {
        return this.marcRepository.findById(id).orElseThrow();
    }

    /**
     * Metodo que devuelve una lista con todas las marcas de la base de datos de marcas de la agencia de viajes.
     *
     * @return Lista de todas las marcas.
     */
    public List<Marca> findAll() {
        return this.marcRepository.findAll();
    }
}