package ui.graphical;

import model.*;
import ui.text.TextUI;

public class GraphicalPlayer extends Player {

    GUI ui;

    public GraphicalPlayer(String name, String color, GUI ui) {
        super(name, color);
        this.ui = ui;
    }
    
}
