package ar.edu.utn.frc.pruebaAgencia.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Pruebas")
@Data
@NoArgsConstructor
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "ID_INTERESADO", referencedColumnName = "ID")
    private Interesado interesado;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "LEGAJO")
    private Empleado empleado;

    @Column(name = "FECHA_HORA_INICIO", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaInicio;

    @Column(name = "FECHA_HORA_FIN", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaFin;

    @Column(name = "COMENTARIOS")
    private String comentarios;

    public Prueba(Vehiculo vehiculo, Interesado interesado, Empleado empleado, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.vehiculo = vehiculo;
        this.interesado = interesado;
        this.empleado = empleado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Prueba{id=" + id + ", vehiculo=" + (vehiculo != null ? vehiculo.getPatente() : "sin vehículo") +
                ", interesado=" + (interesado != null ? interesado.getNombreInteresado() : "sin interesado") +
                ", empleado=" + (empleado != null ? empleado.getNombreEmpleado() : "sin empleado") +
                ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "}";
    }

}
