import java.util.ArrayList;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Move class
 *  - handles checking for valid first turn moves
 */

public class FirstTurnMove {
    private final Player player;
    private final Board board;
    private final Piece piece;
    private final int originX;
    private final int originY;

    public FirstTurnMove(Player player, Board board, Piece piece, ArrayList<Integer> coord) {
        if (player == null || board == null || piece == null) {
            throw new IllegalArgumentException();
        }
        this.player = player;
        this.board = board;
        this.piece = piece;
        this.originX = coord.get(0);
        this.originY = coord.get(1); 
    }

    /**
     * returns true if any square in piece is on either of the starting points
     * 
     * @param piece piece being played
     * @param originX x coordinate of the block origin
     * @param originY y cooridnate of the block origin
     */
    private boolean isValidFirstMove(Piece piece, int originX, int originY) {
        return piece.getBlocks().stream().anyMatch(offset -> isOnFirstMoveSquare(offset, originX, originY));
    }

    /**
     * Checks if move is a valid first move
     * 
     * @param offset block offset from origin
     * @param originX x coordinate of the block origin
     * @param originY y coordinate of the block origin
     */
    private boolean isOnFirstMoveSquare(int[] offset, int originX, int originY) {
        if (player.getColor() == "O") {
            return (offset[0] + originX == 9 && offset[1] + originY == 4);
        } else {
            return (offset[0] + originX == 4 && offset[1] + originY == 9);
        }
    }

    public boolean executeMove() {
        if (!isValidFirstMove(piece, originX, originY) || !board.isEmptyForPiece(piece, originX, originY)) {
            return false;
        }
        board.addPiece(player, piece, originX, originY);
        player.getStock().getPieces().remove(piece);
        return true;
    }

    public Player getPlayer() {
        return player;
    }
}