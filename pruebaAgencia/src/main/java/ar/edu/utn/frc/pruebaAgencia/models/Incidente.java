package ar.edu.utn.frc.pruebaAgencia.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Incidentes")
@Data
@NoArgsConstructor
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_prueba", nullable = false)
    private Prueba prueba;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    public Incidente(Prueba prueba, Empleado empleado) {
        this.prueba = prueba;
        this.empleado = empleado;
    }
}
