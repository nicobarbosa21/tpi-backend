package ar.edu.utn.frc.pruebaAgencia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PosicionVehiculoDTO {
    private int vehiculoId;
    private int latitud;
    private int longitud;
}
