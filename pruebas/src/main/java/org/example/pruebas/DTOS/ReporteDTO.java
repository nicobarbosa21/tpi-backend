package org.example.pruebas.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReporteDTO {
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
}
