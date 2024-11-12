package ar.edu.utn.frc.pruebas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModeloDTO {
    private int id;
    private MarcaDTO marca;
    private String descripcion;
}
