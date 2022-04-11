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

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.Board;
import model.piece.*;
import ui.UI;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class BlokusGame extends Game {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    Thread gameControlThread;
    PrintStream uiStream;
    BlockingQueue<Piece> pieceQueue;

    OrthographicCamera camera;
    Stage stage;
    Skin skin;

    SpriteBatch batch;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer renderer;

    NameScreen nameScreen;
    PlayScreen playScreen;
    PlayScreenInputProcessor playScreenInputProcessor;
    
    public BlokusGame(Thread gameControlThread, UI ui) {
        GUI gui = (GUI)ui;
        this.gameControlThread = gameControlThread;
        gui.setGame(this);
        this.uiStream = new PrintStream(gui.getPipedOutputStream());
        this.pieceQueue = gui.getPieceQueue();
    }

    /**
     * Create a window screen if user choose to have graphical UI
     */
    public void create() {

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.update();

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("BlokusDuo.json"));
        
        tiledMap = new TmxMapLoader().load("prototype.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        renderer.setView(camera);

        nameScreen = new NameScreen(this);
        playScreen = new PlayScreen(this);
        playScreenInputProcessor = new PlayScreenInputProcessor(this);
        activateNameScreen();
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
        if (nameScreen != null) nameScreen.dispose();
        if (playScreen != null) playScreen.dispose();
        if (skin != null) skin.dispose();
        if (stage != null) stage.dispose();
        if (tiledMap != null) tiledMap.dispose();
        if (batch != null) batch.dispose();
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

    /**
     * Show the message on screen
     * @param text String to be printed
     */
    void showMessage(String text) {
        playScreen.showMessage(text);
    }

    /**
     * Update the board on gui
     * @param board board
     */
    public void updateBoard(Board board) {
        playScreen.graphicalBoard.updateBoard(board);
    }

    public void setCurrentPlayerColor(String playerColor) {
        playScreenInputProcessor.setCurrentPlayerColor(playerColor);
    }

    public void setCurrentPlayerValidMove(ArrayList<int[]> validMove) {
        playScreenInputProcessor.setCurrentPlayerValidMove(validMove);
    }
}