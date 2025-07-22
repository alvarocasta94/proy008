package es.cic.curso2025.proy008.controller;

public class EscritorIdNotNullException extends RuntimeException {

    public EscritorIdNotNullException(){
        super("Has tratado de modificar mediante creación");
    }

    public EscritorIdNotNullException(String message){
        super(message);
    }

    public EscritorIdNotNullException (String message, Throwable throwable){
        super(message,throwable);
    }
}
