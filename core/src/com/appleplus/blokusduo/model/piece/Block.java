package com.appleplus.blokusduo.model.piece;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Auxillary class for Piece
 *  - Represents a block in a piece
 */

public class Block {
    private int x;
    private int y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Deep copy constructor
     * @param b is the Block object that is being copied
     */
    public Block(Block b) {
        this(b.getX(), b.getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void invertX() {
        x *= -1;
    }

    public void invertY() {
        y *= -1;
    }
}
