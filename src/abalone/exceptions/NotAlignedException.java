package abalone.exceptions;

public class NotAlignedException extends MoveException {
    public NotAlignedException() {
        super("Erreur : cette bille n'est pas dans l'alignement des autres billes sélectionnées !");
    }
}
