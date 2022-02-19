import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Move class
 *  - handles checking for valid first turn moves
 */

public class FirstTurnMove implements Move {
    private final Player player;
    private final Board board;
    private final Piece piece;
    private final int originX;
    private final int originY;
    private final MidGame midGame;

    @SuppressWarnings("unchecked")
    public FirstTurnMove(Player player, Board board, Scanner s) {
        if (player == null || board == null) {
            throw new IllegalArgumentException();
        }
        this.player = player;
        this.board = board;
        List<Object> list = selectPiece(s);
        this.piece = (Piece) list.get(0);
        this.originX = ((ArrayList<Integer>) list.get(1)).get(0);
        this.originY = ((ArrayList<Integer>) list.get(1)).get(1);
        this.midGame = new MidGame(player, board, piece, originX, originY);
    }

    public int getX() {
        return originX;
    }

    public int getY() {
        return originY;
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
    private boolean isOnFirstMoveSquare(Block offset, int originX, int originY) {
        if (player.getColor() == "O") {
            return (offset.getX() + originX == 9 && offset.getY() + originY == 4);
        } else {
            return (offset.getX() + originX == 4 && offset.getY() + originY == 9);
        }
    }

    public boolean executeMove() {
        if (!isValidFirstMove(piece, originX, originY) || !board.isEmptyForPiece(piece, originX, originY)) {
            return false;
        }
        
        board.addPiece(player, piece, originX, originY);
        for(int j = 0; j < player.getStock().getPieces().size(); j++) {
            if(player.getStock().getPieces().get(j).getName().equals(piece.getName())) {
                player.getStock().getPieces().remove(j);
            }
        }
        midGame.getMove(piece.getBlocks(), originX, originY);

        return true;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Object> selectPiece(Scanner s) {
        if (player.getStock().getPieces().size() == 0) {
            System.out.println("No more pieces left.");
        }
        System.out.println("Select a piece");
        String tmp = s.useDelimiter("\\n").nextLine();
        
        while(true) {
            for (Piece p : player.getStock().getPieces()) {
                if (p.getName().equals(tmp)) {
                    Piece pCopy = new Piece(p);
                    return new ArrayList<>(Arrays.asList(pCopy, pCopy.manipulation(s, player.getColor())));                                
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

        return coord;
    }
}