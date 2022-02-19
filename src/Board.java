/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Board class
 *  - represents Blokus board
 */

import java.util.ArrayList;

public class Board {
    private String[][] board;

    public Board() {
        this.board = new String[14][14];
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
     * 
     * @param offset
     * @param dest_x
     * @param dest_y
     * @return
     */
    public boolean isEmptyAt(Block offset, int dest_x, int dest_y) {
        if(!contains(13 - dest_y - offset.getY(), dest_x + offset.getX())) {
            return false;
        }

        //System.out.println((13 - dest_y - offset.getY()) + " " + (dest_x + offset.getX()));
        return (board[13 - dest_y - offset.getY()][dest_x + offset.getX()] != "X" && board[13 - dest_y - offset.getY()][dest_x + offset.getX()] != "O");
    }

    /**
     * returns true if the board does not contains a piece
     */
    public boolean isEmpty() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(board[i][j] == "X" || board[i][j] == "O") {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 
     * @param piece
     * @param dest_x
     * @param dest_y
     * @return
     */
    public boolean isEmptyForPiece(Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().allMatch(offset -> isEmptyAt(offset, dest_x, dest_y));
    }

    /**
     * 
     * @param block
     * @param i
     * @param dir
     * @return
     */
    public boolean isCornerPiece(ArrayList<Block> block, int i, ArrayList<int[]> dir) {
        int up = 0, down = 0, left = 0, right = 0;
        for (int j = 0; j < block.size(); j++) {
            if ((block.get(i)).getX() == (block.get(j)).getX()) {
                //System.out.println(i + " v1 " + j);
                if ((block.get(i)).getY() - (block.get(j)).getY() == 1) {
                    //System.out.println(block.get(i)[1] + " v2 " + block.get(j)[1]);
                    down++;
                }

                if ((block.get(i)).getY() - (block.get(j)).getY() == -1) {
                    //ystem.out.println(block.get(i)[1] + " v2 " + block.get(j)[1]);
                    up++;
                }
            }

            if ((block.get(i)).getY() == (block.get(j)).getY()) {
                //System.out.println(i + " h1 " + j);
                if ((block.get(i)).getX() - (block.get(j)).getX() == 1) {
                    //System.out.println(block.get(i)[0] + " h2 " + block.get(j)[0]);
                    left++;
                }

                if ((block.get(i)).getX() - (block.get(j)).getX() == -1) {
                    //System.out.println(block.get(i)[0] + " h2 " + block.get(j)[0]);
                    right++;
                }
            }
        }
        //System.out.println(left + " " + right + " count " + up + " " + down);
        dir.add(new int[] {up, down, right, left});

        if ((left + right) < 2 && (up + down) < 2) {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param color
     * @param dest_x
     * @param dest_y
     * @return
     */
    public boolean isSameColor(String color, int dest_x, int dest_y) {
        //System.out.println(dest_x + " color " + dest_y);
        return board[dest_x][dest_y].equals(color);
    }

    /**
     * 
     * @param color
     * @param offset
     * @param dest_x
     * @param dest_y
     * @return
     */
    public boolean isAtSide(String color, Block offset, int dest_x, int dest_y) {
        int x = 13 - dest_y - offset.getY();
        int y = dest_x + offset.getX();

        if(x >= 14 || x <= -1 || y >= 14 || y <= -1) {
            return false;
        }

        if (!contains(x + 1, y)) {
            if (y <= 0) {
                //System.out.println(" a ");
                return isSameColor(color, x, y + 1) || isSameColor(color, x - 1, y);
            }

            if (y >= 13) {
                //System.out.println("b");
                return isSameColor(color, x, y - 1) || isSameColor(color, x - 1, y);
            }

            //System.out.println(" c ");
            return isSameColor(color, x, y + 1) || isSameColor(color, x, y - 1) || isSameColor(color, x - 1, y);
        }

        if(!contains(x - 1, y)) {
            if(y <= 0) {
                //System.out.println(" d ");
                return isSameColor(color, x, y + 1) || isSameColor(color, x + 1, y);
            }

            if(y >= 13) {
                //System.out.println(" e ");
                return isSameColor(color, x, y - 1) || isSameColor(color, x + 1, y);
            }

            //System.out.println(" f ");
            return isSameColor(color, x, y + 1) || isSameColor(color, x, y - 1) || isSameColor(color, x + 1, y);

        }
        
        if(!contains(x, y + 1)) {
            //System.out.println(" g ");
            return isSameColor(color, x, y - 1) || isSameColor(color, x + 1, y) || isSameColor(color, x - 1, y);
        }

        if(!contains(x, y - 1)) {
            //System.out.println(" h ");
            return isSameColor(color, x, y + 1) || isSameColor(color, x + 1, y) || isSameColor(color, x - 1, y);
        }

        System.out.println("| "+isSameColor(color, x, y + 1) + ", " + isSameColor(color, x, y - 1) + ", " + (x + 1) +" "+ y + ", "+ isSameColor(color, x - 1, y) + " |");
        return isSameColor(color, x, y + 1) || isSameColor(color, x, y - 1) ||
               isSameColor(color, x + 1, y) || isSameColor(color, x - 1, y);
    }

    /**
     * 
     * @param player
     * @param piece
     * @param dest_x
     * @param dest_y
     * @return
     */
    public boolean isSide(Player player, Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().anyMatch(offset -> isAtSide(player.getColor(), offset, dest_x, dest_y));
    }

    /*
    public String getColorAt(int dest_x, int dest_y) {
        if(!contains(13 - dest_y, dest_x)) {
            throw new IllegalArgumentException("Position out of bound");
        }

        if(isEmptyAt(new int[] {0, 0}, dest_x, dest_y)) {
            return null;
        }

        System.out.println("a " + board[13 - dest_y][dest_x]);
        return board[13 - dest_y][dest_x];
    }
    */
}