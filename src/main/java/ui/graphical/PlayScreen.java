/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * PlayScreen class
 *  - represents play screen of graphical UI
 */

package ui.graphical;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import model.piece.*;

public class PlayScreen extends ScreenAdapter {

    public final static int GAME_WIDTH = 1024;
    public final static int GAME_HEIGHT = 768;
    public final static String CLICK_AND_DRAG_MESSAGE = "Click and drag the gamepiece.";
    public final static String FLIP_OR_ROTATE_MESSAGE = "Press 'f' to flip, or 'r' to rotate the gamepiece.";

    BlokusGame blokusGame;
    SpriteBatch batch;
    OrthographicCamera camera;
    TiledMap tiledMap;
    TiledMapRenderer mapRenderer;
    Skin skin;
    Stage stage;

    TextureRegion blackSquare;
    TextureRegion whiteSquare;
    BitmapFont font;
    String bannerText;
    float bannerX;
    float bannerY;

    String message;
    BitmapFont messageFont;
    float messageX;
    float messageY;
    float messageWidth;

    ArrayList<GraphicalGamepiece> pieces;
    GraphicalBoard graphicalBoard;

    public PlayScreen(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
        this.skin = blokusGame.skin;
        this.camera = blokusGame.camera;
        this.stage = blokusGame.stage;
        this.batch = blokusGame.batch;
        this.tiledMap = blokusGame.tiledMap;
        this.mapRenderer = blokusGame.renderer;

        TiledMapImageLayer imageLayer = (TiledMapImageLayer) tiledMap.getLayers().get(0);
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
        MapLayer objectLayer = tiledMap.getLayers().get(3);
        MapObjects objects = objectLayer.getObjects();
        
        font = skin.getFont("font1");
        bannerText = "";
        bannerX = 220.0f;
        bannerY = imageLayer.getTextureRegion().getRegionHeight() - (font.getCapHeight() * 0.8f);
        setBannerText(CLICK_AND_DRAG_MESSAGE);

        pieces = new ArrayList<GraphicalGamepiece>();
        blackSquare = skin.getRegion("game_square_black");
        whiteSquare = skin.getRegion("game_square_white");
        addGamePiece(pieces, objects, blackSquare, 0);
        addGamePiece(pieces, objects, whiteSquare, 1);

        MapObject boardLocation = objects.get("Board");
        float boardX = (float) boardLocation.getProperties().get("x");
        float boardY = (float) boardLocation.getProperties().get("y");
        float boardHeight = (float) boardLocation.getProperties().get("height");
        float boardWidth = (float) boardLocation.getProperties().get("width");
        graphicalBoard = new GraphicalBoard(boardX, boardY, boardWidth, boardHeight, blackSquare, whiteSquare);

        message = "";
        MapObject msgLocation = objects.get("Message");
        messageX = (float) msgLocation.getProperties().get("x");
        messageY = (float) msgLocation.getProperties().get("y");
        messageWidth = (float) msgLocation.getProperties().get("width");
        messageFont = skin.getFont("font1");
    }

    /**
     * Function to construct graphical gamepiece for each player and store in an arraylist
     * @param pieces arraylist of graphical gamepiece
     * @param objects get object from tiled map
     * @param square square to draw the pieces
     * @param player current player
     */
    private void addGamePiece(
        ArrayList<GraphicalGamepiece> pieces, 
        MapObjects objects, 
        TextureRegion square,
        int player
        ) {
            for (Piece p : new Stock().getPieces()) {
                String pieceName = player + p.getName();
                MapObject object = objects.get(pieceName);
                float gamepieceX = (float) object.getProperties().get("x");
                float gamepieceY = (float) object.getProperties().get("y");
                pieces.add(new GraphicalGamepiece(p, square, gamepieceX, gamepieceY, player));
            }
    } 

    /**
     * InputMultiplexer is added to combines multiple processors
     */
    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(blokusGame.playScreenInputProcessor);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.WHITE);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Draw the tiled map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Start the batch and draw the message, banner text, game pieces and board
        batch.begin();
        messageFont.draw(batch, message, messageX, messageY, messageWidth, Align.left, true);
        font.draw(batch, bannerText, bannerX, bannerY);
        for(GraphicalGamepiece p : pieces) {
            p.draw(batch);
        }
        graphicalBoard.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    /**
     * Set the message to show on screen
     * @param text string to be printed on screen
     */
    public void showMessage(String text) {
        message = text;
    }

    /**
     * Set the banner text to show on screen
     * @param text string to be printed on screen
     */
    public void setBannerText(String text) {
        bannerText = text;
    }

    public Camera getCamera() {
        return camera;
    }

    public ArrayList<GraphicalGamepiece> getGraphicalGamepieces() {
        return pieces;
    }
}