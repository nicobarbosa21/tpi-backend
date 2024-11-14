package org.example.notificaciones.DTOS;


public class NotificacionDTO {
    private String mensaje;

    public NotificacionDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public NotificacionDTO(){}
}
