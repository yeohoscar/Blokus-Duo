/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * BlokusGame class
 *  - initialises and creates GUI
 */

package ui.graphical;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.piece.*;
import ui.UI;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class BlokusGame extends Game {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 812;

    public final static String CLICK_AND_DRAG_MESSAGE = "Click and drag the gamepiece.";
    public final static String FLIP_OR_ROTATE_MESSAGE = "Press 'f' to flip, or 'r' to rotate the gamepiece.";

    Thread gameControlThread;
    PrintStream uiStream;

    OrthographicCamera camera;
    Stage stage;
    Skin skin;
    Screen nameScreen;
    Screen playScreen;
    //ApplicationListener game;

    SpriteBatch batch;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer renderer;

    /*MapObjects mapObjects;
    TiledMapRenderer mapRenderer;
    TextureRegion blackSquare;
    BitmapFont helvetique;
    String bannerText;
    float bannerX;
    float bannerY;
    Piece gamepiece;
    GraphicalGamepiece graphicalGamepiece;*/

    public BlokusGame(Thread gameControlThread, UI ui) {
        GUI gui = (GUI)ui;
        this.gameControlThread = gameControlThread;
        gui.setGame(this);
        this.uiStream = new PrintStream(gui.getPipedOutputStream());
    }

    /**
     * Create a window screen if user choose to have graphical UI
     */
    public void create() {

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH  * 0.5f, HEIGHT * 0.5f, 0.0f);

        batch = new SpriteBatch();

        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("BlokusDuo.json"));

        tiledMap = new TmxMapLoader().load("prototype.tmx");
        //renderer = new OrthogonalTiledMapRenderer(tiledMap);
        //renderer.setView(camera);

        nameScreen = new NameScreen(this);
        playScreen = new PlayScreen(this);
        activateNameScreen();
        //activateplayScreen();
    }

    /**
     * Set name screen as current screen
     */
    public void activateNameScreen() {
        setScreen(nameScreen);
    }

    public void activatePlayScreen() {
        setScreen(playScreen);
    }

    /**
     * Posts a runnable on the main loop thread
     * @param r the runnable
     */
    public void postRunnable(Runnable r) {
        Gdx.app.postRunnable(r);
    }

    /**
     * Show the name of player who will make the first move on a dialog window
     * @param text message of the name of player who will make the first move
     */
    void showDialog(String text) {
        Dialog dialog = new Dialog("Attention", skin, "default");
        dialog.text(text);
        dialog.button("OK");
        dialog.getContentTable().pad(10);
        dialog.getButtonTable().pad(10);
        dialog.show(stage);
    }

    /**
     * Destroy the screens and windows and end the graphical UI
     */
    @Override
    public void dispose() {
        super.dispose();
        if (playScreen != null) playScreen.dispose();
        if (nameScreen != null) nameScreen.dispose();
        if (skin != null) skin.dispose();
        if (stage != null) stage.dispose();
        if (gameControlThread != null) gameControlThread.stop();
    }

    /**
     * Sets name for start screen
     */
    public void setMessage(String name) {
        showDialog("Player " + name + " goes first!");
    }

    /**
     * Displays results of the game
     */
    public void setResults(String result) {
        showDialog(result);
    }
}