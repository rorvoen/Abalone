package abalone.model;

import abalone.enums.SquareContent;
import abalone.test.TestConfig;

/**
 * The board of the game. <br>
 * The hexagonal board is represented by a 11x11 array of square, one square is one position on the board. <br>
 * The fact that the array has a square shape imply that there is more squares in the array than of the board. <br>
 * The additional squares have a void content to differentiate them. <br>
 * The gutter all around the board is also represented to make the ejection handing easier.
 */
public class Board {
    private Square[][] boardSquares;
    public final int BOARD_SIZE = 9;
    public final int BOARD_SIZE_GUTTER = BOARD_SIZE+2;
    private TestConfig testConfig = null;

    public Board() {
        this.boardSquares = new Square[BOARD_SIZE_GUTTER][BOARD_SIZE_GUTTER];

        // Initialize the board with empty squares
        for (int i=0; i<BOARD_SIZE_GUTTER; i++) {
            for (int j=0; j<BOARD_SIZE_GUTTER; j++) {
                boardSquares[i][j] = new Square(SquareContent.EMPTY,i,j);
            }
        }

        defineVoidSquares();
        linkSquares();
        initStartPositions();
    }

    public Board(TestConfig testConfig){
        this();
        this.testConfig = testConfig;
        initStartPositions();
    }

    private void defineVoidSquares() {
        // Define that the first column of the board is void
        for(int i=0; i<BOARD_SIZE_GUTTER; i++){
            boardSquares[0][i].setContent(SquareContent.VOID);
            boardSquares[BOARD_SIZE_GUTTER-1][i].setContent(SquareContent.VOID);
        }

        // Define that the first and last square of each column is void
        for(int i=1; i<BOARD_SIZE_GUTTER-1; i++) {
            boardSquares[i][0].setContent(SquareContent.VOID);
            boardSquares[i][BOARD_SIZE_GUTTER - 1].setContent(SquareContent.VOID);
        }

        // Correct the shape of the board for the top right 10 squares
        int offseti = (int) Math.floor(BOARD_SIZE/2.0);
        int offsetj = offseti;
        for(int i = 1; i<offseti+1; i++) {
            for(int j = 1; j<offsetj+1; j++) {
                boardSquares[i][j].setContent(SquareContent.VOID);
            }
            offsetj--;
        }

        // Correct the shape of the board for the bottom left 10 squares
        offseti = (int) Math.floor(BOARD_SIZE/2.0);
        offsetj = offseti;
        for(int i = BOARD_SIZE_GUTTER-2; i>BOARD_SIZE_GUTTER-2-offseti; i--) {
            for(int j = BOARD_SIZE_GUTTER-2; j>BOARD_SIZE_GUTTER-2-offsetj; j--) {
                boardSquares[i][j].setContent(SquareContent.VOID);
            }
            offsetj--;
            boardSquares[i][BOARD_SIZE_GUTTER-2].setContent(SquareContent.VOID);
        }
    }

    private void linkSquares() {
        for(int j = 0; j<BOARD_SIZE_GUTTER; j++) {
            for(int i = 0; i<BOARD_SIZE_GUTTER; i++) {
                Square right = null;
                Square left = null;
                Square topRight = null;
                Square topLeft = null;
                Square bottomRight = null;
                Square bottomLeft= null;
                if(j+1 < BOARD_SIZE_GUTTER){
                    right = boardSquares[i][j+1];
                }
                if(j-1 >= 0) {
                    left =boardSquares[i][j-1];
                }
                if((i-1 >= 0) && (j+1 < BOARD_SIZE_GUTTER)){
                    topRight = boardSquares[i-1][j+1];
                }
                if(i-1 >= 0){
                    topLeft = boardSquares[i-1][j];
                }
                if(i+1 < BOARD_SIZE_GUTTER){
                    bottomRight = boardSquares[i+1][j];
                }
                if((i+1 < BOARD_SIZE_GUTTER) && (j-1 >= 0)){
                    bottomLeft = boardSquares[i+1][j-1];
                }
                boardSquares[i][j].setNeighbors(right,left,topRight,topLeft,bottomRight,bottomLeft);
            }
        }
    }

    private void initStartPositions() {
        if(testConfig != null) {
            this.resetBoard();
            testConfig.setupTestConfig(this);
        } else {
            //Première ligne noire
            this.boardSquares[1][5].setContent(SquareContent.BLACK);
            this.boardSquares[1][6].setContent(SquareContent.BLACK);
            this.boardSquares[1][7].setContent(SquareContent.BLACK);
            this.boardSquares[1][8].setContent(SquareContent.BLACK);
            this.boardSquares[1][9].setContent(SquareContent.BLACK);

            //Deuxième ligne noire
            this.boardSquares[2][4].setContent(SquareContent.BLACK);
            this.boardSquares[2][5].setContent(SquareContent.BLACK);
            this.boardSquares[2][6].setContent(SquareContent.BLACK);
            this.boardSquares[2][7].setContent(SquareContent.BLACK);
            this.boardSquares[2][8].setContent(SquareContent.BLACK);
            this.boardSquares[2][9].setContent(SquareContent.BLACK);

            //Troisième ligne noire
            this.boardSquares[3][5].setContent(SquareContent.BLACK);
            this.boardSquares[3][6].setContent(SquareContent.BLACK);
            this.boardSquares[3][7].setContent(SquareContent.BLACK);

            //Première ligne blanche
            this.boardSquares[9][1].setContent(SquareContent.WHITE);
            this.boardSquares[9][2].setContent(SquareContent.WHITE);
            this.boardSquares[9][3].setContent(SquareContent.WHITE);
            this.boardSquares[9][4].setContent(SquareContent.WHITE);
            this.boardSquares[9][5].setContent(SquareContent.WHITE);

            //Deuxième ligne blanche
            this.boardSquares[8][1].setContent(SquareContent.WHITE);
            this.boardSquares[8][2].setContent(SquareContent.WHITE);
            this.boardSquares[8][3].setContent(SquareContent.WHITE);
            this.boardSquares[8][4].setContent(SquareContent.WHITE);
            this.boardSquares[8][5].setContent(SquareContent.WHITE);
            this.boardSquares[8][6].setContent(SquareContent.WHITE);

            //Troisième ligne blanche
            this.boardSquares[7][3].setContent(SquareContent.WHITE);
            this.boardSquares[7][4].setContent(SquareContent.WHITE);
            this.boardSquares[7][5].setContent(SquareContent.WHITE);
        }
    }

    private void resetBoard() {
        for (int i=0; i<BOARD_SIZE_GUTTER; i++) {
            for (int j=0; j<BOARD_SIZE_GUTTER; j++) {
                boardSquares[i][j] = new Square(SquareContent.EMPTY,i,j);
            }
        }

        defineVoidSquares();
        linkSquares();
    }

    public String toString(boolean coords) {
        String[][] rep = new String[BOARD_SIZE+1][2];
        // Init array
        for(int h=0; h<rep.length; h++){
            rep[h] = new String[]{"", ""};
        }

        //Calculation of board middle line
        int middle = (int) Math.ceil(BOARD_SIZE/2.0);

        for (int j=1; j<BOARD_SIZE_GUTTER-1; j++) {
            for (int i=1; i<BOARD_SIZE_GUTTER-1; i++) {
                SquareContent currentContent = boardSquares[i][j].getContent();
                SquareContent previousContent = boardSquares[i][j-1].getContent();
                SquareContent nextContent = boardSquares[i][j+1].getContent();
                boolean firstLine = (1==i);
                boolean lastLine = ((BOARD_SIZE)==i);
                // Display void squares
                if(currentContent.equals(SquareContent.VOID)){
                    if(i<middle){
                        rep[i-1][0] = rep[i-1][0]+"  ";
                        rep[i-1][1] = rep[i-1][1]+"  ";
                    }
                    if(i>middle) {
                        if(!(previousContent.equals(SquareContent.VOID))){
                            rep[i-1][0] = "└─"+rep[i-1][0];
                            rep[i-1][1] = "  "+rep[i-1][1];
                        } else {
                            rep[i-1][0] = "  "+rep[i-1][0];
                            rep[i-1][1] = "  "+rep[i-1][1];
                        }
                        if(lastLine){
                            rep[i][0] = "  "+rep[i][0];
                        }
                    }
                }
                // Handling other type of content
                else {
                //Top part of square
                    if(previousContent.equals(SquareContent.VOID)){
                        if(i<=middle){
                            rep[i-1][0] = rep[i-1][0]+"┌─";
                        } else {
                            rep[i-1][0] = rep[i-1][0]+"┬─";
                        }
                    } else {
                        rep[i-1][0] = rep[i-1][0]+"┬─";
                    }
                    //If first line of the board
                    if(firstLine){
                        rep[i-1][0] = rep[i-1][0]+"──";
                    } else {
                        rep[i-1][0] = rep[i-1][0]+"┴─";
                    }
                    //If last square in the line
                    if(nextContent.equals(SquareContent.VOID)){
                        if(i<=middle){
                            rep[i-1][0] = rep[i-1][0]+"┐";
                        } else {
                            rep[i-1][0] = rep[i-1][0]+"┬─┘";
                        }
                    }
                //Middle part of square
                    if(coords) {
                        rep[i-1][1] = rep[i-1][1]+"│"+i+currentContent.toString()+j;
                    } else {
                        rep[i-1][1] = rep[i-1][1]+"│ "+currentContent.toString()+" ";
                    }
                    if(nextContent.equals(SquareContent.VOID)){
                        rep[i-1][1] = rep[i-1][1]+"│";
                    }
                //Bottom part of square
                    if(lastLine){
                        if(previousContent.equals(SquareContent.VOID)){
                            rep[i][0] = rep[i][0] + "└───";
                        } else {
                            rep[i][0] = rep[i][0] + "┴───";
                            if(nextContent.equals(SquareContent.VOID)){
                                rep[i][0] = rep[i][0] + "┘";
                            }
                        }
                    }
                }
            }
        }
        String ret = "";
        for(String[] line : rep){
            for(String halfLine: line){
                ret = ret + halfLine + "\n";
            }
        }
        return ret;
    }

    public Square[][] getBoardSquares() {
        return boardSquares;
    }
}
