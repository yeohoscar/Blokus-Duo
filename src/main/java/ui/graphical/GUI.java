/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 *
 * GUI
 *  - Connects GUI thread to gameControl thread
 */

package ui.graphical;

import ui.UI;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class GUI implements UI {
    private final PipedOutputStream pipedOutputStream;
    private final PipedInputStream pipedInputStream;
    private BlokusGame blokusGame;
    private final Scanner scanner;

    public GUI() throws IOException {
        //Connect threads
        pipedOutputStream = new PipedOutputStream();
        pipedInputStream = new PipedInputStream(pipedOutputStream);
        scanner = new Scanner(pipedInputStream);
    }

    void setGame(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
    }

    public PipedOutputStream getPipedOutputStream() {
        return pipedOutputStream;
    }

    public String getName() {
        return scanner.next();
    }

    /**
     * Change screen from nameScreen to startScreen
     */
    public void displayFirstPlayer(String name) {
        blokusGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                blokusGame.activateStartScreen();
                blokusGame.setMessage(name);
                blokusGame.activateGameScreen();
            }
        });
    }

    public void displayGame() {

    }

    @Override
    public void displayResults(String result) {
        blokusGame.setResults(result);
    }
}

