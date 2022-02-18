import java.util.ArrayList;


//karyen
public class Game {
    private final Player player;
    private final Board board;
    private final Piece piece;
    private final int originX;
    private final int originY;

    public Game(Player player, Board board, Piece piece, ArrayList<Integer> coord) {
        if (player == null || board == null || piece == null) {
            throw new IllegalArgumentException();
        }
        this.player = player;
        this.board = board;
        this.piece = piece;
        this.originX = coord.get(0);
        this.originY = coord.get(1); 
    }

    public String toString() {
        String s = "";
        for(int[] m : player.getValidMove()) {
            s += m[0] + " " + m[1] + "\n";
        }

        return s;
    }

    /*public boolean hasAvailablePiece() {    
    }*/

    public boolean isValidMove(Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().anyMatch(offset -> isContain(offset, dest_x, dest_y));
    }

    public boolean isContain(Block offset, int dest_x, int dest_y) {
        int[] input = new int[] {offset.getX() + dest_x, offset.getY() + dest_y};
        System.out.println(input[0] + " check1 " + input[1]);

        System.out.println(player.getValidMove().get(0)[0]);

        for(int[] m : player.getValidMove()) {
            System.out.println(m[0] + " m " + m[1]);
            if(m[0] == offset.getX() + dest_x && m[1] == offset.getY() + dest_y) {
                System.out.println("!!!");
                return true;
            }
        }
        System.out.println("no");

        return false;
    }

    public void addMove(Block offset, int dest_x, int dest_y) {
        int[] coor = new int[] {dest_x + offset.getX(), dest_y + offset.getY()};

        if(board.contains(dest_x + offset.getX(), dest_y + offset.getY())) {
            if(board.isEmptyAt(offset, dest_x, dest_y)) {
                player.getValidMove().add(coor);
                //System.out.print(dest_x + offset.getX());
                //System.out.print(" zzz ");
                //System.out.println(dest_y + offset.getY());
            }
        }
    }

    public void updateMove(int[] input, Block offset, int dest_x, int dest_y) {

        player.getValidMove().removeIf(n -> (n.equals(input)));

        addMove(offset, dest_x, dest_y);

        for(int i = 0; i < player.getValidMove().size(); i++) {
            if(board.isAtSide(player.getColor(), new Block(0, 0), player.getValidMove().get(i)[0], player.getValidMove().get(i)[1])) {
                player.getValidMove().remove(i);
            }
        }
    }

    public void getMove(ArrayList<Block> blocks, int dest_x, int dest_y) {
        ArrayList<int[]> dir = new ArrayList<int[]>();
        int[] input = new int[] {dest_x, dest_y};

        for(int i = 0; i < blocks.size(); i++) {
            if(board.isCornerPiece(blocks, i, dir)) {
                System.out.println("yes");
                if(dir.get(i)[0] != 0) {
                    if(dir.get(i)[2] != 0) {
                        updateMove(input, blocks.get(i), dest_x - 1, dest_y - 1);
                        System.out.println(i + " a " + dest_x + " " + dest_y);
                    }
                    else if(dir.get(i)[3] != 0) {
                        updateMove(input, blocks.get(i), dest_x + 1, dest_y - 1);
                        System.out.println(i + " b " + dest_x + " " + dest_y);
                    }
                    else {
                        updateMove(input, blocks.get(i), dest_x - 1, dest_y - 1);
                        updateMove(input, blocks.get(i), dest_x + 1, dest_y - 1);
                        System.out.println(i + " c " + dest_x + " " + dest_y);
                    }
                }
                else if(dir.get(i)[1] != 0) {
                    if(dir.get(i)[2] != 0) {
                        updateMove(input, blocks.get(i), dest_x - 1, dest_y + 1);
                        //System.out.println(i + " d " + dest_x + " " + dest_y);
                    }
                    else if(dir.get(i)[3] != 0) {
                        updateMove(input, blocks.get(i), dest_x + 1, dest_y + 1);
                        //System.out.println(i + " e " + dest_x + " " + dest_y);
                    }
                    else {
                        updateMove(input, blocks.get(i), dest_x - 1, dest_y + 1);
                        updateMove(input, blocks.get(i), dest_x + 1, dest_y + 1);
                        //System.out.println(i + " f " + dest_x + " " + dest_y);
                    }
                }
                else if(dir.get(i)[2] != 0) {
                    updateMove(input, blocks.get(i), dest_x - 1, dest_y + 1);
                    updateMove(input, blocks.get(i), dest_x - 1, dest_y - 1);
                    //System.out.println(i + " g " + dest_x + " " + dest_y);
                }
                else if(dir.get(i)[3] != 0) {
                    updateMove(input, blocks.get(i), dest_x + 1, dest_y + 1);
                    updateMove(input, blocks.get(i), dest_x + 1, dest_y - 1);
                    //System.out.println(i + " h " + dest_x + " " + dest_y);
                }
                else {
                    updateMove(input, blocks.get(i), dest_x + 1, dest_y + 1);
                    updateMove(input, blocks.get(i), dest_x + 1, dest_y - 1);
                    updateMove(input, blocks.get(i), dest_x - 1, dest_y + 1);
                    updateMove(input, blocks.get(i), dest_x - 1, dest_y - 1);
                    //move.add(new int[] {dest_x + block.get(i)[0] + 1, dest_y + block.get(i)[1] + 1});
                    //move.add(new int[] {dest_x + block.get(i)[0] + 1, dest_y + block.get(i)[1] - 1});
                    //move.add(new int[] {dest_x + block.get(i)[0] - 1, dest_y + block.get(i)[1] + 1});
                    //move.add(new int[] {dest_x + block.get(i)[0] - 1, dest_y + block.get(i)[1] - 1});
                }
            }
            else {
                System.out.println("no");
            }
        }

        String s = "";
        for(int[] m : player.getValidMove()) {
            s += m[0] + " " + m[1] + "\n";
        }
        System.out.println(s);
    }

    public boolean executeMove() {
        if (!board.isEmptyForPiece(piece, originX, originY)  || board.isSide(player, piece, originX, originY) || isValidMove(piece, originX, originY)) {
            return false;
        }

        board.addPiece(player, piece, originX, originY);
        player.getStock().getPieces().remove(piece);
        getMove(piece.getBlocks(), originX, originY);
        return true;
    }
}
