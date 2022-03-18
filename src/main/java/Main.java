/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Main class
 *  - runs game
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
    /*
    private final List<Player> players;
    private final Board board;
    private Player currentPlayer;
    private State state;

    public Blokus(Scanner s, int firstPlayer) {
        this.players = new ArrayList<>(Arrays.asList(Player.initPlayer("O", s), Player.initPlayer("X", s)));
        this.board = new Board();
        this.currentPlayer = players.get(firstPlayer);
        this.state = State.FIRST;
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    } 

    public Player getNextPlayer() {
        if (currentPlayer == getPlayers().get(0)) {
            return (getPlayers().get(1));
        }

        return (getPlayers().get(0));
    }
    /*
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void printUI() {
        System.out.println("-------------------------------");
        System.out.println(getCurrentPlayer().getName() + "'s turn\n");
        getBoard().printBoard();
        System.out.println(getCurrentPlayer().getName() + "(" + getCurrentPlayer().getColor() + ") gamepieces:\n" + getCurrentPlayer().getStock());
    }

    public void nextPlayer() {
        if (currentPlayer == getPlayers().get(0)) {
            setCurrentPlayer(getPlayers().get(1));
            return;
        }
        setCurrentPlayer(getPlayers().get(0));
    }

    public void readFile(String fileName) {
        
    }*/

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

        /*
        if (args.length != 0) {
            if (args[0].equals("-X")) {
                if(args.length == 2) {
                    try {
                        s = new Scanner(new File(args[1]));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                b = new Blokus(s, "X", "O");
            } else if (args[0].equals("-O")) {
                if(args.length == 2) {
                    try {
                        s = new Scanner(new File(args[1]));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                b = new Blokus(s, "O", "X");
            } else { 
                if (new Random().nextInt(2) == 0) {
                    if(args.length == 2) {
                        try {
                            s = new Scanner(new File(args[1]));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    b = new Blokus(s, "X", "O");
                } else {
                    if(args.length == 2) {
                        try {
                            s = new Scanner(new File(args[1]));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    b = new Blokus(s, "O", "X");
                }
            }
        } else {
            if (new Random().nextInt(2) == 0) {
                if(args.length == 2) {
                    try {
                        s = new Scanner(new File(args[1]));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                b = new Blokus(s, "X", "O");
            } else {
                if(args.length == 2) {
                    try {
                        s = new Scanner(new File(args[1]));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                b = new Blokus(s, "O", "X");
            }
        }
        
        if (b.isFirstTurn()) {
            b.printUI();
            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getNextPlayer(), b.getBoard(), s).executeMove())) {
                System.out.println("Invalid move.");     
            }
            b.nextPlayer();
            b.printUI();

            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getNextPlayer(), b.getBoard(), s).executeMove())) {
                System.out.println("Invalid move.");
            }
            b.nextPlayer();
            b.printUI();
            b.setState(State.MIDGAME);
        }

        
        while (b.isMidGame()) {
            while(!(new MidGame(b.getCurrentPlayer(), b.getNextPlayer(), b.getBoard(), s).executeMove())) {
                System.out.println("Invalid move.");            
            }    
            b.nextPlayer();
            if(b.getCurrentPlayer().getValidMove().size() == 0) {
                b.nextPlayer();
            }
            b.isGameOver();
            b.printUI();
        }
        
        if(b.state == State.OVER) {
            System.out.println("-------------------------------");
            b.getBoard().printBoard();
            System.out.println(b.getPlayers().get(0).getName() + "(" + b.getPlayers().get(0).getColor() + ") gamepieces: " + b.getPlayers().get(0).getStock());
            System.out.println(b.getPlayers().get(1).getName() + "(" + b.getPlayers().get(1).getColor() + ") gamepieces: " + b.getPlayers().get(1).getStock());
            System.out.println("\nGAME OVER!");
        }
        */
    }
}

