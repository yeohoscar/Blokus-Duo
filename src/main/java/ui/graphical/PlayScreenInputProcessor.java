package ui.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import model.Board;

public class PlayScreenInputProcessor extends InputAdapter {
    public final static String CLICK_AND_DRAG_MESSAGE = "Click and drag the gamepiece.";
    public final static String FLIP_OR_ROTATE_MESSAGE = "Press 'f' to flip, or 'r' to rotate the gamepiece.";

    BlokusGame blokusGame;
    PlayScreen playScreen;
    int currentPlayerNo;
    GraphicalGamepiece selectedPiece;
    GraphicalBoard graphicalBoard;

    public PlayScreenInputProcessor(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
        playScreen = blokusGame.playScreen;
        graphicalBoard = playScreen.graphicalBoard;
        this.selectedPiece = null;
        currentPlayerNo = Board.X;
    }

    public void setCurrentPlayerNo(int playerNo) {
        currentPlayerNo = playerNo;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean result = false;
        if (button == Input.Buttons.LEFT) {
            Vector3 coord = unprojectScreenCoordinates(Gdx.input.getX(), Gdx.input.getY());
            for (GraphicalGamepiece p : playScreen.getGraphicalGamepiece()) {
                if (p.isHit(coord.x, coord.y)) {
                    selectedPiece = p;
                    playScreen.setBannerText(FLIP_OR_ROTATE_MESSAGE);
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
            selectedPiece = null;
            playScreen.setBannerText(CLICK_AND_DRAG_MESSAGE);
            // I could potentially do more stuff here ;)
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
                    blokusGame.uiStream.println("f");
                    result = true;
                }
                case Input.Keys.R -> {
                    blokusGame.uiStream.println("r");
                    result = true;
                }
            }
        }
        return result;
    }

}
