/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Main class
 *  - Entry point of program
 *  - Launches application in GUI or TextUI.
 */

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import controller.*;
import model.Player;
import ui.*;
import ui.graphical.*;
import ui.text.*;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Player> players;
        boolean xFirst = false, oFirst = false, useGUI = false;
        UI ui;
        GameControl gameControl;
        Thread gameControlThread;

        if (args.length != 0) {
            for (String arg : args) {
                switch (arg) {
                    case "-X" -> xFirst = true;
                    case "-O" -> oFirst = true;
                    case "-gui" -> useGUI = true;
                }
            }
        }

        if(useGUI) {
            ui = new GUI();
            players = new ArrayList<>(Arrays.asList(new GraphicalPlayer("X", (GUI)ui), new GraphicalPlayer("O", (GUI)ui)));
        }
        else {
            ui = new TextUI();
            players = new ArrayList<>(Arrays.asList(new TextPlayer("X", (TextUI)ui), new TextPlayer("O", (TextUI)ui)));
        }

        if (xFirst == oFirst) {
            gameControl = new GameControl(ui, new Random().nextInt(2), players);
        } else if (xFirst) {
            gameControl = new GameControl(ui, 0, players);
        } else {
            gameControl = new GameControl(ui, 1, players);
        }
        gameControlThread = new Thread(gameControl);
        gameControlThread.start();

        if (useGUI) {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("Welcome to Blokus Duo");
            config.setWindowedMode(PlayScreen.GAME_WIDTH, PlayScreen.GAME_HEIGHT);
            config.setResizable(false);

            new Lwjgl3Application(new BlokusGame(gameControlThread, ui), config);
        }
    }
}

