package ar.edu.utn.frc.pruebas.repositories;

import ar.edu.utn.frc.pruebas.models.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {
}
