package abalone;

import abalone.exceptions.*;

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

    public void performMove() throws ImpossibleMoveException {
        boolean noVoid = true;
        boolean sumito = true;
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
            i++;
        }
        if(!noVoid || !sumito) {
            throw new ImpossibleMoveException();
        } else {
            for (Square sq : polynomial) {
                Square moveSquare = sq.getNeighbor(moveDirection);
                if(moveSquare.getContent().equals(SquareContent.EMPTY)){
                    // On déplace simplement la bille
                    moveMarble(sq);
                } else if (moveSquare.getContent().equals(sq.getContent().getOpponentColor())) {
                    // Si cas de sumito on l'execute avant de déplacer la bille
                    sumitoMarble(sq);
                    moveMarble(sq);
                } else if(moveSquare.getContent().equals(sq.getContent())){
                    // On Cherche la bille en bout de ligne, la première à bouger, soit sur une case vide, soit sur une bille adverse
                    int lineEndIndex = -1;
                    if(polynomial.get(0).getNeighbor(moveDirection).getContent().equals(SquareContent.EMPTY) ||
                       polynomial.get(0).getNeighbor(moveDirection).getContent().equals(polynomial.get(0).getContent().getOpponentColor())){
                        lineEndIndex = 0;
                    } else if (polynomial.get(polynomial.size()-1).getNeighbor(moveDirection).getContent().equals(SquareContent.EMPTY) ||
                               polynomial.get(polynomial.size()-1).getNeighbor(moveDirection).getContent().equals(polynomial.get(polynomial.size()-1).getContent().getOpponentColor())){
                        lineEndIndex = polynomial.size()-1;
                    }
                    // Selon le cas on execute le mouvement approprié
                    if(lineEndIndex == 0){
                        for(int j = lineEndIndex; j<polynomial.size(); j++){
                            if(j == lineEndIndex && polynomial.get(j).getNeighbor(moveDirection).getContent().equals(polynomial.get(j).getContent().getOpponentColor())){
                                sumitoMarble(polynomial.get(j));
                            }
                            moveMarble(polynomial.get(j));
                        }
                    } else {
                        for(int j = lineEndIndex; j>=0; j--){
                            if(j == lineEndIndex && polynomial.get(j).getNeighbor(moveDirection).getContent().equals(polynomial.get(j).getContent().getOpponentColor())){
                                sumitoMarble(polynomial.get(j));
                            }
                            moveMarble(polynomial.get(j));
                        }
                    }
                }
            }
        }
    }

    public boolean checkSumitoPossible() {
        ArrayList<Square> opponentMarbles = new ArrayList<>();
        boolean ret = false;
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

    public void moveMarble(Square marbleToMove){
        Square moveSquare = marbleToMove.getNeighbor(moveDirection);
        moveSquare.setContent(marbleToMove.getContent());
        marbleToMove.setContent(SquareContent.EMPTY);
    }

    public void sumitoMarble(Square marbleToMove){
        Square moveSquare = marbleToMove.getNeighbor(moveDirection);
        Square sumitoMoveSquare = moveSquare.getNeighbor(moveDirection);
        if(sumitoMoveSquare.getContent().equals(SquareContent.VOID)){
            moveSquare.setContent(SquareContent.EMPTY);
            System.out.println("Une bille poussée en dehors du plateau !");
        } else {
            moveMarble(moveSquare);
        }
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
