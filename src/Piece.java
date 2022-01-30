import java.util.*;

public class Piece {
    private String name;
    private ArrayList<int[]> blocks;
    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    Piece I1, I2, I3, I4, I5, V3, L4, Z4, O4, L5, T5, V5, N5, Z5, T4, P5, W5, U5, F5, X5, Y5;

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

    //putPiece();

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

    public void swap(int x, int y) {
        int tmp = x;
        x = y;
        y = tmp;
    }

    public void setPieces() {
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

        I1 = new Piece("I1", new ArrayList<>(Arrays.asList(ori)));
        I2 = new Piece("I2", new ArrayList<>(Arrays.asList(ori, up1)));
        I3 = new Piece("I3", new ArrayList<>(Arrays.asList(ori, up1, up2)));
        I4 = new Piece("I4", new ArrayList<>(Arrays.asList(ori, up1, up2, up3)));
        I5 = new Piece("I5", new ArrayList<>(Arrays.asList(ori, up1, up2, up3, up4)));
        V3 = new Piece("V3", new ArrayList<>(Arrays.asList(ori, up1, r1)));
        L4 = new Piece("L4", new ArrayList<>(Arrays.asList(ori, up1, up2, r1)));
        Z4 = new Piece("Z4", new ArrayList<>(Arrays.asList(ori, up1, ur, l1)));
        O4 = new Piece("O4", new ArrayList<>(Arrays.asList(ori, up1, ur, r1)));
        L5 = new Piece("L5", new ArrayList<>(Arrays.asList(ori, up1, r1, r2, r3)));
        T5 = new Piece("T5", new ArrayList<>(Arrays.asList(ori, up1, up2, r1, l1)));
        V5 = new Piece("V5", new ArrayList<>(Arrays.asList(ori, up1, up2, r1, r2)));
        N5 = new Piece("N5", new ArrayList<>(Arrays.asList(ori, r1, r2, d1, dl)));
        Z5 = new Piece("Z5", new ArrayList<>(Arrays.asList(ori, r1, ur, l1, dl)));
        T4 = new Piece("T4", new ArrayList<>(Arrays.asList(ori, up1, r1, l1)));
        P5 = new Piece("P5", new ArrayList<>(Arrays.asList(ori, r1, dr, d1, d2)));
        W5 = new Piece("W5", new ArrayList<>(Arrays.asList(ori, up1, ur, l1, dl)));
        U5 = new Piece("U5", new ArrayList<>(Arrays.asList(ori, up1, ur, d1, dr)));
        F5 = new Piece("F5", new ArrayList<>(Arrays.asList(ori, up1, ur, d1, l1)));
        X5 = new Piece("X5", new ArrayList<>(Arrays.asList(ori, up1, r1, d1, l1)));
        Y5 = new Piece("Y5", new ArrayList<>(Arrays.asList(ori, up1, r1, r2, l1)));

        addPieces(I1, I2, I3, I4, I5, V3, L4, Z4, O4, L5, T5, V5, N5, Z5, T4, P5, W5, U5, F5, X5, Y5);
    }

    public void addPieces(Piece... p) {}
        for(Piece a : p) {
            pieces.add(a);
        }
    }
}
