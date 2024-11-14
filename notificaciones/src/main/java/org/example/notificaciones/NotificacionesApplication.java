package org.example.notificaciones;

import org.example.notificaciones.domain.Notificacion;

import org.example.notificaciones.infraestructure.NotificationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// PARA PROBARLO
@SpringBootApplication
public class NotificacionesApplication {
	public static void main(String[] args) {

		SpringApplication.run(NotificacionesApplication.class, args);

	}
}
