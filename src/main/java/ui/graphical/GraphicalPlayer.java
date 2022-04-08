package ui.graphical;

import model.*;
import model.piece.Piece;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphicalPlayer extends Player {
    GUI ui;

    public GraphicalPlayer(String name, String color, GUI ui) {
        super(name, color);
        this.ui = ui;
    }

    public ArrayList<Object> getPiece(Piece p) {
        int x = ui.getXCoordinate();
        int y = ui.getYCoordinate();

        return new ArrayList<Object>(Arrays.asList(p, new ArrayList<>(Arrays.asList(x, y))));
    }
}
