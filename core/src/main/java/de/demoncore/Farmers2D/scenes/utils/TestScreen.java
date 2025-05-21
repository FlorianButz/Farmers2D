package de.demoncore.Farmers2D.scenes.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.demoncore.Farmers2D.gameObjects.Player;
import de.demoncore.Farmers2D.scenes.BaseScreen;
import de.demoncore.Farmers2D.utils.Logger;

public class TestScreen extends BaseScreen {
    private Skin skin;

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json")); // make sure this file exists

        TextButton button = new TextButton("Click me!", skin);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.logInfo("Button clicked");
                super.clicked(event, x, y);
            }
        });

        // Table for dynamic centering
        Table table = new Table();
        table.setFillParent(true); // makes the table fill the whole screen
        table.debug();
        table.center(); // center contents

        table.add(button).width(200).height(50); // optional: set size
        stage.addActor(table);

        Player p = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(25, 25));
        p.color = Color.GRAY;
        addFillShape(p);
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }
}
