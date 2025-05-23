package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.demoncore.Farmers2D.Game;
import de.demoncore.Farmers2D.gameObjects.Player;
import de.demoncore.Farmers2D.utils.GameActionListener;
import de.demoncore.Farmers2D.utils.KeyHandler;

import static com.badlogic.gdx.scenes.scene2d.ui.Value.*;


public class GUIScreen extends BaseScreen {
    private Skin skin;
    protected Stage stage;
    protected Table table;

    @Override
    public void initialize() {
        super.initialize();
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("ui/uiskin.json")); // make sure this file exists

        table = new Table();
        table.setFillParent(true);
        //table.debug();
        table.center();

        stage.addActor(table);
    }

    GameActionListener listener;

    @Override
    public void show() {

        KeyHandler.instance.add(listener = new GameActionListener(){
            @Override
            public void onEscapePressed() {
                super.onEscapePressed();
                Game.instance.switchScreens(2);
            }
        });

        Player p = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(25, 25));
        p.color = Color.GRAY;
        addFillShape(p);
        super.show();
    }

    public <T extends Actor> void addComponent(T component, float widthPercent, float heightPercent) {
        table.add(component)
                .width(percentWidth(widthPercent, table))
                .height(percentHeight(heightPercent, table))
                .padBottom(20);
        table.row();
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

    public Stage getStage() {
        return stage;
    }
}
