package ui.graphical;

import java.util.ArrayList;
import java.util.Arrays;

import controller.FirstTurnMove;
import controller.MidGameMove;
import model.*;
import model.piece.Piece;

public class GraphicalPlayer extends Player {
    GUI ui;

    public GraphicalPlayer(String color, GUI ui) {
        super(color);
        this.ui = ui;
    }

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

        return new ArrayList<Object>(Arrays.asList(p, new ArrayList<>(Arrays.asList(x, y))));
    }
    
}
