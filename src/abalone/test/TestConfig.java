package abalone.test;

import abalone.model.Board;
import abalone.model.Move;
import abalone.model.Player;

public interface TestConfig {
    Move testMove = new Move();
    void setupTestConfig(Board board);
    void setupTestMove(Board board, Player player1, Player player2);
    void executeTest(Board board);
}
