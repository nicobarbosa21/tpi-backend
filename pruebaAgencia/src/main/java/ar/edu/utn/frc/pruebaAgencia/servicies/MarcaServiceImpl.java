package ar.edu.utn.frc.pruebaAgencia.servicies;

import ar.edu.utn.frc.pruebaAgencia.models.Marca;
import ar.edu.utn.frc.pruebaAgencia.repositories.MarcaRepository;
import ar.edu.utn.frc.pruebaAgencia.servicies.interfaces.MarcaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl extends ServiceImpl<Marca, Integer> implements MarcaService {
    private final MarcaRepository marcaRepository;

    public MarcaServiceImpl(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public void add(Marca marca){
        this.marcaRepository.save(marca);
    }

    public void update(Marca marca){
        this.marcaRepository.save(marca);
    }

    public Marca delete(Integer id){
        Marca marca = this.marcaRepository.findById(id).orElseThrow();
        this.marcaRepository.delete(marca);
        return marca;
    }

    public Marca findById(Integer id){
        return this.marcaRepository.findById(id).orElseThrow();
    }

    public List<Marca> findAll(){
        return this.marcaRepository.findAll();
    }
}
