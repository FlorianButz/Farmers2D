package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.gameObjects.Player;
import de.demoncore.Farmers2D.scenes.utils.ShapeEntry;
import de.demoncore.Farmers2D.scenes.utils.Shapes;
import de.demoncore.Farmers2D.utils.Logger;

public class DefaultScene extends BaseScene{

    private Color background = Color.BLACK;

    public DefaultScene(){
        Logger.logInfo("loaded new Default Screen");
    }

    @Override
    public void show() {
        Logger.logInfo("shown default Screen");
        super.show();
        addFillShape(new ShapeEntry(Shapes.Rectangle,
            Vector2.Zero.cpy(),
            new Vector2(windowSize.x + 20, windowSize.y + 20),
            background));

        Player p = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), new Vector2(25, 25));
        p.color = Color.GRAY;
        addFillShape(p);

        tempObstacle();


        super.show();
    }

    @Override
    public void render(float delta) {
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
