package ar.edu.utn.frc.pruebaAgencia.repositories;

import ar.edu.utn.frc.pruebaAgencia.models.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Integer> {
}
