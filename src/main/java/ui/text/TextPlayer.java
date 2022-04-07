package ui.text;

import model.Player;

public class TextPlayer extends Player {

    TextUI ui;

    public TextPlayer(String name, String color, TextUI ui) {
        super(name, color);
        this.ui = ui;
    }
    
}
