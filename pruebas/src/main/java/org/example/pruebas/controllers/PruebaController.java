package org.example.pruebas.controllers;

import org.example.pruebas.DTOS.PruebaDTO;
import org.example.pruebas.models.Prueba;
import org.example.pruebas.repositories.EmpleadoRepository;
import org.example.pruebas.repositories.PruebaRepository;
import org.example.pruebas.services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    private final PruebaService pruebaService;
    private final PruebaRepository pruebaRepository;
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public PruebaController(PruebaService pruebaService, PruebaRepository pruebaRepository, EmpleadoRepository empleadoRepository) {
        this.pruebaService = pruebaService;
        this.pruebaRepository = pruebaRepository;
        this.empleadoRepository = empleadoRepository;
    }

    // Endpoint para crear una nueva prueba
    @PostMapping("/create")
    public ResponseEntity<Prueba> createPrueba(@RequestBody PruebaDTO pruebaDTO) {
        try {
            // Extraer datos necesarios desde el DTO
            String documento = pruebaDTO.getInteresadoDTO().getDocumento();
            String patente = pruebaDTO.getVehiculoDTO().getPatente();
            Integer legajoEmpleado = pruebaDTO.getEmpleadoDTO().getLegajo();
            String comentarios = pruebaDTO.getComentarios();

            // Crear la nueva prueba a través del servicio
            Prueba nuevaPrueba = pruebaService.crearNuevaPrueba(documento, patente, legajoEmpleado, comentarios);

            // Respuesta exitosa con la entidad creada
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPrueba);

        } catch (Exception e) {
            // Manejo de excepciones en caso de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para obtener la lista de todas las pruebas registradas
    @GetMapping("/list")
    public ResponseEntity<?> listarPruebas() {
        List<Prueba> pruebas = pruebaService.obtenerTodasLasPruebas();
        if (pruebas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No existen pruebas cargadas en el sistema.");
        }
        return ResponseEntity.ok(pruebas);
    }

    // Endpoint para finalizar una prueba y permitir agregar comentarios adicionales
    @PutMapping("/complete")
    public ResponseEntity<String> finalizarPrueba(@RequestBody PruebaDTO pruebaDTO) {
        try {
            // Obtener ID y comentario desde el DTO
            Integer id = pruebaDTO.getId();
            String comentario = pruebaDTO.getComentarios();

            // Llamar al servicio para completar la prueba
            boolean resultado = pruebaService.finalizarPrueba(id, comentario);
            if (resultado) {
                return ResponseEntity.ok("La prueba se ha finalizado correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prueba no encontrada.");
            }
        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al finalizar la prueba.");
        }
    }
}
