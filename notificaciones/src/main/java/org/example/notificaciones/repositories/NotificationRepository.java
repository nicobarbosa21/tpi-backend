package org.example.notificaciones.repositories;

import org.example.notificaciones.domain.Notificacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/notis")

public interface NotificationRepository extends CrudRepository<Notificacion, Long> {

}
