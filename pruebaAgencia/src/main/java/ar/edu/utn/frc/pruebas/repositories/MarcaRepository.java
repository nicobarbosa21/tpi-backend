package ar.edu.utn.frc.pruebas.repositories;

import ar.edu.utn.frc.pruebas.models.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {
}
