import java.util.*;

public class Blokus {
    private final List<Player> players;
    private final Board board;
    private Player currentPlayer;
    private State state;


    public Blokus(Scanner s) {
        this.players = new ArrayList<>(Arrays.asList(Player.initPlayer("X", s), Player.initPlayer("O", s)));
        this.board = new Board(players.get(0), players.get(1));
        this.currentPlayer = players.get(0);
        this.state = State.FIRST;
    }
    
    public boolean isFirstTurn() {
        return state == State.FIRST;
    }

    public Piece selectPiece(Scanner s) {
        /**
         * TODO:
         * check if pieces remain -> pieces.size != 0
         * if (pieces contains piece named "piece") {
         *      return piece
         * } else {
         *      throw error -> piece doesnt exist
         * }
         */

        if (currentPlayer.getStock().getPieces().size() == 0) {
            System.out.println("No more pieces left.");
        }
        System.out.println("Select a piece");
        String tmp = s.useDelimiter("\\n").nextLine();
        
        while(true) {
            for (Piece p : currentPlayer.getStock().getPieces()) {
                if (p.getName().equals(tmp)) {
                    currentPlayer.getStock().getPieces().remove(p);// remove the piece selected from list
                    return p;
                }
            }
            System.out.println("Piece not in stock.\nSelect a piece.");
            tmp = s.useDelimiter("\\n").nextLine();
        }
    }

    public ArrayList<Integer> selectSquare(Scanner s) {
        ArrayList<Integer> coord = new ArrayList<>();
        System.out.print("Enter x coordinate of square: ");
        coord.add(Integer.parseInt(s.useDelimiter("\\n").nextLine()));

        System.out.print("Enter y coordinate of square: ");
        coord.add(Integer.parseInt(s.useDelimiter("\\n").nextLine()));
        return coord;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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
        System.out.println(getCurrentPlayer().getName() + "'s turn\n");
        getBoard().printBoard();
    }

    public void nextPlayer() {
        if (currentPlayer == getPlayers().get(0)) {
            setCurrentPlayer(getPlayers().get(1));
            return;
        }
        setCurrentPlayer(getPlayers().get(0));
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in).useDelimiter("\\n");
        Blokus b = new Blokus(s);
        
        if (b.isFirstTurn()) {
            b.printUI();
            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getBoard(), b.selectPiece(s), b.selectSquare(s)).executeMove()));
            b.nextPlayer();
            b.printUI();
            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getBoard(), b.selectPiece(s), b.selectSquare(s)).executeMove()));
            b.printUI();
            b.setState(State.MIDGAME); 
            b.nextPlayer();
        }
    }
}