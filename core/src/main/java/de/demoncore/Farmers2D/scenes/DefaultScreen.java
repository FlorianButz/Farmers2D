package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.demoncore.Farmers2D.components.Inventory;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.gameObjects.InteractableObject;
import de.demoncore.Farmers2D.gameObjects.Player;
import de.demoncore.Farmers2D.questSystem.QuestManager;
import de.demoncore.Farmers2D.questSystem.quests.Quest;
import de.demoncore.Farmers2D.utils.GameActionListener;
import de.demoncore.Farmers2D.utils.KeyHandler;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.UtilityMethods;
import de.demoncore.Farmers2D.utils.enums.QuestType;

import static com.badlogic.gdx.scenes.scene2d.ui.Value.percentHeight;
import static com.badlogic.gdx.scenes.scene2d.ui.Value.percentWidth;

public class DefaultScreen extends BaseScreen {

    private Color background = Color.BLACK;
    private GameActionListener listener;

    private Stage stage;
    private Table table;
    public Inventory inv;

    /**
     * Constructs the default screen and logs its creation.
     */
    public DefaultScreen(){
        Logger.logInfo("loaded new Default Screen");
    }

    @Override
    public void initialize() {
        super.initialize();
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Player p = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(25, 25));
        p.color = Color.GRAY;
        cameraFollowObject = p;
        addObject(p);

        InteractableObject iO = new InteractableObject(new Vector2(300f, 300f),
                new Vector2(25, 25),
                Color.DARK_GRAY,
                75f,
                new Runnable() {
                    @Override
                    public void run() {
                        Logger.logInfo("interacted Event");
                    }
                });
        addObject(iO);

        InteractableObject QuestBoard = new InteractableObject(
                new Vector2(500, 500),
                new Vector2(25, 25),
                Color.CYAN,
                75f,
                new Runnable() {
                    @Override
                    public void run() {
                        QuestManager.instance.addNewQuest(QuestManager.instance.getNewQuest(QuestType.POSITION,
                                "Test",
                                "Just a test",
                                0,
                                1,
                                null,
                                new Vector2(1000, 1000) ), false);

                        for(Quest q : QuestManager.instance.currentQuests){
                            Logger.logInfo(q.toString());
                        }
                    }
                });
        addObject(QuestBoard);

        tempObstacle();

        inv = new Inventory(new Skin(Gdx.files.internal("ui/uiskin.json")));
        table.add(inv)
                .width(percentWidth(80f / 100, table))
                .height(percentHeight(80f / 100, table))
                .padBottom(20);

        //stage.setDebugAll(true);
    }

    @Override
    public void show() {
        Logger.logInfo("shown default Screen");

        KeyHandler.instance.add(listener = new GameActionListener(){
            @Override
            public void onEscapePressed() {
                if(inv.isVisible()) {
                    inv.hide();
                    return;
                }
                Game.instance.switchScreens("pause");
                super.onEscapePressed();
            }

            @Override
            public void onInteractionKeyPressed() {
                super.onInteractionKeyPressed();
                UtilityMethods.callInteractionOnObjects();
            }

            @Override
            public void onTabPressed(){
                if(inv.isVisible())
                    inv.hide();
                else
                    inv.show();
            }
        }, "DefaultScreen");

        Game.instance.multiplexer.addProcessor(1, stage);

        super.show();
    }

    @Override
    public void render(float delta) {
        srFilled.begin(ShapeRenderer.ShapeType.Filled);
        srFilled.setColor(background);
        srFilled.rect(0,0,windowSize.x + 20, windowSize.y + 20);
        srFilled.end();
        super.render(delta);

        stage.act(delta);
        stage.draw();
    }

    /**
     * Adds temporary obstacles to the screen.
     * These are randomly placed white rectangles used for testing.
     */
    public void tempObstacle(){
        for (int i = 0; i < 200; i++) {
            GameObject g = new GameObject(new Vector2(MathUtils.random(-2000, 2000), MathUtils.random(-2000, 2000)),
                    new Vector2(30, 30),
                    Color.WHITE);
            addObject(g);
        }
    }

    @Override
    public void hide() {
        super.hide();
        KeyHandler.instance.remove(listener, "defaultScreen");

        Game.instance.multiplexer.removeProcessor(1);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
    }
}
