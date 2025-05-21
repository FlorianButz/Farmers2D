package de.demoncore.Farmers2D.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.Player;

import java.util.ArrayList;

public class KeyHandler extends InputAdapter {

    public static KeyHandler instance;

    ArrayList<Integer> pressedKeys = new ArrayList<>();
    public boolean isAnyKeyPressed = false;

    boolean isCtrlPressed = false;
    Vector2 playerInput = Vector2.Zero.cpy();

    ArrayList<GameActionListener> listeners = new ArrayList<>();

    public KeyHandler() {
        instance = this;
    }

    @Override
    public boolean keyDown(int keycode) {
        Vector2 temp = playerInput.cpy();
        if (pressedKeys.contains(keycode)) return super.keyDown(keycode); // Locks the Input to prevent spam
        pressedKeys.add(keycode); // lock

        isCtrlPressed = pressedKeys.contains(Input.Keys.CONTROL_LEFT);

        for (GameActionListener listener : new ArrayList<>(listeners)) {
            if (keycode == Input.Keys.ESCAPE)
                listener.onEscapePressed();
            if (keycode == Input.Keys.E)
                listener.onInteractionKeyPressed();
            if (keycode == Input.Keys.SPACE)
                listener.onSpaceKeyPressed();
        }

        if (keycode == Input.Keys.A) {
            playerInput.sub(new Vector2(1, 0));
        } else if (keycode == Input.Keys.D) {
            playerInput.add(new Vector2(1, 0));
        } else if (keycode == Input.Keys.W) {
            playerInput.add(new Vector2(0, 1));
        } else if (keycode == Input.Keys.S) {
            playerInput.sub(new Vector2(0, 1));
        }

        isAnyKeyPressed = pressedKeys.size() != 0;
        if (temp.dst(playerInput) != 0) {
            update();
        }

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        isCtrlPressed = !pressedKeys.contains(Input.Keys.CONTROL_LEFT);

        if (pressedKeys.contains(keycode)) {
            pressedKeys.remove((Integer) keycode);
        } // removes the lock

        if (keycode == Input.Keys.A) {
            playerInput.add(new Vector2(1, 0));
        } else if (keycode == Input.Keys.D) {
            playerInput.sub(new Vector2(1, 0));
        } else if (keycode == Input.Keys.W) {
            playerInput.sub(new Vector2(0, 1));
        } else if (keycode == Input.Keys.S) {
            playerInput.add(new Vector2(0, 1));
        }

        update();
        isAnyKeyPressed = pressedKeys.size() != 0;
        return super.keyUp(keycode);
    }

    public void add(GameActionListener gameActionListener) {
        listeners.add(gameActionListener);
    }

    public void remove(GameActionListener listener) {
        listeners.remove(listener);
    }

    public void update() {
        ArrayList<GameActionListener> l = new ArrayList<>(listeners);
        for (GameActionListener g : l) {
            g.onPlayerMovement(playerInput.cpy());
        }
    }
}
