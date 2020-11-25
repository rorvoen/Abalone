package abalone.exceptions;

public class OutOfBoardException extends Exception {
    public OutOfBoardException() {
        super("Erreur : coordonnée saisie hors du plateau");
    }
}
