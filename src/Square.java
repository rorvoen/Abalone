public class Square {
    private Square top;
    private Square bottom;
    private Square topRight;
    private Square topLeft;
    private Square bottomRight;
    private Square bottomLeft;
    private SquareContent content;

    public Square(SquareContent content) {
        this.content = content;
    }

    //----- Getters and Setters -----//
    public Square getTop() {
        return top;
    }

    public void setTop(Square top) {
        this.top = top;
    }

    public Square getBottom() {
        return bottom;
    }

    public void setBottom(Square bottom) {
        this.bottom = bottom;
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
}
