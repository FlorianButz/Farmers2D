package de.demoncore.Farmers2D.itemSystem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.GameObject;

/**
 * TEMPORARY NEEDED FOR THE QUESTS
 * NEEDS REWORK LATER
 */
public class Item extends GameObject {
    Sprite sprite;

    public int id = -1;
    public int stackSize = -1;

    /**
     * Creates a new Item instance.
     *
     * @param sprite the sprite of the item
     * @param pos    the position of the item in world coordinates
     */
    public Item(Sprite sprite, Vector2 pos) {
        super(pos, new Vector2(20, 20), Color.BLUE);
        this.sprite = sprite;

    }

    public Item(){
        super(Vector2.Zero.cpy(), Vector2.Zero.cpy(), Color.BLACK);
    }

    public Item(int id, int stackSize){
        super(Vector2.Zero.cpy(), Vector2.Zero.cpy(), Color.BLACK);
        this.id = id >= 0 ? id : -1;
        this.stackSize = stackSize > 0 ? stackSize : -1;
    }

}
