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

public class TextUI implements UI {
    protected Scanner s;
    
    public TextUI() {
        s = new Scanner(System.in).useDelimiter("\\n| ");
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
    public String getPiece() {
        String tmp = s.useDelimiter(" |\\n").next();
        tmp = tmp.replaceAll("(?:\\n|\\r)", "");

        return tmp;
    }

    @Override
    public String[] getManipulation() {
        return s.useDelimiter(" |\\n").nextLine().split(" ");
    }

    public void displayFirstPlayer(String name) {
        System.out.println("\n" + name + " is going first!\n");
    }

    @Override
    public void displayResults(String result) {
        System.out.println(result);
    }

    

    
}
