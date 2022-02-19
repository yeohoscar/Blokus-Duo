import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * MidGame class
 *  - handles checking for valid game moves after first turn
 */
public class MidGame implements Move{
    private final Player player;
    private final Board board;
    private final Piece piece;
    private final int originX;
    private final int originY;

    @SuppressWarnings("unchecked")
    public MidGame(Player player, Board board, Scanner s) {
        if (player == null || board == null) {
            throw new IllegalArgumentException();
        }
        this.player = player;
        this.board = board;
        List<Object> list = selectPiece(s);
        this.piece = (Piece) list.get(0);
        this.originX = ((ArrayList<Integer>) list.get(1)).get(0);
        this.originY = ((ArrayList<Integer>) list.get(1)).get(1);
    }

    public MidGame(Player player, Board board, Piece piece, int originX, int originY) {
        if (player == null || board == null || piece == null) {
            throw new IllegalArgumentException();
        }
        this.player = player;
        this.board = board;
        this.piece = piece;
        this.originX = originX;
        this.originY = originY;
    }

    /**
     * Print the valid moves
     */
    @Override
    public String toString() {
        String s = "";
        for(int[] m : player.getValidMove()) {
            s += m[0] + " " + m[1] + "\n";
        }

        return s;
    }

    /**
     * Check if the coordinate is a valid move
     * 
     * @param player Current player
     * @param piece Selected piece by player
     * @param dest_x x coordinates on the board
     * @param dest_y y coordinates on the board
     * @return true if it is a valid move
     */
    public boolean isValidMove(Player player, Piece piece, int dest_x, int dest_y) {
        if(!isAtCorner(piece, dest_x, dest_y) || board.isSide(player, piece, dest_x, dest_y)) {
            return false;
        }
        return true;
    }

    /**
     * Check if the piece is available to place on the coordinates
     * 
     * @param p Piece to be check
     * @param x x coordinates of the valid move
     * @param y y coordinates of the valid move
     * @return true if the piece is not able to place on the coordinates
     */
    public int checkEveryPiece(Piece p, int x, int y) {
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
     * @param move Coordinates of valid move
     * @return true if there is available piece
     */
    public boolean hasAvailablePiece(int[] move) { 
        int count = 0;

        for(Piece piece : player.getStock().getPieces()) {
            int flag = 0;
            Piece pCopy = piece;
            switch(piece.getName()) {
                case "I1":
                case "O4":
                case "X5":
                    flag += checkEveryPiece(piece, move[0], move[1]);
                    break;
                case "I2":
                case "I3":
                case "I4":
                case "I5":
                    flag += checkEveryPiece(piece, move[0], move[1]);
                    pCopy.rotatePieceClockwise();
                    flag += checkEveryPiece(pCopy, move[0], move[1]);
                    break;
                default:
                    flag += checkEveryPiece(piece, move[0], move[1]);
                    pCopy.rotatePieceClockwise();
                    flag += checkEveryPiece(pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    flag += checkEveryPiece(pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    pCopy.rotatePieceClockwise();
                    flag += checkEveryPiece(pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    flag += checkEveryPiece(pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    pCopy.rotatePieceClockwise();
                    flag += checkEveryPiece(pCopy, move[0], move[1]);
                    pCopy.flipPiece();
                    flag += checkEveryPiece(pCopy, move[0], move[1]);
                    break;
            }

            if(flag == 1 || flag == 2 || flag == 7) {
                count++;
            }
        }

        if(count == player.getStock().getPieces().size()) {
            return false;
        }
        
        return true;   
    }

    /**
     * Check if the selected piece is at the edge of placed pieces 
     * @param piece Selected piece by player
     * @param dest_x x coordinates on board
     * @param dest_y y coordinates on board
     * @return true if any square of the piece touch the edge of placed piece
     */
    public boolean isAtCorner(Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().anyMatch(offset -> isContain(offset, dest_x, dest_y));
    }

    /**
     * Check if a square of a piece touch the edge of placed pieces
     * @param offset block offset from origin
     * @param dest_x x coordinates on board
     * @param dest_y y coordinates on board
     * @return true if the square touch the edge of placed pieces
     */
    public boolean isContain(Block offset, int dest_x, int dest_y) {
        for(int[] m : player.getValidMove()) {
            if(m[0] == offset.getX() + dest_x && m[1] == offset.getY() + dest_y) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add the coordinates of valid move into the arraylist
     * @param offset block offset from origin
     * @param dest_x x coordinate of the edge of placed pieces 
     * @param dest_y y coordinate of the edge of placed pieces 
     */
    public void addMove(Block offset, int dest_x, int dest_y) {
        int[] coor = new int[] {dest_x + offset.getX(), dest_y + offset.getY()};

        if(board.contains(dest_x + offset.getX(), dest_y + offset.getY())) {
            if(board.isEmptyAt(offset, dest_x, dest_y)) {
                player.getValidMove().add(coor);
            }
        }
    }

    /**
     * Update the arraylist that stored the valid moves by removing selected moves and checking available pieces with valid moves
     * @param input Coordinates of valid move entered by user
     */
    public void updateMove(int[] input) {
        player.getValidMove().removeIf(n -> (n.equals(input)));

        for(int j = 0; j < player.getValidMove().size(); j++) {
            if(player.getStock().getPieces().size() > 16) {
                if(board.isAtSide(player.getColor(), new Block(0, 0), player.getValidMove().get(j)[0], player.getValidMove().get(j)[1])) {
                    player.getValidMove().remove(j);
                    j--;
                }
            }
            else {
                if(!hasAvailablePiece(player.getValidMove().get(j))) {
                    player.getValidMove().remove(j);
                    j--;
                }
            }
        }
    }

    /**
     * Get the valid moves of each player
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
                        //System.out.println(i + " a " + dest_x + " " + dest_y);
                    }
                    else if(dir.get(i)[3] != 0) {
                        addMove(blocks.get(i), dest_x + 1, dest_y - 1);
                        //System.out.println(i + " b " + dest_x + " " + dest_y);
                    }
                    else {
                        addMove(blocks.get(i), dest_x - 1, dest_y - 1);
                        addMove(blocks.get(i), dest_x + 1, dest_y - 1);
                        //System.out.println(i + " c " + dest_x + " " + dest_y);
                    }
                }
                else if(dir.get(i)[1] != 0) {
                    if(dir.get(i)[2] != 0) {
                        addMove(blocks.get(i), dest_x - 1, dest_y + 1);
                        //System.out.println(i + " d " + dest_x + " " + dest_y);
                    }
                    else if(dir.get(i)[3] != 0) {
                        addMove(blocks.get(i), dest_x + 1, dest_y + 1);
                        //System.out.println(i + " e " + dest_x + " " + dest_y);
                    }
                    else {
                        addMove(blocks.get(i), dest_x - 1, dest_y + 1);
                        addMove(blocks.get(i), dest_x + 1, dest_y + 1);
                        //System.out.println(i + " f " + dest_x + " " + dest_y);
                    }
                }
                else if(dir.get(i)[2] != 0) {
                    addMove(blocks.get(i), dest_x - 1, dest_y + 1);
                    addMove(blocks.get(i), dest_x - 1, dest_y - 1);
                    //System.out.println(i + " g " + dest_x + " " + dest_y);
                }
                else if(dir.get(i)[3] != 0) {
                    addMove(blocks.get(i), dest_x + 1, dest_y + 1);
                    addMove(blocks.get(i), dest_x + 1, dest_y - 1);
                    //System.out.println(i + " h " + dest_x + " " + dest_y);
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

        String str = "";
        for(int[] m : player.getValidMove()) {
            str += m[0] + " " + m[1] + " / ";
        }
        System.out.println(str);
    }

    /**
     * Validate move before placing the piece on board
     */
    public boolean executeMove() {
        if (!board.isEmptyForPiece(piece, originX, originY) || !isValidMove(player, piece, originX, originY)) {
            return false;
        }

        board.addPiece(player, piece, originX, originY);

        for(int j = 0; j < player.getStock().getPieces().size(); j++) {
            if(player.getStock().getPieces().get(j).getName().equals(piece.getName())) {
                player.getStock().getPieces().remove(j);
            }
        }

        getMove(piece.getBlocks(), originX, originY);
        return true;
    }

    /**
     * Prompts player to select and manipulate piece 
     */
    public ArrayList<Object> selectPiece(Scanner s) {
        if (player.getStock().getPieces().size() == 0) {
            System.out.println("No more pieces left.");
        }
        System.out.println("Select a piece");
        String tmp = s.useDelimiter("\\n").nextLine();
        
        while(true) {
            for (Piece p : player.getStock().getPieces()) {
                if (p.getName().equals(tmp)) {
                    Piece pCopy = new Piece(p);
                    return new ArrayList<>(Arrays.asList(pCopy, pCopy.manipulation(s, player.getColor())));                                
                }
            }
            System.out.println("Piece not in stock.\n Select a piece");
            tmp = s.useDelimiter("\\n").nextLine();
        }
    }

    /**
     * Prompts player to enter the coordinates
     * @param arr Coordinates entered by player
     * @return an integer arraylist that stored the coordinates
     */
    public ArrayList<Integer> selectSquare(ArrayList<String> arr) {
        ArrayList<Integer> coord = new ArrayList<>();

        coord.add(Integer.parseInt(arr.get(0)));
        coord.add(Integer.parseInt(arr.get(1)));

        return coord;
    }
}
