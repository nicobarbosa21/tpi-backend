package ar.edu.utn.frc.pruebas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AgenciaDTO {
    private CoordenadaDTO coordenadasAgencia;
    private int radioAdmintido; // Radio de alcance permitido
    private List<ZonaRestringidaDTO> zonasRestringidas; // Lista de las zonas restringidas
}
