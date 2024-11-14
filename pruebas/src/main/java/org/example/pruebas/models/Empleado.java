package org.example.pruebas.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Empleados")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor (force = true)

public class Empleado {
    @Id @Column(name = "legajo")
    private int legajo;

    @Basic @Column(name = "nombre")
    private String nombre;

    @Basic @Column(name = "apellido")
    private String apellido;

    @Basic @Column(name = "telefono_contacto")
    private int telefono;

    @OneToMany(mappedBy = "empleado")
    private Set<Prueba> pruebas;

}
