package org.example.pruebas.repositories;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.example.pruebas.config.Configuracion;
import org.example.pruebas.models.Posicion;
import org.example.pruebas.services.ConfiguracionService;
import org.example.pruebas.services.PosicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@NoArgsConstructor
@Repository
public class PosicionRepository {
    @PersistenceContext
    private EntityManager em;
    private PruebaRepository pruebaRepository;
    @Autowired
    private ConfiguracionService configuracionService;


    public PosicionRepository(EntityManager em, PruebaRepository pruebaRepository, ConfiguracionService configuracionService, PosicionService posicionService) {
        this.em = em;
        this.pruebaRepository = pruebaRepository;
        this.configuracionService = configuracionService;
    }

    @Transactional
    public Posicion savePosicion(Posicion posicion) {
        em.persist(posicion);
        return posicion;
    }

//    public double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
//        // Fórmula de Haversine para calcular la distancia entre dos puntos geográficos
//        final int EARTH_RADIUS_KM = 6371;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        return EARTH_RADIUS_KM * c;
//    }

    public double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        // Distancia euclídea en un plano
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        return Math.sqrt(dLat * dLat + dLon * dLon);
    }


    // Vehiculo y un tiempo determinado
    public List<Posicion> obtenerPosiciones(Integer idVehiculo, Timestamp fechaInicio, Timestamp fechaFin) {
        try {
            // Consulta JPQL para obtener las posiciones del vehículo en el rango de fechas
            String query = "SELECT p FROM Posicion p WHERE p.vehiculo.id = :idVehiculo " +
                    "AND p.fechaHora BETWEEN :fechaInicio AND :fechaFin";

            return em.createQuery(query, Posicion.class)
                    .setParameter("idVehiculo", idVehiculo)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public Double cantidadKilometros(Integer idVehiculo, Timestamp fechaInicio, Timestamp fechaFin ) {
        List<Posicion> posiciones = obtenerPosiciones(idVehiculo,fechaInicio,fechaFin);
        System.out.println("POSCIONES" + posiciones);


        // Coordenadas iniciales de la agencia
        Configuracion configuracion = configuracionService.obtenerConfiguracion();
        System.out.println(configuracion);
        double latActual = configuracion.getCoordenadasAgencia().getLat();
        double lonActual = configuracion.getCoordenadasAgencia().getLon();

        double distanciaTotal = 0.0;

        for (Posicion posicion : posiciones) {
            double latNueva = posicion.getLatitud();
            double lonNueva = posicion.getLongitud();

            // Calcula la distancia entre la posición actual y la nueva
            double distancia = calcularDistancia(latActual, lonActual, latNueva, lonActual);
            System.out.println(distancia);

            // Suma la distancia al total
            distanciaTotal += distancia;

            // Actualiza las coordenadas actuales para la siguiente iteración
            latActual = latNueva;
            lonActual = lonNueva;
        }

        return distanciaTotal;
    }


}
