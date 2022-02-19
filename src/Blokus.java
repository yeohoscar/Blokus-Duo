import java.util.*;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Main class
 *  - runs game
 */

public class Blokus {
    private final List<Player> players;
    private final Board board;
    private Player currentPlayer;
    private State state;

    public Blokus(Scanner s, String firstColor, String secondColor) {
        this.players = new ArrayList<>(Arrays.asList(Player.initPlayer(firstColor, s), Player.initPlayer(secondColor, s)));
        this.board = new Board();
        this.currentPlayer = players.get(0);
        this.state = State.FIRST;
    }
    
    public boolean isFirstTurn() {
        return state == State.FIRST;
    }

    public boolean isMidGame() {
        return state == State.MIDGAME;
    }

    public void isGameOver() {
        if (players.get(0).getValidMove().size() == 0 || players.get(1).getValidMove().size() == 0 || players.get(0).getStock().getPieces().size() == 0 || players.get(1).getStock().getPieces().size() == 0) {
            setState(State.OVER);
        }
        System.out.println("game " + state);
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

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in).useDelimiter("\\n| ");
        Blokus b;

        if (args.length != 0) {
            if (args[1] == "-X") {
                b = new Blokus(s, "X", "O");
            } else if (args[0].equals("-O")) {
                b = new Blokus(s, "O", "X");
            } else { 
                if (new Random().nextInt(2) == 0) {
                    b = new Blokus(s, "X", "O");
                } else {
                    b = new Blokus(s, "O", "X");
                }
            }
        } else {
            if (new Random().nextInt(2) == 0) {
                b = new Blokus(s, "X", "O");
            } else {
                b = new Blokus(s, "O", "X");
            }
        }
        
        if (b.isFirstTurn()) {
            b.printUI();
            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getNextPlayer(), b.getBoard(), s).executeMove())) {
                System.out.println("Invalid move.");     
            }
            for(int[] move : b.getCurrentPlayer().getValidMove()) {
                System.out.print(move[0] + ", " + move[1] + " | ");
            }
            System.out.println();

            b.nextPlayer();
            b.printUI();

            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getNextPlayer(), b.getBoard(), s).executeMove())) {
                System.out.println("Invalid move.");
            }
            for(int[] move : b.getCurrentPlayer().getValidMove()) {
                System.out.print(move[0] + ", " + move[1] + " | ");
            }
            System.out.println();

            b.nextPlayer();
            b.printUI();
            b.setState(State.MIDGAME);
        }

        
        while (b.isMidGame()) {
            for(int[] move : b.getCurrentPlayer().getValidMove()) {
                System.out.print(move[0] + ", " + move[1] + " | ");
            }
            System.out.println();
            while(!(new MidGame(b.getCurrentPlayer(), b.getNextPlayer(), b.getBoard(), s).executeMove())) {
                System.out.println("Invalid move.");            
            }    
            System.out.println("a " + b.getPlayers().get(0).getValidMove().size() + "b " + b.getPlayers().get(1).getValidMove().size());
            b.nextPlayer();
            b.printUI();
            b.isGameOver();

            /*for(int[] move : b.getCurrentPlayer().getValidMove()) {
                System.out.print(move[0] + ", " + move[1] + " | ");
            }
            System.out.println();
            while(!(new MidGame(b.getCurrentPlayer(), b.getNextPlayer(), b.getBoard(), s).executeMove())) {
                System.out.println("Invalid move.");            
            }    
            System.out.println("a " + b.getPlayers().get(0).getValidMove().size() + "b " + b.getPlayers().get(1).getValidMove().size());
            b.nextPlayer();
            b.printUI();
            b.isGameOver();*/
        }

        if(b.state == State.OVER) {
            System.out.println("\nGAME OVER");
        }
    }
}

