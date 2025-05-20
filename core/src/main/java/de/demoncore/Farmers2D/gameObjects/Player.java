package de.demoncore.Farmers2D.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.Game;
import de.demoncore.Farmers2D.scenes.BaseScene;
import de.demoncore.Farmers2D.scenes.utils.Shapes;
import de.demoncore.Farmers2D.utils.GameActionListener;
import de.demoncore.Farmers2D.utils.KeyHandler;
import de.demoncore.Farmers2D.utils.UtilityMethodes;
import sun.java2d.loops.ProcessPath;

import java.util.ArrayList;
import java.util.List;

public class Player extends RigidBody {

    public static Player _instance;
    public GameActionListener listener;

    public Player(Vector2 pos, Vector2 size) {
        super(Shapes.Rectangle, pos, size, Color.WHITE);
        _instance = this;
    }

    @Override
    public void onCreation() {
        super.onCreation();

        KeyHandler.instance.add(listener = new GameActionListener(){
            @Override
            public void onPlayerMovement(Vector2 movement) {
                super.onPlayerMovement(movement);
                addForce(movement.nor().scl(acceleration));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeyHandler.instance.remove(listener);
    }

    @Override
    public void update() {
        super.update();
        //Logger.logInfo("pos->" + pos.toString());
    }


}
