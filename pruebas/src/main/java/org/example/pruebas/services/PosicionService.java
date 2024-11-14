package org.example.pruebas.services;


import jakarta.transaction.Transactional;
import org.example.pruebas.models.Interesado;
import org.example.pruebas.models.Posicion;
import org.example.pruebas.models.Prueba;
import org.example.pruebas.models.Vehiculo;
import org.example.pruebas.repositories.PosicionRepository;
import org.example.pruebas.repositories.PruebaRepository;
import org.example.pruebas.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.example.pruebas.servExt.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class PosicionService {

    private final VehiculoRepository vehiculoRepository;
    private final PosicionRepository posicionRepository;
    private final ConfiguracionService configuracionService;
    private final PruebaRepository pruebaRepository;
    private final NotificacionService notificacionService;

    public PosicionService(VehiculoRepository vehiculoRepository, PosicionRepository posicionRepository, ConfiguracionService configuracionService, PruebaRepository pruebaRepository, NotificacionService notificacionService ) {
        this.vehiculoRepository = vehiculoRepository;
        this.posicionRepository = posicionRepository;
        this.configuracionService = configuracionService;
        this.pruebaRepository = pruebaRepository;
        this.notificacionService = notificacionService;
    }

    @Transactional
    public Posicion crearNuevaPosicion(Integer id_vehiculo, Double longitud, Double latitud) {
        try {
            Vehiculo vehiculo = vehiculoRepository.findByID(id_vehiculo);

            Timestamp fechaActual = Timestamp.from(Instant.now());

            //Busca la prueba activa para el vehiculo
            Prueba prueba = pruebaRepository.pruebaByVehiculo(id_vehiculo);
            int numEmpleado = prueba.getEmpleado().getTelefono();
            Boolean pruebaActivaVehiculo = pruebaRepository.esPruebaActivaParaVehiculo(id_vehiculo);
            if (pruebaActivaVehiculo){
                System.out.println("EL VEHICULO ESTÁ EN UNA PRUEBA");
            } else {
                System.out.println("EL VEHICULO NO ESTA EN UNA PRUEBA");
                return null;
            }

            Interesado interesado = prueba.getInteresado();
            Configuracion configuracion = configuracionService.getConfiguracion();

            Posicion posicion = new Posicion(vehiculo, fechaActual, longitud, latitud);
            posicionRepository.savePosicion(posicion);

            if (estaDentroDelRadio(posicion, configuracion) && !estaEnZonaRestringida(posicion, configuracion.getZonasRestringidas())) {
                System.out.println("ESTA DENTRO DE LAS CONDICIONES PERMITIDAS");
            } else {
                 if(estaDentroDelRadio(posicion,configuracion)){
                     String mensaje = "EL VEHICULO CON PATENTE: " + vehiculo.getPatente() + " DEBE REGRESAR " + "TELEFONO : " + numEmpleado;
                     String tipo = "ROMPIO LA CONDICION: FUERA DE RADIO ADMITIDO";
                     Notificacion notificacion = new Notificacion(mensaje, tipo);
                     notificacionService.enviarNotificacion(notificacion);
                 } else{
                     String mensaje = "EL VEHICULO CON PATENTE: " + vehiculo.getPatente() + " DEBE REGRESAR " + "TELEFONO : " + numEmpleado;
                     String tipo = "ROMPIO LA CONDICION: ZONA RESTRINGIDA";
                     Notificacion notificacion = new Notificacion(mensaje, tipo);
                     notificacionService.enviarNotificacion(notificacion);
                 }

                System.out.println("NO ESTA DENTRO DE LO PERMITIDO");
                 // Marcar al interesado como restringido y en la tabla pruebas se pone en 1 la casilla incidente
                interesado.setRestringido(true);
                prueba.setInicidente(true);
            }

            return posicion;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean estaDentroDelRadio(Posicion posicion, Configuracion configuracion) {
        double distancia = posicionRepository.calcularDistancia(
                posicion.getLatitud(), posicion.getLongitud(),
                configuracion.getCoordenadasAgencia().getLat(),
                configuracion.getCoordenadasAgencia().getLon()
        );
        return distancia <= configuracion.getRadioAdmitidoKm();
    }

    private boolean estaEnZonaRestringida(Posicion posicion, List<ZonaRestringida> zonasRestringidas) {
        for (ZonaRestringida zona : zonasRestringidas) {
            if (posicion.getLatitud() >= zona.getSureste().getLat() && posicion.getLatitud() <= zona.getNoroeste().getLat() &&
                    posicion.getLongitud() >= zona.getNoroeste().getLon() && posicion.getLongitud() <= zona.getSureste().getLon()) {
                return true;
            }
        }
        return false;
    }

    //USADO PARA EL REPORTE DE KM
    public String obtenerCantidadKilometros(Integer idVehiculo, Timestamp fechaInicio, Timestamp fechaFin){

        Double cantiKm = posicionRepository.cantKilometros(idVehiculo, fechaInicio, fechaFin);
        Vehiculo vehiculo = vehiculoRepository.findByID(idVehiculo);
        String patente = vehiculo.getPatente();

        return "///////////////////////////////////////////////////////////////////////////////////////\n" +
                "REPORTE DE CANTIDAD DE KILOMETROS DE PRUEBA RECORRIDOS PARA EL VEHICULO: " + patente + "\n" +
                "///////////////////////////////////////////////////////////////////////////////////////\n\n" +
                "--------------------------------------------------\n" +
                "CANTIDAD DE KM RECORRIDOS: " + cantiKm + "km\n" + "--------------------------------------------------";
    }
}
