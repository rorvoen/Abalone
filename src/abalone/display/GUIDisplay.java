package abalone.display;

import abalone.model.Board;
import abalone.model.Move;
import abalone.model.Player;

public class GUIDisplay implements Display {
    public void displayTitleScreen() {

    }
    public String getPlayername(Player player) {
        return null;
    }
    public void turnStart(Player player, int turnNumber) {

    }
    public Move composeMove(Player player, Board board) {
        return null;
    }
    public int handleImpossibleMoveException(Player player, Board board) {
        return 0;
    }
    public void displayOutMarbles(int outMarblesCount, Player player) {

    }
    public void turnEnd(Board board) {
    }
    public void displayWinner(Player player) {

    }
}
