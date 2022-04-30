package ApplePlus;

import SimpleBot.SimpleBotPlayer;
import model.Board;
import model.Gamepiece;
import model.Location;
import model.Move;

import java.util.ArrayList;

public class ApplePlusBot extends SimpleBotPlayer {
    private int builderWeight = 1;
    private int blockerWeight = 1;
    private int bigPieceWeight = 1;
    ArrayList<Double> pointList = new ArrayList<>();
    private int index = 0;

    public ApplePlusBot(int playerno) {
        super(playerno);
    }

    @Override
    public Move makeMove(Board board) {
        if (isFirstMove) {
            isFirstMove = false;
            // Play gamepiece "F" at the starting location in default orientation
            return new Move(
                    this,
                    new Gamepiece(getGamepieceSet().get("F")),
                    "F",
                    new Location(Board.startLocations[getPlayerNo()]));
        } else {
            ArrayList<Move> moves = getPlayerMoves(this, board);
            return getOptimalMove(moves);
        }
    }

    /**
     * Iterates through bot's moves and gets the best move
     *
     * @param moves the bot's available moves
     * @return optimal move
     */
    private Move getOptimalMove(ArrayList<Move> moves) {
        /*double maxPoints = -1000;
        Move optimalMove = null;
        for (Move move : moves) {
            double points = gradeMove(move, moves.size());
            if (maxPoints < points) {
                //System.out.println(moves.size());
                maxPoints = points;
                optimalMove = move;
            }
        }*/

        for (Move move : moves) {
            pointList.add(gradeMove(move, moves.size()));
        }

        double maxPoints = -1000;
        int n = 0;
        for (int i = 0; i < moves.size(); i++) {
            double point = minimax(0, 3, Double.MIN_VALUE, Double.MAX_VALUE, true);
            if (maxPoints < point) {
                //System.out.println(moves.size());
                maxPoints = point;
                n = i;
            }
        }

        System.out.println(index + " " + n);
        return moves.get(index);
    }

    /**
     * Calculates how good the move is and assigns it points based on how good the move is.
     *
     * @param move move to be graded
     * @return the points the move got
     */
    public double gradeMove(Move move, int numMoves, Board b) {
        return builder(move, numMoves, b) * builderWeight + blocker(move, b) * blockerWeight + bigPiece(move) * bigPieceWeight;
    }
    
    private double bigPiece(Move move) {
        return move.getGamepiece().getLocations().length / this.getGamepieceSet().getPieces().size();
    }

    /**
     * Grades move based on builder
     * Not completed
     *
     * @param move
     * @return
     */
    private int builder(Move move, int numMovesBefore, Board b) {
        Board possibleBoard = new Board(b);
        possibleBoard.makeMove(move);

        getGamepieceSet().remove(move.getGamepieceName());
        int numMovesAfter = getPlayerMoves(this, possibleBoard).size();
        getGamepieceSet().getPieces().put(move.getGamepieceName(), move.getGamepiece());

        return numMovesAfter - numMovesBefore;
    }

    private int blocker(Move move, Board b) {
        Board possibleBoard = new Board(b);
        possibleBoard.makeMove(move);

        int before = getPlayerMoves(this.opponent, b).size();
        int after = getPlayerMoves(this.opponent, possibleBoard).size();

        return before - after;
    }

    

    private double minimax(int position, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return pointList.get(position);
        }

        if (maximizingPlayer) {
            double max = Double.MIN_VALUE;

            for (int i = 0; i < 2; i++) {
                double value = minimax(position * 2 + i, depth - 1, alpha, beta, false);
                max = Math.max(max, value);
                alpha = Math.max(alpha, max);
                if (max == value) {
                    index = position;
                }
                if (beta <= alpha) {
                    break;
                }
            }

            return max;

        } else {
            double min = Double.MAX_VALUE;

            for (int i = 0; i < 2; i++) {
                double value = minimax(position * 2 + i, depth - 1, alpha, beta, true);
                min = Math.min(min, value);
                if (min == value) {
                    index = position;
                }

                beta = Math.min(beta, min);

                if (beta <= alpha) {
                    break;
                }
            }

            return min;
        }
    }
}
