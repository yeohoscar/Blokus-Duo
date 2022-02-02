import java.util.ArrayList;

public class Player {
    private String name; //Player name
    private int score; //Player score -> calculate at end
    private ArrayList<Piece> pieces;

    public Player(String name) {
        this.name = name;
        score = 0;
        this.pieces = //init pieces
        this.stock = new Stock();
    }

    public static Player initPlayer(String color, Scanner s) {
        System.out.println("Enter Player name: ");
        String tmp = s.nextLine();
        Player p = new Player(tmp, color);
        return p;
    }

    public String getName() {
        return name;
    }
}