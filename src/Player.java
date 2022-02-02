import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Player {
    private final String name; //Player name
    private int score; //Player score -> calculate at end
    private final String color;
    private Stock stock;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        score = 0;
        this.stock = new Stock();
    }

    public static Player initPlayer(String color, Scanner s) {
        System.out.println("Enter Player name: ");
        String tmp = s.useDelimiter("\\n").nextLine();
        Player p = new Player(tmp, color);
        return p;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Stock getStock() {
        return stock;
    }
}