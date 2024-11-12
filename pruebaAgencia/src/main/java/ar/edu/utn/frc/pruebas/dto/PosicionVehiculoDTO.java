package ar.edu.utn.frc.pruebas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PosicionVehiculoDTO {
    private int vehiculoId;
    private double latitud;
    private double longitud;
}
