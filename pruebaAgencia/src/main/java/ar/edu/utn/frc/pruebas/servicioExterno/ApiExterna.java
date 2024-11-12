package ar.edu.utn.frc.pruebas.servicioExterno;

import ar.edu.utn.frc.pruebas.dto.AgenciaDTO;
import ar.edu.utn.frc.pruebas.dto.CoordenadaDTO;
import ar.edu.utn.frc.pruebas.dto.ZonaRestringidaDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ApiExterna {
    public AgenciaDTO getAgenciaInfo(){
        try {
            //REST Template es una clase que nos permite hacer peticiones HTTP a un servidor
            RestTemplate restTemplate = new RestTemplate();
            String URL_API = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

            //Hacemos una peticion GET a la URL de la API y guardamos la respuesta en un String llamado response
            String elementosAPI = restTemplate.getForObject(URL_API, String.class);

            //Procesamiento de la respuesta de la API
            JSONObject jsonRespuesta = new JSONObject(elementosAPI);

            //Primera parte del servicio externo
            JSONObject coordenadasAgencia = jsonRespuesta.getJSONObject("coordenadasAgencia");
            double lat = coordenadasAgencia.getDouble("lat");
            double lon = coordenadasAgencia.getDouble("lon");
            int radioAdmitido = jsonRespuesta.getInt("radioAdmitidoKm");

            //Segunda parte del servicio externo
            JSONArray zonasRestringidas = jsonRespuesta.getJSONArray("zonasRestringidas");
            List<ZonaRestringidaDTO> zonas = new ArrayList<>();

            //Se itera el array de zonas restringidas para obtener cada una de ellas por iteracion (si cumplen con ser noroeste y sureste) y se agregan a la lista de zonas
            for (Object obj : zonasRestringidas) {
                JSONObject zona = (JSONObject) obj;
                JSONObject noroeste = zona.getJSONObject("noroeste");
                JSONObject sureste = zona.getJSONObject("sureste");

                // Se crea un objeto ZonaRestringidaDTO con las coordenadas noroeste y sureste de cada zona restringida
                zonas.add(new ZonaRestringidaDTO(
                        new CoordenadaDTO(noroeste.getDouble("lat"), noroeste.getDouble("lon")),
                        new CoordenadaDTO(sureste.getDouble("lat"), sureste.getDouble("lon"))
                ));
            }
            /*si falla reemplazar con:
            * for (int i = 0; i < zonasRestringidas.length(); i++) {
            * JSONObject zona = zonasRestringidas.getJSONObject(i);
            * JSONObject noroeste = zona.getJSONObject("noroeste");
            * JSONObject sureste = zona.getJSONObject("sureste");
            *
            //Se crea un objeto ZonaRestringidaDTO con las coordenadas noroeste y sureste de cada zona restringida
            zonas.add(new ZonaRestringidaDTO(
                    new CoordenadaDTO(noroeste.getDouble("lat"), noroeste.getDouble("lon")),
                    new CoordenadaDTO(sureste.getDouble("lat"), sureste.getDouble("lon"))
            ));
        }*/

            return new AgenciaDTO(new CoordenadaDTO(lat, lon), radioAdmitido, zonas);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error de recuperación de informacion");
        }
    }
}

