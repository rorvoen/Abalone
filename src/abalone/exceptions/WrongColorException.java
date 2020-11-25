package abalone.exceptions;

public class WrongColorException extends MoveException {
    public WrongColorException() {
        super("Erreur : la bille choisie n'est pas de votre couleur");
    }
}
