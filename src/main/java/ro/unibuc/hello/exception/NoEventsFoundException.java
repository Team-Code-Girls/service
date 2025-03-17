package ro.unibuc.hello.exception;

public class NoEventsFoundException extends RuntimeException {
    
    public NoEventsFoundException(String message) {
        super(message);
    }
}
