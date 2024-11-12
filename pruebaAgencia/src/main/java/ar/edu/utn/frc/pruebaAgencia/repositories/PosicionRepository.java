package ar.edu.utn.frc.pruebaAgencia.repositories;

import ar.edu.utn.frc.pruebaAgencia.models.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
}
