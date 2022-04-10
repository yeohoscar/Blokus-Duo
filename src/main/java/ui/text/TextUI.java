/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * TextUI class
 *  - prompt users with text UI
 */

package ui.text;

import model.Player;
import ui.UI;

import java.util.List;
import java.util.Scanner;

import controller.GameControl;
import model.Board;

public class TextUI implements UI {
    protected Scanner s;
    GameControl game;
    
    public TextUI() {
        s = new Scanner(System.in).useDelimiter("\\n| ");
    }

    public Scanner getS() {
        return s;
    }

    @Override
    public String getName() {
        System.out.println("Enter Player name: ");
        String name = s.useDelimiter("\\n| ").nextLine();
        while(name == null || name.trim().isEmpty()) {
            System.out.println("Player name cannot be null\n Enter Player name:");
            name = s.useDelimiter("\\n| ").nextLine();
        }

        return name;
    }

    /**
     * Display the name of first player on text ui
     */
    @Override
    public void displayFirstPlayer(String name) {
        System.out.println("\n" + name + " is going first!\n");
    }

    /**
     * Update player's turn, board and players' available pieces
     */
    @Override
    public void updateUI(Player player, Board board) {
        System.out.println("-------------------------------");
        System.out.println(player.getName() + "'s turn\n");
        board.printBoard();
        System.out.println(player.getName() + "(" + player.getColor() + ") gamepieces:\n" + player.getStock());
    }

    /**
     * Display the winner and players' score
     */
    @Override
    public void displayResults(String result) {
        System.out.println("GAME OVER!");
        System.out.println(result);
    }

    /**
     * Print error message when invalid coordinate is obtained
     */
    @Override
    public void printCoordinateError() {
        System.out.println("Invalid coordinates provided.");
    }

}
