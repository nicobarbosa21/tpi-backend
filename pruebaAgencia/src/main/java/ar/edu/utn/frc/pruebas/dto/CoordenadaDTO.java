package ar.edu.utn.frc.pruebas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoordenadaDTO {
    private double lat; // Latitud del punto geográfico
    private double lon; // Longitud del punto geográfico
}
