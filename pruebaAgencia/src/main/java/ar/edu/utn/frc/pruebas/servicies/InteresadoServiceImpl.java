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
    private final InteresadoRepository interesadoRepository;
    private PosicionServiceImpl posicionService;

    public InteresadoServiceImpl(InteresadoRepository interesadoRepository, PosicionServiceImpl posicionService) {
        this.interesadoRepository = interesadoRepository;
        this.posicionService = posicionService;
    }
@Transactional
    public void add(Interesado interesado){
        System.out.println("service: " + interesado);
        this.interesadoRepository.save(interesado);
    }

    public void update(Interesado interesado){
        this.interesadoRepository.save(interesado);
    }

    public Interesado delete(Integer id){
        Interesado interesado = this.interesadoRepository.findById(id).orElseThrow();
        this.interesadoRepository.delete(interesado);
        return interesado;
    }

    public Interesado findById(Integer id){
        return this.interesadoRepository.findById(id).orElseThrow();
    }

    public List<Interesado> findAll(){
        return this.interesadoRepository.findAll();
    }

    public void guardarPosicionVehiculo(int idInteresado){
        Interesado interesado = findById(idInteresado);

        List<Prueba> pruebas = interesado.getPruebasInteresado();

        Prueba prueba = pruebas.stream()
                .filter(p -> p.getFechaFin().isAfter(LocalDateTime.now()))
                .findFirst()
                .orElse(null);

        if (prueba == null){
            throw new PruebaException("El interesado no tiene una prueba activa");
        } else {
            double latitud = 42 + Math.random();
            double longitud = 1 + Math.random();

            Posicion posicion = new Posicion(prueba.getVehiculo(), LocalDateTime.now(), latitud, longitud);
            posicionService.add(posicion);
        }
    }
}
