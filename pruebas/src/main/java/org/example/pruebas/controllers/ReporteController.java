package org.example.pruebas.controllers;

import org.example.pruebas.DTOS.ReporteDTO;
import org.example.pruebas.DTOS.VehiculoDTO;
import org.example.pruebas.services.PosicionService;
import org.example.pruebas.services.PruebaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/pruebas")
public class ReporteController {
        private final PruebaService pruebaService;
    private final PosicionService posicionService;

    public ReporteController(PruebaService pruebaService, PosicionService posicionService) {
        this.pruebaService = pruebaService;
        this.posicionService = posicionService;
    }

    // 6a - Reportes de incidentes
    @GetMapping("/reportesIncidentes")
    public ResponseEntity<String> obtenerReportesIncidentes() {
        try {
            // Llamada al servicio para obtener las pruebas como un String
            String reportes = pruebaService.obtenerPruebasConIncidentes();
            // Retorna el reporte con un código HTTP 200
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            // Manejo de errores, retorna un mensaje con código 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los reportes de incidentes.");
        }
    }

    //6b - Reportes de incidentes x empleado
    @GetMapping("/reportesIncidentes/{legajoEmpleado}")
    public ResponseEntity<String> obtenerReporteIncidentesPorEmpleado(@PathVariable int legajoEmpleado) {
        String reporte = pruebaService.obtenerPruebasConIncidentesPorLegajo(legajoEmpleado);

        return ResponseEntity.ok(reporte);
    }


    // 6c - Reportes de kilometros
    @PostMapping("/reporteKm")
    public ResponseEntity<String> obtenerKilometrosXVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {

        Integer vehiculoId = vehiculoDTO.getId();
        System.out.println(vehiculoId);
        ReporteDTO reporteDTO = vehiculoDTO.getReporteDTO();
        Timestamp fechaInicio = reporteDTO.getFechaInicio();
        System.out.println(fechaInicio);
        Timestamp fechaFin = reporteDTO.getFechaFin();
        System.out.println(fechaFin);

        String reporte = posicionService.obtenerCantidadKilometros(vehiculoId, fechaInicio, fechaFin);
        if (reporte.isEmpty()) {
            return ResponseEntity.ok("NO HAY PRUEBAS PARA DICHO VEHÍCULO");
        }
        return ResponseEntity.ok(reporte);
    }


    //6d - Reportes de pruebas x Vehiculos
    @GetMapping("/reporteVehiculo")
    public ResponseEntity<String> obtenerReporteIncidentesPorVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        String reporte = pruebaService.obtenerPruebasXVehiculo(vehiculoDTO.getPatente());

        if (reporte.isEmpty()) {
            return ResponseEntity.ok("NO HAY PRUEBAS PARA DICHO VEHICULO");
        }

        return ResponseEntity.ok(reporte);
    }


}
