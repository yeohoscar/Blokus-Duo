/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 *
 * GUI class
 *  - Connects GUI thread to gameControl thread
 */

package ui.graphical;

import model.Board;
import model.Player;
import model.piece.Piece;
import ui.UI;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public String getName() {
        return scanner.next();
    }

    public void setCurrentPlayerColor(String playerColor) {
        blokusGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                blokusGame.setCurrentPlayerColor(playerColor);
            }
        });
    }

    public void setCurrentPlayerValidMove(ArrayList<int[]> validMove) {
        blokusGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                blokusGame.setCurrentPlayerValidMove(validMove);
            }
        });
    }

    /**
     * Change screen from nameScreen to playScreen
     */
    public void displayFirstPlayer(String name) {
        blokusGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                blokusGame.setMessage(name);
                blokusGame.activatePlayScreen();
            }
        });
    }

    /**
     * Update the player's turn and board on graphical UI
     */
    public void updateUI(Player player, Board board) {
        blokusGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                blokusGame.showMessage(player.getName() + "'s turn: \n");
                blokusGame.updateBoard(board);
            }
        });
    }

    /**
     * Display the winner and players's score on graphical UI
     */
    @Override
    public void displayResults(String result) {
        blokusGame.setResults(result);
    }

    /**
     * Print error message when invalid move is obtained
     */
    @Override
    public void printCoordinateError() {
        blokusGame.postRunnable(new Runnable() {
            @Override
            public void run() {
                blokusGame.showMessage("Invalid piece placement.");
            }
        });
    }

    /**
     * Announce game over by showing in dialog in graphical UI
     */
    @Override
    public void printGameOver(Board board, List<Player> players) {
        blokusGame.showDialog("GAME OVER!");
    }
}

