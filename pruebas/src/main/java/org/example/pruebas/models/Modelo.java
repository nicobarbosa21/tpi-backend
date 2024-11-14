package org.example.pruebas.models;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table (name = "Modelos")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)

public class Modelo {
    @Id @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "modelo")
    private Set<Vehiculo> vehiculos;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @Basic @Column(name = "descripcion")
    private String descripcion;
}
