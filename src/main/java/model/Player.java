package model;

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

import model.piece.Stock;

import java.util.Scanner;
import java.util.ArrayList;

public class Player {
    private final String name; //Player name
    private final int score; //Player score -> calculate at end
    private final String color;
    private final Stock stock;
    private final ArrayList<int[]> validMove;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        score = 0;
        this.stock = new Stock();
        this.validMove = new ArrayList<int[]>();
    }

    public String getName() {
        return name;
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
}