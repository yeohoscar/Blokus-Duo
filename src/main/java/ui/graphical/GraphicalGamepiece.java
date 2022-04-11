/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 *
 * GraphicalGamepiece class
 *  - Game piece class for GUI
 */

 package ui.graphical;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import model.piece.*;

public class GraphicalGamepiece {
    private final Piece gamepiece;
    private final TextureRegion square;
    private final float originX; // world X coordinate of the gamepiece's origin
    private final float originY; // world Y coordinate of the gamepiece's origin
    private final int playerColor;
    float currentX;
    float currentY;
    boolean visible;

    public GraphicalGamepiece(Piece gamepiece, TextureRegion square, float originX, float originY, int playerColor) {
        this.gamepiece = gamepiece;
        this.square = square;
        this.originX = originX;
        this.originY = originY;
        this.playerColor = playerColor;
        resetLocation();
        setVisible(true);
    }

    public String getPlayerColor() {
        if (playerColor == 0) {
            return "X";
        }
        return "O";
    }

    public Piece getGamePiece() {
        return gamepiece;
    }

    /**
     * Function to reset the location of graphical gamepiece
     */
    public void resetLocation() {
        currentX = originX;
        currentY = originY;
    }

    /**
     * Function to set the visibility of graphical gamepiece
     * @param visible boolean value to represent the visibility of gamepiece
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Set new location for graphical gamepiece
     * @param newX new coordinate x
     * @param newY new coordinate y
     */
    public void setLocation(float newX, float newY) {
        currentX = newX;
        currentY = newY;
    }

    /**
     * Function to set new position of the gamepiece
     * so that the pointer coordinates are in the middle of the "origin" square ( Location(0,0) )
     * @param x coordinate x
     * @param y coordinate y
     */
    public void setPosition(float x, float y) {
        currentX = x - square.getRegionWidth() * 0.5f;
        currentY = y - square.getRegionHeight() * 0.5f;
    }

    /**
     * Draw the graphical gamepiece
     * @param batch spritebatch
     */
    public void draw(SpriteBatch batch) {
        ArrayList<Block> blocks = gamepiece.getBlocks();
        for(Block l : blocks) {
            if (visible) {
                batch.draw(square, currentX + square.getRegionWidth() * l.getX(), currentY + square.getRegionHeight() * l.getY());
            }
        }
    }

    /**
     * Flip gamepiece
     */
    public void flipPiece() {
        gamepiece.flipPiece();
    }

    /**
     * Rotate gamepiece
     */
    public void rotatePiece() {
        gamepiece.rotatePieceClockwise();
    }

    /**
     * Check if gamepiece is hit by user
     * @param x coordinate x
     * @param y coordinate y
     * @return true if gamepiece is hitted, otherwise false
     */
    public boolean isHit(float x, float y) {
        boolean isHit = false;
        ArrayList<Block> blocks = gamepiece.getBlocks();
        for(Block l : blocks) {
            Rectangle rectangle = new Rectangle(currentX + l.getX() * square.getRegionWidth(),
                                                currentY + l.getY() * square.getRegionHeight(),
                                                square.getRegionWidth(),
                                                square.getRegionHeight());
            if (rectangle.contains(x, y)) {
                isHit = true;
            }
        }
        return isHit;
    }
}
