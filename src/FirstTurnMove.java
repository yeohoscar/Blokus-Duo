import java.util.Hashtable;

public class FirstTurnMove {
    




    /**
     * returns true if any square in piece is on either of the starting points
     * 
     * @param piece piece being played
     * @param dest_x x coordinate of the block origin
     * @param dest_y y cooridnate of the block origin
     */
    public static boolean isValidFirstMove(Hashtable<String, int[]> piece, int dest_x, int dest_y) {
        return piece.values().stream().anyMatch(offset -> isOnFirstMoveSquare(offset, dest_x, dest_y));
    }

    /**
     * Checks if move is a valid first move
     * 
     * @param offset block offset from origin
     * @param dest_x x coordinate of the block origin
     * @param dest_y y coordinate of the block origin
     */
    public static boolean isOnFirstMoveSquare(int[] offset, int dest_x, int dest_y) {
        return (offset[0] + dest_x == 9 && offset[1] + dest_y == 4 || offset[0] + dest_x == 4 && offset[1] + dest_y == 9);
    }

    public static void main(String[] args) {
        Hashtable<String, int[]> piece = new Hashtable<>();

        piece.put("0", new int[] {0, 0});
        piece.put("1", new int[] {0, 1});
        piece.put("2", new int[] {1, 0});

        System.out.println(isValidFirstMove(piece, 8, 4));
    }
}