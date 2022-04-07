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
    
    public TextUI(Scanner s) {
        this.s = s;
    }

    @Override
    public String getName() {
        System.out.println("Enter Player name: ");
        String name = s.useDelimiter("\\n| ").nextLine();
        while(name == null || name.trim().isEmpty()) {
            System.out.println("Player name cannot be null\nEnter Player name:");
            name = s.useDelimiter("\\n| ").nextLine();
        }

        return name;
    }

    public void displayFirstPlayer(String name) {
        System.out.println("\n" + name + " is going first!\n");
    }

    @Override
    public void displayResults(String result) {
        System.out.println(result);
    }
}
