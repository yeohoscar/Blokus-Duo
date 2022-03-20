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
import ui.*;
import ui.graphical.BlokusGame;
import ui.graphical.GUI;
import ui.text.TextUI;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in).useDelimiter("\\n| ");
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

        ui = useGUI ? new GUI() : new TextUI(s);

        if (xFirst == oFirst) {
            gameControl = new GameControl(ui, new Random().nextInt(2), s);
        } else if (xFirst) {
            gameControl = new GameControl(ui, 1, s);
        } else {
            gameControl = new GameControl(ui, 0, s);
        }
        gameControlThread = new Thread(gameControl);
        gameControlThread.start();

        if (useGUI) {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            int height = BlokusGame.HEIGHT;
            int width = BlokusGame.WIDTH;
            config.setWindowSizeLimits(width, height, width, height);
            new Lwjgl3Application(new BlokusGame(gameControlThread,ui), config);
        }
    }
}

