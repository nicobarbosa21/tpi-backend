package ar.edu.utn.frc.pruebas.repositories;

import ar.edu.utn.frc.pruebas.models.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Integer> {
}
