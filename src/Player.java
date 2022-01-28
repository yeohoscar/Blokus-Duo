import java.util.ArrayList;

public class Player {
    private String name; //Player name
    private int score; //Player score -> calculate at end
    private ArrayList<Piece> pieces;

    public Player(String name) {
        this.name = name;
        score = 0;
        this.pieces = //init pieces
    }

    public String getName() {
        return name;
    }
}