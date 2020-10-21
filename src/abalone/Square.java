package abalone;

public class Square {
    private Square right;
    private Square left;
    private Square topRight;
    private Square topLeft;
    private Square bottomRight;
    private Square bottomLeft;
    private SquareContent content;

    public Square(SquareContent content) {
        this.content = content;
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

    public void setNeighbors(Square right, Square left, Square topRight, Square topLeft, Square bottomRight, Square bottomLeft) {
        this.right = right;
        this.left = left;
        this.topRight = topRight;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }
}
