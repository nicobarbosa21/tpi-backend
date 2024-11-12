package ar.edu.utn.frc.pruebas.repositories;

import ar.edu.utn.frc.pruebas.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
}
