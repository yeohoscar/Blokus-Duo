/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Unit Test for Board Class
 *  - test the functions in Board Class
 */

 import static org.junit.Assert.fail;
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
        assertTrue(board.contains(2, 12));
        assertFalse(board.contains(14, 14));
        assertFalse(board.contains(0, -4));
        assertFalse(board.contains(-2, 20));
    }

    @Test
    void testAddPiece() {
        Board board = new Board();
        Player player = new Player("player", "X");
        Piece piece = new Piece("T4", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 0), new Block(-1, 0))));

        assertTrue(board.isEmpty());
        board.addPiece(player, piece, 9, 4);
        assertFalse(board.isEmpty());

        if (!(board.getBoard()[8][9] == "X" || board.getBoard()[9][8] == "X" || board.getBoard()[9][9] == "X" || board.getBoard()[9][10] == "X")) {
            fail("Piece was not placed on board correctly");
        }
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
    void testIsSameColor() {
        Board board = new Board();
        board.getBoard()[1][1] = "X";
        board.printBoard();
        assertTrue(board.isSameColor("X", 1, 1));
        assertFalse(board.isSameColor("O", 1, 1));
    }

    @Test
    void testEmptySpaceForPiece() {
        Board board = new Board();
        Player player = new Player("player", "X");
        Piece I2 = new Piece("I2", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1))));
        Piece O4 = new Piece("O4", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 1), new Block(1, 0))));

        assertTrue(board.isEmptyForPiece(I2, 9, 4));
        assertTrue(board.isEmptyForPiece(I2, 9, 5));
        assertTrue(board.isEmptyForPiece(I2, 9, 6));
        assertTrue(board.isEmptyForPiece(I2, 4, 0));
        board.addPiece(player, I2, 9, 4);
        assertFalse(board.isEmptyForPiece(I2, 9, 4));
        assertFalse(board.isEmptyForPiece(I2, 9, 5));
        assertTrue(board.isEmptyForPiece(I2, 9, 6));
        assertTrue(board.isEmptyForPiece(I2, 4, 0));
        board.addPiece(player, O4, 4, 0);
        assertFalse(board.isEmptyForPiece(I2, 4, 0));
        assertFalse(board.isEmptyForPiece(I2, 4, 1));
        assertFalse(board.isEmptyForPiece(I2, 5, 0));
        assertFalse(board.isEmptyForPiece(I2, 5, 1));
    }

    @Test
    void testIsCornerSquareOfPiece() {
        Board board = new Board();
        Piece I2 = new Piece("I2", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1))));
        Piece X = new Piece("X", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 0), new Block(0, -1), new Block(-1, 0))));
        ArrayList<int[]> dir = new ArrayList<int[]>();

        assertTrue(board.isCornerPiece(I2.getBlocks(), 1, dir));
        assertEquals(4, dir.get(0).length);
        assertEquals(0, dir.get(0)[0]);
        assertEquals(1, dir.get(0)[1]);
        assertEquals(0, dir.get(0)[2]);
        assertEquals(0, dir.get(0)[3]);

        dir = new ArrayList<int[]>();
        assertFalse(board.isCornerPiece(X.getBlocks(), 0, dir));
        assertEquals(4, dir.get(0).length);
        assertEquals(1, dir.get(0)[0]);
        assertEquals(1, dir.get(0)[1]);
        assertEquals(1, dir.get(0)[2]);
        assertEquals(1, dir.get(0)[3]);
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
