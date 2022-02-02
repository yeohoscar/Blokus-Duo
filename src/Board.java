public class Board {
    private String board[][];
    private Player one;
    private Player two;

    public Board(Player one, Player two) {
        this.one = one;
        this.two = two;
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

        // available pieces for each player
        System.out.println("Player "+one.getName()+"(X) gamepieces: "+ pieces);
        System.out.println("Player "+two.getName()+"(O) gamepieces: "+ pieces);

        System.out.println("\nPlayer "+one.getName()+"(X) gamepieces:\n" + one.getStock());

        System.out.println("Player "+two.getName()+"(O) gamepieces:\n" + two.getStock());

    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

}