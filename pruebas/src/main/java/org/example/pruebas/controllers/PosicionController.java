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

/**
 * Controlador REST para gestionar operaciones relacionadas con la posición.
 * Mapea las solicitudes a "/api/pruebas".
 */

@RestController
@RequestMapping("/api/pruebas")
public class PosicionController {

    private final PosicionService posicionService;

    public PosicionController(PosicionService posicionService) {
        this.posicionService = posicionService;
    }

    /**
     * Endpoint para guardar una nueva posición en el sistema.
     * Recibe un objeto PosicionDTO (que contiene la información de la posición a guardar) en el cuerpo de la solicitud.
     * retorna una respuesta HTTP con el estado de la operación
     */
    @PostMapping("/position")
    public ResponseEntity<String> guardarPosicion(@RequestBody PosicionDTO posicionDTO) {
        try {
            // Llama al servicio para crear una nueva instancia de posición con los datos del DTO.
            Posicion posicion = posicionService.crearNuevaPosicion(
                    posicionDTO.getId_vehiculo(),
                    posicionDTO.getLongitud(),
                    posicionDTO.getLatitud()
            );
            return ResponseEntity.ok("Posición guardada");
            // Manejo de excepciones: imprime el error y responde con error 500 en caso de fallo
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
