package org.example.pruebas.services;

import org.example.pruebas.config.Notificacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacionService {

    private static final String CONFIG_URL = "http://localhost:8050/api/notis/warning";

    public void enviarNotificacion(Notificacion notificacion) {
        try {
            // Crear una nueva instancia de RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Enviar la notificación y obtener la respuesta
            ResponseEntity<Void> response = restTemplate.postForEntity(CONFIG_URL, notificacion, Void.class);

            // Verificar el código de estado de la respuesta
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Notificación enviada exitosamente.");
            } else {
                System.err.println("Error al enviar la notificación. Código de respuesta: " + response.getStatusCode());
            }

        } catch (Exception e) {
            // Imprimir la traza completa del error
            e.printStackTrace();
            System.err.println("Error al enviar la notificación: " + e.getMessage());
        }
    }
}
