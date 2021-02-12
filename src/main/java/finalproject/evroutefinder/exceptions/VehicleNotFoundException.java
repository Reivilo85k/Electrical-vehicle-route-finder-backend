package finalproject.evroutefinder.exceptions;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(String message){
        super(message);
    }
}
