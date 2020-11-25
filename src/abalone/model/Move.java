package abalone.model;

import abalone.enums.Direction;
import abalone.enums.SquareContent;
import abalone.exceptions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Represent a move to perform during the game. <br>
 * A move is composed by the marbles it involves, it's direction and an axis if it's a line movement
 */
public class Move {
    private ArrayList<Square> polynomial;
    private Direction moveDirection;
    private Direction axis;

    public Move() {
        this.polynomial = new ArrayList<Square>();
    }

    /**
     * Add a given marble to a move if it's possible
     * @param marble the board square containing the marble you want to add
     * @param player the player which want to add a marble to it's move
     * @throws MoveException differents exception according to the reason that make impossible adding the given marble to the move
     */
    public void addMarbleToMove(Square marble, Player player) throws MoveException {
        if(marble.getContent() == SquareContent.VOID){
            throw new VoidSquareException(marble.getI(),marble.getJ());
        } else if(marble.getContent() == SquareContent.EMPTY) {
            throw new NoMarbleException(marble.getI(),marble.getJ());
        } else {
            if(!player.getColor().checkGoodColor(marble.getContent())){
                throw new WrongColorException();
            } else {
                if (this.polynomial.size() >= 3) {
                    throw new FullMoveException();
                } else {
                    if (this.polynomial.size() == 0) {
                        this.polynomial.add(marble);
                    } else if (this.polynomial.size() == 1) {
                        //System.out.println(this.polynomial.get(0));
                        Iterator iterator = this.polynomial.get(0).getNeighbors().entrySet().iterator();
                        boolean nextToFirstMarble = false;
                        while (!nextToFirstMarble && iterator.hasNext()) {
                            Map.Entry<Direction, Square> toTest = (Map.Entry<Direction, Square>) iterator.next();
                            //System.out.println("ToTest : "+toTest.getValue());
                            //System.out.println("ToAdd : "+marble);
                            if (marble.equals(toTest.getValue())) {
                                this.polynomial.add(marble);
                                this.axis = toTest.getKey();
                                nextToFirstMarble = true;
                            }
                        }
                        if (!nextToFirstMarble) {
                            throw new NotAlignedException();
                        }
                    } else if (this.polynomial.size() == 2) {
                        if (marble.equals(this.polynomial.get(1).getNeighbor(this.axis))) {
                            this.polynomial.add(marble);
                        } else {
                            throw new NotAlignedException();
                        }
                    }
                }
            }
        }
    }

    /**
     * Launch the move
     * @return the number of opponent's ejected marbles by the move
     * @throws ImpossibleMoveException if the movement is impossible due to different reasons (superiority, blocked movement, etc...)
     */
    public int performMove() throws ImpossibleMoveException {
        int outMarbles = 0;
        boolean noVoid = true;
        boolean sumito = true;
        boolean noColision = true;
        int i = 0;
        while (i<polynomial.size() && noVoid == true && sumito == true){
            Square sq = polynomial.get(i);
            Square moveSquare = sq.getNeighbor(moveDirection);
            // On vérifie si le mouvement des billes du polynome n'entraîne pas de mise hors plateau de ses propres billes
            if(moveSquare.getContent().equals(SquareContent.VOID)){
                noVoid = false;
            }
            // On vérifie si les billes situées sur la trajectoire des billes du polynome, peuvent être poussées en sumito
            if(moveSquare.getContent().equals(sq.getContent().getOpponentColor())){
                sumito = checkSumitoPossible();
            }
            //On vérifie que le direction n'entraine pas de colision avec ses propres billes
            if(moveSquare.getContent().equals(sq.getContent())){
                if(!(moveDirection.equals(axis))){
                    noColision = false;
                }
            }
            i++;
        }
        if(!noVoid || !sumito || !noColision) {
            throw new ImpossibleMoveException();
        } else {
            if(moveDirection.equals(axis)){
                //Cas déplacement en ligne
                // On cherche la bille en bout de ligne, la première à bouger, soit sur une case vide, soit sur une bille adverse
                int lineEndIndex = -1;
                if(polynomial.get(0).getNeighbor(moveDirection).getContent().equals(SquareContent.EMPTY) ||
                        polynomial.get(0).getNeighbor(moveDirection).getContent().equals(polynomial.get(0).getContent().getOpponentColor())){
                    lineEndIndex = 0;
                } else if (polynomial.get(polynomial.size()-1).getNeighbor(moveDirection).getContent().equals(SquareContent.EMPTY) ||
                        polynomial.get(polynomial.size()-1).getNeighbor(moveDirection).getContent().equals(polynomial.get(polynomial.size()-1).getContent().getOpponentColor())){
                    lineEndIndex = polynomial.size()-1;
                }
                //On cherche la bille en début de ligne des billes adverses déplacées par le sumito
                ArrayList<Square> opponentMarblesToMove = getOpponentsNextToLine();
                if(!opponentMarblesToMove.isEmpty()) {
                    boolean find = false;
                    int h = 0;
                    while (!find && h < opponentMarblesToMove.size()) {
                        if (polynomial.get(lineEndIndex).getNeighbor(moveDirection).equals(opponentMarblesToMove.get(h))) {
                            find = true;
                        } else {
                            h++;
                        }
                    }
                    // Maintenant on récupère la bille en bout de ligne
                    Square nextOppponentMarbleInLine = opponentMarblesToMove.get(h);
                    while (!(nextOppponentMarbleInLine.getNeighbor(moveDirection).getContent().equals(SquareContent.VOID) ||
                            nextOppponentMarbleInLine.getNeighbor(moveDirection).getContent().equals(SquareContent.EMPTY))) {
                        nextOppponentMarbleInLine = nextOppponentMarbleInLine.getNeighbor(moveDirection);
                    }
                    // On déplace les billes adverses qui doivent l'être
                    Square nextOpponentMarbleToMove = nextOppponentMarbleInLine;
                    boolean continuer = true;
                    while (continuer) {
                        if (moveMarble(nextOpponentMarbleToMove)) {
                            outMarbles++;
                        }
                        ;
                        nextOpponentMarbleToMove = nextOpponentMarbleToMove.getNeighbor(moveDirection.getOpposite());
                        if (nextOpponentMarbleToMove.getContent().equals(nextOpponentMarbleToMove.getNeighbor(moveDirection.getOpposite()).getContent())) {
                            continuer = false;
                        }
                    }
                }
                // Selon le coté de la première bille de la ligne on execute le mouvement approprié
                if(lineEndIndex == 0){
                    for(int j = lineEndIndex; j<polynomial.size(); j++){
                        if(moveMarble(polynomial.get(j))){
                            outMarbles++;
                        }
                    }
                } else {
                    for(int j = lineEndIndex; j>=0; j--){
                        if(moveMarble(polynomial.get(j))){
                            outMarbles++;
                        }
                    }
                }
            } else {
                // Cas déplacement en flèche
                for (Square sq : polynomial) {
                    Square moveSquare = sq.getNeighbor(moveDirection);
                    if(moveSquare.getContent().equals(SquareContent.EMPTY)){
                        // On déplace simplement la bille
                        moveMarble(sq);
                    } else if (moveSquare.getContent().equals(sq.getContent().getOpponentColor())) {
                        // Si cas de sumito on l'execute avant de déplacer la bille
                        if(sumitoMarble(sq)){
                            outMarbles++;
                        }
                        moveMarble(sq);
                    }
                }
            }

        }
        return outMarbles;
    }

    private boolean checkSumitoPossible() {
        ArrayList<Square> opponentMarbles = new ArrayList<>();
        if(moveDirection.equals(axis)){
            ArrayList<Boolean> rets = new ArrayList<>();
            Boolean tempRet = false;
            // Cas sumito mouvement en ligne
            for(Square sq : polynomial){
                Square nextToPush = sq.getNeighbor(moveDirection);
                boolean loop = true;
                boolean emptySquare = false;
                while(loop){
                    if(nextToPush.getContent().equals(sq.getContent())){
                        tempRet = false;
                        loop = false;
                    } else if (nextToPush.getContent().equals(sq.getContent().getOpponentColor())) {
                        opponentMarbles.add(nextToPush);
                    } else if(nextToPush.getContent().equals(SquareContent.EMPTY)) {
                        emptySquare = true;
                        loop = false;
                    } else if(nextToPush.getContent().equals(SquareContent.VOID)) {
                        emptySquare = true;
                        loop = false;
                    }
                    nextToPush = nextToPush.getNeighbor(moveDirection);
                }
                if(emptySquare){
                    if(opponentMarbles.size()<this.polynomial.size()){
                        tempRet = true;
                    }
                }
                rets.add(tempRet);
            }
            for(Boolean ret : rets){
                if(ret){
                    return true;
                }
            }
            return false;
        } else {
            boolean ret = false;
            // Cas sumito mouvement en flèche
            for(Square sq : polynomial){
                Square moveSquare = sq.getNeighbor(moveDirection);
                if (moveSquare.getContent().equals(sq.getContent().getOpponentColor())) {
                    opponentMarbles.add(moveSquare);
                }
            }
            if(opponentMarbles.size()<this.polynomial.size()){
                ret = true;
            }
            int i = 0;
            while ((i<opponentMarbles.size() && ret == true)){
                if(opponentMarbles.get(i).getNeighbor(moveDirection).getContent().equals(SquareContent.WHITE) ||
                        opponentMarbles.get(i).getNeighbor(moveDirection).getContent().equals(SquareContent.BLACK)){
                    ret = false;
                }
                i++;
            }
            return ret;
        }
    }

    private boolean moveMarble(Square marbleToMove){
        boolean ret = false;
        Square moveSquare = marbleToMove.getNeighbor(moveDirection);
        if(moveSquare.getContent().equals(SquareContent.VOID)){
            ret = true;
            marbleToMove.setContent(SquareContent.EMPTY);
        } else {
            moveSquare.setContent(marbleToMove.getContent());
            marbleToMove.setContent(SquareContent.EMPTY);
        }
        return ret;
    }

    private boolean sumitoMarble(Square marbleToMove){
        boolean ret = false;
        Square moveSquare = marbleToMove.getNeighbor(moveDirection);
        Square sumitoMoveSquare = moveSquare.getNeighbor(moveDirection);
        if(sumitoMoveSquare.getContent().equals(SquareContent.VOID)){
            moveSquare.setContent(SquareContent.EMPTY);
            // Une bille est poussée en dehors du plateau
            ret = true;
        } else {
            moveMarble(moveSquare);
        }
        return  ret;
    }

    private ArrayList<Square> getOpponentsNextToLine() {
        ArrayList<Square> opponentMarbles = new ArrayList<>();
        for(Square sq : polynomial){
            Square nextToPush = sq.getNeighbor(moveDirection);
            boolean loop = true;
            while(loop){
                if(nextToPush.getContent().equals(sq.getContent())){
                    loop = false;
                } else if (nextToPush.getContent().equals(sq.getContent().getOpponentColor())) {
                    opponentMarbles.add(nextToPush);
                } else if(nextToPush.getContent().equals(SquareContent.EMPTY)) {
                    loop = false;
                } else if(nextToPush.getContent().equals(SquareContent.VOID)) {
                    loop = false;
                }
                nextToPush = nextToPush.getNeighbor(moveDirection);
            }
        }
        return opponentMarbles;
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

    public void resetMove() {
        this.polynomial = new ArrayList<>();
        this.moveDirection = null;
        this.axis = null;
    }

    public String toString() {
        String ret = "";
        ret = ret + "Direction : " + moveDirection + "\n";
        for(Square sq : polynomial){
            ret = ret + "v : " + sq.getI() + " | h : " + sq.getJ() + " | " + sq.getContent().toString() + "\n";
        }
        return ret;
    }
}
