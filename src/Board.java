public class Board {
    private String board[][];

    public Board() {
        this.board = new String[14][14];
        board[4][4] = "*";
        board[9][9] = "*";
    }

    public void printBoard() {
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                /*TODO: print board + numbers*/
            }
        } 
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;         
    }
}