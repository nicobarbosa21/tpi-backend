package ar.edu.utn.frc.pruebaAgencia.controllers;

import ar.edu.utn.frc.pruebaAgencia.dto.*;
import ar.edu.utn.frc.pruebaAgencia.exceptions.PruebaException;
import ar.edu.utn.frc.pruebaAgencia.models.Prueba;
import ar.edu.utn.frc.pruebaAgencia.servicies.PruebaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agencia/prueba")
public class PruebaController {
    private static final String urlAgencia = "http://localhost:8082/api/agencia/informacion";
    private final PruebaServiceImpl pruebaService;
    private final RestTemplate restTemplate;

    public PruebaController(PruebaServiceImpl pruebaService, RestTemplate restTemplate) {
        this.pruebaService = pruebaService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/listado")
    public ResponseEntity<List<PruebaDTO>> listadoPruebasEnCurso() {
        List<Prueba> pruebas = pruebaService.listadoPruebasEnCurso();
        List<PruebaDTO> pruebaDTOs = pruebas.stream()
                .map(p -> new PruebaDTO(p.getId(), p.getFechaFin(),
                        new InteresadoDTO(p.getInteresado().getId(), p.getInteresado().getNombreInteresado(), p.getInteresado().getApellidoInteresado()),
                        new VehiculoDTO(p.getVehiculo().getPatente(),
                                new ModeloDTO(p.getVehiculo().getModelo().getId(),
                                        new MarcaDTO(p.getVehiculo().getModelo().getMarca().getId(), p.getVehiculo().getModelo().getMarca().getNombre()),
                                        p.getVehiculo().getModelo().getDescripcion()),
                                p.getVehiculo().getAnio())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(pruebaDTOs);
    }

    @PostMapping("/crear")
    public ResponseEntity<Object> agregarPrueba(@RequestBody Prueba prueba) {
        try {
            pruebaService.crearPrueba(prueba);
            return new ResponseEntity<>("Prueba creada correctamente!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<Object> finalizarPrueba(@PathVariable int id, @RequestBody String comentarios) {
        try {
            pruebaService.agregarComentarios(id, comentarios);
            return new ResponseEntity<>("Prueba finalizada correctamente!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verificar-posicion/{idVehiculo}")
    public ResponseEntity<Object> verificarYEnviarNotificacion(@PathVariable int idVehiculo) {
        try {
            NotificacionAlertaDTO notificacion = pruebaService.verificarEnviarNotificacion(idVehiculo);
            if (notificacion != null) {
                return new ResponseEntity<>(notificacion, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se envio notificacion", HttpStatus.OK);
            }
        } catch (PruebaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}