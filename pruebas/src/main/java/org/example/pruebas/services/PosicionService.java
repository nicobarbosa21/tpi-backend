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
import org.example.pruebas.config.*;

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

            //BUSCAR LA PREUBA ASOCIADA AL VEHICULO
            Prueba prueba = pruebaRepository.pruebaByVehiculo(id_vehiculo);
            Integer numEmpleado = prueba.getEmpleado().getTelefono();
            Boolean pruebaActivaVehiculo = pruebaRepository.existePruebaActivaParaVehiculo(id_vehiculo);
            if (pruebaActivaVehiculo){
                System.out.println("EL VEHICULO ESTA SIENDO PROBADO");
            } else {
                System.out.println("EL VEHICULO NO ESTA SIENDO PROBADO");
                return null;
            }

            Interesado interesado = prueba.getInteresado();
            Configuracion configuracion = configuracionService.obtenerConfiguracion();

            Posicion posicion = new Posicion(vehiculo, fechaActual, longitud, latitud);
            System.out.println("SE CREO LA POSICION ANTES DE SER GUARDADO" + posicion);
            posicionRepository.savePosicion(posicion);


            if (estaDentroDelRadioAdmitido(posicion, configuracion) && !estaEnZonaRestringida(posicion, configuracion.getZonasRestringidas())) {
                System.out.println("ESTA DENTRO DE LO PERMITIDO");
            } else {
                // CUANDO PASA ALGUNAS DE ESTAS COSAS DEBO AGREGAR AL CLIENTE A LA LISTA DE CLIENTES RESTRINGIDOS
                // MANDAR NOTIFICACION
                 if(estaDentroDelRadioAdmitido(posicion,configuracion)){
                     String mensaje = "EL VEHICULO: " + vehiculo.getPatente() + " DEBE REGRESAR INMEDIATAMENTE " + "TELEFONO : " + numEmpleado;
                     String tipo = "FUERA DE RADIO ADMITIDO";
                     Notificacion notificacion = new Notificacion(mensaje, tipo);
                     notificacionService.enviarNotificacion(notificacion);
                 } else{
                     String mensaje = "EL VEHICULO: " + vehiculo.getPatente() + " DEBE REGRESAR INMEDIATAMENTE " + "TELEFONO : " + numEmpleado;
                     String tipo = "ZONA RESTRINGIDA";
                     Notificacion notificacion = new Notificacion(mensaje, tipo);
                     notificacionService.enviarNotificacion(notificacion);
                 }

                System.out.println("NO ESTA DENTRO DE LO PERMITIDO");
                interesado.setRestringido(true);
                prueba.setInicidente(true);
            }

            return posicion;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean estaDentroDelRadioAdmitido(Posicion posicion, Configuracion configuracion) {
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



    public String obtenerCantidadKilometros(Integer idVehiculo, Timestamp fechaInicio, Timestamp fechaFin){
        Double cantidadKilometros = posicionRepository.cantidadKilometros(idVehiculo, fechaInicio, fechaFin);

        Vehiculo vehiculo = vehiculoRepository.findByID(idVehiculo);
        String patente = vehiculo.getPatente();

        StringBuilder reporte = new StringBuilder();
        reporte.append("REPORTE DE KILOMETROS PARA EL VEHICULO: " + patente  ).append("\n");
        reporte.append("Fecha Actual :").append(Timestamp.from(Instant.now())).append("\n\n");
        reporte.append("LA CANTIDAD DE KILOMETROS RECORRIDO EN PRUEBAS ES : " + cantidadKilometros);

        return reporte.toString();
    }


}
