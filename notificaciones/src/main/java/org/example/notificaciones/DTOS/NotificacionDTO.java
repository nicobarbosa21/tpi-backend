package org.example.notificaciones.DTOS;


public class NotificacionDTO {
    private String msj;

    // Constructor con un mensaje
    public NotificacionDTO(String mensaje) {
        this.msj = mensaje;
    }

    // Constructor vacio
    public NotificacionDTO(){}

    public String getMsj() {
        return msj;
    }
}
