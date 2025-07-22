package es.cic.curso2025.proy008.controller;

public class EscritorIdNotNullException extends RuntimeException {

    public EscritorIdNotNullException(){

    }

    public EscritorIdNotNullException(String message){
        super(message);
    }

    public EscritorIdNotNullException (String message, Throwable throwable){
        super(message,throwable);
    }
}
