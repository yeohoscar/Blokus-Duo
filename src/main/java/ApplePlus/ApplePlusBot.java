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
        int maxPoints = 0;
        Move optimalMove = null;
        for (Move move : moves) {
            int points = gradeMove(move);
            if (maxPoints < points) {
                maxPoints = points;
                optimalMove = move;
            }
        }
        return optimalMove;
    }

    /**
     * Calculates how good the move is and assigns it points based on how good the move is.
     *
     * @param move move to be graded
     * @return the points the move got
     */
    public int gradeMove(Move move) {
        return builder(move) * builderWeight + blocker(move) * blockerWeight + bigPiece(move) * bigPieceWeight;
    }
    
    private int bigPiece(Move move) {
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
}
