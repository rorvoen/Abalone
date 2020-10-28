package abalone;

public enum SquareContent {
    VOID,
    EMPTY,
    BLACK,
    WHITE;

    @Override
    public String toString() {
        switch(this) {
            case VOID: return "■";
            case EMPTY: return " ";
            case BLACK: return "⏺";
            case WHITE: return "○";
            default: throw new IllegalArgumentException();
        }
    }

    public SquareContent getOpponentColor() {
        SquareContent ret = null;
        if(this.equals(SquareContent.BLACK)){
            ret = SquareContent.WHITE;
        } else if (this.equals(SquareContent.WHITE)){
            ret = SquareContent.BLACK;
        }
        return  ret;
    }
}

