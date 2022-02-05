import java.util.ArrayList;

public class FirstTurnMove {
    private final Player player;
    private final Board board;
    private final Piece piece;
    private final int dest_x;
    private final int dest_y;

    public FirstTurnMove(Player player, Board board, Piece piece, ArrayList<Integer> coord) {
        if (player == null || board == null || piece == null) {
            throw new IllegalArgumentException();
        }
        this.player = player;
        this.board = board;
        this.piece = piece;
        this.dest_x = coord.get(0);
        this.dest_y = coord.get(1); 
    }

    /**
     * returns true if any square in piece is on either of the starting points
     * 
     * @param piece piece being played
     * @param dest_x x coordinate of the block origin
     * @param dest_y y cooridnate of the block origin
     */
    private static boolean isValidFirstMove(Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().anyMatch(offset -> isOnFirstMoveSquare(offset, dest_x, dest_y));
    }

    /**
     * Checks if move is a valid first move
     * 
     * @param offset block offset from origin
     * @param dest_x x coordinate of the block origin
     * @param dest_y y coordinate of the block origin
     */
    private static boolean isOnFirstMoveSquare(int[] offset, int dest_x, int dest_y) {
        return (offset[0] + dest_x == 9 && offset[1] + dest_y == 4 || offset[0] + dest_x == 4 && offset[1] + dest_y == 9);
    }

    public boolean executeMove() {
        if (!isValidFirstMove(piece, dest_x, dest_y)) {
            return false;
        }
        board.addPiece(player, piece, dest_x, dest_y);
        player.getStock().getPieces().remove(piece);
        return true;
    }
}