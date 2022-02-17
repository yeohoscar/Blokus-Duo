import java.util.ArrayList;

public class Game {
    private final Player player;
    private final Board board;

    public Game(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    public String toString() {
        String s = "";
        for(int[] m : player.getValidMove()) {
            s += m[0] + " " + m[1] + "\n";
        }

        return s;
    }

    public boolean isValidMove(Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().anyMatch(offset -> isContain(offset, dest_x, dest_y));
    }

    public boolean isContain(int[] offset, int dest_x, int dest_y) {
        int[] input = new int[] {offset[0] + dest_x, offset[1] + dest_y};
        System.out.println(input[0] + " check " + input[1]);

        for(int[] m : player.getValidMove()) {
            System.out.println(m[0] + " m " + m[1]);
            if(m[0] == offset[0] + dest_x && m[1] == offset[1] + dest_y) {
                System.out.println("!!!");
                return true;
            }
        }

        return false;
    }

    public void addMove(int[] offset, int dest_x, int dest_y) {
        int[] coor = new int[] {dest_x + offset[0], dest_y + offset[1]};

        if(board.contains(dest_x + offset[0], dest_y + offset[1])) {
            if(board.isEmptyAt(offset, dest_x, dest_y)) {
                // Need to check? Because it will remove all from line 42
                if(!player.getValidMove().contains(coor)) {
                    player.getValidMove().add(coor);
                }
                //System.out.print(dest_x + offset[0]);
                //System.out.print(" zzz ");
                //System.out.println(dest_y + offset[1]);
            }
        }
    }

    public void updateMove(int[] input, int[] offset, int dest_x, int dest_y) {
        int[] zero = new int[] {0, 0};

        player.getValidMove().removeIf(n -> (n.equals(input)));

        addMove(offset, dest_x, dest_y);

        for(int i = 0; i < player.getValidMove().size(); i++) {
            if(board.isAtSide(player.getColor(), zero, player.getValidMove().get(i)[0], player.getValidMove().get(i)[1])) {
                player.getValidMove().remove(i);
            }
        }
    }

    public void getMove(ArrayList<int[]> block, int dest_x, int dest_y) {
        ArrayList<int[]> dir = new ArrayList<int[]>();
        int[] input = new int[] {dest_x, dest_y};

        for(int i = 0; i < block.size(); i++) {
            if(board.isCornerPiece(block, i, dir)) {
                System.out.println("yes");
                if(dir.get(i)[0] != 0) {
                    if(dir.get(i)[2] != 0) {
                        updateMove(input, block.get(i), dest_x - 1, dest_y - 1);
                        System.out.println(i + " a " + dest_x + " " + dest_y);
                        //move.add(new int[] {dest_x + block.get(i)[0] - 1, dest_y + block.get(i)[1] - 1});
                    }
                    else if(dir.get(i)[3] != 0) {
                        updateMove(input, block.get(i), dest_x + 1, dest_y - 1);
                        System.out.println(i + " b " + dest_x + " " + dest_y);
                        //move.add(new int[] {dest_x + block.get(i)[0] + 1, dest_y + block.get(i)[1] - 1});
                    }
                    else {
                        updateMove(input, block.get(i), dest_x - 1, dest_y - 1);
                        updateMove(input, block.get(i), dest_x + 1, dest_y - 1);
                        System.out.println(i + " c " + dest_x + " " + dest_y);
                        //move.add(new int[] {dest_x + block.get(i)[0] - 1, dest_y + block.get(i)[1] - 1});
                        //move.add(new int[] {dest_x + block.get(i)[0] + 1, dest_y + block.get(i)[1] - 1});
                    }
                }
                else if(dir.get(i)[1] != 0) {
                    if(dir.get(i)[2] != 0) {
                        updateMove(input, block.get(i), dest_x - 1, dest_y + 1);
                        //System.out.println(i + " d " + dest_x + " " + dest_y);
                        //move.add(new int[] {dest_x + block.get(i)[0] - 1, dest_y + block.get(i)[1] + 1});
                    }
                    else if(dir.get(i)[3] != 0) {
                        updateMove(input, block.get(i), dest_x + 1, dest_y + 1);
                        //System.out.println(i + " e " + dest_x + " " + dest_y);
                        //move.add(new int[] {dest_x + block.get(i)[0] + 1, dest_y + block.get(i)[1] + 1});
                    }
                    else {
                        updateMove(input, block.get(i), dest_x - 1, dest_y + 1);
                        updateMove(input, block.get(i), dest_x + 1, dest_y + 1);
                        //System.out.println(i + " f " + dest_x + " " + dest_y);
                        //move.add(new int[] {dest_x + block.get(i)[0] - 1, dest_y + block.get(i)[1] + 1});
                       // move.add(new int[] {dest_x + block.get(i)[0] + 1, dest_y + block.get(i)[1] + 1});
                    }
                }
                else if(dir.get(i)[2] != 0) {
                    updateMove(input, block.get(i), dest_x - 1, dest_y + 1);
                    updateMove(input, block.get(i), dest_x - 1, dest_y - 1);
                    //System.out.println(i + " g " + dest_x + " " + dest_y);
                    //move.add(new int[] {dest_x + block.get(i)[0] - 1, dest_y + block.get(i)[1] + 1});
                    //move.add(new int[] {dest_x + block.get(i)[0] - 1, dest_y + block.get(i)[1] - 1});
                }
                else if(dir.get(i)[3] != 0) {
                    updateMove(input, block.get(i), dest_x + 1, dest_y + 1);
                    updateMove(input, block.get(i), dest_x + 1, dest_y - 1);
                    //System.out.println(i + " h " + dest_x + " " + dest_y);
                    //move.add(new int[] {dest_x + block.get(i)[0] + 1, dest_y + block.get(i)[1] + 1});
                    //move.add(new int[] {dest_x + block.get(i)[0] + 1, dest_y + block.get(i)[1] - 1});
                }
                else {
                    updateMove(input, block.get(i), dest_x + 1, dest_y + 1);
                    updateMove(input, block.get(i), dest_x + 1, dest_y - 1);
                    updateMove(input, block.get(i), dest_x - 1, dest_y + 1);
                    updateMove(input, block.get(i), dest_x - 1, dest_y - 1);
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
}
