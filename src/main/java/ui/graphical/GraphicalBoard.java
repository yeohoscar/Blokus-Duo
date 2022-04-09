package ui.graphical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.Board;
import model.piece.Piece;

public class GraphicalBoard {
    float boardX;
    float boardY;
    float boardWidth;
    float boardHeight;
    float cellWidth;
    float cellHeight;
    TextureRegion blackSquare;
    TextureRegion whiteSquare;
    Board board;

    public GraphicalBoard(float boardX, float boardY, float boardWidth, float boardHeight, TextureRegion blackSquare, TextureRegion whiteSquare, Board board) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.blackSquare = blackSquare;
        this.whiteSquare = whiteSquare;
        this.board = board;
        cellHeight = boardHeight / Board.HEIGHT;
        cellWidth = boardWidth / Board.WIDTH;
    }

    public void updateBoard(Board board) {
        this.board = new Board(board);
    }

    public void draw(SpriteBatch batch) {
        for (int y = 0; y < Board.HEIGHT; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                if (board.isOccupied(x, y)) {
                    TextureRegion picture;
                    picture = (board.getColorOnSquare(x, y) == "X") ? blackSquare : whiteSquare;
                    batch.draw(
                            picture,
                            boardX + x * cellWidth + cellWidth * 0.2f,
                            boardY + y * cellHeight  + cellHeight * 0.1f
                    );
                }
            }
        }
    }

    public int getBoardColumn(float x) {
        int result = -1;
        if ((boardX < x) && ((boardX + boardWidth) > x)) {
            result = (int)((x - boardX) / cellWidth);
        }
        return result;
    }

    public int getBoardRow(float y) {
        int result = -1;
        if ((boardY < y) && ((boardY + boardHeight) > y)) {
            result = (int)((y - boardY) / cellHeight);
        }
        return result;
    }

    public boolean isHit(float x, float y) {
        return (getBoardColumn(x) != -1) && (getBoardRow(y) != -1);
    }

    /* take in xcoor, y coor and piece and called is emptyforpiece */
    public boolean isOccupied(int boardColumn, int boardRow) {
        return board.isOccupied(boardColumn, boardRow);
    }

    public boolean isEmptyForPiece(Piece piece, int dest_x, int dest_y) {
        return board.isEmptyForPiece(piece, dest_x, dest_y);
    }
}
