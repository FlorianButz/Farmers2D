package de.demoncore.Farmers2D;

import com.badlogic.gdx.*;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.scenes.BaseScreen;
import de.demoncore.Farmers2D.scenes.DefaultScreen;
import de.demoncore.Farmers2D.utils.KeyHandler;
import de.demoncore.Farmers2D.utils.Logger;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Game extends com.badlogic.gdx.Game implements ApplicationListener {
    public static Game instance;

    KeyHandler keyHandler = new KeyHandler();

    public Game(){
        instance = this;
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(keyHandler);
        Logger.logInfo("Setting screen to DefaultScene");
        setScreen(new DefaultScreen());
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

    public ArrayList<GameObject> getScreenObjects() {
        BaseScreen scene = (BaseScreen) getScreen();
        return scene.screenObjects;
    }
}
