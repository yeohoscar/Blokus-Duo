/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * StartScreen class
 *  - represents start screen of graphical UI
 */

package ui.graphical;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen extends ScreenAdapter {
    BlokusGame blokusGame;
    Stage stage;
    Skin skin;
    Table table;

    public StartScreen(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
        this.stage = blokusGame.stage;
        this.skin = blokusGame.skin;

        table = new Table();
        table.setFillParent(true);
    }

    /**
     * Show the screen as the current screen
     */
    public void show() {
        super.show();
        stage.addActor(table);
    }

    /**
     * To present the screen
     */
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.WHITE);
        stage.act(delta);
        stage.draw();
    }

    /**
     * Hide the screen when it is no longer the current screen
     */
    public void hide() {
        super.hide();
        stage.clear();
    }

    /**
     * Resize the screen
     */
    public void resize(int width, int height) {
        super.resize(width,height);
        stage.getViewport().update(width, height, true);
    }
}
