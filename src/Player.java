import java.util.Scanner;

import java.util.ArrayList;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Player class
 *  - represents Blokus player
 *  - holds player name, colour and pieces
 */

public class Player {
    private final String name; //Player name
    private int score; //Player score -> calculate at end
    private final String color;
    private Stock stock;
    private ArrayList<int[]> validMove;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        score = 0;
        this.stock = new Stock();
        this.validMove = new ArrayList<int[]>();
    }

    public static Player initPlayer(String color, Scanner s) {
        System.out.println("Enter Player(" + color + ") name: ");
<<<<<<< HEAD

        String tmp = s.useDelimiter("\\n").nextLine();

=======
        String tmp = s.useDelimiter("\\n| ").nextLine();
>>>>>>> 504c1c7697f23cdecc89902607ff2f34018c9919
        while(tmp == null || tmp.trim().isEmpty()) {
            System.out.println("Player name cannot be null\nEnter Player(" + color + ") name:");
            tmp = s.useDelimiter("\\n| ").nextLine();
        }
        
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

    public ArrayList<int[]> getValidMove() {
        return validMove;
    }
}