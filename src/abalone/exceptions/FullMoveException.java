package abalone.exceptions;

public class FullMoveException extends MoveException {
    public FullMoveException() {
        super("Erreur : le maximum de billes selectionnées est atteint ! (3 billes)");
    }
}
