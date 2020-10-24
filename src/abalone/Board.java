package abalone;

import abalone.exceptions.FullMoveException;
import abalone.exceptions.NoMarbleException;
import abalone.exceptions.NotAlignedException;
import abalone.exceptions.VoidSquareException;

public class Board {
    private Square[][] boardSquares;
    private final int BOARD_SIZE = 9;
    private final int BOARD_SIZE_GUTTER = BOARD_SIZE+2;

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
    }

    void defineVoidSquares() {
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

    void linkSquares(){
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

    String testBoard() {
        String ret = "";
        for (int i=0; i<BOARD_SIZE_GUTTER; i++) {
            for (int j=0; j<BOARD_SIZE_GUTTER; j++) {
                if(boardSquares[i][j].getContent().equals(SquareContent.EMPTY)){
                    ret = ret + "□ ";
                } else {
                    ret = ret + boardSquares[i][j].getContent().toString() +" ";
                }
                if(i==0 && j==BOARD_SIZE_GUTTER-1) {
                    ret = ret + "j";
                }
            }
            ret = ret + "\n";
            if(i == BOARD_SIZE_GUTTER-1){
                ret = ret + "i";
            }
        }
        return ret;
    }

    public String toString() {
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
                    rep[i-1][1] = rep[i-1][1]+"│ "+currentContent.toString()+" ";
                    //Debug rep[i-1][1] = rep[i-1][1]+"│"+i+currentContent.toString()+j;
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

    public static void main(String[] args) {
        Board test = new Board();
        test.getBoardSquares()[4][8].setContent(SquareContent.BLACK);
        test.getBoardSquares()[4][8].getLeft().setContent(SquareContent.BLACK);
        test.getBoardSquares()[4][7].getLeft().setContent(SquareContent.BLACK);
        Move testMove = new Move(Direction.LEFT);
        try {
            testMove.addMarbleToMove(test.getBoardSquares()[4][8]);
            testMove.addMarbleToMove(test.getBoardSquares()[4][8].getLeft());
            testMove.addMarbleToMove(test.getBoardSquares()[4][7].getLeft());
        } catch (NoMarbleException e) {
            e.printStackTrace();
        } catch (VoidSquareException e) {
            e.printStackTrace();
        } catch (FullMoveException e) {
            e.printStackTrace();
        } catch (NotAlignedException e) {
            e.printStackTrace();
        }


        System.out.println(test.testBoard());
        System.out.println(test);
    }
}
