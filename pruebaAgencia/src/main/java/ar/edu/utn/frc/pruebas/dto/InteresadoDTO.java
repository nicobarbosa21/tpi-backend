package ar.edu.utn.frc.pruebas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InteresadoDTO {
    private int id;
    private String nombreInteresado;
    private String apellidoInteresado;
}
