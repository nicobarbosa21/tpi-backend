package ar.edu.utn.frc.pruebaAgencia.repositories;

import ar.edu.utn.frc.pruebaAgencia.models.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {
}
