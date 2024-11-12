package ar.edu.utn.frc.pruebaAgencia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AgenciaDTO {
    private CoordenadaDTO coordenadasAgencia;
    private int radioAdmintido;
    private List<ZonaRestringidaDTO> zonasRestringidas;
}
