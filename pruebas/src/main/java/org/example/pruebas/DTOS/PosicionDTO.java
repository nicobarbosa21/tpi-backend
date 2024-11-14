package org.example.pruebas.DTOS;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosicionDTO {
    private Integer id_vehiculo;
    private Double latitud;
    private Double longitud;
}
