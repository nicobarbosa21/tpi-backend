package ar.edu.utn.frc.pruebaAgencia.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Posiciones")
@Data
@NoArgsConstructor
public class Posicion {
    @Id
    @GeneratedValue(generator = "posiciones")
    @TableGenerator(name = "posiciones", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue = "id",
            initialValue = 1, allocationSize = 1)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID")
    private Vehiculo vehiculo;

    @Column(name = "FECHA_HORA")
    private LocalDateTime fechaHora;

    @Column(name = "LATITUD")
    private double latitud;

    @Column(name = "LONGITUD")
    private double longitud;

    public Posicion(Vehiculo vehiculo, LocalDateTime fechaHora, double latitud, double longitud) {
        this.vehiculo = vehiculo;
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Posicion{" +
                "id=" + id +
                ", fechaHora=" + fechaHora +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
