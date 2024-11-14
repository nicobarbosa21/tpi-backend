package org.example.pruebas.models;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table (name = "Posiciones")
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)

public class Posicion {
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    @Basic @Column(name = "fecha_hora")
    private Timestamp fechaHora;

    @Basic @Column(name = "latitud")
    private Double latitud;

    @Basic @Column(name = "longitud")
    private Double longitud;

    public Posicion(Vehiculo vehiculo, Timestamp fechaHora, Double latitud, Double longitud) {
        this.vehiculo = vehiculo;
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
