package abalone.test;

import abalone.display.ConsoleDisplay;
import abalone.Game;

public class TestMain {
    public static void main(String[] args) {
        Game test1 = new Game(new ConsoleDisplay(), new ArrowMoveTest());
        test1.start();
        Game test2 = new Game(new ConsoleDisplay(), new LineMoveTest());
        test2.start();
        Game test3 = new Game(new ConsoleDisplay(), new LineSumitoValidMoveTest());
        test3.start();
        Game test4 = new Game(new ConsoleDisplay(), new ArrowSumitoValidMoveTest());
        test4.start();
        Game test5 = new Game(new ConsoleDisplay(), new LineSumitoInvalidMoveTest());
        test5.start();
        Game test6 = new Game(new ConsoleDisplay(), new ArrowSumitoInvalidMoveTest());
        test6.start();
        Game test7 = new Game(new ConsoleDisplay(), new LineSumitoBlockedMoveTest());
        test7.start();
        Game test8 = new Game(new ConsoleDisplay(), new ArrowSumitoBlockedMoveTest());
        test8.start();
        Game test9 = new Game(new ConsoleDisplay(), new LineSumitoEjectionMoveTest());
        test9.start();
        Game test10 = new Game(new ConsoleDisplay(), new ArrowSumitoEjectionMoveTest());
        test10.start();

    }
}
