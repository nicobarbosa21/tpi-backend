package ar.edu.utn.frc.pruebas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificacionAlertaDTO {
    private String motivo; // Motivo de la alerta
    private String mensaje; // Mensaje de la alerta que puede incluir detalles especificos
    private int vehiculoId; // Identificador del vehiculo a alertar
}
