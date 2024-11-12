package ar.edu.utn.frc.pruebas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ZonaRestringidaDTO {
    private CoordenadaDTO noroeste; // Delimita el extremo superior izquierdo de la zona.
    private CoordenadaDTO sureste; // Delimita el extremo inferior derecho de la zona.
}
