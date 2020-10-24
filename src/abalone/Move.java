package abalone;

import abalone.exceptions.FullMoveException;
import abalone.exceptions.NoMarbleException;
import abalone.exceptions.NotAlignedException;
import abalone.exceptions.VoidSquareException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Move {
    private ArrayList<Square> polynomial;
    private Direction moveDirection;
    private Direction axis;

    public Move(Direction direction) {
        this.moveDirection = direction;
        this.polynomial = new ArrayList<Square>();
    }

    public void addMarbleToMove(Square marble) throws NoMarbleException, VoidSquareException, FullMoveException, NotAlignedException {
        if(marble.getContent() == SquareContent.VOID){
            throw new VoidSquareException();
        } else if(marble.getContent() == SquareContent.EMPTY) {
            throw new NoMarbleException();
        } else {
            if(this.polynomial.size()>=3){
                throw new FullMoveException();
            } else {
                if(this.polynomial.size()==0){
                    this.polynomial.add(marble);
                } else if(this.polynomial.size()==1){
                    //System.out.println(this.polynomial.get(0));
                    Iterator iterator = this.polynomial.get(0).getNeighbors().entrySet().iterator();
                    boolean nextToFirstMarble = false;
                    while(!nextToFirstMarble && iterator.hasNext()) {
                        Map.Entry<Direction,Square> toTest = (Map.Entry<Direction, Square>) iterator.next();
                        //System.out.println("ToTest : "+toTest.getValue());
                        //System.out.println("ToAdd : "+marble);
                        if (marble.equals(toTest.getValue())) {
                            this.polynomial.add(marble);
                            this.axis = toTest.getKey();
                            nextToFirstMarble = true;
                        }
                    }
                    if(!nextToFirstMarble){
                        throw new NotAlignedException();
                    }
                } else if(this.polynomial.size()==2){
                    if(marble.equals(this.polynomial.get(1).getNeighbor(this.axis))){
                        this.polynomial.add(marble);
                    } else {
                        throw new NotAlignedException();
                    }
                }
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

    public Direction getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(Direction direction) {
        this.moveDirection = direction;
    }
}
