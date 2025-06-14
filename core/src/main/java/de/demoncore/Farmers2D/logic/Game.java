package de.demoncore.Farmers2D.logic;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.questSystem.QuestManager;
import de.demoncore.Farmers2D.saveFiles.SaveManager;
import de.demoncore.Farmers2D.scenes.*;
import de.demoncore.Farmers2D.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Main game class managing screens and input */
public class Game extends com.badlogic.gdx.Game implements ApplicationListener {
    public static Game instance;

    KeyHandler keyHandler = new KeyHandler();
    public GameState state = new GameState();

    Stage currentStage;
    QuestManager questManager;
    private HashMap<String, BaseScreen> screens = new HashMap<>();
    private String defaultScreen = "main";
    private String lastScreen;

    public InputMultiplexer multiplexer = new InputMultiplexer();
    public ArrayList<RenderListener> renderListeners = new ArrayList<>();
    public ArrayList<TimeListener> timeListeners = new ArrayList<>();

    public boolean isPaused = false;
    boolean finishedLoading;

    public Game(){
        questManager = new QuestManager();
        instance = this;
    }

    @Override
    public void create() {}

    @Override
    public void resize(final int width, final int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        if (!finishedLoading) {
            if (Resources.instance == null) {
                Resources res = new Resources();
                res.init();
            }

            if (Resources.instance.initialized) {
                finishLoading();
            }
            return;
        }
        super.render();
        if(keyHandler.isAnyKeyPressed){
            keyHandler.update();
        }
        QuestManager.instance.updateAll();
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
        SaveManager.saveAll();
        Logger.logInfo("application closed");
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
            GUIScreen gs = (GUIScreen) screen;
            currentStage = gs.getStage();

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
    public void switchScreens(String screen){
        if(screens.get(screen) instanceof PauseMenu){
            PauseMenu pM = (PauseMenu) screens.get(screen);
            BaseScreen bS = getScreen();
            TextureRegion texture = bS.takeScreenshotTexture();

            pM.setBackground(texture);
            lastScreen = getScreenKey(getScreen());
            setScreen(pM);
            return;
        }

        lastScreen = getScreenKey(getScreen());
        setScreen(screens.get(screen));
    }

    public void addRenderListener(RenderListener rListener) {
        renderListeners.add(rListener);
    }

    @Override
    public BaseScreen getScreen() {
        return (BaseScreen) super.getScreen();
    }

    public void finishLoading(){
        Translation translation = new Translation();
        translation.init();

        initScreens();

        multiplexer.addProcessor(keyHandler);
        if(currentStage != null) multiplexer.addProcessor(currentStage);

        Gdx.input.setInputProcessor(multiplexer);
        Logger.logInfo("Setting screen to DefaultScene");

        switchScreens(defaultScreen);
        Settings.load();
        QuestManager.instance.load();
        QuestManager.instance.loadListeners();
        Logger.logInfo("quests->"+QuestManager.instance.currentQuests.size());
        GameState.load();

        finishedLoading = true;
    }

    public void initScreens(){
        screens.put("main", new MainMenu());
        screens.put("default", new DefaultScreen());
        screens.put("pause", new PauseMenu());
    }

    public void switchScreenBack(){
        switchScreens(lastScreen);
    }

    public String getScreenKey(BaseScreen screen){
        if(screen == null) return null;
        for(Map.Entry<String, BaseScreen> entry : screens.entrySet()){
            if(entry.getValue().getClass() == screen.getClass()){
                return entry.getKey();
            }
        }
        return null;
    }

}
