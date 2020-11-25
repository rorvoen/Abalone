package abalone.enums;

/**
 * Color of the marbles of a player
 */
public enum PlayerColor {
    BLACK,
    WHITE;

    public String toString(boolean genderF) {
        if(genderF) {
            switch(this) {
                case BLACK: return "noire";
                case WHITE: return "blanche";
                default: throw new IllegalArgumentException();
            }
        } else {
            switch(this) {
                case BLACK: return "noir";
                case WHITE: return "blanc";
                default: throw new IllegalArgumentException();
            }
        }
    }

    public boolean checkGoodColor(SquareContent color) {
        switch (this) {
            case BLACK: if(color == SquareContent.BLACK){
                return true;
            } else {
                return false;
            }
            case WHITE: if(color == SquareContent.WHITE){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
