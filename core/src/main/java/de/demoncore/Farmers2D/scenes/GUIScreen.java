package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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

    @Override
    public void show() {
        table.clear();
        super.show();
    }

    /**
     * Adds a UI component (Actor) to the table with specified width and height in percent.
     *
     * @param component     the UI element to add (e.g., Button, Label, Image)
     * @param widthPercent  width in percent of the parent table (0.0f to 100.0f)
     * @param heightPercent height in percent of the parent table (0.0f to 100.0f)
     * @param <T>           type of the Actor
     */
    public <T extends Actor> void addComponent(T component, float widthPercent, float heightPercent) {
        table.add(component)
                .width(percentWidth(widthPercent / 100, table))
                .height(percentHeight(heightPercent / 100, table))
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

    /**
     * Returns the stage used to render UI elements.
     *
     * @return the stage instance
     */
    public Stage getStage() {
        return stage;
    }

    @Override
    public void hide() {
        super.hide();
    }
}
