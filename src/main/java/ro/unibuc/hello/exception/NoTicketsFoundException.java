package ro.unibuc.hello.exception;

public class NoTicketsFoundException extends RuntimeException {
    
    public NoTicketsFoundException(String message) {
        super(message);
    }
}
