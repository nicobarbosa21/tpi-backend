package org.example.pruebas.models;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table (name = "Marcas")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)

public class Marca {
    @Id @Column(name = "id")
    private int id;

    @Basic @Column(name = "nombre")
    private String name;

    @OneToMany(mappedBy = "marca")
    private Set<Modelo> modelos;
}
