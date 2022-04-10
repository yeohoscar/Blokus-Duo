package ui.graphical;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import model.piece.*;

public class GraphicalGamepiece {
    private final Piece gamepiece;
    private final TextureRegion square;
    private float originX; // world X coordinate of the gamepiece's origin
    private float originY; // world Y coordinate of the gamepiece's origin
    private int playerColor;
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

    public void resetLocation() {
        currentX = originX;
        currentY = originY;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setLocation(float newX, float newY) {
        currentX = newX;
        currentY = newY;
    }

    public void setPosition(float x, float y) {
        // set new position of the gamepiece, so that the pointer coordinates
        // are in the middle of the "origin" square ( Location(0,0) ).
        currentX = x - square.getRegionWidth() * 0.5f;
        currentY = y - square.getRegionHeight() * 0.5f;
    }

    public void draw(SpriteBatch batch) {
        ArrayList<Block> blocks = gamepiece.getBlocks();
        for(Block l : blocks) {
            if (visible) {
                batch.draw(square, currentX + square.getRegionWidth() * l.getX(), currentY + square.getRegionHeight() * l.getY());
            }
        }
    }

    public void flipPiece() {
        gamepiece.flipPiece();
    }

    public void rotatePiece() {
        gamepiece.rotatePieceClockwise();
    }

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
