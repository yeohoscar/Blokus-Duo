package ui.graphical;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import controller.*;
import model.piece.*;

public class PlayScreenInputProcessor extends InputAdapter {
    BlokusGame blokusGame;
    PlayScreen playScreen;
    String currentPlayerColor;
    GraphicalGamepiece selectedPiece;
    Piece piece;
    GraphicalBoard graphicalBoard;
    State state;
    ArrayList<int[]> validMove;
    static int count = 2;

    public PlayScreenInputProcessor(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
        playScreen = blokusGame.playScreen;
        graphicalBoard = playScreen.graphicalBoard;
        this.selectedPiece = null;
        this.piece = null;
        currentPlayerColor = "X";
        validMove = new ArrayList<int[]>();
        state = State.FIRST;
    }

    public String getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    public void setCurrentPlayerColor(String playerColor) {
        currentPlayerColor = playerColor;
    }

    public ArrayList<int[]> getValidMove() {
        return validMove;
    }

    public void setCurrentPlayerValidMove(ArrayList<int[]> validMove) {
        this.validMove = validMove;
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
                    playScreen.setBannerText(playScreen.FLIP_OR_ROTATE_MESSAGE);
                    result = true;
                    break;
                }
            }            
        }
        return result;
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
                    selectedPiece.setVisible(false);
                    playScreen.setBannerText(playScreen.CLICK_AND_DRAG_MESSAGE);
                } else {
                    selectedPiece.resetLocation();
                    playScreen.setBannerText("Invalid piece placement. " + playScreen.CLICK_AND_DRAG_MESSAGE);
                }

                if(count == 0) {
                    state = State.MIDGAME;
                }
            } else {
                if (graphicalBoard.isHit(coord.x, coord.y) && isValidMove(getCurrentPlayerColor(), piece, boardColumn, boardRow)) {
                    try {
                        blokusGame.pieceQueue.put(piece);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    blokusGame.uiStream.printf("%d %d\n", boardColumn, boardRow);
                    selectedPiece.setVisible(false);
                    playScreen.setBannerText(playScreen.CLICK_AND_DRAG_MESSAGE);
                } else {
                    selectedPiece.resetLocation();
                    playScreen.setBannerText("Invalid piece placement. " + playScreen.CLICK_AND_DRAG_MESSAGE);
                }
            }
            
            selectedPiece = null;
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

    private boolean isValidMove(String playerColor, Piece piece, int dest_x, int dest_y) {
        return (graphicalBoard.isEmptyForPiece(piece, dest_x, dest_y) && isPieceTouchEdge(playerColor, piece, dest_x, dest_y) && !graphicalBoard.isSide(playerColor, piece, dest_x, dest_y));
    }

    public boolean isPieceTouchEdge(String playerColor, Piece piece, int dest_x, int dest_y) {
        return piece.getBlocks().stream().anyMatch(offset -> isContain(playerColor, offset, dest_x, dest_y));
    }

    public boolean isContain(String playerColor, Block offset, int dest_x, int dest_y) {
        if (getCurrentPlayerColor().equals(playerColor)) {
            for(int[] m : getValidMove()) {
                if(m[0] == offset.getX() + dest_x && m[1] == offset.getY() + dest_y) {
                    return true;
                }
            }
        }
        return false;
    }

}
