package es.cic.curso2025.proy008.service;

public class EscritorServiceIdNotNullException extends RuntimeException {

    public EscritorServiceIdNotNullException(){

    }

    public EscritorServiceIdNotNullException(String message){
        super(message);
    }

    public EscritorServiceIdNotNullException(String message, Throwable throwable){
        super(message,throwable);
    }
}
