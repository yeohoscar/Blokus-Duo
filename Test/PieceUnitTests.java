import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class PieceUnitTests {
    @Test
    void testDeepCopy() {
        Piece original = new Piece("I2", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1))));
        Piece copy = new Piece(original);

        assertEquals(original.getName(), copy.getName());
        for (int i = 0; i < original.getBlocks().size(); i++) {
            if (!(original.getBlocks().get(i).getX() == copy.getBlocks().get(i).getX() && original.getBlocks().get(i).getY() == copy.getBlocks().get(i).getY())) {
                fail("Blocks not copied correctly");
            }
        }

        if (original.equals(copy)) {
            fail("not deep copy");
        }
    }

    @Test
    void testManipulation() {
        Piece p = new Piece("I2", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1))));
        ByteArrayInputStream in = new ByteArrayInputStream("r r f".getBytes());
        Scanner s = new Scanner(in).useDelimiter("\\A");

        p.flipPiece();
        assertEquals(0, p.getBlocks().get(1).getX());
        assertEquals(1, p.getBlocks().get(1).getY());
        
        p.rotatePieceClockwise();
        assertEquals(1, p.getBlocks().get(1).getX());
        assertEquals(0, p.getBlocks().get(1).getY());

        p.flipPiece();
        assertEquals(-1, p.getBlocks().get(1).getX());
        assertEquals(0, p.getBlocks().get(1).getY());

        Piece p2 = new Piece("Z4", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1))));

        p2.manipulation(s, "X");
        assertEquals(0, p.getBlocks().get(1).getX());
        assertEquals(-1, p.getBlocks().get(1).getY());

        assertEquals(1, p.getBlocks().get(2).getX());
        assertEquals(-1, p.getBlocks().get(2).getY());

        assertEquals(-1, p.getBlocks().get(1).getX());
        assertEquals(0, p.getBlocks().get(1).getY());
        
        s.close();
    }
}
