package ar.edu.utn.frc.pruebaAgencia.repositories;

import ar.edu.utn.frc.pruebaAgencia.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
}
