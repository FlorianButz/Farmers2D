package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.gameObjects.Player;
import de.demoncore.Farmers2D.scenes.utils.Shapes;
import de.demoncore.Farmers2D.utils.Logger;

public class DefaultScreen extends BaseScreen {

    private Color background = Color.BLACK;

    public DefaultScreen(){
        Logger.logInfo("loaded new Default Screen");
    }

    @Override
    public void show() {
        Logger.logInfo("shown default Screen");

        Player p = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(25, 25));
        p.color = Color.GRAY;
        addFillShape(p);

        tempObstacle();

        //TODO: adding GUI Buttons and components

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

    public void tempObstacle(){
        for (int i = 0; i < 200; i++) {
            GameObject g = new GameObject(Shapes.Rectangle,
                    new Vector2(MathUtils.random(-2000, 2000), MathUtils.random(-2000, 2000)),
                    new Vector2(30, 30),
                    Color.WHITE);
            addFillShape(g);
        }
    }

}
