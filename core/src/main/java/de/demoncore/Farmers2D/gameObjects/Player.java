package de.demoncore.Farmers2D.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.utils.GameActionListener;
import de.demoncore.Farmers2D.utils.KeyHandler;

/**
 * Represents the player character in the game.
 * The player is a {@link RigidBody} that reacts to keyboard input.
 */
public class Player extends RigidBody {

    /**
     * Singleton instance of the player.
     */
    public static Player instance;

    public GameActionListener listener;

    /**
     * Creates a new Player instance at the specified position and size.
     *
     * @param pos  the position of the player
     * @param size the size of the player
     */
    public Player(Vector2 pos, Vector2 size) {
        super(pos, size, Color.WHITE);
        instance = this;
    }

    @Override
    public void onCreation() {
        super.onCreation();

        KeyHandler.instance.add(listener = new GameActionListener() {
            @Override
            public void onPlayerMovement(Vector2 movement) {
                super.onPlayerMovement(movement);
                addForce(movement.nor().scl(acceleration));
            }
        }, "Player");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        KeyHandler.instance.remove(listener, "player");
    }
}
