package ar.edu.utn.frc.pruebaAgencia.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Marcas")
@Data
@NoArgsConstructor
public class Marca {
    @Id
    @GeneratedValue(generator = "marcas")
    @TableGenerator(name = "marcas", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue = "ID",
            initialValue = 1, allocationSize = 1)
    private int id;

    @Column(name = "NOMBRE")
    private String nombre;

    @OneToMany(mappedBy = "marca", fetch = FetchType.LAZY)
    private List<Modelo> modelos;
    // En Marca
    @Override
    public String toString() {
        return "Marca{id=" + id + ", nombre=" + nombre + "}";
    }
}
