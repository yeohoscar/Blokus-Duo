package ui.graphical;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class NameScreen extends ScreenAdapter {
    BlokusGame blokusGame;

    Stage stage;
    Skin skin;
    Table table;

    public NameScreen(BlokusGame blokusGame) {
        this.blokusGame = blokusGame;
        this.stage = blokusGame.stage;
        this.skin = blokusGame.skin;

        table = new Table();
        table.setFillParent(true);

        Label title = new Label("Blokus Duo", skin, "font", Color.BLACK);
        table.add(title).pad(10).colspan(3).center();
        table.row();

        Label label = new Label("Enter the name of Player 1:", skin,"font", Color.BLACK);
        table.add(label).center();

        ImageButton color = new ImageButton(skin, "default");
        table.add(color).center();

        TextField textField = new TextField(null, skin);
        textField.setMessageText("John");
        table.add(textField).center();
        table.row();

        Label label2 = new Label("Enter the name of Player 2:", skin, "font", Color.BLACK);
        table.add(label2).center();

        ImageButton color2 = new ImageButton(skin, "white");
        table.add(color2).pad(10).center();

        TextField textField2 = new TextField(null, skin);
        textField2.setMessageText("Mary");
        table.add(textField2).center();
        table.row();

        TextButton textButton = new TextButton("Start Game",skin);
        table.add(textButton).pad(10).colspan(3).center();
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String playerOneName = textField.getText();
                String playerTwoName = textField2.getText();
                if (playerOneName.isEmpty()) {
                    playerOneName = textField.getMessageText();
                }

                if (playerTwoName.isEmpty()) {
                    playerTwoName = textField2.getMessageText();
                }

                // send name to game control thread
                blokusGame.uiStream.println(playerOneName);
                blokusGame.uiStream.println(playerTwoName);
            }
        });
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
