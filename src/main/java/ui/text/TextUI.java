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

import ui.UI;

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

    /*@Override
    public void printUI(Board board, String name, int playerNo, Stock stock) {
        System.out.println("-------------------------------");
        System.out.println(name + "'s turn\n");
        board.printBoard();
        if (playerNo == Board.X) {
            System.out.println(name + "(X) gamepieces:\n" + stock);       
        } 
        else {
            System.out.println(name + "(O) gamepieces:\n" + stock);       
        }
    }*/

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
