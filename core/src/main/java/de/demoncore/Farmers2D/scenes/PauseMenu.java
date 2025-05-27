package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.utils.Logger;

public class PauseMenu extends GUIScreen{

    Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

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

        TextButton backToGame = new TextButton("back to game", skin);
        backToGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.instance.switchScreens(0);
                super.clicked(event, x, y);
            }
        });
        //backToGame.getLabel().setFontScale(1.5f);   //<-disabled lässt Font verschwimmen
        addComponent(backToGame, 0.3f, 0.075f);


        TextButton settings = new TextButton("settings", skin);
        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.logInfo("settings");
                super.clicked(event, x, y);
            }
        });
        //settings.getLabel().setFontScale(1.5f);   //<-disabled lässt Font verschwimmen
        addComponent(settings, 0.3f, 0.075f);
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
    }
}
