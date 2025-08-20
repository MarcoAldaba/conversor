package com.alura.conversor.excepcion;

public class ErrorEnConversionException extends RuntimeException {
    private String mensaje;

    public ErrorEnConversionException(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }
}