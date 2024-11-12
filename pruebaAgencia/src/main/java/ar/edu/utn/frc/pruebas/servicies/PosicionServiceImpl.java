package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.models.Posicion;
import ar.edu.utn.frc.pruebas.repositories.PosicionRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.PosicionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosicionServiceImpl extends ServiceImpl<Posicion, Integer> implements PosicionService {
    private final PosicionRepository posicionRepository;

    public PosicionServiceImpl(PosicionRepository posicionRepository) {
        this.posicionRepository = posicionRepository;
    }

    public void add(Posicion posicion){
        this.posicionRepository.save(posicion);
    }

    public void update(Posicion posicion){
        this.posicionRepository.save(posicion);
    }

    public Posicion delete(Integer id){
        Posicion posicion = this.posicionRepository.findById(id).orElseThrow();
        this.posicionRepository.delete(posicion);
        return posicion;
    }

    public Posicion findById(Integer id){
        return this.posicionRepository.findById(id).orElseThrow();
    }

    public List<Posicion> findAll(){
        return this.posicionRepository.findAll();
    }
}
