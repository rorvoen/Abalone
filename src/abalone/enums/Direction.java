package abalone.enums;

/**
 * Possible directions for a move
 */
public enum Direction {
    RIGHT,
    LEFT,
    TOPRIGHT,
    TOPLEFT,
    BOTTOMRIGHT,
    BOTTOMLEFT;

    public Direction getOpposite() {
        Direction ret = null;
        if(this.equals(Direction.RIGHT)){
            ret = Direction.LEFT;
        } else if (this.equals(Direction.LEFT)){
            ret = Direction.RIGHT;
        } else if (this.equals(Direction.TOPRIGHT)){
            ret = Direction.BOTTOMLEFT;
        } else if (this.equals(Direction.TOPLEFT)){
            ret = Direction.BOTTOMRIGHT;
        } else if (this.equals(Direction.BOTTOMRIGHT)){
            ret = Direction.TOPLEFT;
        } else if (this.equals(Direction.BOTTOMLEFT)){
            ret = Direction.TOPRIGHT;
        }
        return  ret;
    }
}
