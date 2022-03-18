package ui.graphical;

import com.badlogic.gdx.Application;

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

    public void displayFirstPlayer(String name) {
        blokusGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                blokusGame.activateStartScreen();
                blokusGame.setMessage(name);
            }
        });
    }
}

