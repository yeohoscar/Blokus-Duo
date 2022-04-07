package ui.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import controller.GameControl;

public class PlayScreenInputProcessor extends InputAdapter {
    public final static String CLICK_AND_DRAG_MESSAGE = "Click and drag the gamepiece.";
    public final static String FLIP_OR_ROTATE_MESSAGE = "Press 'f' to flip, or 'r' to rotate the gamepiece.";

    BlokusGame blokusGame;
    PlayScreen playScreen;
    int currentPlayer;
    GraphicalGamepiece selectedPiece;

    public PlayScreenInputProcessor(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
        playScreen = blokusGame.playScreen;
        this.selectedPiece = null;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean eventHandled = false;
        if (button == Input.Buttons.LEFT) {
            Vector3 coord = unprojectScreenCoordinates(Gdx.input.getX(), Gdx.input.getY());
            GraphicalGamepiece gamepiece = playScreen.getGraphicalGamepiece();
            if (gamepiece.isHit(coord.x, coord.y)) {
                selectedPiece = gamepiece;
                playScreen.setBannerText(FLIP_OR_ROTATE_MESSAGE);
                eventHandled = true;
            }
        }
        return eventHandled;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        boolean eventHandled = false;
        if (null != selectedPiece) {
            selectedPiece = null;
            playScreen.setBannerText(CLICK_AND_DRAG_MESSAGE);
            // I could potentially do more stuff here ;)
        }
        return eventHandled;
    }

    /*@Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        boolean eventHandled = false;
        if (null != selectedPiece) {
            Vector3 coord = unprojectScreenCoordinates(screenX, screenY);
            selectedPiece.setPosition(coord.x,coord.y);
            eventHandled = true;
        }
        return eventHandled;
    }*/

    Vector3 unprojectScreenCoordinates(int x, int y) {
        Vector3 screenCoordinates = new Vector3(x, y, 0);
        Vector3 worldCoordinates = playScreen.getCamera().unproject(screenCoordinates);
        return worldCoordinates;
    }

    @Override
    public boolean keyDown (int keycode) {
        boolean eventHandled = false;
        if (null != selectedPiece) {
            switch(keycode) {
                case Input.Keys.F:
                    selectedPiece.flipPiece();
                    eventHandled = true;
                    break;
                case Input.Keys.R:
                    selectedPiece.rotatePiece();
                    eventHandled = true;
                    break;
            }
        }
        return eventHandled;
    }

}
