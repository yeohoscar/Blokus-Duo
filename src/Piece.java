import java.util.*;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Piece class
 *  - represents Blokus piece
 *  - holds piece name and blocks that make up the piece
 */

public class Piece {
    private String name;
    private ArrayList<Block> blocks;

    public Piece(String name, ArrayList<Block> blocks) {
        this.name = name;
        this.blocks = blocks;
    }

    public Piece(Piece p) {
        this.name = p.getName();
        this.blocks = new ArrayList<Block>();
        for (Block b : p.getBlocks()) {
            this.blocks.add(new Block(b));
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void rotatePieceClockwise() {
        int count = getBlocks().size() - 1;

        while(count >= 0) {
            if (getBlocks().get(count).getX() != 0) {
                getBlocks().get(count).invertX();
                swap(count);
            }
            else {
                swap(count);
            }

            count--;
        }
    }

    private void swap(int index) {
        int tmp = blocks.get(index).getX();
        blocks.get(index).setX(blocks.get(index).getY());
        blocks.get(index).setY(tmp);
    }

    public void flipPiece() {
        int count = getBlocks().size() - 1;

        while (count >= 0) {
            if (blocks.get(count).getX() != 0) {
                blocks.get(count).invertX();
            }
            count--;
        }
    }

    public void printPiece(String colour) {
        // store piece's info into a 2D array
        int[][] displayPiece = new int[9][9];
        for (int i = 0; i < displayPiece.length; i++) {
            for (int j = 0 ; j < displayPiece[0].length; j++) {
                displayPiece[i][j] = 0;
            }
        }
    
        for (Block block : blocks) {
            int x = block.getX();
            int y = block.getY();
            displayPiece[4-y][4+x] = 1;
        }
    
        // print the piece via 2D array
        for (int[] line: displayPiece) {
            if (allElementsTheSame(line)) {
                continue;
            }
            for ( int val: line) {
                if (val == 1) System.out.print(colour);
                    else System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static boolean allElementsTheSame(int[] array) {
        if (array.length == 0) {
            return true;
        } else {
            int first = array[0];
            for (int element : array) {
                if (element != first) {
                    return false;
                }
            }
            return true;
        }
    }

    /*public ArrayList<Integer> manipulation(Scanner s, String color) {
        printPiece(color);
        while (true) {
            System.out.println("Enter 'r' to rotate, 'f' to flip, or 'p' to place the gamepiece:");
            String[] instruct = s.useDelimiter("\\n").nextLine().split("");
            int indexOfp = 0;
            for (int index = 0; index < instruct.length; index++) {
                if (Objects.equals(instruct[index], "p")) {
                    indexOfp = index;
                    break;
                } else {
                    switch (instruct[index]) {
                        case " ":
                            printPiece(color);
                            break;
                        case "r":
                            rotatePieceClockwise();
                            break;
                        case "f":
                            flipPiece();
                            break;
                        default:
                            System.out.println("Invalid instruction");
                    }
                }
            }
            try {
                return new ArrayList<Integer>(Arrays.asList(Integer.parseInt(instruct[indexOfp+2]), Integer.parseInt(instruct[indexOfp+4])));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Coordinates not provided.");
            }
        }
    }*/
    /*public ArrayList<Integer> manipulation(Scanner s, String color) {
        printPiece(color);
        while (true) {
            System.out.println("Enter 'r' to rotate, 'f' to flip, or 'p' to place the gamepiece:");
            String[] instruct = s.useDelimiter("\\n").nextLine().split(" ");
            switch (instruct[0]) {
                case "r":
                    rotatePieceClockwise();
                    printPiece(color);
                    break;
                case "f":
                    flipPiece();
                    printPiece(color);
                    break;
                case "p":
                    try {
                        return new ArrayList<Integer>(Arrays.asList(Integer.parseInt(instruct[1]), Integer.parseInt(instruct[2])));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Coordinates not provided.");
                        break; 
                    }
                default:
                    System.out.println("Invalid instruction");
            }
        }
    }*/

    public ArrayList<Integer> manipulation(Scanner s, String color) {
        printPiece(color);
        while (true) {
            System.out.println("Enter 'r' to rotate, 'f' to flip, or 'p' to place the gamepiece:");
            String[] instruct = s.useDelimiter("\\n").nextLine().split(" ");
            int indexOfp = 0;
            for (int index = 0; index < instruct.length; index++) {
                if (Objects.equals(instruct[index], "p")) {
                    indexOfp = index;
                    try {
                        return new ArrayList<>(Arrays.asList(Integer.parseInt(instruct[indexOfp+1]), Integer.parseInt(instruct[indexOfp+2])));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Coordinates not provided.");
                    }
                    break;
                } else {
                    switch (instruct[index]) {
                        case "r":
                            rotatePieceClockwise();
                            printPiece(color);
                            break;
                        case "f":
                            flipPiece();
                            printPiece(color);
                            break;
                        default:
                            System.out.println("Invalid instruction");
                    }
                }
            }
        }
    }

    
}
 