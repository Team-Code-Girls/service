package ro.unibuc.hello.exception;

public class NoUsersFoundException extends RuntimeException {
    
    public NoUsersFoundException(String message) {
        super(message);
    }
}
