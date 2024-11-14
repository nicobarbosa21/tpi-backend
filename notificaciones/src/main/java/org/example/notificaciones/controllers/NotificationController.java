package org.example.notificaciones.controllers;

import org.example.notificaciones.DTOS.NotificacionDTO;
import org.example.notificaciones.domain.Notificacion;
import org.example.notificaciones.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/notis")
public class NotificationController {

    private final NotificationRepository notiRepository;

    @Autowired
    public NotificationController(NotificationRepository notiRepository) {
        this.notiRepository = notiRepository;
    }

    @PostMapping("/promotion")
    public ResponseEntity<String> saveNotification(@RequestBody NotificacionDTO notidto) {
        try {
            String msj =  notidto.getMsj();
            String type= "PROMOCION";
            Notificacion noti = new Notificacion(msj,type);
            notiRepository.save(noti);
            return ResponseEntity.ok("La notificacion se ha guardado de forma correcta");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/warning")
    public ResponseEntity<String> saveNotification(@RequestBody Notificacion noti) {
       try {
           notiRepository.save(noti);
           return ResponseEntity.ok("La notificacion se ha guardado de forma correcta");
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }


}