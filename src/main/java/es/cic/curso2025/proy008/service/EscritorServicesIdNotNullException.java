package es.cic.curso2025.proy008.service;

public class EscritorServicesIdNotNullException extends RuntimeException {

    public EscritorServicesIdNotNullException(){

    }

    public EscritorServicesIdNotNullException(String message){
        super(message);
    }

    public EscritorServicesIdNotNullException(String message, Throwable throwable){
        super(message,throwable);
    }
}
