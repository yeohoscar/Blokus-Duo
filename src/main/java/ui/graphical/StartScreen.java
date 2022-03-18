package ui.graphical;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

    public void show() {
        super.show();
        stage.addActor(table);
    }

    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.WHITE);
        stage.act(delta);
        stage.draw();
    }

    public void hide() {
        super.hide();
        stage.clear();
    }

    public void resize(int width, int height) {
        super.resize(width,height);
        stage.getViewport().update(width, height, true);
    }
}
