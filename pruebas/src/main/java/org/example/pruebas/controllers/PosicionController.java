package org.example.pruebas.controllers;


import org.example.pruebas.DTOS.PosicionDTO;
import org.example.pruebas.models.Posicion;
import org.example.pruebas.services.PosicionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pruebas")
public class PosicionController {

    private final PosicionService posicionService;

    public PosicionController(PosicionService posicionService) {
        this.posicionService = posicionService;
    }

    // 4 - Endpoint para guardar una posicion
    @PostMapping("/posicion")
    public ResponseEntity<String> guardarPosicion(@RequestBody PosicionDTO posicionDTO) {
        try {
            // Llama al servicio para crear una nueva posición
            Posicion posicion = posicionService.crearNuevaPosicion(
                    posicionDTO.getId_vehiculo(),
                    posicionDTO.getLongitud(),
                    posicionDTO.getLatitud()
            );
            return ResponseEntity.ok("Posición guardada exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la posición.");
        }
    }
}
