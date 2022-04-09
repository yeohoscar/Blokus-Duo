package ui.text;

import java.util.ArrayList;
import java.util.Arrays;

import controller.FirstTurnMove;
import controller.MidGameMove;
import model.Player;
import model.piece.Piece;

public class TextPlayer extends Player {
    TextUI ui;

    public TextPlayer(String color, TextUI ui) {
        super(color);
        this.ui = ui;
    }

    public ArrayList<Object> getPiece() {
        if (getStock().getPieces().size() == 0) {
            System.out.println("No more pieces left.");
        }

        System.out.println("Select a piece");
        String tmp = ui.getS().useDelimiter(" |\\n").next();
        tmp = tmp.replaceAll("(?:\\n|\\r)", "");

        while(true) {
            for (Piece p : getStock().getPieces()) {
                if (p.getName().equals(tmp)) {
                    Piece pCopy = new Piece(p);
                    return new ArrayList<>(Arrays.asList(pCopy, pCopy.manipulation(ui.getS(), getColor())));
                }
            }
            System.out.println("Piece not in stock.\nSelect a piece");
            tmp = ui.getS().useDelimiter(" |\\n").next();
            tmp = tmp.replaceAll("(?:\\n|\\r)", "");
            ui.getS().next();
        }
    }

    /*@Override
    public FirstTurnMove FirstMove() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MidGameMove MidMove() {
        // TODO Auto-generated method stub
        return null;
    }*/
    
}
