import java.util.*;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Piece class
 *  - represents Blokus piece
 *  - holds piece name and blocks that make up the piece
 */

public class Piece {
    private String name;
    private ArrayList<int[]> blocks;

    public Piece(String name, ArrayList<int[]> blocks) {
        this.name = name;
        this.blocks = blocks;
    }

    public String getName() {
        return name;
    }

    public ArrayList<int[]> getBlocks() {
        return blocks;
    }

    /**
     * TODO: implement rotation
     */
    public void rotatePieceClockwise(Piece p) {
        int count = p.getBlocks().size();

        while(count != 0) {
            int x = p.getBlocks().get(count)[0];
            int y = p.getBlocks().get(count)[1];

            if(x != 0) {
                x *= -1;
                swap(x, y);
            }
            else {
                swap(x, y);
            }

            count--;
        }
    }

    public void rotatePieceAntiClockwise(Piece p) {
        int count = p.getBlocks().size();

        while(count != 0) {
            int x = p.getBlocks().get(count)[0];
            int y = p.getBlocks().get(count)[1];

            if(x == 0) {
                y *= -1;
                swap(x, y);
            }
            else {
                swap(x, y);
            }

            count--;
        }
    }

    private void swap(int x, int y) {
        int tmp = x;
        x = y;
        y = tmp;
    }
}
 