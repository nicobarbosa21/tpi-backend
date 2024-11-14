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


    // 1 Endopoint - Crear Prueba
    @PostMapping("/create")
    public ResponseEntity<Prueba> createPrueba(@RequestBody PruebaDTO pruebaDTO) {
        try {
            String documento = pruebaDTO.getInteresadoDTO().getDocumento();
            String patente = pruebaDTO.getVehiculoDTO().getPatente();
            Integer legajoEmpleado = pruebaDTO.getEmpleadoDTO().getLegajo();
            String comentarios = pruebaDTO.getComentarios();

            // Llamar al servicio para crear la nueva prueba
            Prueba nuevaPrueba = pruebaService.crearNuevaPrueba(documento, patente, legajoEmpleado, comentarios);

            // Devolver la entidad Prueba creada
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPrueba);

        } catch (Exception e) {
            // Manejar errores y devolver una respuesta adecuada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2 Endpoint para listar todas las pruebas en un momento dado
    @GetMapping("/list")
    public ResponseEntity<?> listarPruebas() {
        List<Prueba> pruebas = pruebaService.obtenerTodasLasPruebas();
        if (pruebas == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No existe pruebas cargadas en el sistema.");
        } else {
            return ResponseEntity.ok(pruebas);
        }
    }

    // 3 Endopoint para finalizar una prueba permitiendo al empleado agregar un comentario
    @PutMapping("/complete")
    public ResponseEntity<String> finalizarPrueba(@RequestBody PruebaDTO pruebaDTO) {
        try {
            //Obtener una id y comentarios
            Integer id = pruebaDTO.getId();
            String comentario = pruebaDTO.getComentarios();

            boolean resultado = pruebaService.finalizarPrueba(id, comentario);
            if (resultado) {
                return ResponseEntity.ok("Prueba finalizó correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La prueba no fue encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al finalizar la prueba\n" + e.getMessage());
        }
    }
}




