package frc.utn.edu.ar.notificaciones.repositories;

import frc.utn.edu.ar.notificaciones.models.Notificacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends CrudRepository<Notificacion, Long> {
}
