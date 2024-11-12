package ar.edu.utn.frc.pruebas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ZonaRestringidaDTO {
    private CoordenadaDTO noroeste;
    private CoordenadaDTO sureste;
}
