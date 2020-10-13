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
}

