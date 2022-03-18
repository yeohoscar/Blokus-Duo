package ui.graphical;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ui.UI;

import java.io.PrintStream;

public class BlokusGame extends Game {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    Thread gameControlThread;
    PrintStream uiStream;

    OrthographicCamera camera;
    Stage stage;
    Skin skin;

    Screen nameScreen;
    Screen startScreen;

    public BlokusGame(Thread gameControlThread, UI ui) {
        GUI gui = (GUI)ui;
        this.gameControlThread = gameControlThread;

        // establishing communication between GameControl thread and HelloGame (libGDX thread)
        // control -> game
        gui.setGame(this); // for posting runnables into libGDX game loop
        // game -> control
        this.uiStream = new PrintStream(gui.getPipedOutputStream()) ;  // for sending text to control
    }

    public void create() {
        camera = new OrthographicCamera(WIDTH,HEIGHT);
        camera.position.set(WIDTH  * 0.5f, HEIGHT * 0.5f, 0.0f);
        stage = new Stage(new FitViewport(WIDTH,HEIGHT,camera));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("HelloGameSkin.json"));

        nameScreen = new NameScreen(this);
        startScreen = new StartScreen(this);
        activateNameScreen();
    }

    public void activateStartScreen() {
        setScreen(startScreen);
    }

    public void activateNameScreen() {
        setScreen(nameScreen);
    }

    public void postRunnable(Runnable r) {
        Gdx.app.postRunnable(r);
    }

    void showDialog(String text) {
        Dialog dialog = new Dialog("Attention", skin, "default");
        dialog.text(text);
        dialog.button("OK");
        dialog.getContentTable().pad(10);
        dialog.getButtonTable().pad(10);
        dialog.show(stage);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (startScreen != null) startScreen.dispose();
        if (nameScreen != null) nameScreen.dispose();
        if (skin != null) skin.dispose();
        if (stage != null) stage.dispose();
        if (gameControlThread != null) gameControlThread.stop();
    }

    public void setMessage(String name) {
        showDialog("Player " + name + " goes first!");
    }
}
