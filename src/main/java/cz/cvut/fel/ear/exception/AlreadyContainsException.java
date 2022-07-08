package cz.cvut.fel.ear.exception;


public class AlreadyContainsException extends Exception{
    
    public AlreadyContainsException(String message) {
        super(message);
    }

    public AlreadyContainsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static AlreadyContainsException create(Object objectContains, Object objectToAdd) {
        return new AlreadyContainsException(objectContains.toString() + 
                "already contains " + objectToAdd.toString());
    }
}
