package de.demoncore.Farmers2D;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.scenes.BaseScreen;
import de.demoncore.Farmers2D.scenes.DefaultScreen;
import de.demoncore.Farmers2D.scenes.GUIScreen;
import de.demoncore.Farmers2D.scenes.PauseMenu;
import de.demoncore.Farmers2D.utils.KeyHandler;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.Resources;

import java.util.ArrayList;

/** Main game class managing screens and input */
public class Game extends com.badlogic.gdx.Game implements ApplicationListener {
    public static Game instance;

    KeyHandler keyHandler = new KeyHandler();
    Resources resources;
    Stage currentStage;
    private ArrayList<Screen> screens = new ArrayList<>();
    private int defaultScreen = 0;

    InputMultiplexer multiplexer = new InputMultiplexer();

    public boolean isPaused = false;
    public boolean isInDebug = true;

    public Game(){
        resources = new Resources();
        instance = this;
    }

    @Override
    public void create() {
        multiplexer.addProcessor(keyHandler);
        if(currentStage != null) multiplexer.addProcessor(currentStage);

        Gdx.input.setInputProcessor(multiplexer);
        Logger.logInfo("Setting screen to DefaultScene");

        screens.add(new DefaultScreen());
        screens.add(new GUIScreen());
        screens.add(new PauseMenu());

        switchScreens(defaultScreen);
    }

    @Override
    public void resize(final int width, final int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
        if(keyHandler.isAnyKeyPressed){
            keyHandler.update();
        }
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        getScreen().dispose();
    }

    /**
     * Returns list of game objects on the current screen.
     * @return list of GameObject
     */
    public ArrayList<GameObject> getScreenObjects() {
        BaseScreen scene = (BaseScreen) getScreen();
        return scene.screenObjects;
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        if (screen instanceof GUIScreen) {
            GUIScreen bs = (GUIScreen) screen;
            currentStage = bs.getStage();

            multiplexer.clear();
            multiplexer.addProcessor(keyHandler);
            if (currentStage != null) {
                multiplexer.addProcessor(currentStage); // Stage later to stop input loss
            }

            Gdx.input.setInputProcessor(multiplexer);
        }
    }

    /**
     * Switches between screens by index.
     * Sets a screenshot background for the PauseMenu.
     * @param screen index of screen to switch to
     */
    public void switchScreens(int screen){
        if(screens.get(screen) instanceof PauseMenu){
            PauseMenu pM = (PauseMenu) screens.get(screen);
            BaseScreen bS = (BaseScreen) getScreen();
            TextureRegion texture = bS.takeScreenshotTexture();

            pM.setBackground(texture);
            setScreen(pM);
            return;
        }

        setScreen(screens.get(screen));
        if(!Resources.instance.initialized) Resources.instance.init();
    }
}
