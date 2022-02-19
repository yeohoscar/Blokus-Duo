import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


//karyen
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
     * 
     */
    @Override
    public String toString() {
        String s = "";
        for(int[] m : player.getValidMove()) {
            s += m[0] + " " + m[1] + "\n";
        }

        return s;
    }

    public boolean isValidMove(Player player, Piece piece, int dest_x, int dest_y) {
        if(!isAtCorner(piece, dest_x, dest_y) || board.isSide(player, piece, dest_x, dest_y)) {
            return false;
        }
        return true;
    }

    public int checkEveryPiece(Piece p, int x, int y) {
        if(!isValidMove(player, p, x, y)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public boolean hasAvailablePiece(int[] move) { 
        int flag = 0;
        int count = 0;

        for(Piece piece : player.getStock().getPieces()) {
            Piece pCopy = piece;
            switch(piece.getName()) {
                case "I1":
                case "O4":
                case "X5":
                    System.out.println("skip");
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
        }

        if(flag == 2 || flag == 7) {
            count++;
        }

        if(count == player.getStock().getPieces().size()) {
            return false;
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
    public boolean isAtCorner(Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().anyMatch(offset -> isContain(offset, dest_x, dest_y));
    }

    /**
     * 
     * @param offset
     * @param dest_x
     * @param dest_y
     * @return
     */
    public boolean isContain(Block offset, int dest_x, int dest_y) {
        //System.out.println(input[0] + " check1 " + input[1]);
        for(int[] m : player.getValidMove()) {
            //System.out.println(m[0] + " m " + m[1]);
            if(m[0] == offset.getX() + dest_x && m[1] == offset.getY() + dest_y) {
                return true;
            }
        }
        System.out.println("no");

        return false;
    }

    /**
     * 
     * @param offset
     * @param dest_x
     * @param dest_y
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
     * 
     * @param input
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

    public ArrayList<Integer> selectSquare(ArrayList<String> arr) {
        ArrayList<Integer> coord = new ArrayList<>();

        coord.add(Integer.parseInt(arr.get(0)));
        coord.add(Integer.parseInt(arr.get(1)));

        return coord;
    }
}
