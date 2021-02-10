package finalproject.evroutefinder.exceptions;

public class EVRouteFinderException extends RuntimeException{
    public EVRouteFinderException(String exMessage, Exception exception) {
        super(exMessage);
    }
}
