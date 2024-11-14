package org.example.pruebas.services;
import org.example.pruebas.servExt.Configuracion;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConfiguracionService {

    private static final String CONFIGURACION_URL = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    public Configuracion getConfiguracion() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(CONFIGURACION_URL, Configuracion.class);
    }
}
