package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.exceptions.PruebaException;
import ar.edu.utn.frc.pruebas.models.Vehiculo;
import ar.edu.utn.frc.pruebas.repositories.VehiculoRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.VehiculoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceImpl extends ServiceImpl<Vehiculo, Integer> implements VehiculoService {
    private final VehiculoRepository RepositoryVehiculo; // Repositorio para acceder a los datos de vehículos.

    public VehiculoServiceImpl(VehiculoRepository RepositoryVehiculo) {
        this.RepositoryVehiculo = RepositoryVehiculo;
    } // Constructor de la clase VehiculoServiceImpl.

    /**
     * Metodo para encontrar todos los vehículos en la base de datos.
     * @return Lista de todos los vehículos.
     */
    public List<Vehiculo> findAll(){
        return this.RepositoryVehiculo.findAll();
    }

    /**
     * Metodo para encontrar un vehículo por su ID.
     * @param id El ID del vehículo a encontrar.
     * @return El vehículo encontrado.
     */
    public Vehiculo findById(Integer id){
        return this.RepositoryVehiculo.findById(id).orElseThrow(() -> new PruebaException("Vehiculo no encontrado!"));
    }

    /**
     * Metodo para agregar un vehículo a la base de datos.
     * @param vehiculo El vehículo a agregar.
     */
    public void add(Vehiculo vehiculo){
        this.RepositoryVehiculo.save(vehiculo);
    }

    /**
     * Metodo para eliminar un vehículo de la base de datos.
     * @param id El ID del vehículo a eliminar.
     * @return El vehículo eliminado.
     */
    public Vehiculo delete(Integer id){
        Vehiculo vehiculo = this.RepositoryVehiculo.findById(id).orElseThrow();
        this.RepositoryVehiculo.delete(vehiculo);
        return vehiculo;
    }

    /**
     * Metodo para actualizar un vehículo en la base de datos.
     * @param vehiculo El vehículo a actualizar.
     */
    public void update(Vehiculo vehiculo){
        this.RepositoryVehiculo.save(vehiculo);
    }
}