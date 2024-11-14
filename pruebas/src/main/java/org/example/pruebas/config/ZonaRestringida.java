package org.example.pruebas.config;


import lombok.Data;

@Data
public class ZonaRestringida {
    private Coordenadas noroeste;
    private Coordenadas sureste;

}