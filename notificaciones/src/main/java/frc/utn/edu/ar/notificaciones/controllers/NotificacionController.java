package frc.utn.edu.ar.notificaciones.controllers;

import frc.utn.edu.ar.notificaciones.models.Notificacion;
import frc.utn.edu.ar.notificaciones.servicies.NotificacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @Autowired
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public String mensajeBienvenida() {
        return "Bienvenido a la API de Notificaciones";
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Notificacion>> listarNotificaciones() {
        return new ResponseEntity<>(notificacionService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Notificacion> agregarNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion nueva = notificacionService.crearNotificacion(notificacion);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }
}
