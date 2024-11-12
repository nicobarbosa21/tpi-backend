package ar.edu.utn.frc.pruebaAgencia.repositories;

import ar.edu.utn.frc.pruebaAgencia.models.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Integer> {
}
