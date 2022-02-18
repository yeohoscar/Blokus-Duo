import java.util.*;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Auxillary class of Piece
 *  - holds and initialises all the pieces the player has
 */

public class Stock {
    private List<Piece> pieces = new ArrayList<>();

    public Stock() {
        setPieces();
    }

    /**
     * Initialises pieces and adds to arraylist
     */
    private void setPieces() {
        /*
        int[] new Block(0, 0) = new int[] {0, 0};
        int[] new Block(0, 1) = new int[] {0, 1};
        int[] new Block(0, 2) = new int[] {0, 2};
        int[] new Block(0, 3) = new int[] {0, 3};
        int[] new Block(0, 4) = new int[] {0, 4};
        int[] new Block(1, 0) = new int[] {1, 0};
        int[] new Block(2, 0) = new int[] {2, 0};
        int[] new Block(3, 0) = new int[] {3, 0};
        int[] new Block(-1, 0) = new int[] {-1, 0};
        int[] new Block(0, -1) = new int[] {0, -1};
        int[] d2 = new int[] {0, -2};
        int[] newBlock(1, 1) = new int[] {1, 1};
        int[] newBlock(1, -1) = new int[] {1, -1};
        int[] new Block(-1, -1) = new int[] {-1, -1};
        */

        addPieces(
            new Piece("I1", new ArrayList<>(Arrays.asList(new Block(0, 0)))),
            new Piece("I2", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1)))),
            new Piece("I3", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2)))),
            new Piece("I4", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(0, 3)))),
            new Piece("I5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(0, 3), new Block(0, 4)))),
            new Piece("V3", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 0)))),
            new Piece("L4", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(1, 0)))),
            new Piece("Z4", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 1), new Block(-1, 0)))),
            new Piece("O4", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 1), new Block(1, 0)))),
            new Piece("L5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 0), new Block(2, 0), new Block(3, 0)))),
            new Piece("T5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(1, 0), new Block(-1, 0)))),
            new Piece("V5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(1, 0), new Block(2, 0)))),
            new Piece("N5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(1, 0), new Block(2, 0), new Block(0, -1), new Block(-1, -1)))),
            new Piece("Z5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(1, 0), new Block(1, 1), new Block(-1, 0), new Block(-1, -1)))),
            new Piece("T4", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 0), new Block(-1, 0)))),
            new Piece("P5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(1, 0), new Block(1, -1), new Block(0, -1), new Block(0, -2)))),
            new Piece("W5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 1), new Block(-1, 0), new Block(-1, -1)))),
            new Piece("U5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 1), new Block(0, -1), new Block(1, -1)))),
            new Piece("F5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 1), new Block(0, -1), new Block(-1, 0)))),
            new Piece("X5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 0), new Block(0, -1), new Block(-1, 0)))),
            new Piece("Y5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 0), new Block(2, 0), new Block(-1, 0))))
        );
    }

    private void addPieces(Piece... p) {
        for(Piece a : p) {
            pieces.add(a);
        }
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        String s = "";
        for (Piece p : pieces) {
            s = s.concat(p.getName() + " "); 
        }
        return s;
    }
}
