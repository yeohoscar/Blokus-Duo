import java.util.*;

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
}
 