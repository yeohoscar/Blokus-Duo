package controller;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * MidGame class
 *  - handles checking for valid game moves after first turn
 */

import model.*;
import model.piece.*;
import ui.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MidGameMove {
    UI ui;
    private final Player currentPlayer;
    private final Player nextPlayer;
    private final Board board;
    private final Piece piece;
    private final int originX;
    private final int originY;

    @SuppressWarnings("unchecked")
    public MidGameMove(Player currentPlayer, Player nextPlayer, Board board, UI ui) {
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
    }

    public MidGameMove(Player currentPlayer, Player nextPlayer, Board board, Piece piece, int originX, int originY, UI ui) {
        if (currentPlayer == null || nextPlayer == null || board == null || piece == null) {
            throw new IllegalArgumentException();
        }
        this.nextPlayer = nextPlayer;
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.piece = piece;
        this.originX = originX;
        this.originY = originY;
        this.ui = ui;
    }

    /**
     * Print the valid moves
     */
    @Override
    public String toString() {
        String s = "";
        for(int[] m : currentPlayer.getValidMove()) {
            s += m[0] + " " + m[1] + "\n";
        }

        return s;
    }

    /**
     * Check if the coordinate is a valid move
     * 
     * @param player Player
     * @param piece Selected piece by currentPlayer
     * @param dest_x x coordinates on the board
     * @param dest_y y coordinates on the board
     * @return true if it is a valid move
     */
    public boolean isValidMove(Player player, Piece piece, int dest_x, int dest_y) {
        return (board.isEmptyForPiece(piece, dest_x, dest_y) && isPieceTouchEdge(player, piece, dest_x, dest_y) && !board.isSide(player.getColor(), piece, dest_x, dest_y));
    }

    /**
     * Check if the piece is available to place on the coordinates
     * 
     * @param player Player
     * @param p Piece to be check
     * @param x x coordinates of the valid move
     * @param y y coordinates of the valid move
     * @return true if the piece is not able to place on the coordinates
     */
    public int checkEveryPiece(Player player, Piece p, int x, int y) {
        if(!isValidMove(player, p, x, y)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Check if there is any available piece to put on valid moves
     * 
     * @param player Player
     * @param move Coordinates of valid move
     * @return true if there is available piece
     */
    public boolean hasAvailablePiece(Player player, int[] move) { 
        int count = 0;

        for(Piece piece : player.getStock().getPieces()) {
            int flag = 0;
            Piece pCopy = new Piece(piece);
            switch (piece.getName()) {
                case "I1", "X5" -> {
                    flag += checkEveryPiece(player, piece, move[0], move[1]);
                }
                default -> {
                    flag += checkEveryPiece(player, piece, move[0], move[1]);
                    pCopy.rotatePieceClockwise();
                    flag += checkEveryPiece(player, pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    flag += checkEveryPiece(player, pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    pCopy.rotatePieceClockwise();
                    flag += checkEveryPiece(player, pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    flag += checkEveryPiece(player, pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    pCopy.rotatePieceClockwise();
                    flag += checkEveryPiece(player, pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    flag += checkEveryPiece(player, pCopy, move[0], move[1]);
                }
            }

            if(flag == 1 || flag == 2 || flag == 7) {
                count++;
            }
        }

        return count != player.getStock().getPieces().size();
    }

    /**
     * Check if the selected piece is at the edge of placed pieces 
     * 
     * @param player Player
     * @param piece Selected piece by player
     * @param dest_x x coordinates on board
     * @param dest_y y coordinates on board
     * @return true if any square of the piece touch the edge of placed piece
     */
    public boolean isPieceTouchEdge(Player player, Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().anyMatch(offset -> isContain(player, offset, dest_x, dest_y));
    }

    /**
     * Check if selected piece touch the edge of placed pieces
     * 
     * @param player Player
     * @param offset block offset from origin
     * @param dest_x x coordinates on board
     * @param dest_y y coordinates on board
     * @return true if the square touch the edge of placed pieces
     */
    public boolean isContain(Player player, Block offset, int dest_x, int dest_y) {
        for(int[] m : player.getValidMove()) {
            if(m[0] == offset.getX() + dest_x && m[1] == offset.getY() + dest_y) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add the coordinates of valid move into the arraylist
     * 
     * @param offset block offset from origin
     * @param dest_x x coordinate of the edge of placed pieces 
     * @param dest_y y coordinate of the edge of placed pieces 
     */
    public void addMove(Block offset, int dest_x, int dest_y) {
        int[] coor = new int[] {dest_x + offset.getX(), dest_y + offset.getY()};

        if(board.contains(dest_x + offset.getX(), dest_y + offset.getY())) {
            if(board.isEmptyAt(offset, dest_x, dest_y)) {
                currentPlayer.getValidMove().add(coor);
            }
        }
    }

    /**
     * Update the arraylist that stored the valid moves by removing selected moves and checking available pieces with valid moves
     * 
     * @param input Coordinates of valid move entered by user
     */
    public void updateMove(int[] input) {
        currentPlayer.getValidMove().removeIf(n -> (Arrays.equals(n, input)));

        for(int i = 0; i < currentPlayer.getValidMove().size(); i++) {
            if(!hasAvailablePiece(currentPlayer, currentPlayer.getValidMove().get(i))) {
                currentPlayer.getValidMove().remove(i);
                i--;
            }
        }

        for(int j = 0; j < nextPlayer.getValidMove().size(); j++) {
            if(!hasAvailablePiece(nextPlayer, nextPlayer.getValidMove().get(j))) {
                nextPlayer.getValidMove().remove(j);
                j--;
            }
        }
    }

    /**
     * Get the valid moves of each player
     * 
     * @param blocks block offset from origin
     * @param dest_x x coordinate of placed pieces 
     * @param dest_y y coordinate of placed pieces 
     */
    public void getMove(ArrayList<Block> blocks, int dest_x, int dest_y) {
        ArrayList<int[]> dir = new ArrayList<int[]>();
        int[] input = new int[] {dest_x, dest_y};

        for(int i = 0; i < blocks.size(); i++) {
            if(board.isCornerPiece(blocks, i, dir)) {
                if(dir.get(i)[0] != 0) {
                    if(dir.get(i)[2] != 0) {
                        addMove(blocks.get(i), dest_x - 1, dest_y - 1);
                    }
                    else if(dir.get(i)[3] != 0) {
                        addMove(blocks.get(i), dest_x + 1, dest_y - 1);
                    }
                    else {
                        addMove(blocks.get(i), dest_x - 1, dest_y - 1);
                        addMove(blocks.get(i), dest_x + 1, dest_y - 1);
                    }
                }
                else if(dir.get(i)[1] != 0) {
                    if(dir.get(i)[2] != 0) {
                        addMove(blocks.get(i), dest_x - 1, dest_y + 1);
                    }
                    else if(dir.get(i)[3] != 0) {
                        addMove(blocks.get(i), dest_x + 1, dest_y + 1);
                    }
                    else {
                        addMove(blocks.get(i), dest_x - 1, dest_y + 1);
                        addMove(blocks.get(i), dest_x + 1, dest_y + 1);
                    }
                }
                else if(dir.get(i)[2] != 0) {
                    addMove(blocks.get(i), dest_x - 1, dest_y + 1);
                    addMove(blocks.get(i), dest_x - 1, dest_y - 1);
                }
                else if(dir.get(i)[3] != 0) {
                    addMove(blocks.get(i), dest_x + 1, dest_y + 1);
                    addMove(blocks.get(i), dest_x + 1, dest_y - 1);
                }
                else {
                    addMove(blocks.get(i), dest_x + 1, dest_y + 1);
                    addMove(blocks.get(i), dest_x + 1, dest_y - 1);
                    addMove(blocks.get(i), dest_x - 1, dest_y + 1);
                    addMove(blocks.get(i), dest_x - 1, dest_y - 1);
                }
            }
        }

        updateMove(input);
    }

    /**
     * Validate move before placing the piece on board
     */
    public boolean executeMove() {
        if (!isValidMove(currentPlayer, piece, originX, originY)) {
            return false;
        }

        board.addPiece(currentPlayer, piece, originX, originY);

        for(int j = 0; j < currentPlayer.getStock().getPieces().size(); j++) {
            if(currentPlayer.getStock().getPieces().get(j).getName().equals(piece.getName())) {
                currentPlayer.setScore(currentPlayer.getScore() + currentPlayer.getStock().getPieces().get(j).getBlocks().size());
                currentPlayer.getStock().getPieces().remove(j);
                currentPlayer.setLastPieceI1(piece.getName().equals("I1"));
            }
        }

        getMove(piece.getBlocks(), originX, originY);
        return true;
    }
}
