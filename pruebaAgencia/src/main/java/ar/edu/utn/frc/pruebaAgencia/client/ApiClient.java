package ar.edu.utn.frc.pruebaAgencia.client;

import ar.edu.utn.frc.pruebaAgencia.dto.AgenciaDTO;
import ar.edu.utn.frc.pruebaAgencia.dto.CoordenadaDTO;
import ar.edu.utn.frc.pruebaAgencia.dto.ZonaRestringidaDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ApiClient {
    private String urlString = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    public AgenciaDTO getAgenciaInfo(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(urlString, String.class);

            if (response == null){
                throw new RuntimeException("Respuesta de la API nula");
            }

            JSONObject jsonResponse = new JSONObject(response);
            JSONObject coordenadasAgencia = jsonResponse.getJSONObject("coordenadasAgencia");
            double lat = coordenadasAgencia.getDouble("lat");
            double lon = coordenadasAgencia.getDouble("lon");
            int radioAdmitido = jsonResponse.getInt("radioAdmitidoKm");

            // Procesamos las zonas restringidas
            JSONArray zonasRestringidasJson = jsonResponse.getJSONArray("zonasRestringidas");
            List<ZonaRestringidaDTO> zonasRestringidas = new ArrayList<>();

            for (int i = 0; i < zonasRestringidasJson.length(); i++) {
                JSONObject zona = zonasRestringidasJson.getJSONObject(i);
                JSONObject noroeste = zona.getJSONObject("noroeste");
                JSONObject sureste = zona.getJSONObject("sureste");

                zonasRestringidas.add(new ZonaRestringidaDTO(
                        new CoordenadaDTO(noroeste.getDouble("lat"), noroeste.getDouble("lon")),
                        new CoordenadaDTO(sureste.getDouble("lat"), sureste.getDouble("lon"))
                ));
            }

            return new AgenciaDTO(new CoordenadaDTO(lat, lon), radioAdmitido, zonasRestringidas);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener información de la agencia.");
        }
    }
}

