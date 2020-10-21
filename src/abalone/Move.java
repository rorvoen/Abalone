package abalone;

import abalone.exceptions.FullMoveException;
import abalone.exceptions.NoMarbleException;
import abalone.exceptions.VoidSquareException;

import java.util.ArrayList;

public class Move {
    private ArrayList<Square> polynomial;
    private MoveDirection direction;

    public Move(ArrayList<Square> polynomial, MoveDirection direction) {
        this.polynomial = polynomial;
        this.direction = direction;
    }

    public void addMarbleToMove(Square marble) throws NoMarbleException, VoidSquareException, FullMoveException {
        if(marble.getContent() == SquareContent.VOID){
            throw new VoidSquareException();
        } else if(marble.getContent() == SquareContent.EMPTY) {
            throw new NoMarbleException();
        } else {
            if(this.polynomial.size()>=3){
                throw new FullMoveException();
            } else {
                this.polynomial.add(marble);
            }
        }
    }

    public void performMove(){

    }

    public ArrayList<Square> getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(ArrayList<Square> polynomial) {
        this.polynomial = polynomial;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public void setDirection(MoveDirection direction) {
        this.direction = direction;
    }
}
