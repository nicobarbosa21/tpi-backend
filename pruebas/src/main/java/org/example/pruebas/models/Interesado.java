package org.example.pruebas.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table (name = "Interesados")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)

public class Interesado {
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic @Column(name = "tipo_documento")
    private String tipo_doc;

    @Basic @Column(name = "documento")
    private String documento;

    @Basic @Column(name = "nombre")
    private String nombre;

    @Basic @Column(name = "apellido")
    private String apellido;

    @Basic @Column(name = "restringido")
    private boolean restringido;

    @Basic @Column(name = "nro_licencia")
    private Integer nro_licencia;

    @Basic @Column(name = "fecha_vencimiento_licencia")
    private Timestamp vencimiento;

    @OneToMany(mappedBy = "interesado")
    private Set<Prueba> pruebas;

    public void setRestringido(boolean restringido) {
        this.restringido = restringido;
    }
}
