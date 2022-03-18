package ui.text;

import model.Player;
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

    }
}
