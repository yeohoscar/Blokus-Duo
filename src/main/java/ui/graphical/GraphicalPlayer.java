/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 *
 * GraphicalPlayer class
 *  - Player class for GUI
 */
package ui.graphical;

import java.util.ArrayList;
import java.util.Arrays;

import model.*;
import model.piece.Piece;

public class GraphicalPlayer extends Player {
    GUI ui;

    public GraphicalPlayer(String color, GUI ui) {
        super(color);
        this.ui = ui;
    }

    /**
     * Get piece from players in gui
     */
    @Override
    public ArrayList<Object> getPiece() {
        Piece p = null;
        try {
            ui.setCurrentPlayerColor(getColor());
            ui.setCurrentPlayerValidMove(getValidMove());
            p = ui.getPieceQueue().take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int x = ui.getXCoordinate();
        int y = ui.getYCoordinate();

        return new ArrayList<>(Arrays.asList(p, new ArrayList<>(Arrays.asList(x, y))));
    }
    
}
