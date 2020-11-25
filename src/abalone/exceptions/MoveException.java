package abalone.exceptions;

public class MoveException extends Exception {
    public MoveException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getLocalizedMessage();
    }
}
