/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 *
 * GameControl class
 *  - Controls and runs game
 */

package controller;

import model.Board;
import model.Player;
import ui.UI;

import java.util.*;

public class GameControl implements Runnable {
    UI ui;
    private List<Player> players;
    private final Board board;
    private Player currentPlayer;
    private State state;
    private final int firstPlayer;

    public GameControl(UI ui, int firstPlayer, List<Player> players) {
        this.ui = ui;
        this.players = players;
        board = new Board();
        this.players = players;
        this.firstPlayer = firstPlayer;
        state = State.FIRST;
    }

    public boolean isFirstTurn() {
        return state == State.FIRST;
    }

    public boolean isMidGame() {
        return state == State.MIDGAME;
    }

    public void isGameOver() {
        if ((players.get(0).getValidMove().size() == 0 && players.get(1).getValidMove().size() == 0) || players.get(0).getStock().getPieces().size() == 0 || players.get(1).getStock().getPieces().size() == 0) {
            setState(State.OVER);
        }
    }

    public Board getBoard() {
        return board;
    }

    public int getFirstPlayer() {
        return firstPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getNextPlayer() {
        if (currentPlayer == getPlayers().get(0)) {
            return (getPlayers().get(1));
        }

        return (getPlayers().get(0));
    }

    public void nextPlayer() {
        if (currentPlayer == getPlayers().get(0)) {
            setCurrentPlayer(getPlayers().get(1));
            return;
        }
        setCurrentPlayer(getPlayers().get(0));
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void calculateScore() {
        for (Player p : getPlayers()) {
            if (p.getStock().getPieces().size() == 0) {
                p.setScore(p.getScore() + 15);
                if (p.getLastPieceI1()) {
                    p.setScore(p.getScore() + 5);
                }
            }
        }
    }

    @Override
    public void run() {
        //Initialise players
        setPlayers(players);
        players.get(0).setName(ui.getName());
        players.get(1).setName(ui.getName());
        setCurrentPlayer(getPlayers().get(firstPlayer));
        ui.displayFirstPlayer(getCurrentPlayer().getName());

        if (isFirstTurn()) {
            ui.updateUI(getCurrentPlayer(), getBoard());
            while(!(new FirstTurnMove(getCurrentPlayer(), getNextPlayer(), getBoard(), ui).executeMove())) {
                ui.printCoordinateError();
            }
            nextPlayer();
            ui.updateUI(getCurrentPlayer(), getBoard());

            while(!(new FirstTurnMove(getCurrentPlayer(), getNextPlayer(), getBoard(), ui).executeMove())) {
                ui.printCoordinateError();
            }
            nextPlayer();
            ui.updateUI(getCurrentPlayer(), getBoard());
            setState(State.MIDGAME);
        }

        while (isMidGame()) {
            while(!(new MidGameMove(getCurrentPlayer(), getNextPlayer(), getBoard(), ui).executeMove())) {
                ui.printCoordinateError();
            }
            nextPlayer();
            if(getCurrentPlayer().getValidMove().size() == 0) {
                nextPlayer();
            }
            isGameOver();
            ui.updateUI(getCurrentPlayer(), getBoard());
        }

        if(state == State.OVER) {
            calculateScore();
            if (currentPlayer.getScore() > getNextPlayer().getScore()) {
                ui.displayResults(currentPlayer.getName() + " is the winner! Score: " + currentPlayer.getName() + "(" + currentPlayer.getColor() + ") " + currentPlayer.getScore() + " | " +
                        "" + getNextPlayer().getName() + "(" + getNextPlayer().getColor() + ") " + getNextPlayer().getScore());
            } else if (currentPlayer.getScore() < getNextPlayer().getScore()) {
                ui.displayResults(currentPlayer.getName() + " is the winner! Score: " + currentPlayer.getName() + "(" + currentPlayer.getColor() + ") " + currentPlayer.getScore() + " | " +
                        "" + getNextPlayer().getName() + "(" + getNextPlayer().getColor() + ") " + getNextPlayer().getScore());
            } else {
                ui.displayResults("Draw! Score: " + currentPlayer.getName() + "(" + currentPlayer.getColor() + ") " + currentPlayer.getScore() + " | " +
                        "" + getNextPlayer().getName() + "(" + getNextPlayer().getColor() + ") " + getNextPlayer().getScore());
            }
        }
    }
}

