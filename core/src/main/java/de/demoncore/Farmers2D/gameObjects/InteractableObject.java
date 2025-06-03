package de.demoncore.Farmers2D.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.interfaces.Interactable;

public class InteractableObject extends GameObject implements Interactable {

    public Runnable event;
    public float interactionRange;

    /**
     * Creates a new InteractableObject instance.
     *
     * @param pos              the position of the object in world coordinates
     * @param size             the size of the object
     * @param color            the color used for rendering the object
     * @param interactionRange the range to interact with the object
     * @param event            the action executing on interaction
     */
    public InteractableObject(Vector2 pos, Vector2 size, Color color, float interactionRange, Runnable event) {
        super(pos, size, color);
        this.interactionRange = interactionRange;
        this.event = event;
    }

    @Override
    public void onInteraction(){
        event.run();
        Logger.logInfo("interacted->"+this);
    };

    /**
     * Checks if the Player is in range of the Object for interaction
     *
     * @return true when player is in range
     */
    @Override
    public boolean canInteract(){
        Vector2 player = Player.instance.pos.cpy().add(Player.instance.size.cpy().scl(0.5f));
        return player.dst(pos.cpy().add(size.cpy().scl(0.5f))) <= interactionRange;
    }

    @Override
    public void drawShapes(ShapeRenderer srLine, ShapeRenderer srFilled) {
        if(!shouldDraw()) return;
        srFilled.setColor(color);
        srFilled.ellipse(pos.x, pos.y, size.x, size.y);
    }

    @Override
    public void drawDebug(SpriteBatch sb, ShapeRenderer sr) {
        if(!shouldDraw()) return;
        sr.setColor(Color.LIME);
        sr.ellipse(pos.x - 1, pos.y - 1, size.x + 2, size.y + 2);
        sr.setColor(Color.RED);
        sr.circle(pos.x + size.x / 2, pos.y + size.y / 2, interactionRange);

        super.drawDebug(sb, sr);
    }
}
