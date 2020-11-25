package abalone.display;

import abalone.enums.Direction;
import abalone.exceptions.*;
import abalone.model.Board;
import abalone.model.Move;
import abalone.model.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleDisplay implements Display {
    private Scanner readInput;

    public ConsoleDisplay() {
        this.readInput = new Scanner(System.in);
    }

    /**
     * Allow to display a title screen when starting the game
     */
    public void displayTitleScreen() {
        System.out.println(
                "                       @@@:                                     @@@@                                                         \n" +
                        "                       %@@:                                     @@%@                                                         \n" +
                        "                       @@@:                                     @@@@                                                         \n" +
                        "                       @@@:                                     @@@@                                                         \n" +
                        "                       @@@:                                     @%%%                                                         \n" +
                        "                       @@@:                                     %@@@                                                         \n" +
                        "     *@@%@@#           @@@% @@@%%@             @@@@@@           @@@@       #%@%@@+           @@@@@           =@%@@@%         \n" +
                        "   @@@@@@@%@@@         @@@@@@@@@@@%@        +%@%@@@@@@@         %%@@     @@@@@@@@@%@       @@@@%@@%%       %%%@@@@%%@@       \n" +
                        "  @@@%     %@@@        @@@@@*    %@@@%     @@@@+    %@@@*       @@@@    @@@@     @@@@     @@@@   %@@@     @@@@     @@%@      \n" +
                        " @%@@       #@%@*      @@@@        %%@    %@@@        @@%@      @@@@   %@@%       @@@@   +%@%     @@@@   @@@@      +%@%@     \n" +
                        " @@%          @%@@     @@@%        @@%@   @%%-         @@@@     @@%@  .@%@         @@@   @@%@     %@%%   @@@     @@%@@       \n" +
                        "+@@@           @@@@    @@%-        @@%@   %%%           %@%%    @%@%  %@@%         @@@   @@%@     %%@@   @%@    @@+          \n" +
                        " @@@            @@@@   %@@:        %@%@   %@@@           =%%@#  %@%%   @@@         @%@   @%%@     @@@@   @@%.                \n" +
                        " #%@%       %*   #%@%# @@@:       @@@@     @@@#      @@    @@@@ @%%%   @%%%       %@@%   @%@%     @@@@   -@@%                \n" +
                        "  #@%@@@@@%%@%%    @%@@@%%:  @%%@%@%@       %@%@%@@@@@%@    @@@@@%@@    %@@%@@%%@@@@%    %@@@     %@@@    -@@@%@@@@@@%#      \n" +
                        "    %@%@@%@@%       @@%@@@:  @@@%%%          #%@@%@@%@       @@%@@@@      @@%@@@@@@      @@@@     %%%@      %@%@@%@@@        \n" +
                        "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――     \n" +
                        "                                                   Nouvelle partie                                                           "
        );
    }

    /**
     * Method to let the players choose their name
     * @param player the player who needs to choose his name
     * @return the name the player chose
     */
    public String getPlayername(Player player) {
        System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        System.out.println("Merci de d'indiquer le nom du joueur "+player.getColor().toString(false)+" : ");
        String name = readInput.nextLine();
        return name;
    }

    /**
     * Method to display something when the turn of a player start
     * @param player the player who is starting his turn
     * @param turnNumber the number of the turn
     */
    public void turnStart(Player player, int turnNumber) {
        System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        System.out.println("Début du tour "+turnNumber+" de "+player.getPlayerName()+" jouant les billes "+player.getColor().toString(true)+"s");
        System.out.println();
    }

    /**
     * Method to let the player compose the move he will play for this turn
     * @param player player who is composing the move
     * @param board the game board
     * @return the move composed by the player
     */
    public Move composeMove(Player player, Board board) {
        Move move = new Move();
        System.out.println("Choix des billes à déplacer");
        System.out.println("Instructions :");
        System.out.println("- Veuillez saisir successivemement les coordonnée des billes à déplacer");
        System.out.println("- Commencez toujours par une extrémité du groupe de bille que vous sélectionnez");
        System.out.println("- Pour chaque bille saisissez sa première coordonnée puis faites ENTREE avant de rentrer la seconde");
        System.out.println("- Entre chaque saisie il vous sera demandé si vous souhaitez continuer.");
        System.out.println();
        chooseMarble(move,1,board,player);
        if(askContinue()){
            chooseMarble(move,2,board,player);
            if(askContinue()){
                chooseMarble(move,3,board,player);
            }
        }
        move.setMoveDirection(chooseDirection());
        System.out.println("");
        System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        return move;
    }

    /**
     * Method to define what to do if the move the player composed is impossible due to many possible reasons <br>
     * In our case the player is ask to compose a new move and this move is performed
     * @param player player who composed an invalid move
     * @param board the game board
     * @return in our case it returns the number of marbles ejected from the board
     */
    public int handleImpossibleMoveException(Player player, Board board) {
        System.out.println("Erreur : mouvement impossible, veuillez recommencer !");
        int marblesOut = 0;
        while(true){
            try {
                marblesOut = composeMove(player,board).performMove();
                break;
            } catch (ImpossibleMoveException e) {
                System.out.println("Erreur : mouvement impossible, veuillez recommencer !");
            }
        }
        return marblesOut;
    }

    /**
     * Method to display something when some marbles have been ejected from the board
     * @param outMarblesCount number of marbles ejected
     * @param player player who ejected marbles
     */
    public void displayOutMarbles(int outMarblesCount, Player player) {
        System.out.println(player.getPlayerName()+" a mis hors jeu "+outMarblesCount+" bille(s) du joueurs adverse !");
    }

    /**
     * Method to display something when the turn of a player ends
     * @param board the game board
     */
    public void turnEnd(Board board) {
        System.out.println(board.toString(false));
    }

    /**
     * Method to display something when someone win the game
     * @param player the player who wins the game
     */
    public void displayWinner(Player player) {
        System.out.println("Partie terminée !");
        System.out.println(player.getPlayerName()+" remporte la partie avec les billes "+player.getColor().toString(true));
        System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
    }

    // Private methods to make choices un precedent methods
    private int chooseCoordinate(Board board) throws OutOfBoardException {
        int coord = readInput.nextInt();
        if(coord>=board.BOARD_SIZE_GUTTER || coord<0){
            throw new OutOfBoardException();
        } else {
            return coord;
        }
    }

    private void chooseMarble(Move move, int number, Board board, Player player) {
        while(true) {
            System.out.print("\n\n");
            System.out.println(board.toString(true));
            int vert = 0;
            while (true) {
                try {
                    System.out.println("Veuillez saisir la coordonnée verticale de la bille numéro " + number + " :");
                    vert = chooseCoordinate(board);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("La valeur entrée n'est pas un entier, veuillez réessayer ! ");
                } catch (OutOfBoardException e) {
                    System.out.println("La valeur entrée est en dehors du plateau, veuillez réessayer ! ");
                }
            }
            int hori = 0;
            while (true) {
                try {
                    System.out.println("Veuillez saisir la coordonnée horizontale de la bille numéro " + number + " :");
                    hori = chooseCoordinate(board);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("La valeur entrée n'est pas un entier, veuillez réessayer !");
                } catch (OutOfBoardException e) {
                    System.out.println("La valeur entrée est en dehors du plateau, veuillez réessayer ! ");
                }
            }
            try {
                move.addMarbleToMove(board.getBoardSquares()[vert][hori],player);
                break;
            } catch (MoveException e) {
                System.out.println(e);
            }
        }
    }

    private Direction chooseDirection() {
        int dir = -1;
        while(true) {
            System.out.println("Choix de la direction du déplacement des billes sélectionnées");
            System.out.println("1 - Direction droite haute");
            System.out.println("2 - Direction gauche haute");
            System.out.println("3 - Direction droite ");
            System.out.println("4 - Direction gauche");
            System.out.println("5 - Direction droite basse");
            System.out.println("6 - Direction gauche basse");
            try {
                dir = readInput.nextInt();
                if (dir < 1 || dir > 6) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("La valeur entrée est incorrecte, veuillez réessayer ! ");
            }
        }
        switch (dir){
            case 1:
                return Direction.TOPRIGHT;
            case 2:
                return Direction.TOPLEFT;
            case 3:
                return Direction.RIGHT;
            case 4:
                return Direction.LEFT;
            case 5:
                return Direction.BOTTOMRIGHT;
            case 6:
                return Direction.BOTTOMLEFT;
            default:
                return Direction.RIGHT;
        }
    }

    private boolean askContinue() {
        System.out.println("Souhaitez-vous ajouter une autre bille au mouvement ?");
        System.out.println("0 - Non");
        System.out.println("1 - Oui");
        int rep = -1;
        while (true) {
            try {
                System.out.println("Veuillez saisir le numéro de votre réponse : ");
                rep = readInput.nextInt();
                if(!(rep == 1 || rep == 0)) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("La valeur entrée n'est pas valide, veuillez réessayer !");
            }
        }
        if(rep == 1){
            return true;
        } else {
            return false;
        }
    }
}
