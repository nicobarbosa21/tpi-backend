package org.example.pruebas.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.example.pruebas.repositories.*;
import org.example.pruebas.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PruebaService {
    private final PruebaRepository pruebaRepository;
    private final VehiculoRepository vehiculoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final InteresadoRepository interesadoRepository;
    private final PosicionRepository posicionRepository;

    @Autowired
    public PruebaService(PruebaRepository pruebaRepository,
                         VehiculoRepository vehiculoRepository,
                         EmpleadoRepository empleadoRepository,
                         InteresadoRepository interesadoRepository,
                         PosicionRepository posicionRepository
    ){
        this.pruebaRepository = pruebaRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.empleadoRepository = empleadoRepository;
        this.interesadoRepository = interesadoRepository;
        this.posicionRepository = posicionRepository;
    }

    @Transactional
    public Prueba crearNuevaPrueba(String documento, String patente, Integer legajoEmpleado, String comentario) {
        try {
            // Buscar vehiculo
            Vehiculo vehiculo = vehiculoRepository.findByPatente(patente);
            // Buscar Interesado
            Interesado interesado = interesadoRepository.findByDocumento(documento);
            //Buscar empleado
            Empleado empleado = empleadoRepository.findByLegajo(legajoEmpleado);

            // Verificar que el vehiculo no este siendo probado
            Boolean existe = pruebaRepository.esPruebaActivaParaVehiculo(vehiculo.getId());
            if (existe) {
                System.out.println("EL VEHICULO ESTA SIENDO PROBADO");
                return null;
            }

            // Verificar que el cliente no tenga la licencia vencida
            Timestamp fechaLicencia = interesado.getVencimiento();
            Timestamp fechaActual = Timestamp.from(Instant.now());

            boolean licenciaVencida = fechaLicencia.before(fechaActual);
            boolean clienteRestringido = interesado.isRestringido();

            // VERIFICAR QUE EL VEHICULO ESTE PATENTADO
            if (vehiculo.getPatente() == null) {
                System.out.println("EL VEHICULO NO ESTÁ PATENTADO");
                return null;
            }

            // No guarda la prueba si la licencia está vencida o el cliente está restringido
            if (licenciaVencida || clienteRestringido) {
                System.out.println("ERROR EN LAS RESTRICCIONES, NO SE PUEDE GUARDAR LA PRUEBA");
                return null;
            }

            if (comentario == null) {
                comentario = "SIN COMENTARIOS";
            }

            // Código para guardar la prueba
            System.out.println("Generando y guardando la prueba...");
            Timestamp fechaInicio = Timestamp.from(Instant.now());

            // UNA PRUEBA RECIEN CREADA NO SE HA CERRADO, POR LO QUE SE INICIALIZA COMO NULA
            Prueba prueba = new Prueba(vehiculo, interesado, empleado, fechaInicio, null, comentario);
            pruebaRepository.save(prueba);

            return prueba;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prueba> obtenerTodasLasPruebas() {
        return pruebaRepository.findAllEnCurso();
    }

    @Transactional
    public boolean finalizarPrueba(Integer id, String comentario){
        //Verificar que exista la prueba
        Boolean existe = pruebaRepository.esPruebaActiva(id);
        if (existe) {
            pruebaRepository.terminarPruebaActiva(id, comentario);
        }
        else {
            System.out.println("No existe esa prueba activa");
        }
        return existe;
    }

    public String obtenerPruebasConIncidentes() {
        List<Prueba> pruebas = pruebaRepository.getPruebasConIncidente();
        StringBuilder reporte = new StringBuilder();

        reporte.append("//////////////////////////////////////\n");
        reporte.append("Visualizador de Reporte de Incidentes\n");
        reporte.append("//////////////////////////////////////\n");
        //reporte.append("Fecha Actual: ").append(Timestamp.from(Instant.now())).append("\n\n");

        for (Prueba prueba : pruebas) {
            reporte.append("Vehículo: ").append(prueba.getVehiculo().getPatente()).append("\n")
                    .append("Interesado: ").append(prueba.getInteresado().getNombre()).append(" ") .append(prueba.getInteresado().getApellido()).append("\n")
                    .append("Empleado: ").append(prueba.getEmpleado().getNombre()).append(prueba.getEmpleado().getApellido()).append("\n")
                    .append("Fecha Inicio: ").append(prueba.getFecha_hora_inicio()).append("\n")
                    .append("Fecha Fin: ").append(prueba.getFecha_hora_fin()).append("\n")
                    .append("-------------------------------------------");
        }
        return reporte.toString();
    }

    // REPORTE II - Obtener las pruebas con incidentes por empleado
    public String obtenerPruebasConIncidentesPorLegajo(Integer legajo) {
        List<Prueba> pruebas = empleadoRepository.getPruebasIncidentePorLegajo(legajo);
        StringBuilder reporte = new StringBuilder();

        // Título y fecha actual
        reporte.append("//////////////////////////////////////\n");
        reporte.append("Visualizador de Reporte de Incidentes\n");
        reporte.append("Para el empleado con Numero de Legajo: ").append(legajo).append("\n");
        reporte.append("//////////////////////////////////////\n");

        if (pruebas.isEmpty()) {
            reporte.append("No hay incidentes asignados al empleado\n");
        } else {
            for (Prueba prueba : pruebas) {
                reporte.append("Vehículo: ").append(prueba.getVehiculo().getPatente()).append("\n")
                        .append("Interesado: ").append(prueba.getInteresado().getNombre()).append(" ") .append(prueba.getInteresado().getApellido()).append("\n")
                        .append("Empleado: ").append(prueba.getEmpleado().getNombre()).append(prueba.getEmpleado().getApellido()).append("\n")
                        .append("Fecha Inicio: ").append(prueba.getFecha_hora_inicio()).append("\n")
                        .append("Fecha Fin: ").append(prueba.getFecha_hora_fin()).append("\n")
                        .append("-------------------------------------------\n");
            }
        }

        return reporte.toString();
    }

    // REPORTE IV - Detalle de las pruebas para un vehiculo
    public String obtenerPruebasPorVehiculo(String patente) {
        List<Prueba> pruebas = vehiculoRepository.getPruebasFinalizadasPorVehiculo(patente);
        StringBuilder reporte = new StringBuilder();
        reporte.append("////////////////////////////////////////////////////////////////////////\n");
        reporte.append("Visualizador de Reporte de pruebas para el vehículo: ").append(patente).append("\n");
        reporte.append("////////////////////////////////////////////////////////////////////////\n");
        for (Prueba prueba : pruebas) {
            reporte.append("VEHÍCULO: ").append(prueba.getVehiculo().getPatente()).append("\n")
                    .append("INTERESADO: ").append(prueba.getInteresado().getNombre()).append("\n")
                    .append("EMPLEADO: ").append(prueba.getEmpleado().getNombre()).append("\n")
                    .append("FECHA DE INICIO: ").append(prueba.getFecha_hora_inicio()).append("\n")
                    .append("FECHA DE FIN: ").append(prueba.getFecha_hora_fin()).append("\n\n")
                    .append("-------------------------------------------\n");
        }
        return reporte.toString();
}
}