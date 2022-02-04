import java.util.*;

public class Stock {
    private List<Piece> pieces = new ArrayList<>();

    public Stock() {
        setPieces();
    }

    /**
     * Initialises pieces and adds to arraylist
     */
    private void setPieces() {
        int[] ori = new int[] {0, 0};
        int[] up1 = new int[] {0, 1};
        int[] up2 = new int[] {0, 2};
        int[] up3 = new int[] {0, 3};
        int[] up4 = new int[] {0, 4};
        int[] r1 = new int[] {1, 0};
        int[] r2 = new int[] {2, 0};
        int[] r3 = new int[] {3, 0};
        int[] l1 = new int[] {-1, 0};
        int[] d1 = new int[] {0, -1};
        int[] d2 = new int[] {0, -2};
        int[] ur = new int[] {1, 1};
        int[] dr = new int[] {1, -1};
        int[] dl = new int[] {-1, -1};

        addPieces(
            new Piece("I1", new ArrayList<>(Arrays.asList(ori))),
            new Piece("I2", new ArrayList<>(Arrays.asList(ori, up1))),
            new Piece("I3", new ArrayList<>(Arrays.asList(ori, up1, up2))),
            new Piece("I4", new ArrayList<>(Arrays.asList(ori, up1, up2, up3))),
            new Piece("I5", new ArrayList<>(Arrays.asList(ori, up1, up2, up3, up4))),
            new Piece("V3", new ArrayList<>(Arrays.asList(ori, up1, r1))),
            new Piece("L4", new ArrayList<>(Arrays.asList(ori, up1, up2, r1))),
            new Piece("Z4", new ArrayList<>(Arrays.asList(ori, up1, ur, l1))),
            new Piece("O4", new ArrayList<>(Arrays.asList(ori, up1, ur, r1))),
            new Piece("L5", new ArrayList<>(Arrays.asList(ori, up1, r1, r2, r3))),
            new Piece("T5", new ArrayList<>(Arrays.asList(ori, up1, up2, r1, l1))),
            new Piece("V5", new ArrayList<>(Arrays.asList(ori, up1, up2, r1, r2))),
            new Piece("N5", new ArrayList<>(Arrays.asList(ori, r1, r2, d1, dl))),
            new Piece("Z5", new ArrayList<>(Arrays.asList(ori, r1, ur, l1, dl))),
            new Piece("T4", new ArrayList<>(Arrays.asList(ori, up1, r1, l1))),
            new Piece("P5", new ArrayList<>(Arrays.asList(ori, r1, dr, d1, d2))),
            new Piece("W5", new ArrayList<>(Arrays.asList(ori, up1, ur, l1, dl))),
            new Piece("U5", new ArrayList<>(Arrays.asList(ori, up1, ur, d1, dr))),
            new Piece("F5", new ArrayList<>(Arrays.asList(ori, up1, ur, d1, l1))),
            new Piece("X5", new ArrayList<>(Arrays.asList(ori, up1, r1, d1, l1))),
            new Piece("Y5", new ArrayList<>(Arrays.asList(ori, up1, r1, r2, l1)))
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
