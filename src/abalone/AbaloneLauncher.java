package abalone;

import abalone.display.ConsoleDisplay;

/**
 * Launcher class for Abalone game
 * You can change the display by changing the game constructor parameter
 */
public class AbaloneLauncher {
    public static void main(String[] args) {
        Game game = new Game(new ConsoleDisplay());
        game.start();
    }
}
