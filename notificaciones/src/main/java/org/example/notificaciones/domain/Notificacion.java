package org.example.notificaciones.domain;

import jakarta.persistence.*;

@Entity
@Table(name="notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String mensaje;
    private String tipo;

    public Notificacion(String mensaje, String tipo) {
        this.mensaje = mensaje;
        this.tipo = tipo;
    }

    public Notificacion() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "mensaje='" + mensaje + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
