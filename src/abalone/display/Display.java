package abalone.display;

import abalone.model.Board;
import abalone.model.Move;
import abalone.model.Player;

/**
 * Interface defining methods corresponding to the different steps of an Abalone game
 * If some methods are useless for you to display the game, implement them as empty
 * You can add new methods, but you will have to implement them as empty in ConsoleDisplay
 * Detail of the role of each method in ConsoleDisplay
 */
public interface Display {
    void displayTitleScreen();
    String getPlayername(Player player);
    void turnStart(Player player, int turnNumber);
    Move composeMove(Player player, Board board);
    int handleImpossibleMoveException(Player player, Board board);
    void displayOutMarbles(int outMarblesCount, Player player);
    void turnEnd(Board board);
    void displayWinner(Player player);
}

