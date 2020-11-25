package abalone.exceptions;

public class VoidSquareException extends MoveException {
    public VoidSquareException(int i, int j) {
        super("Erreur : la case située aux coordonnées v:"+i+" & h:"+j+" du plateau, est en dehors de celui-ci");
    }
}
