package ui.graphical;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import model.piece.*;

public class GraphicalGamepiece {
    private Piece gamepiece;
    private TextureRegion square;
    private float originX; // world X coordinate of the gamepiece's origin
    private float originY; // world Y coordinate of the gamepiece's origin

    public GraphicalGamepiece(Piece gamepiece, TextureRegion square, float originX, float originY) {
        this.gamepiece = gamepiece;
        this.square = square;
        this.originX = originX;
        this.originY = originY;
    }

    public void draw(SpriteBatch batch) {
        ArrayList<Block> blocks = gamepiece.getBlocks();
        for(Block l : blocks) {
            batch.draw(square, originX + square.getRegionWidth() * l.getX(), originY + square.getRegionHeight() * l.getY());
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
            Rectangle rectangle = new Rectangle(originX + l.getX() * square.getRegionWidth(),
                                                originY + l.getY() * square.getRegionHeight(),
                                                square.getRegionWidth(),
                                                square.getRegionHeight());
            if (rectangle.contains(x, y)) {
                isHit = true;
            }
        }
        return isHit;
    }

    public void setPosition(float x, float y) {
        // set new position of the gamepiece, so that the pointer coordinates
        // are in the middle of the "origin" square ( Location(0,0) ).
        originX = x - square.getRegionWidth() * 0.5f;
        originY = y - square.getRegionHeight() * 0.5f;
    }
}
