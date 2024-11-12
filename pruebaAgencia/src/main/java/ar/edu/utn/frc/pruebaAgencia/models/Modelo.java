package ar.edu.utn.frc.pruebaAgencia.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Modelos")
@Data
@NoArgsConstructor
public class Modelo {
    @Id
    @GeneratedValue(generator = "modelos")
    @TableGenerator(name = "modelos", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue = "ID",
            initialValue = 1, allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_MARCA", referencedColumnName = "ID")
    private Marca marca;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @OneToMany(mappedBy = "modelo", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculos;

    // En Modelo
    @Override
    public String toString() {
        return "Modelo{id=" + id + ", descripcion=" + descripcion + ", marca=" + (marca != null ? marca.getNombre() : "sin marca") + "}";
    }
}