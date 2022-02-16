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
    private Game game;

    public Blokus(Scanner s, String firstColor, String secondColor) {
        this.players = new ArrayList<>(Arrays.asList(Player.initPlayer(firstColor, s), Player.initPlayer(secondColor, s)));
        this.board = new Board();
        this.currentPlayer = players.get(0);
        this.state = State.FIRST;
    }
    
    public boolean isFirstTurn() {
        return state == State.FIRST;
    }

    public Piece selectPiece(Scanner s) {
        if (currentPlayer.getStock().getPieces().size() == 0) {
            System.out.println("No more pieces left.");
        }
        System.out.println("Select a piece");
        String tmp = s.useDelimiter("\\n| ").next();
        
        while(true) {
            for (Piece p : currentPlayer.getStock().getPieces()) {
                if (p.getName().equals(tmp)) {
                    return p;
                }
            }
            System.out.println("Piece not in stock.\nSelect a piece");
            tmp = s.useDelimiter("\\n| ").next();
        }
    }

    public static boolean allElementsTheSame(int[] array) {
        if (array.length == 0) {
            return true;
        } else {
            int first = array[0];
            for (int element : array) {
                if (element != first) {
                    return false;
                }
            }
            return true;
        }
    }

    public void printPiece( Piece p) {
        int[][] displayPiece = new int[9][9];
        for (int i = 0; i < displayPiece.length; i++) {
            for (int j = 0 ; j < displayPiece[0].length; j++) {
                displayPiece[i][j] = 0;
            }
        }

        for (int[] block : p.getBlocks()) {
            int x = block[0];
            int y = block[1];
            displayPiece[4+x][4-y] = 1;
        }

        for ( int[] line: displayPiece) {
            if (allElementsTheSame(line)) {
                continue;
            }
            for ( int val: line) {
                if( val == 1) System.out.println(currentPlayer.getColor());
                    else System.out.println(" ");
            }
            System.out.println("\n");
        }

    }


    public void manipulation( Piece p) {
        printPiece(p);
        while (true) {
            Scanner x = new Scanner(System.in);
            System.out.println("Enter 'r' to rotate, 'f' to flip, or 'p' to place the gamepeiece:");
            String instruct = x.nextLine();
            switch (instruct) {
                case "r":
                    p.rotatePieceClockwise();printPiece(p);
                    return;
                case "f":
                    p.flipPiece();printPiece(p);
                    return;
                case "p":
                    return;
                default:
                    System.out.println("Invalid instruction");
            }
        }
    }

    public ArrayList<Integer> selectSquare(Scanner s) {
        ArrayList<Integer> coord = new ArrayList<>();
        System.out.print("Enter x coordinate of square: ");
        coord.add(Integer.parseInt(s.useDelimiter("\\n| ").next()));

        System.out.print("Enter y coordinate of square: ");
        coord.add(Integer.parseInt(s.useDelimiter("\\n| ").next()));

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
<<<<<<< HEAD
        System.out.println(getCurrentPlayer().getName() + "(" + getCurrentPlayer().getColor() + ") gamepieces:\n" + getCurrentPlayer().getStock());
=======
        System.out.println(getCurrentPlayer().getName()+"("+getCurrentPlayer().getColor()+") gamepieces:\n" + getCurrentPlayer().getStock());
>>>>>>> 504c1c7697f23cdecc89902607ff2f34018c9919
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

<<<<<<< HEAD
        if (args.length == 1) {
            if (args[0].equals("-X")) {
=======
        if (args.length != 0) {
            if (args[1] == "-X") {
>>>>>>> 504c1c7697f23cdecc89902607ff2f34018c9919
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
            Piece p = b.selectPiece(s);
            b.manipulation(p);
            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getBoard(), p, b.selectSquare(s)).executeMove())) {
                System.out.println("Invalid move");            
            }
            //new Game(b.getCurrentPlayer(), b.getBoard());
            //System.out.println(move1.toString());
            b.nextPlayer();
            b.printUI();

            p = b.selectPiece(s);
            b.manipulation(p);
            while(!(new FirstTurnMove(b.getCurrentPlayer(), b.getBoard(), p, b.selectSquare(s)).executeMove())) {
                System.out.println("Invalid move");
            }
            //Game move2 = new Game(b.getCurrentPlayer(), b.getBoard());
            //System.out.println(move2.toString());
            b.nextPlayer();
            b.printUI();
            b.setState(State.MIDGAME);
        }

        Piece p = b.selectPiece(s);
        ArrayList<Integer> coor;
        int x, y;
        do {
            
            coor = b.selectSquare(s);
            x = coor.get(0);
            y = coor.get(1);      
            System.out.println(x + " Invalid move " + y);
            System.out.println(!b.getBoard().isEmptyForPiece(p, x, y));   
            System.out.println(b.getBoard().isSide(b.currentPlayer, p, x, y));   
            System.out.println(!new Game(b.currentPlayer, b.getBoard()).isValidMove(p, x, y));   
        } while(!b.getBoard().isEmptyForPiece(p, x, y) || b.getBoard().isSide(b.currentPlayer, p, x, y) || !new Game(b.currentPlayer, b.getBoard()).isValidMove(p, x, y));
        
        b.getBoard().addPiece(b.getCurrentPlayer(), p, x, y);
        b.getCurrentPlayer().getStock().getPieces().remove(p);
        //b.getCurrentPlayer().getValidMove().getValidMove(b.getBoard(), p.getBlocks(), x, y);
        Game move1 = new Game(b.getCurrentPlayer(), b.getBoard());
        move1.getMove(p.getBlocks(), x, y);
        System.out.println(move1.toString());
        //b.getCurrentPlayer().getValidMove().remove(y);
        b.nextPlayer();
        b.printUI();
        

        p = b.selectPiece(s);
        do {
            
            coor = b.selectSquare(s);
            x = coor.get(0);
            y = coor.get(1);      
            System.out.println(x + " Invalid move " + y);
            System.out.println(!b.getBoard().isEmptyForPiece(p, x, y));   
            System.out.println(b.getBoard().isSide(b.currentPlayer, p, x, y));   
            System.out.println(!new Game(b.currentPlayer, b.getBoard()).isValidMove(p, x, y));   
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
    }
}