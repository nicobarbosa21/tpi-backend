package org.example.pruebas.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table (name = "Pruebas")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)


public class Prueba {
    @Id @GeneratedValue (strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_interesado")
    private Interesado interesado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @Basic @Column(name = "fecha_hora_inicio")
    private Timestamp fecha_hora_inicio;

    @Basic @Column(name = "fecha_hora_fin")
    private Timestamp fecha_hora_fin;

    @Basic @Column(name = "comentarios")
    private String comentarios;

    @Column (name =  "incidente")
    private Boolean inicidente;


    public Prueba(Vehiculo vehiculo, Interesado interesado, Empleado empleado, Timestamp fecha_hora_inicio, Timestamp fecha_hora_fin, String comentarios) {
        this.vehiculo = vehiculo;
        this.interesado = interesado;
        this.empleado = empleado;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fecha_hora_fin;
        this.comentarios = comentarios;
    }

}
