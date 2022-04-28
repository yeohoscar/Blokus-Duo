package ApplePlus;

import SimpleBot.SimpleBotPlayer;
import model.Board;
import model.Move;

import java.util.ArrayList;

public class ApplePlusBot extends SimpleBotPlayer {
    public ApplePlusBot(int playerno) {
        super(playerno);
    }

    @Override
    public Move makeMove(Board board) {
        ArrayList<Move> moves = getPlayerMoves(this, board);
        Board possibleBoard  = new Board(board);

        possibleBoard.makeMove(moves.get(0));

        return super.makeMove(board);
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
        return //Big Piece Points * weight based on stage of game
               //+ Builder * weight based on optimisation through play testing
               //+ Blocker * weight based on optimisation through play testing
               //+ NumMoves through later boards * weight based on optimisation through play testing
    }
    
    private int bigPiece() {

    }

    private int builder(Move move) {
        Board possibleBoard = new Board(board);
        possibleBoard.makeMove(move);
    }
}
