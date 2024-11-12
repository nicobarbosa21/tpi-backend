package ar.edu.utn.frc.pruebaAgencia.controllers;

import ar.edu.utn.frc.pruebaAgencia.exceptions.PruebaException;
import ar.edu.utn.frc.pruebaAgencia.models.Interesado;
import ar.edu.utn.frc.pruebaAgencia.servicies.InteresadoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interesado")
public class InteresadoController {

    private final InteresadoServiceImpl interesadoService;

    public InteresadoController(InteresadoServiceImpl interesadoService) {
        this.interesadoService = interesadoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Object> agregarCliente(@RequestBody Interesado interesado) {
        try {
            interesadoService.add(interesado);
            System.out.println("controller: " + interesado);
            return ResponseEntity.status(HttpStatus.CREATED).body("Interesado creado correctamente!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/guardar-posicion/{id}")
    public ResponseEntity<Object> guardarPosicion(@PathVariable int id) {
        try {
            interesadoService.guardarPosicionVehiculo(id);
            return ResponseEntity.status(HttpStatus.OK).body("Posicion guardada!");
        } catch (PruebaException p) {
            throw new PruebaException("El interesado no tiene una prueba activa");
        }
    }
}