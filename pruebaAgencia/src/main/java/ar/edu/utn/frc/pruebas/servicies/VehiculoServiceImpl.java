package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.exceptions.PruebaException;
import ar.edu.utn.frc.pruebas.models.Vehiculo;
import ar.edu.utn.frc.pruebas.repositories.VehiculoRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.VehiculoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceImpl extends ServiceImpl<Vehiculo, Integer> implements VehiculoService {
    private final VehiculoRepository vehiculoRepository;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public void add(Vehiculo vehiculo){
        this.vehiculoRepository.save(vehiculo);
    }

    public void update(Vehiculo vehiculo){
        this.vehiculoRepository.save(vehiculo);
    }

    public Vehiculo delete(Integer id){
        Vehiculo vehiculo = this.vehiculoRepository.findById(id).orElseThrow();
        this.vehiculoRepository.delete(vehiculo);
        return vehiculo;
    }

    public Vehiculo findById(Integer id){
        return this.vehiculoRepository.findById(id).orElseThrow(() -> new PruebaException("Vehiculo no encontrado!"));
    }

    public List<Vehiculo> findAll(){
        return this.vehiculoRepository.findAll();
    }
}
