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
    public Move getOptimalMove(ArrayList<Move> moves) {
        /*double maxPoints = -1000;
        Move optimalMove = null;
        for (Move move : moves) {
            double points = gradeMove(move);
            pointList.add(points);
            if (maxPoints < points) {
                //System.out.println(moves.size());
                maxPoints = points;
                optimalMove = move;
            }
        }*/

        for (Move move : moves) {
            pointList.add(gradeMove(move));
        }

        int index = minimax(0, 3, Double.MIN_VALUE, Double.MAX_VALUE, true);

        return moves.get(index);
    }

    /**
     * Calculates how good the move is and assigns it points based on how good the move is.
     *
     * @param move move to be graded
     * @return the points the move got
     */
    public double gradeMove(Move move) {
        return builder(move) * builderWeight + blocker(move) * blockerWeight + bigPiece(move) * bigPieceWeight;
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
    private int builder(Move move) {
        Board possibleBoard = new Board(board);
        possibleBoard.makeMove(move);

        int before = getPlayerMoves(this, board).size();
        getGamepieceSet().remove(move.getGamepieceName());
        int after = getPlayerMoves(this, possibleBoard).size();
        getGamepieceSet().getPieces().put(move.getGamepieceName(), move.getGamepiece());

        return after - before;
    }

    private int blocker(Move move) {
        Board possibleBoard = new Board(board);
        possibleBoard.makeMove(move);

        int before = getPlayerMoves(this.opponent, board).size();
        int after = getPlayerMoves(this.opponent, possibleBoard).size();

        return before - after;
    }

    private int minimax(int position, int depth, double alpha, double beta, boolean maximizingPlayer) {
        int idx = 0;
        if (depth == 0) {
            return position;
        }

        if (maximizingPlayer) {
            double max = Double.MIN_VALUE;

            for (int i = 0; i < 2; i++) {
                double value = minimax(position * 2 + i, depth - 1, alpha, beta, false);
                max = Math.max(max, value);
                if (max == value) {
                    idx = position;
                }

                alpha = Math.max(alpha, max);

                if (beta <= alpha) {
                    break;
                }
            }

            return idx;

        } else {
            double min = Double.MAX_VALUE;

            for (int i = 0; i < 2; i++) {
                double value = minimax(position * 2 + i, depth - 1, alpha, beta, true);
                min = Math.min(min, value);
                if (min == value) {
                    idx = position;
                }

                beta = Math.min(beta, min);

                if (beta <= alpha) {
                    break;
                }
            }

            return idx;
        }
    }
}
