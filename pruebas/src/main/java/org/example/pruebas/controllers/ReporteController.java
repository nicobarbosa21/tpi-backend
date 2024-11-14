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

    // PUNTO 6 A
    @GetMapping("/reportesIncidentes")
    public ResponseEntity<String> obtenerReportesIncidentes() {
        try {
            String reportes = pruebaService.obtenerPruebasConIncidentes();
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    // PUNTO 6 B
    @GetMapping("/reportesIncidentes/{legajoEmpleado}")
    public ResponseEntity<String> obtenerReporteIncidentesPorEmpleado(@PathVariable int legajoEmpleado) {
        String reporte = pruebaService.obtenerPruebasConIncidentesPorLegajo(legajoEmpleado);

        return ResponseEntity.ok(reporte);
    }

    // PUNTO 6 C
    @PostMapping("/reporteKm")
    public ResponseEntity<String> obtenerKilometrosXVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {

        Integer vehiculoId = vehiculoDTO.getId();
        ReporteDTO reporteDTO = vehiculoDTO.getReporteDTO();
        Timestamp fechaInicio = reporteDTO.getFechaInicio();
        Timestamp fechaFin = reporteDTO.getFechaFin();

        String reporteKm = posicionService.obtenerCantidadKilometros(vehiculoId, fechaInicio, fechaFin);
        if (reporteKm.isEmpty()) {
                    return ResponseEntity.ok("NO HAY PRUEBAS ASOCIADAS AL VEHICULO");
        }
        return ResponseEntity.ok(reporteKm);
    }

    //6d - Reportes de pruebas x Vehiculos
    @GetMapping("/reporteVehiculo")
    public ResponseEntity<String> obtenerReporteIncidentesPorVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        String reporteVehiculo = pruebaService.obtenerPruebasPorVehiculo(vehiculoDTO.getPatente());

        if (reporteVehiculo.isEmpty()) {
            return ResponseEntity.ok("NO HAY PRUEBAS ASOCIADAS AL VEHICULO");
        }

        return ResponseEntity.ok(reporteVehiculo);
    }


}
