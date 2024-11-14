package org.example.pruebas.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table (name = "Vehiculos")
@Getter
@Setter
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)


public class Vehiculo {
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic @Column(name = "patente")
    private String patente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;

    @OneToMany(mappedBy = "vehiculo")
    private Set<Posicion> posiciones;

    @Basic @Column (name = "anio")
    private int anio;

}
