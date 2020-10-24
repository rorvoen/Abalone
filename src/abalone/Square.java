package abalone;

import java.util.ArrayList;
import java.util.HashMap;

public class Square {
    private Square right;
    private Square left;
    private Square topRight;
    private Square topLeft;
    private Square bottomRight;
    private Square bottomLeft;
    private int i;
    private int j;
    private SquareContent content;

    public Square(SquareContent content, int i, int j) {
        this.content = content;
        this.i = i;
        this.j = j;
    }

    //----- Getters and Setters -----//


    public Square getRight() {
        return right;
    }

    public void setRight(Square right) {
        this.right = right;
    }

    public Square getLeft() {
        return left;
    }

    public void setLeft(Square left) {
        this.left = left;
    }

    public Square getTopRight() {
        return topRight;
    }

    public void setTopRight(Square topRight) {
        this.topRight = topRight;
    }

    public Square getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Square topLeft) {
        this.topLeft = topLeft;
    }

    public Square getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Square bottomRight) {
        this.bottomRight = bottomRight;
    }

    public Square getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(Square bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public SquareContent getContent() {
        return content;
    }

    public void setContent(SquareContent content) {
        this.content = content;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setNeighbors(Square right, Square left, Square topRight, Square topLeft, Square bottomRight, Square bottomLeft) {
        this.right = right;
        this.left = left;
        this.topRight = topRight;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    public HashMap<Direction,Square> getNeighbors() {
        HashMap<Direction,Square> ret = new HashMap<Direction,Square>();
        ret.put(Direction.RIGHT,this.right);
        ret.put(Direction.LEFT,this.left);
        ret.put(Direction.TOPRIGHT,this.topRight);
        ret.put(Direction.TOPLEFT,this.topLeft);
        ret.put(Direction.BOTTOMRIGHT,this.bottomRight);
        ret.put(Direction.BOTTOMLEFT,this.bottomLeft);
        return ret;
    }

    public Square getNeighbor(Direction direction) {
        Square ret = null;
        if(direction.equals(Direction.RIGHT)){
            return this.right;
        }
        if(direction.equals(Direction.LEFT)){
            return this.left;
        }
        if(direction.equals(Direction.TOPRIGHT)){
            return this.topRight;
        }
        if(direction.equals(Direction.TOPLEFT)){
            return this.topLeft;
        }
        if(direction.equals(Direction.BOTTOMRIGHT)){
            return this.bottomRight;
        }
        if(direction.equals(Direction.BOTTOMLEFT)){
            return this.bottomLeft;
        }
        return ret;
    }

    public String toString() {
        return "Content : "+this.content+" | i : "+this.i+" | j : "+this.j;
    }

    /*public boolean equals(Square square){
        boolean ret = false;
        if((this.i ==square.getI()) && (this.j == square.getJ()) && (this.content == square.getContent())){
            ret = true;
        }
        return ret;
    }*/
}
