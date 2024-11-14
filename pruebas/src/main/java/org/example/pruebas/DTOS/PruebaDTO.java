package org.example.pruebas.DTOS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PruebaDTO {
    private Integer id;
    private EmpleadoDTO empleadoDTO;
    private InteresadoDTO interesadoDTO;
    private VehiculoDTO vehiculoDTO;
    private Timestamp fechaHoraInicio;
    private String comentarios;
}
