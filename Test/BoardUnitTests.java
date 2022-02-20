import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;


public class BoardUnitTests {
    @Test
    void testValidCoordinates() {
        Board board = new Board();
        
        assertTrue(board.contains(9, 4));
        assertTrue(board.contains(5, 6));
        assertFalse(board.contains(14, 14));
        assertFalse(board.contains(0, -4));
    }

    @Test
    void testEmptyBoard() {
        Board board = new Board();
        Player player = new Player("player", "X");
        Piece piece = new Piece("I2", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1))));


        assertTrue(board.isEmpty());
        board.addPiece(player, piece, 9, 4);
        assertFalse(board.isEmpty());
    }

    @Test
    void testEmptySpaceForPiece() {
        Board board = new Board();
        Player player = new Player("player", "X");
        Piece piece = new Piece("I2", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1))));

        assertTrue(board.isEmptyForPiece(piece, 9, 4));
        board.addPiece(player, piece, 9, 4);
        assertFalse(board.isEmptyForPiece(piece, 9, 4));
    }

    @Test
    void testIsCornerSquareOfPiece() {
        Board board = new Board();
        Piece piece = new Piece("I2", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1))));
        ArrayList<int[]> dir = new ArrayList<int[]>();

        assertTrue(board.isCornerPiece(piece.getBlocks(), 0, dir));
        assertEquals(4, dir.get(0).length);
        assertEquals(1, dir.get(0)[0]);
        assertEquals(0, dir.get(0)[1]);
        assertEquals(0, dir.get(0)[2]);
        assertEquals(0, dir.get(0)[3]);
    }

    @Test
    void testIsSide() {
        Board board = new Board();
        Player player = new Player("player", "X");
        Piece I5 = new Piece("I5", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(0, 3), new Block(0, 4))));
        Piece F = new Piece("F", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 1), new Block(0, -1), new Block(-1, 0))));

        board.addPiece(player, I5, 9, 4);
        assertTrue(board.isSide(player, F, 9, 3));
        assertFalse(board.isSide(player, F, 2, 2));

        board.addPiece(player, F, 6, 7);
        assertTrue(board.isSide(player, I5, 5, 6));
        assertFalse(board.isSide(player, I5, 13, 1));
    }
}
