package ar.edu.utn.frc.pruebas.repositories;

import ar.edu.utn.frc.pruebas.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
