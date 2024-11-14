package org.example.pruebas.repositories;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.example.pruebas.servExt.Configuracion;
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
    public void savePosicion(Posicion posicion) {
        em.persist(posicion);
    }

    public double calcularDistancia(double latAct, double lonAct, double latNuev, double lonNuev) {
        // Cálculo euclídeo de la distancia entre dos puntos geográficos
        double distLat = latNuev - latAct;
        double distLon = lonNuev - lonAct;
        return Math.sqrt(distLat * distLat + distLon * distLon);
    }

    // Vehiculo y un tiempo determinado
    public List<Posicion> getPosiciones(Integer idVehiculo, Timestamp fechaInicio, Timestamp fechaFin) {
        try {
            // Consulta JPQL para obtener las posiciones del vehículo en el rango de fechas
            String query = "SELECT pru FROM Posicion pru WHERE pru.vehiculo.id = :idVehiculo " +
                    "AND pru.fechaHora BETWEEN :fechaInicio AND :fechaFin";

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

    public Double cantKilometros(Integer idVehiculo, Timestamp fechaInicio, Timestamp fechaFin ) {
        //obtiene una lista de posiciones de los vehiculos
        List<Posicion> posiciones = getPosiciones(idVehiculo,fechaInicio,fechaFin);

        Configuracion configuracion = configuracionService.getConfiguracion();

        //inicializo las coordenadas actuales
        double latActual = configuracion.getCoordenadasAgencia().getLat();
        double lonActual = configuracion.getCoordenadasAgencia().getLon();

        //inicializo la distancia total
        double distTotal = 0.0;

        for (Posicion posicion : posiciones) {

            //obtiene las coordenadas de la nueva posicion, es decir, de cada iteracion de la lista
            double latNueva = posicion.getLatitud();
            double lonNueva = posicion.getLongitud();

            // Calcula la distancia entre la posición actual y la nueva
            double distancia = calcularDistancia(latActual, lonActual, latNueva, lonNueva);

            // suma la distancia calculada al acumulador de distancia total
            distTotal += distancia;

            // actualiza las coordenadas actuales
            latActual = latNueva;
            lonActual = lonNueva;
        }

        return distTotal;
    }


}
