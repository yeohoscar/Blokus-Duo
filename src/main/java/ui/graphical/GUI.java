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

import model.piece.Piece;
import ui.UI;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GUI implements UI {
    private final PipedOutputStream pipedOutputStream;
    private final PipedInputStream pipedInputStream;
    private final BlockingQueue<Piece> pieceQueue;
    private BlokusGame blokusGame;
    private final Scanner scanner;

    public GUI() throws IOException {
        //Connect threads
        pipedOutputStream = new PipedOutputStream();
        pipedInputStream = new PipedInputStream(pipedOutputStream);
        pieceQueue = new LinkedBlockingQueue<>();
        scanner = new Scanner(pipedInputStream);
    }

    void setGame(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
    }

    public PipedOutputStream getPipedOutputStream() {
        return pipedOutputStream;
    }

    @Override
    public String getName() {
        return scanner.next();
    }

   

    /*@Override
    public void printUI(Board board, String name, int playerNo, Stock stock) {
        blokusGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                blokusGame.updateBoard(board);
            }
        });
    }*/

    /**
     * Change screen from nameScreen to startScreen
     */
    public void displayFirstPlayer(String name) {
        blokusGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                blokusGame.activatePlayScreen();
                blokusGame.setMessage(name);
            }
        });
    }

    @Override
    public void displayResults(String result) {
        blokusGame.setResults(result);
    }

    @Override
    public void printCoordinateError() {
        //TODO: display "Invalid piece placement in banner"
    }

    public int getXCoordinate() {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        return scanner.nextInt();
    }

    public int getYCoordinate() {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        return scanner.nextInt();
    }

    public BlockingQueue<Piece> getPieceQueue() {
        return pieceQueue;
    }
    
}

