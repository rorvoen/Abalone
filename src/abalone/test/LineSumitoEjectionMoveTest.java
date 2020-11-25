package abalone.test;

import abalone.model.Board;
import abalone.model.Player;
import abalone.enums.Direction;
import abalone.enums.SquareContent;
import abalone.exceptions.ImpossibleMoveException;
import abalone.exceptions.MoveException;

import java.util.Scanner;

public class LineSumitoEjectionMoveTest implements TestConfig {
    public void setupTestConfig(Board board) {
        // On place les billes pour le test
        board.getBoardSquares()[5][5].setContent(SquareContent.BLACK);
        board.getBoardSquares()[5][6].setContent(SquareContent.BLACK);
        board.getBoardSquares()[5][7].setContent(SquareContent.BLACK);
        board.getBoardSquares()[5][8].setContent(SquareContent.WHITE);
        board.getBoardSquares()[5][9].setContent(SquareContent.WHITE);
    }

    public void setupTestMove(Board board, Player player1, Player player2){
        // On paramètre le mouvement de test
        try {
            testMove.addMarbleToMove(board.getBoardSquares()[5][5],player1);
            testMove.addMarbleToMove(board.getBoardSquares()[5][6],player1);
            testMove.addMarbleToMove(board.getBoardSquares()[5][7],player1);
            testMove.setMoveDirection(Direction.RIGHT);
        } catch (MoveException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du test");
        }
    }

    public void executeTest(Board board) {
        System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        System.out.println("Test mouvement en ligne avec sumito entrainant ejection de billes adverses : pas d'erreur attendue et 2 billes ejectées \n");
        Scanner scanner = new Scanner(System.in);
        System.out.println(board.toString(false));
        System.out.println("Voulez vous executer le test ?");
        System.out.println("0 - Non");
        System.out.println("1 - Oui");
        int answer = scanner.nextInt();
        if(answer == 1){
            try {
                System.out.println("Nombre de billes ejectées : "+testMove.performMove());
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
