package ui.graphical;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import model.piece.*;

public class GameScreen extends ScreenAdapter {

    public final static int GAME_WIDTH = 1024;
    public final static int GAME_HEIGHT = 812;
    public final static String CLICK_AND_DRAG_MESSAGE = "Click and drag the gamepiece.";
    public final static String FLIP_OR_ROTATE_MESSAGE = "Press 'f' to flip, or 'r' to rotate the gamepiece.";

    BlokusGame blokusGame;
    SpriteBatch batch;
    OrthographicCamera camera;
    TiledMap tiledMap;
    MapObjects mapObjects;
    TiledMapRenderer mapRenderer;
    Skin skin;
    TextureRegion blackSquare;
    BitmapFont helvetique;
    String bannerText;
    float bannerX;
    float bannerY;
    Piece gamepiece;
    GraphicalGamepiece graphicalGamepiece;

    public GameScreen(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
        this.skin = blokusGame.skin;
        this.camera = blokusGame.camera;

        batch = new SpriteBatch();

        tiledMap = new TmxMapLoader().load("prototype.tmx");
        TiledMapImageLayer imageLayer = (TiledMapImageLayer) tiledMap.getLayers().get(0);
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
        MapLayer objectLayer = tiledMap.getLayers().get(2);
        mapObjects = objectLayer.getObjects();
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        blackSquare = skin.getRegion("game_square_black");
        helvetique = skin.getFont("font");
        bannerText = "";
        bannerX = 10.0f;
        bannerY = imageLayer.getTextureRegion().getRegionHeight() + helvetique.getCapHeight()*1.5f;
        setBannerText(CLICK_AND_DRAG_MESSAGE);

        MapObject mapObject = mapObjects.get("0I3");
        float gamepieceX = (float) mapObject.getProperties().get("x");
        float gamepieceY = (float) mapObject.getProperties().get("y");
        gamepiece = new Piece("I3", new ArrayList<>(Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2))));
        graphicalGamepiece = new GraphicalGamepiece(gamepiece, blackSquare, gamepieceX, gamepieceY);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new EventHandler(this));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(1.0f, 1.0f, 1.0f, 1.0f);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // first let's draw the tiled Map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Next we draw the gamepiece and the banner
        batch.begin();
        graphicalGamepiece.draw(batch);
        helvetique.draw(batch, bannerText, bannerX, bannerY);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
        tiledMap.dispose();
    }

    public void setBannerText(String text) {
        bannerText = new String(text);
    }

    public Camera getCamera() {
        return camera;
    }

    public GraphicalGamepiece getGraphicalGamepiece() {
        return graphicalGamepiece;
    }
}
