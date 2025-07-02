package de.demoncore.Farmers2D.logic;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.questSystem.QuestManager;
import de.demoncore.Farmers2D.saveFiles.SaveManager;
import de.demoncore.Farmers2D.scenes.*;
import de.demoncore.Farmers2D.utils.*;

import java.util.*;

/** Main game class managing screens and input */
public class Game extends com.badlogic.gdx.Game implements ApplicationListener {
    public static Game instance;

    KeyHandler keyHandler = new KeyHandler();
    public GameState state = new GameState();

    Stage currentStage;
    QuestManager questManager;
    private HashMap<String, BaseScreen> screens = new HashMap<>();
    private String defaultScreen = "main";
    private Deque<String> lastScreen = new ArrayDeque<>();

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
        if(screens.get(screen) instanceof PauseMenu && !getScreenKey(getScreen()).equals("settings")){
            PauseMenu pM = (PauseMenu) screens.get(screen);
            BaseScreen bS = getScreen();
            TextureRegion texture = bS.takeScreenshotTexture();

            pM.setBackground(texture);
            addLastScreen(getScreenKey(getScreen()));
            setScreen(pM);
            //Logger.logWarning("currentScreen->"+getScreenKey(getScreen())+ " lastScreen->"+lastScreen);
            return;
        }

        addLastScreen(getScreenKey(getScreen()));
        setScreen(screens.get(screen));
        //Logger.logWarning("currentScreen->"+getScreenKey(getScreen())+ " lastScreen->"+lastScreen);
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
        Settings.load();

        initScreens();

        multiplexer.addProcessor(keyHandler);
        if(currentStage != null) multiplexer.addProcessor(currentStage);

        Gdx.input.setInputProcessor(multiplexer);
        Logger.logInfo("Setting screen to DefaultScene");

        switchScreens(defaultScreen);
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
        screens.put("settings", new SettingsScreen());
    }

    public void switchScreenBack(){
        switchScreens(getLastScreen());
    }

    public void switchScreenBack(List<String> exclude){
        switchScreens(getLastScreen(exclude));
    }

    private void addLastScreen(String lastScreen){
        if (lastScreen == null) return;
        this.lastScreen.push(lastScreen);
        if(this.lastScreen.size() > 15) this.lastScreen.removeLast();
    }

    private String getLastScreen(){
        if (lastScreen.isEmpty()) {
            Logger.logWarning("Screen stack is empty, returning default screen");
            return "main";
        }
        return lastScreen.pop();
    }

    private String getLastScreen(List<String> exclude) {
        while (!lastScreen.isEmpty()) {
            String key = lastScreen.pop();
            if (!exclude.contains(key)) return key;
        }
        Logger.logWarning("No screen found matching exclusion list -> " + exclude);
        return "main";
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
