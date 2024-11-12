package ar.edu.utn.frc.pruebaAgencia.controllers;

import ar.edu.utn.frc.pruebaAgencia.exceptions.PruebaException;
import ar.edu.utn.frc.pruebaAgencia.models.Interesado;
import ar.edu.utn.frc.pruebaAgencia.servicies.InteresadoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agencia/interesado")
public class InteresadoController {
    private final InteresadoServiceImpl interesadoService;

    public InteresadoController(InteresadoServiceImpl interesadoService) {
        this.interesadoService = interesadoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Object> agregarCliente(@RequestBody Interesado interesado){
        try{
            this.interesadoService.add(interesado);
            System.out.println("controller: " + interesado);
            return new ResponseEntity<>("Interesado creado correctamente!", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/guardar-posicion/{id}")
    public ResponseEntity<Object> guardarPosicion(@PathVariable int id){
        try {
            interesadoService.guardarPosicionVehiculo(id);
            return new ResponseEntity<>("Posicion guardada!", HttpStatus.OK);
        } catch (PruebaException p) {
            throw new PruebaException("El interesado no tiene una prueba activa");
        }
    }
}
