package ar.edu.utn.frc.pruebas.servicies;

import ar.edu.utn.frc.pruebas.models.Modelo;
import ar.edu.utn.frc.pruebas.repositories.ModeloRepository;
import ar.edu.utn.frc.pruebas.servicies.interfaces.ModeloService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloServiceImpl extends ServiceImpl<Modelo, Integer> implements ModeloService {
    private final ModeloRepository modeloRepository;

    public ModeloServiceImpl(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    public void add(Modelo modelo){
        this.modeloRepository.save(modelo);
    }

    public void update(Modelo modelo){
        this.modeloRepository.save(modelo);
    }

    public Modelo delete(Integer id){
        Modelo modelo = this.modeloRepository.findById(id).orElseThrow();
        this.modeloRepository.delete(modelo);
        return modelo;
    }

    public Modelo findById(Integer id){
        return this.modeloRepository.findById(id).orElseThrow();
    }

    public List<Modelo> findAll(){
        return this.modeloRepository.findAll();
    }
}
