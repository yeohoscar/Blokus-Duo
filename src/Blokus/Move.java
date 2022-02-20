package src.Blokus;

import java.util.*;

public interface Move {
    public ArrayList<Object> selectPiece(Scanner s);
    public boolean executeMove();
}
