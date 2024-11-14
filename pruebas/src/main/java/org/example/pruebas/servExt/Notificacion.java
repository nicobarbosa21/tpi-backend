package org.example.pruebas.servExt;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Notificacion {

    private String mensaje;
    private String tipo;
}
