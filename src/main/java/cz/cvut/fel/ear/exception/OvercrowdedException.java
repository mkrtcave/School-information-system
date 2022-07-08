package cz.cvut.fel.ear.exception;


public class OvercrowdedException extends Exception{
    
    public OvercrowdedException(String message) {
        super(message);
    }

    public OvercrowdedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static NotFoundException create(Object objectType) {
        return new NotFoundException(objectType.toString() + "is owercrowded.");
    }
}
