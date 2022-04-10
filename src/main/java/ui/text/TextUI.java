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
import model.piece.Stock;
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

    public void getPlayer() {

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

    @Override
    public void printUI(Board board, Player player, Stock stock) {
        System.out.println("-------------------------------");
        System.out.println(player.getName() + "'s turn\n");
        board.printBoard();
        if (player.getColor().equals("X")) {
            System.out.println(player.getName() + "(X) gamepieces:\n" + stock);
        } 
        else {
            System.out.println(player.getName() + "(O) gamepieces:\n" + stock);
        }
    }

    @Override
    public void printGameOver(Board board, List<Player> players) {
        System.out.println("-------------------------------");
        board.printBoard();
        System.out.println(players.get(0).getName() + "(" + players.get(0).getColor() + ") gamepieces: " + players.get(0).getStock());
        System.out.println(players.get(1).getName() + "(" + players.get(1).getColor() + ") gamepieces: " + players.get(1).getStock());
        System.out.println("\nGAME OVER!");
    }

    @Override
    public void displayFirstPlayer(String name) {
        System.out.println("\n" + name + " is going first!\n");
    }

    @Override
    public void displayResults(String result) {
        System.out.println(result);
    }

    @Override
    public void printCoordinateError() {
        System.out.println("Invalid coordinates provided.");
    }

    public Scanner getS() {
        return s;
    }

    @Override
    public void updateBoardDisplay(Board board) {
        board.printBoard();
    }
}
