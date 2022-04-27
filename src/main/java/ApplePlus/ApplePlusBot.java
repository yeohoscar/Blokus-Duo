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
}
