package abalone;

import abalone.display.ConsoleDisplay;
import abalone.display.Display;
import abalone.model.Board;
import abalone.model.Player;
import abalone.enums.PlayerColor;
import abalone.exceptions.ImpossibleMoveException;
import abalone.test.TestConfig;

public class Game {
    private Board board;
    private int turnCount;
    public Player player1;
    public Player player2;
    private Display display;
    public boolean player1Win;
    public boolean player2Win;
    public TestConfig testConfig;

    /**
     * Creates a new Abalone game
     * @param display the display method you choose for the game
     */
    public Game(Display display) {
        this.player1 = new Player(PlayerColor.BLACK);
        this.player2 = new Player(PlayerColor.WHITE);
        this.board = new Board();
        this.display = display;
        this.turnCount = 0;
        this.player1Win = player2.getOutMarbles()>=6;
        this.player2Win = player1.getOutMarbles()>=6;
        this.testConfig = null;
    }

    /**
     * Creates a game using a defined test case
     * @param display the display method you choose for the game
     * @param testConfig the test case to use
     */
    public Game(Display display, TestConfig testConfig) {
        this.player1 = new Player(PlayerColor.BLACK);
        this.player2 = new Player(PlayerColor.WHITE);
        this.board = new Board(testConfig);
        testConfig.setupTestMove(this.board, this.player1, this.player2);
        this.display = display;
        this.turnCount = 0;
        this.player1Win = player2.getOutMarbles()>=6;
        this.player2Win = player1.getOutMarbles()>=6;
        this.testConfig = testConfig;
    }

    /**
     * Starts the game
     */
    public void start() {
        if(testConfig != null) {
            testConfig.executeTest(this.board);
        } else {
            ConsoleDisplay conditionTestConsoleDisplay = new ConsoleDisplay();
            if(display.getClass().equals(conditionTestConsoleDisplay.getClass())){
                startConsole();
            } else {
                // Méthode à modifier
                startGUI();
            };
        }

    }

    /**
     * Starts the game using console display
     */
    private void startConsole() {
        display.displayTitleScreen();
        initializePlayersName();
        Player winner = gameLoop();
        display.displayWinner(winner);
    }

    /**
     * Starts the game using GUI display
     */
    private void startGUI() {
        // Création joueurs nécéssaire
        // Player winner = gameLoop();
    }

    /**
     * Initialize the name of the two player using the method defined by the display you chose
     */
    private void initializePlayersName() {
        String player1Name = this.display.getPlayername(player1);
        String player2Name = this.display.getPlayername(player2);
        this.player1.setPlayerName(player1Name);
        this.player2.setPlayerName(player2Name);
    }

    /**
     * The loop which is launched when the first turn start, only ends when someone wins the game
     * @return the player who wins
     */
    private Player gameLoop() {
        Player[] players = new Player[2];
        if(player1.isStarter()){
            players[0] = player1;
            players[1] = player2;
        } else {
            players[0] = player2;
            players[1] = player1;
        }
        boolean winner = player1Win || player2Win;
        int i = 0;
        Player currentPlayer = null;
        while (!winner){
            currentPlayer = players[i];
            this.playerTurn(currentPlayer);
            this.updateWinConditions();
            i++;
            if(i>=2) i = 0;
        }
        return currentPlayer;
    }

    /**
     * Represents the whole turn of one player
     * @param player the player concerned by the turn
     */
    private void playerTurn(Player player) {
        int marblesOut = 0;
        if(player.isStarter()){
            turnCount++;
        }
        display.turnStart(player,turnCount);
        try {
            marblesOut = display.composeMove(player,board).performMove();
        } catch (ImpossibleMoveException e) {
            marblesOut = display.handleImpossibleMoveException(player,board);
        }
        this.getOpponent(player).setOutMarbles(marblesOut);
        this.display.displayOutMarbles(marblesOut,player);
        display.turnEnd(board);
        updateWinConditions();
    }

    /**
     * Get the opponent to one given player
     * @param player one player
     * @return his opponent
     */
    private Player getOpponent(Player player) {
        if(player.equals(player1)) {
            return player2;
        } else if(player.equals(player2)){
            return player1;
        } else {
            return null;
        }
    }

    /**
     * Method to update the win condition booleans player1Win & player2Win after each player turn
     */
    private void updateWinConditions() {
        this.player1Win = player2.getOutMarbles()>=6;
        this.player2Win = player1.getOutMarbles()>=6;
    }
}
