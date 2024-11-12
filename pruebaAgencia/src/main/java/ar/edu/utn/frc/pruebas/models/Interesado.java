package ar.edu.utn.frc.pruebas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Interesados")
@Data
@NoArgsConstructor
public class Interesado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;

    @Column(name = "DOCUMENTO")
    private String documento;

    @Column(name = "NOMBRE")
    private String nombreInteresado;

    @Column(name = "APELLIDO")
    private String apellidoInteresado;

    @Column(name = "RESTRINGIDO")
    private Boolean restringido;

    @Column(name = "NRO_LICENCIA")
    private int numeroLicencia;

    @Column(name = "FECHA_VENCIMIENTO_LICENCIA", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaVencimientoLicencia;

    @OneToMany(mappedBy = "interesado", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Prueba> pruebasInteresado;
    @Override
    public String toString() {
        return "Interesado{id=" + id + ", nombre=" + nombreInteresado + ", fechaVencimientoLicencia=" + fechaVencimientoLicencia + "}";
    }

}
