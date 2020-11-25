package abalone.exceptions;

public class NoMarbleException extends MoveException {
    public NoMarbleException(int i, int j) {
        super("Erreur : il n'y a pas de bille sur la case v:"+i+" & h:"+j);
    }
}
