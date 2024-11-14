package org.example.pruebas.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuracion {
    private Coordenadas coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;

    // Getters y Setters
}

