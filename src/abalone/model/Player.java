package abalone.model;

import abalone.enums.PlayerColor;

/**
 * A player of the Abalone game. <br>
 * Represented by his name and the color of it's marbles, whether he starts the game and the count of it's marbles ejected
 */
public class Player {
    private String playerName;
    private PlayerColor color;
    private boolean starter;
    private int outMarbles;

    public Player(PlayerColor color) {
        this.color = color;
        if(this.color == PlayerColor.BLACK) {
            this.starter = true;
        }
        this.outMarbles = 0;
    }

    public Player(String playerName, PlayerColor color) {
        this.playerName = playerName;
        this.color = color;
        if(this.color == PlayerColor.BLACK) {
            this.starter = true;
        }
        this.outMarbles = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
        if(this.color == PlayerColor.BLACK) {
            this.starter = true;
        }
    }

    public boolean isStarter() {
        return starter;
    }

    public void setStarter(boolean starter) {
        this.starter = starter;
    }

    public int getOutMarbles() {
        return outMarbles;
    }

    public void setOutMarbles(int outMarbles) {
        this.outMarbles = outMarbles;
    }
}
