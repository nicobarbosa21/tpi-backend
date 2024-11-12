package ar.edu.utn.frc.pruebaAgencia.servicies;

import ar.edu.utn.frc.pruebaAgencia.models.Empleado;
import ar.edu.utn.frc.pruebaAgencia.repositories.EmpleadoRepository;
import ar.edu.utn.frc.pruebaAgencia.servicies.interfaces.EmpleadoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl extends ServiceImpl<Empleado, Integer> implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public void add(Empleado empleado){
        this.empleadoRepository.save(empleado);
    }

    public void update(Empleado empleado){
        this.empleadoRepository.save(empleado);
    }

    public Empleado delete(Integer id){
        Empleado empleado = this.empleadoRepository.findById(id).orElseThrow();
        this.empleadoRepository.delete(empleado);
        return empleado;
    }

    public Empleado findById(Integer id){
        return this.empleadoRepository.findById(id).orElseThrow();
    }

    public List<Empleado> findAll(){
        return this.empleadoRepository.findAll();
    }
}
