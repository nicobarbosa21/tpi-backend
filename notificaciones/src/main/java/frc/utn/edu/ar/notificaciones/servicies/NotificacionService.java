package frc.utn.edu.ar.notificaciones.servicies;

import frc.utn.edu.ar.notificaciones.models.Notificacion;
import frc.utn.edu.ar.notificaciones.repositories.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public Iterable<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    public Notificacion crearNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }
}
