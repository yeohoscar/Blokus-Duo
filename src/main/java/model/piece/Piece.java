package model.piece;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Piece class
 *  - represents Blokus piece
 *  - holds piece name and blocks that make up the piece
 *  - enable users to manipulate the piece
 */

import java.util.*;

public class Piece {
    private final String name;
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

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = new ArrayList<>();
        for (Block b : blocks) {
            this.blocks.add(new Block(b));
        }
    }

    /**
     * Rotate the selected piece 90 degrees clockwise around the reference point
     */
    public void rotatePieceClockwise() {
        int count = getBlocks().size() - 1;

        while(count >= 0) {
            if (getBlocks().get(count).getX() != 0) {
                getBlocks().get(count).invertX();
            }
            swap(count);
            count--;
        }
    }

    /**
     * Swap the values of x and y
     *
     * @param index index of the block of the piece
     */
    private void swap(int index) {
        int tmp = blocks.get(index).getX();
        blocks.get(index).setX(blocks.get(index).getY());
        blocks.get(index).setY(tmp);
    }

    /**
     * Flip the selected piece along the vertical axis going through the reference point
     */
    public void flipPiece() {
        int count = getBlocks().size() - 1;

        while (count >= 0) {
            if (blocks.get(count).getX() != 0) {
                blocks.get(count).invertX();
            }
            count--;
        }
    }

    /**
     * Print gamepiece via a 2D array
     *
     * @param colour color of current player: 'X' or 'O'
     */
    public void printPiece(String colour) {
        // store piece's blocks into
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
    
        // print the piece
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

    /**
     * Check if all elements of the integer array are same
     *
     * @param array array needs to be checked
     * @return true if all elements are same
     */
    public static boolean allElementsTheSame(int[] array) {
        if (array.length != 0) {
            int first = array[0];
            for (int element : array) {
                if (element != first) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Manipulate the piece by specifying a single-letter command from users Until receive 'place' command
     *
     * @param s the user input
     * @param color color of current player for printing pieces
     * @return an arraylist of piece's info : properties and coordinate
     */
    public ArrayList<Integer> manipulation(Scanner s, String color) {
        printPiece(color);
        while (true) {
            System.out.println("Enter 'r' to rotate, 'f' to flip, or 'p' to place the gamepiece:");
            String[] instruct = s.nextLine().split(" ");
            int indexOfp; // to record the index of 'p' command

            if(instruct.length == 1 && instruct[0].equals("")) {
                instruct = s.useDelimiter(" |\\n|\\r").nextLine().split(" ");
            }

            for (int index = 0; index < instruct.length; index++) {
                if (Objects.equals(instruct[index], "p")) {
                    indexOfp = index;
                    try {
                        return new ArrayList<>(Arrays.asList(Integer.parseInt(instruct[indexOfp+1]), Integer.parseInt(instruct[indexOfp+2])));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid coordinates provided.");
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
                        case "":
                            break;
                        default:
                            System.out.println("Invalid instruction");
                    }
                }
            }
        }
    }
}
 