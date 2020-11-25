package abalone.test;

import abalone.model.Board;
import abalone.model.Player;
import abalone.enums.Direction;
import abalone.enums.SquareContent;
import abalone.exceptions.ImpossibleMoveException;
import abalone.exceptions.MoveException;

import java.util.Scanner;

public class ArrowMoveTest implements TestConfig {
    public void setupTestConfig(Board board) {
        // On place les billes pour le test
        board.getBoardSquares()[3][3].setContent(SquareContent.BLACK);
        board.getBoardSquares()[4][2].setContent(SquareContent.BLACK);
        board.getBoardSquares()[5][1].setContent(SquareContent.BLACK);
    }

    public void setupTestMove(Board board, Player player1, Player player2){
        // On paramètre le mouvement de test
        try {
            testMove.addMarbleToMove(board.getBoardSquares()[3][3],player1);
            testMove.addMarbleToMove(board.getBoardSquares()[4][2],player1);
            testMove.addMarbleToMove(board.getBoardSquares()[5][1],player1);
            testMove.setMoveDirection(Direction.RIGHT);
        } catch (MoveException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du test");
        }
    }

    public void executeTest(Board board) {
        System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        System.out.println("Test mouvement en fléche : pas d'erreur attendue \n");
        Scanner scanner = new Scanner(System.in);
        System.out.println(board.toString(false));
        System.out.println("Voulez vous executer le test ?");
        System.out.println("0 - Non");
        System.out.println("1 - Oui");
        int answer = scanner.nextInt();
        if(answer == 1){
            try {
                testMove.performMove();
            } catch (ImpossibleMoveException e) {
                e.printStackTrace();
                System.err.println("Erreur lors du test");
            }
            System.out.println(board.toString(false));
        } else {
            System.out.println("Test annulé");
        }
        testMove.resetMove();
        System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
    };
}
