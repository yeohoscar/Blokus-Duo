/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Move class
 *  - handles checking for valid first turn moves
 */

package controller;

import model.*;
import model.piece.*;
import ui.UI;

import java.util.*;

public class FirstTurnMove {
    UI ui;
    private final Player currentPlayer;
    private final Player nextPlayer;
    private final Board board;
    private final Piece piece;
    private final int originX;
    private final int originY;
    private final MidGameMove midGameMove;

    @SuppressWarnings("unchecked")
    public FirstTurnMove(Player currentPlayer, Player nextPlayer, Board board, UI ui) {
        if (currentPlayer == null || nextPlayer == null || board == null) {
            throw new IllegalArgumentException();
        }
        this.currentPlayer = currentPlayer;
        this.nextPlayer = nextPlayer;
        this.board = board;
        this.ui = ui;
        List<Object> list = currentPlayer.getPiece();
        this.piece = (Piece) list.get(0);
        this.originX = ((ArrayList<Integer>) list.get(1)).get(0);
        this.originY = ((ArrayList<Integer>) list.get(1)).get(1);
        this.midGameMove = new MidGameMove(currentPlayer, nextPlayer, board, piece, originX, originY, ui);
    }

    public int getX() {
        return originX;
    }

    public int getY() {
        return originY;
    }

    /**
     * returns true if any square in piece is on either of the starting points
     * 
     * @param piece piece being played
     * @param originX x coordinate of the block origin
     * @param originY y cooridnate of the block origin
     */
    private boolean isValidFirstMove(Piece piece, int originX, int originY) {
        return piece.getBlocks().stream().anyMatch(offset -> isOnFirstMoveSquare(offset, originX, originY));
    }

    /**
     * Checks if move is a valid first move
     * 
     * @param offset block offset from origin
     * @param originX x coordinate of the block origin
     * @param originY y coordinate of the block origin
     */
    private boolean isOnFirstMoveSquare(Block offset, int originX, int originY) {
        if (Objects.equals(currentPlayer.getColor(), "O")) {
            return (offset.getX() + originX == 9 && offset.getY() + originY == 4);
        } else {
            return (offset.getX() + originX == 4 && offset.getY() + originY == 9);
        }
    }

    public boolean executeMove() {
        if (!isValidFirstMove(piece, originX, originY)) {
            return false;
        }

        board.addPiece(currentPlayer, piece, originX, originY);
        for (int j = 0; j < currentPlayer.getStock().getPieces().size(); j++) {
            if (currentPlayer.getStock().getPieces().get(j).getName().equals(piece.getName())) {
                currentPlayer.setScore(currentPlayer.getScore() + currentPlayer.getStock().getPieces().get(j).getBlocks().size());
                currentPlayer.getStock().getPieces().remove(j);
                currentPlayer.setLastPieceI1(piece.getName().equals("I1"));
            }
        }
        midGameMove.getMove(piece.getBlocks(), originX, originY);

        return true;
    }
}