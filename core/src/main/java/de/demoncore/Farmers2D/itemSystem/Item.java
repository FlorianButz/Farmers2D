package de.demoncore.Farmers2D.itemSystem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.scenes.utils.Shapes;
import de.demoncore.Farmers2D.scenes.utils.SpriteEntry;

/**
 * TEMPORARY NEEDED FOR THE QUESTS
 * NEEDS REWORK LATER
 */
public class Item extends GameObject {
    Sprite sprite;

    /**
     * Creates a new Item instance.
     *
     * @param sprite the sprite of the item
     * @param pos    the position of the item in world coordinates
     */
    public Item(Sprite sprite, Vector2 pos) {
        super(Shapes.Rectangle, pos, new Vector2(20, 20), Color.BLUE);
        this.sprite = sprite;
    }

    public SpriteEntry getSpriteEntry(){
        return new SpriteEntry(sprite, pos);
    }

}
