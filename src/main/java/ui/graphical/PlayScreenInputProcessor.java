package ui.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import controller.*;
import model.Board;
import model.piece.*;

public class PlayScreenInputProcessor extends InputAdapter {
    public final static String CLICK_AND_DRAG_MESSAGE = "Click and drag the gamepiece.";
    public final static String FLIP_OR_ROTATE_MESSAGE = "Press 'f' to flip, or 'r' to rotate the gamepiece.";

    BlokusGame blokusGame;
    PlayScreen playScreen;
    String currentPlayerColor;
    GraphicalGamepiece selectedPiece;
    Piece piece;
    GraphicalBoard graphicalBoard;
    State state;
    static int count = 2;

    public PlayScreenInputProcessor(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
        playScreen = blokusGame.playScreen;
        graphicalBoard = playScreen.graphicalBoard;
        this.selectedPiece = null;
        this.piece = null;
        currentPlayerColor = "X";
        state = State.FIRST;
    }

    public void setCurrentPlayerNo(String playerColor) {
        currentPlayerColor = playerColor;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean result = false;
        if (null != selectedPiece) {
            selectedPiece.resetLocation();
            selectedPiece = null;
        }
        if (button == Input.Buttons.LEFT) {
            Vector3 coord = unprojectScreenCoordinates(Gdx.input.getX(), Gdx.input.getY());
            for (GraphicalGamepiece p : playScreen.getGraphicalGamepieces()) {
                if (p.getPlayerColor() == currentPlayerColor && p.isHit(coord.x, coord.y)) {
                    selectedPiece = p;
                    playScreen.setBannerText(FLIP_OR_ROTATE_MESSAGE);
                    result = true;
                    break;
                }
            }            
        }
        return result;
    }

    private boolean isValidFirstMove(Piece piece, int originX, int originY) {
        return piece.getBlocks().stream().anyMatch(offset -> isOnFirstMoveSquare(offset, originX, originY));
    }

    private boolean isOnFirstMoveSquare(Block offset, int originX, int originY) {
        if (currentPlayerColor.equals("O")) {
            return (offset.getX() + originX == 9 && offset.getY() + originY == 4);
        } else {
            return (offset.getX() + originX == 4 && offset.getY() + originY == 9);
        }
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        boolean result = false;
        if (null != selectedPiece) {
            piece = selectedPiece.getGamePiece();
            Vector3 coord = unprojectScreenCoordinates(screenX, screenY);
            int boardColumn = graphicalBoard.getBoardColumn(coord.x);
            int boardRow = graphicalBoard.getBoardRow(coord.y);

            if(state == State.FIRST) {
                if (isValidFirstMove(piece, boardColumn, boardRow) && graphicalBoard.isHit(coord.x, coord.y)) {
                    try {
                        blokusGame.pieceQueue.put(piece);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    blokusGame.uiStream.printf("%d %d\n", boardColumn, boardRow);
                    count -= 1;
                    System.out.println(boardColumn + " " + boardRow + " " + count);
                    selectedPiece.setVisible(false);
                } else {
                    selectedPiece.resetLocation();
                }

                if(count == 0) {
                    state = State.MIDGAME;
                }
            } else {
                System.out.println("B\n");
                if (graphicalBoard.isHit(coord.x, coord.y) && graphicalBoard.isEmptyForPiece(piece, boardColumn, boardRow)) {
                    try {
                        blokusGame.pieceQueue.put(piece);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    blokusGame.uiStream.printf("%d %d\n", boardColumn, boardRow);
                    System.out.println(boardColumn + " " + boardRow);
                    selectedPiece.setVisible(false);
                    for (GraphicalGamepiece p : playScreen.getGraphicalGamepieces()) {
                        if (p.equals(selectedPiece)) {
                            selectedPiece.setVisible(true);;
                        } 
                    }
                } else {
                    selectedPiece.resetLocation();
                }
            }
            
            selectedPiece = null;
            playScreen.setBannerText(CLICK_AND_DRAG_MESSAGE);
        }
        return result;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        boolean result = false;
        if (null != selectedPiece) {
            Vector3 coord = unprojectScreenCoordinates(screenX, screenY);
            selectedPiece.setPosition(coord.x,coord.y);
            result = true;
        }
        return result;
    }

    Vector3 unprojectScreenCoordinates(int x, int y) {
        Vector3 screenCoordinates = new Vector3(x, y, 0);
        Vector3 worldCoordinates = playScreen.getCamera().unproject(screenCoordinates);
        return worldCoordinates;
    }

    @Override
    public boolean keyDown (int keycode) {
        boolean result = false;
        if (null != selectedPiece) {
            switch (keycode) {
                case Input.Keys.F -> {
                    selectedPiece.flipPiece();
                    result = true;
                }
                case Input.Keys.R -> {
                    selectedPiece.rotatePiece();
                    result = true;
                }
            }
        }
        return result;
    }

}
