package model;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Board class
 *  - represents Blokus board
 */

import model.piece.*;

import java.util.ArrayList;

public class Board {
    public final static int WIDTH = 14;
    public final static int HEIGHT = 14;
    public final static int X = 0;
    public final static int O = 1;

    private String[][] board = new String[WIDTH][HEIGHT];
    boolean[][] occupied = new boolean[WIDTH][HEIGHT];

    public Board() {
        // initialize the board
        for (int i = 0; i < board.length; i++ ) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = ".";
            }
        }
        // Starting points
        board[4][4] = "*";
        board[9][9] = "*";
    }

    public Board(Board board) {
        for (int i = 0; i < HEIGHT; i++ ) {
            for (int j = 0; j < WIDTH; j++) {
                occupied[i][j] = board.isOccupied(i, j);
                this.board[i][j] = board.getColorOnSquare(i, j);
            }
        }
    }

    /**
     * Visualize the board by using 2D array to print the board
     */
    public void printBoard() {
        int row = board.length - 1;
        System.out.print("BLOKUS DUO\n");

        for (int i = 0; i < board[0].length; i++) {
            // column index
            if(row > 9) {
                System.out.print("\n" + row + " ");
            } else {
                System.out.print("\n " + row + " ");
            }
            row--;
            // every square
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
        }

        // row index
        System.out.print("\n   ");
        for (int i = 0; i < board[0].length; i++) {
            if (i > 9) {
                System.out.print(i);
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public boolean isOccupied(int x, int y) {
        return occupied[x][y];
    }

    public String getColorOnSquare(int x, int y) {
        return board[x][y];
    }

    /**
     *  Adds piece to the board
     * 
     *  @param player player playing the piece
     *  @param piece piece being placed
     *  @param originX x coordinate originY piece origin
     *  @param originY y coordinate of piece origin
     */
    public void addPiece(Player player, Piece piece, int originX, int originY) {
        for (Block block : piece.getBlocks()) {
            board[13 - originY - block.getY()][originX + block.getX()] = player.getColor();
            occupied[13 - originY - block.getY()][originX + block.getX()] = true;
        }
    }

    /**
     * returns false if square is out of bounds
     * 
     * @param x x coordinate
     * @param y y coordinate
     */
    public boolean contains(int x, int y) {
        return x >= 0 && x < board[0].length && y >= 0 && y < board.length;
    }

    /**
     * Checks if the square on the board is empty 
     * 
     * @param offset block offset from origin
     * @param dest_x x coordinates
     * @param dest_y y coordinates
     * @return true if the square is empty
     */
    public boolean isEmptyAt(Block offset, int dest_x, int dest_y) {
        if(!contains(13 - dest_y - offset.getY(), dest_x + offset.getX())) {
            return false;
        }

        return (board[13 - dest_y - offset.getY()][dest_x + offset.getX()] != "X" && board[13 - dest_y - offset.getY()][dest_x + offset.getX()] != "O");
    }

    /**
     * @return true if the board does not contains any piece
     */
    public boolean isEmpty() {
        for (String[] strings : board) {
            for (int j = 0; j < board.length; j++) {
                if (strings[j] == "X" || strings[j] == "O") {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the input coordinate has enough space to place the piece
     * 
     * @param piece selected piece
     * @param dest_x x coordinates
     * @param dest_y y coordinates
     * @return true if the coordinates are able to place the piece
     */
    public boolean isEmptyForPiece(Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().allMatch(offset -> isEmptyAt(offset, dest_x, dest_y));
    }

    /**
     * Check if the square of the placed piece is the corner square
     * The arraylist dir stored the direction of the remaining square which is from the same piece
     * 
     * @param block block offset from origin
     * @param i index of block
     * @param dir direction of the square
     * @return true if the square is an corner square of the piece
     */
    public boolean isCornerPiece(ArrayList<Block> block, int i, ArrayList<int[]> dir) {
        int up = 0, down = 0, left = 0, right = 0;
        for (int j = 0; j < block.size(); j++) {
            if ((block.get(i)).getX() == (block.get(j)).getX()) {
                if ((block.get(i)).getY() - (block.get(j)).getY() == 1) {
                    down++;
                }

                if ((block.get(i)).getY() - (block.get(j)).getY() == -1) {
                    up++;
                }
            }

            if ((block.get(i)).getY() == (block.get(j)).getY()) {
                if ((block.get(i)).getX() - (block.get(j)).getX() == 1) {
                    left++;
                }

                if ((block.get(i)).getX() - (block.get(j)).getX() == -1) {
                    right++;
                }
            }
        }

        dir.add(new int[] {up, down, right, left});

        return (left + right) < 2 && (up + down) < 2;
    }

    /**
     * Check the color of square on the board
     * 
     * @param color player's piece color
     * @param dest_x x coordinate
     * @param dest_y y coordinate
     * @return true if the square has the same color as player's piece color
     */
    public boolean isSameColor(String color, int dest_x, int dest_y) {
        return board[dest_x][dest_y].equals(color);
    }

    /**
     * Check if there is any placed piece at the side of selected square
     * 
     * @param color player's piece color
     * @param offset block offset from origin
     * @param dest_x x coordinate
     * @param dest_y y coordinate
     * @return true if there is piece from the same player at the side of the selected coordinates
     */
    public boolean isAtSide(String color, Block offset, int dest_x, int dest_y) {
        int x = 13 - dest_y - offset.getY();
        int y = dest_x + offset.getX();

        if(x >= 14 || x <= -1 || y >= 14 || y <= -1) {
            return false;
        }

        if (!contains(x + 1, y)) {
            if (y <= 0) {
                return isSameColor(color, x, y + 1) || isSameColor(color, x - 1, y);
            }

            if (y >= 13) {
                return isSameColor(color, x, y - 1) || isSameColor(color, x - 1, y);
            }

            return isSameColor(color, x, y + 1) || isSameColor(color, x, y - 1) || isSameColor(color, x - 1, y);
        }

        if(!contains(x - 1, y)) {
            if(y <= 0) {
                return isSameColor(color, x, y + 1) || isSameColor(color, x + 1, y);
            }

            if(y >= 13) {
                return isSameColor(color, x, y - 1) || isSameColor(color, x + 1, y);
            }

            return isSameColor(color, x, y + 1) || isSameColor(color, x, y - 1) || isSameColor(color, x + 1, y);

        }
        
        if(!contains(x, y + 1)) {
            return isSameColor(color, x, y - 1) || isSameColor(color, x + 1, y) || isSameColor(color, x - 1, y);
        }

        if(!contains(x, y - 1)) {
            return isSameColor(color, x, y + 1) || isSameColor(color, x + 1, y) || isSameColor(color, x - 1, y);
        }

        return isSameColor(color, x, y + 1) || isSameColor(color, x, y - 1) ||
               isSameColor(color, x + 1, y) || isSameColor(color, x - 1, y);
    }

    /**
     * Check if there is any placed piece at the side of the selected coordinates
     * 
     * @param player current player
     * @param piece selected piece by player
     * @param dest_x x coordinate
     * @param dest_y y coordinate
     * @return
     */
    public boolean isSide(String playerColor, Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().anyMatch(offset -> isAtSide(playerColor, offset, dest_x, dest_y));
    }

}