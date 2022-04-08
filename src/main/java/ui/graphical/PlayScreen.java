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
import com.badlogic.gdx.utils.ScreenUtils;

import model.Board;
import model.piece.*;

public class PlayScreen extends ScreenAdapter {

    public final static int GAME_WIDTH = 1024;
    public final static int GAME_HEIGHT = 812;
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

    ArrayList<GraphicalGamepiece> pieces;
    Piece gamepiece;
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
        
        font = skin.getFont("font");
        bannerText = "";
        bannerX = 10.0f;
        bannerY = imageLayer.getTextureRegion().getRegionHeight() + font.getCapHeight() * 1.5f;
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
        graphicalBoard = new GraphicalBoard(boardX, boardY, boardWidth, boardHeight, blackSquare, whiteSquare, new Board());

    }

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
                pieces.add(new GraphicalGamepiece(p, square, gamepieceX, gamepieceY));
            }
    } 

    @Override
    public void show() {
        /* Added InputMultiplexer
         * Combines multiple processors
         * I combined the stage input processor with our custom one
         * Idk if itll break anything in the future, but it fixes the dialog problem for now
         */
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new PlayScreenInputProcessor(blokusGame));
        Gdx.input.setInputProcessor(multiplexer);
        //Gdx.input.setInputProcessor(blokusGame.playScrEventHandler);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.WHITE);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // first let's draw the tiled Map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Next we draw the gamepiece and the banner
        batch.begin();
        for(GraphicalGamepiece p : pieces) {
            p.draw(batch);
        }
        font.draw(batch, bannerText, bannerX, bannerY);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
        tiledMap.dispose();
    }

    public void setBannerText(String text) {
        bannerText = text;
    }

    public Camera getCamera() {
        return camera;
    }

    public ArrayList<GraphicalGamepiece> getGraphicalGamepiece() {
        return pieces;
    }
}