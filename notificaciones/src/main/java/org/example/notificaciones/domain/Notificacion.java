package org.example.notificaciones.domain;

import jakarta.persistence.*;

@Entity
@Table(name="notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String msj;
    private String type;

    public Notificacion(String msj, String type) {
        this.msj = msj;
        this.type = type;
    }

    public Notificacion() {
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String mensaje) {
        this.msj = mensaje;
    }

    public String getType() {
        return type;
    }

    public void setType(String tipo) {
        this.type = tipo;
    }

    public Long getId() {
        return id;
    }


    @Override
    public String toString() { // este metodo es para que se muestre de forma mas legible en consola
        return "Notificacion{" +
                "mensaje='" + msj + '\'' +
                ", tipo='" + type + '\'' +
                '}';
    }
}
