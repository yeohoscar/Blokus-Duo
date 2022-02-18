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
    private MidGame game;

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
        if (players.get(0).getValidMove().size() == 0 && players.get(1).getValidMove().size() == 0 || players.get(0).getStock().getPieces().size() == 0 && players.get(1).getStock().getPieces().size() == 0) {
            setState(State.OVER);
        }
    }

    public ArrayList<Object> selectPiece(Scanner s) {
        if (currentPlayer.getStock().getPieces().size() == 0) {
            System.out.println("No more pieces left.");
        }
        System.out.println("Select a piece");
        String tmp = s.useDelimiter("\\n").nextLine();
        
        while(true) {
            for (Piece p : currentPlayer.getStock().getPieces()) {
                if (p.getName().equals(tmp)) {
                    return new ArrayList<>(Arrays.asList(p, p.manipulation(s, getCurrentPlayer().getColor())));                                
                }
            }
            System.out.println("Piece not in stock.\n Select a piece");
            tmp = s.useDelimiter("\\n").nextLine();
        }
    }

    public ArrayList<Integer> selectSquare(ArrayList<String> arr) {
        ArrayList<Integer> coord = new ArrayList<>();

        coord.add(Integer.parseInt(arr.get(0)));
        coord.add(Integer.parseInt(arr.get(1)));
        System.out.println(coord.get(0) + " coor " + coord.get(1));

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

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in).useDelimiter("\\n| ");
        ArrayList<Object> arr;
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
            arr = b.selectPiece(s);
            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getBoard(), (Piece) arr.get(0), b.selectSquare((ArrayList<String>) arr.get(1))).executeMove())) {
                System.out.println("Invalid move. Please enter the coordinate: ");            
                arr.set(1, new ArrayList<>(Arrays.asList(s.useDelimiter("\\n").nextLine().split(" "))));          
            }
            b.nextPlayer();
            b.printUI();

            arr = b.selectPiece(s);
            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getBoard(), (Piece) arr.get(0), b.selectSquare((ArrayList<String>) arr.get(1))).executeMove())) {
                System.out.println("Invalid move. Please enter the coordinate: ");            
                arr.set(1, new ArrayList<>(Arrays.asList(s.useDelimiter("\\n").nextLine().split(" "))));
            }
            b.nextPlayer();
            b.printUI();
            b.setState(State.MIDGAME);
        }

        
        while (b.isMidGame()) {
            b.printUI();
            arr = b.selectPiece(s);
            while(!(new MidGame(b.getCurrentPlayer(), b.getBoard(), (Piece) arr.get(0), b.selectSquare((ArrayList<String>) arr.get(1))).executeMove())) {
                System.out.println("Invalid move. Please enter the coordinate: ");            
                arr.set(1, new ArrayList<>(Arrays.asList(s.useDelimiter("\\n").nextLine().split(" "))));  
            }    
            for(int[] move : b.getCurrentPlayer().getValidMove()) {
                System.out.print(move[0] + ", " + move[1] + " | ");
            }
            System.out.println();
            b.nextPlayer();
            b.printUI();

            arr = b.selectPiece(s);
            while(!(new MidGame(b.getCurrentPlayer(), b.getBoard(), (Piece) arr.get(0), b.selectSquare((ArrayList<String>) arr.get(1))).executeMove())) {
                System.out.println("Invalid move. Please enter the coordinate: ");            
                arr.set(1, new ArrayList<>(Arrays.asList(s.useDelimiter("\\n").nextLine().split(" "))));  
            }    
            for(int[] move : b.getCurrentPlayer().getValidMove()) {
                System.out.print(move[0] + ", " + move[1] + " | ");
            }
            System.out.println();
            b.nextPlayer();
            b.printUI();
            b.isGameOver();
        }
    }
}

//KarYen test
        /*Piece p = b.selectPiece(s);
        ArrayList<Integer> coor;
        int x, y;
        do {
            
            coor = b.selectSquare(s);
            x = coor.get(0);
            y = coor.get(1);      
        } while(!b.getBoard().isEmptyForPiece(p, x, y) || b.getBoard().isSide(b.currentPlayer, p, x, y) || !urnew Game(b.crentPlayer, b.getBoard()).isValidMove(p, x, y));
        
        b.getBoard().addPiece(b.getCurrentPlayer(), p, x, y);
        b.getCurrentPlayer().getStock().getPieces().remove(p);
        //b.getCurrentPlayer().getValidMove().getValidMove(b.getBoard(), p.getBlocks(), x, y);
        Game move1 = new Game(b.getCurrentPlayer(), b.getBoard());
        move1.getMove(p.getBlocks(), x, y);
        //b.getCurrentPlayer().getValidMove().remove(y);
        b.nextPlayer();
        b.printUI();
        

        p = b.selectPiece(s);
        do {
            
            coor = b.selectSquare(s);
            x = coor.get(0);
            y = coor.get(1);      
        } while(!b.getBoard().isEmptyForPiece(p, x, y) || b.getBoard().isSide(b.currentPlayer, p, x, y) || !new Game(b.currentPlayer, b.getBoard()).isValidMove(p, x, y));
        
        if(b.getBoard().isSide(b.getCurrentPlayer(), p, x, y)) {
            System.out.println("2yes");
        }
        else {
            System.out.println("2no");
        }
        b.getBoard().addPiece(b.getCurrentPlayer(), p, x, y);
        b.getCurrentPlayer().getStock().getPieces().remove(p);
        
        Game move2 = new Game(b.getCurrentPlayer(), b.getBoard());
        move2.getMove(p.getBlocks(), x, y);
        //System.out.println(move2.toString());
        b.nextPlayer();
        b.printUI();
    }*/
