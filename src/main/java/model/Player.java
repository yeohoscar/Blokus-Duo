/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Player class
 *  - represents Blokus player
 *  - holds player name, colour and pieces
 */

package model;

import model.piece.Stock;

import java.util.ArrayList;

import controller.FirstTurnMove;
import controller.MidGameMove;

public abstract class Player {
    private String name; //Player name
    private int score; //Player score -> calculate at end
    private final String color;
    private final Stock stock;
    private final ArrayList<int[]> validMove;
    private boolean lastPieceI1;

    public Player(String color) {
        this.color = color;
        score = 0;
        this.stock = new Stock();
        this.validMove = new ArrayList<>();
        lastPieceI1 = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getColor() {
        return color;
    }

    public Stock getStock() {
        return stock;
    }

    public ArrayList<int[]> getValidMove() {
        return validMove;
    }

    public boolean getLastPieceI1() {
        return lastPieceI1;
    }

    public void setLastPieceI1(boolean b) {
        this.lastPieceI1 = b;
    }

    public abstract ArrayList<Object> getPiece();
    public abstract FirstTurnMove getFirstMove();
    public abstract MidGameMove getMidMove();
}