package ar.edu.utn.frc.pruebaAgencia.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Vehiculos")
@Data
@NoArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(generator = "vehiculos")
    @TableGenerator(name = "vehiculos", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue = "ID",
            initialValue = 1, allocationSize = 1)
    private int id;

    @Column(name = "PATENTE")
    private String patente;

    @ManyToOne
    @JoinColumn(name = "ID_MODELO", referencedColumnName = "id")
    private Modelo modelo;

    @Column(name = "ANIO")
    private int anio;

    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Prueba> pruebasVehiculo;

    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Posicion> posicionesVehiculo;


    // En Vehiculo
    @Override
    public String toString() {
        return "Vehiculo{id=" + id + ", patente=" + patente + ", modelo=" + (modelo != null ? modelo.getDescripcion() : "sin modelo") + "posiciones" + posicionesVehiculo +"}";
    }




}