package ar.edu.utn.frc.pruebaAgencia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PruebaDTO {
    private int id;
    private LocalDateTime fechaFin;
    private InteresadoDTO interesado;
    private VehiculoDTO vehiculo;

    public PruebaDTO(int id, LocalDateTime fechaFin, InteresadoDTO interesado) {
        this.id = id;
        this.fechaFin = fechaFin;
        this.interesado = interesado;
    }
}
