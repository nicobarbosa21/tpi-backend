package org.example.notificaciones.DTOS;


public class NotificacionDTO {
    private String mensaje;

    // Constructor con un mensaje
    public NotificacionDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    // Constructor vacio
    public NotificacionDTO(){}

    public String getMensaje() {
        return mensaje;
    }
}
