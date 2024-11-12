package ar.edu.utn.frc.pruebas.exceptions;

public class PruebaException extends RuntimeException {
    public PruebaException(String message) {
        super(message);
    }

    public PruebaException(String message, Throwable cause) {
        super(message, cause);
    }
}