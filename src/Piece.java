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
    public void rotatePieceClockwise() {
        int count = getBlocks().size()-1;

        while(count >= 0) {
            int x = getBlocks().get(count)[0];
            int y = getBlocks().get(count)[1];

            if ( x == 0 && y != 0) {
                swap(count);
            } else if ( x != 0 && y == 0) {
                blocks.get(count)[0]*=-1;
                swap(count);
            } else if ( x == y) {
                blocks.get(count)[1]*=-1;
            } else {
                blocks.get(count)[0]*=-1;
            }

            count--;
        }
    }



    private void swap(int index) {
        int tmp = blocks.get(index)[0];
        blocks.get(index)[0] = blocks.get(index)[1];
        blocks.get(index)[1] = tmp;
    }

    public void flipPiece() {
        int count = getBlocks().size()-1;

        while(count >= 0) {
            if ( blocks.get(count)[0] != 0) {
                blocks.get(count)[0]*=-1;
            }
            count--;
        }
    }
}
 