package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.gameObjects.InteractableObject;
import de.demoncore.Farmers2D.gameObjects.Player;
import de.demoncore.Farmers2D.questSystem.QuestManager;
import de.demoncore.Farmers2D.questSystem.quests.Quest;
import de.demoncore.Farmers2D.scenes.utils.Shapes;
import de.demoncore.Farmers2D.utils.GameActionListener;
import de.demoncore.Farmers2D.utils.KeyHandler;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.UtilityMethods;
import de.demoncore.Farmers2D.utils.enums.QuestType;

public class DefaultScreen extends BaseScreen {

    private Color background = Color.BLACK;
    private GameActionListener listener;

    /**
     * Constructs the default screen and logs its creation.
     */
    public DefaultScreen(){
        Logger.logInfo("loaded new Default Screen");
    }

    @Override
    public void initialize() {
        super.initialize();
        
        Player p = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(25, 25));
        p.color = Color.GRAY;
        cameraFollowObject = p;
        addFillShape(p);

        InteractableObject iO = new InteractableObject(Shapes.Rectangle,
                new Vector2(300f, 300f),
                new Vector2(25, 25),
                Color.DARK_GRAY,
                75f,
                new Runnable() {
                    @Override
                    public void run() {
                        Logger.logInfo("interacted Event");
                    }
                });
        addFillShape(iO);

        InteractableObject QuestBoard = new InteractableObject(Shapes.Oval,
                new Vector2(500, 500),
                new Vector2(25, 25),
                Color.CYAN,
                75f,
                new Runnable() {
                    @Override
                    public void run() {
                        QuestManager.instance.addNewQuest(QuestManager.instance.getNewQuest(QuestType.POSITION,
                                "Test",
                                "Just A test",
                                0,
                                1,
                                null,
                                new Vector2(1000, 1000) ), false);

                        for(Quest q : QuestManager.instance.currentQuests){
                            Logger.logInfo(q.toString());
                        }
                    }
                });
        addFillShape(QuestBoard);

        tempObstacle();

    }

    @Override
    public void show() {
        Logger.logInfo("shown default Screen");

        KeyHandler.instance.add(listener = new GameActionListener(){
            @Override
            public void onEscapePressed() {
                super.onEscapePressed();
                Game.instance.switchScreens("pause");
            }

            @Override
            public void onInteractionKeyPressed() {
                super.onInteractionKeyPressed();
                UtilityMethods.callInteractionOnObjects();
            }
        }, "DefaultScreen");
        super.show();
    }

    @Override
    public void render(float delta) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(background);
        sr.rect(0,0,windowSize.x + 20, windowSize.y + 20);
        sr.end();
        super.render(delta);

    }

    /**
     * Adds temporary obstacles to the screen.
     * These are randomly placed white rectangles used for testing.
     */
    public void tempObstacle(){
        for (int i = 0; i < 200; i++) {
            GameObject g = new GameObject(Shapes.Rectangle,
                    new Vector2(MathUtils.random(-2000, 2000), MathUtils.random(-2000, 2000)),
                    new Vector2(30, 30),
                    Color.WHITE);
            addFillShape(g);
        }
    }

    @Override
    public void hide() {
        super.hide();
        KeyHandler.instance.remove(listener, "defaultScreen");
    }
}
