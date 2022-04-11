/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 *
 * GraphicalBoard class
 *  - Board class for GUI
 */

package ui.graphical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.Board;

public class GraphicalBoard extends Board {
    float boardX;
    float boardY;
    float boardWidth;
    float boardHeight;
    float cellWidth;
    float cellHeight;
    TextureRegion blackSquare;
    TextureRegion whiteSquare;

    public GraphicalBoard(float boardX, float boardY, float boardWidth, float boardHeight, TextureRegion blackSquare, TextureRegion whiteSquare) {
        super();
        this.boardX = boardX;
        this.boardY = boardY;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.blackSquare = blackSquare;
        this.whiteSquare = whiteSquare;
        cellHeight = boardHeight / Board.HEIGHT;
        cellWidth = boardWidth / Board.WIDTH;
    }

    public int getBoardColumn(float x) {
        int result = -1;
        if ((boardX < x) && ((boardX + boardWidth) > x)) {
            result = (int)((x - boardX) / cellWidth);
        }
        return result;
    }

    public int getBoardRow(float y) {
        int result = -1;
        if ((boardY < y) && ((boardY + boardHeight) > y)) {
            result = (int)((y - boardY) / cellHeight);
        }
        return result;
    }

    /**
     * Function to update board in gui
     * @param board board
     */
    public void updateBoard(Board board) {
        super.setBoard(board.getBoard());
        super.setOccupied(board.getOccupied());
    }

    /**
     * Draw the gamepiece on the board according to players' color
     * @param batch spritebatch
     */
    public void draw(SpriteBatch batch) {
        for (int y = 0; y < Board.HEIGHT; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                if (isOccupied(x, y)) {
                    TextureRegion picture;
                    picture = (getColorOnSquare(x, y) == "X") ? blackSquare : whiteSquare;
                    batch.draw(picture, boardX + y * cellWidth, boardY + (13 - x) * cellHeight);
                }
            }
        }
    }

    /**
     * Check if the board is hit
     * @param x coordinate x
     * @param y coordinate y
     * @return true if the board is hit, otherwise false
     */
    public boolean isHit(float x, float y) {
        return (getBoardColumn(x) != -1) && (getBoardRow(y) != -1);
    }
}
