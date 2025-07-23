package es.cic.curso2025.proy008.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.cic.curso2025.proy008.controller.EscritorIdNotNullException;
import es.cic.curso2025.proy008.controller.ModificacionSecurityException;

@RestControllerAdvice
public class MiControllerAdvice {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ModificacionSecurityException.class)
    public String manejarModificacionSecurity(ModificacionSecurityException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EscritorIdNotNullException.class)
    public String manejarEscritorIdNotNull(EscritorIdNotNullException ex) {
        return ex.getMessage();
    }
}
