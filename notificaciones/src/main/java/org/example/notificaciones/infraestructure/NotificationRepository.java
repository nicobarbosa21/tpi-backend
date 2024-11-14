package org.example.notificaciones.infraestructure;

import org.example.notificaciones.domain.Notificacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notificaciones")

public interface NotificationRepository extends CrudRepository<Notificacion, Long> {

}
