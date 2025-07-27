package es.cic.curso2025.proy008.controller;



public class PremioIdNotNullException extends RuntimeException {

    public PremioIdNotNullException(){
        super("Has tratado de modificar mediante creación");
    }

    public PremioIdNotNullException(String message){
        super(message);
    }

    public PremioIdNotNullException(String message,Throwable throwable){
        super(message,throwable);
    }
}
