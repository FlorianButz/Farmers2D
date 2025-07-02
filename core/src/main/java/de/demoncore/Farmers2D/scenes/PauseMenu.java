package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.ui.DTextButton;
import de.demoncore.Farmers2D.utils.*;

import java.util.Arrays;

public class PauseMenu extends GUIScreen{

    private GameActionListener listener;

    private TextureRegion backgroundRegion;
    private Image backgroundImage;
    private Image blackOverlay;

    @Override
    public void initialize() {
        super.initialize();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        Texture blackTexture = new Texture(pixmap);
        pixmap.dispose();

        // create an Image with the black texture
        blackOverlay = new Image(new TextureRegionDrawable(new TextureRegion(blackTexture)));
        blackOverlay.setFillParent(true);
        blackOverlay.setColor(0, 0, 0, 0.75f);

    }

    @Override
    public void show() {
        super.show();
        Game.instance.isPaused = true;

        KeyHandler.instance.add(listener = new GameActionListener() {
            @Override
            public void onEscapePressed() {
                super.onEscapePressed();
                Game.instance.switchScreenBack(Arrays.asList("settings", "pause"));
            }
        }, "PauseMenu");

        DTextButton backToGame = new DTextButton(Translation.get("component.button.btg"));
        backToGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.instance.switchScreens("default");
                super.clicked(event, x, y);
            }
        });
        addComponent(backToGame, 30f, 7.5f);

        DTextButton settings = new DTextButton(Translation.get("component.button.settings"));
        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.instance.switchScreens("settings");
                super.clicked(event, x, y);
            }
        });
        addComponent(settings, 30f, 7.5f);

        DTextButton backToMainMenu = new DTextButton(Translation.get("component.button.btmm"));
        backToMainMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.instance.switchScreens("main");
                super.clicked(event, x, y);
            }
        });
        addComponent(backToMainMenu, 30f, 7.5f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    /**
     * Sets the background image of the pause menu using a TextureRegion.
     * Disposes the old background texture and removes the old image actor if they exist.
     * Adds the new background image at the bottom of the stage actor stack.
     *
     * @param region the TextureRegion to use as the background image
     */
    public void setBackground(TextureRegion region) {
        if (backgroundRegion != null && backgroundRegion.getTexture() != null) {    //disposing old Image if already existing
            backgroundRegion.getTexture().dispose();
        }

        backgroundRegion = region;

        if (backgroundImage != null) {
            backgroundImage.remove();   //removing old Image from every Parent
            blackOverlay.remove();
        }

        backgroundImage = new Image(new TextureRegionDrawable(backgroundRegion));
        backgroundImage.setFillParent(true);
        stage.getRoot().addActorAt(0, backgroundImage); // adding background
        stage.getRoot().addActorAt(1, blackOverlay);
    }

    @Override
    public void hide() {
        super.hide();
        Game.instance.isPaused = false;
        KeyHandler.instance.remove(listener, "MainMenu");
    }
}
