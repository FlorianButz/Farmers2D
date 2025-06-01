package de.demoncore.Farmers2D.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Base class for handling game-related actions.
 * Methods can be overridden to respond to specific input events.
 */
public abstract class GameActionListener {

    /**
     * Called when the Escape key is pressed.
     */
    public void onEscapePressed() {}

    /**
     * Called when the interaction key is pressed.
     */
    public void onInteractionKeyPressed() {}

    /**
     * Called when the space key is pressed.
     */
    public void onSpaceKeyPressed() {}

    /**
     * Called when the player moves.
     * @param movement the movement vector (direction and magnitude)
     */
    public void onPlayerMovement(Vector2 movement) {}

    public void onTabPressed() {};
}

