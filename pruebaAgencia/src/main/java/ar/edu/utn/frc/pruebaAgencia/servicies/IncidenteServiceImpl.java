package ar.edu.utn.frc.pruebaAgencia.servicies;

import ar.edu.utn.frc.pruebaAgencia.models.Incidente;
import ar.edu.utn.frc.pruebaAgencia.models.Prueba;
import ar.edu.utn.frc.pruebaAgencia.repositories.IncidenteRepository;
import ar.edu.utn.frc.pruebaAgencia.servicies.interfaces.IncidenteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidenteServiceImpl extends ServiceImpl<Incidente, Integer> implements IncidenteService {
    private final IncidenteRepository incidenteRepository;

    public IncidenteServiceImpl(IncidenteRepository incidenteRepository) {
        this.incidenteRepository = incidenteRepository;
    }

    @Override
    public void add(Incidente incidente) {
        this.incidenteRepository.save(incidente);
    }

    @Override
    public void update(Incidente incidente) {
        this.incidenteRepository.save(incidente);
    }

    public Incidente delete(Integer id){
        Incidente incidente = this.incidenteRepository.findById(id).orElseThrow();
        this.incidenteRepository.delete(incidente);
        return incidente;
    }

    public Incidente findById(Integer id){
        return this.incidenteRepository.findById(id).orElseThrow();
    }

    public List<Incidente> findAll(){
        return this.incidenteRepository.findAll();
    }

    public List<Prueba> listadoPruebas() {
        return this.incidenteRepository.findAll()
                .stream()
                .map(Incidente::getPrueba)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Prueba> listadoPruebasEmpleado(int idEmpleado){
        List<Prueba> pruebas = listadoPruebas();
        return pruebas.stream()
                .filter(p -> p.getEmpleado().getLegajo() == idEmpleado)
                .collect(Collectors.toList());
    }
}
