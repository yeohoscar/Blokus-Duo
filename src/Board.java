import java.util.Hashtable;
import java.util.Set;

public class Board {
    private String board[][];

    public Board() {
        this.board = new String[14][14];
        // initialize the board
        for (int i = 0; i < board.length; i++ ) {
            for ( int j = 0; j < board[0].length; j++) {
                board[i][j] = ".";
            }
        }
        // Starting points
        board[4][4] = "*";
        board[9][9] = "*";
    }

    public void printBoard() {
        int row = board.length-1;
        System.out.print("BLOKUS DUO\n");

        for (int i = 0; i < board[0].length; i++) {
            // column index
            if( row > 9) {
                System.out.print("\n"+row+" ");
            } else {
                System.out.print("\n "+row+" ");
            }
            row--;

            // every square
            for (int j = 0; j < board.length; j++) {
                /*TODO: print board + numbers*/
                System.out.print(board[i][j]+" ");
            }
        }

        // row index
        System.out.print("\n   ");
        for (int i = 0; i < board[0].length; i++) {
            if (i > 9) {
                System.out.print(i);
            } else {
                System.out.print(i+" ");
            }
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    /**
     *  Adds piece to the board
     * 
     *  @param player player playing the piece
     *  @param piece piece being placed
     *  @param dest_x x coordinate of piece origin
     *  @param dest_y y coordinate of piece origin
     */
    public void addPiece(Player player, Hashtable<String, int[]> piece, int dest_x, int dest_y) {
        Set<String> setOfKeys = piece.keySet();
        for (String s : setOfKeys) {
            board[dest_x + piece.get(s)[0]][dest_y + piece.get(s)[1]] = player.getColor();
        }
        board[dest_x][dest_y] = player.getColor();
    }

    /**
     * returns false if square is out of bounds
     * 
     * @param x x coordinate
     * @param y y coordinate
     */
    public boolean contains(int x, int y) {
        return x >= 0 && x < board[0].length && y >= 0 && y < board.length;
    }

    public static void main( String[] args) {
        Board b1 = new Board();
        b1.printBoard();
    }
}