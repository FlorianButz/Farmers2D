package de.demoncore.Farmers2D.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.scenes.utils.ShapeEntry;
import de.demoncore.Farmers2D.scenes.utils.Shapes;

/**
 * Represents a basic game object with position, size, color, shape and simple physics.
 */
public class GameObject {

    private Shapes shapes;
    public Vector2 pos;
    public Vector2 size;
    public Color color;
    public Vector2 velocity = Vector2.Zero.cpy();
    public boolean isDistanceCulled = false;
    private final Rectangle boundingBox = new Rectangle();

    public boolean collisionEnabled = true;

    /**
     * Creates a new GameObject instance.
     *
     * @param shapes the shape type of the object (e.g. Rectangle, Oval, etc.)
     * @param pos the position of the object in world coordinates
     * @param size the size of the object
     * @param color the color used for rendering the object
     */
    public GameObject(Shapes shapes, Vector2 pos, Vector2 size, Color color){
        this.shapes = shapes;
        this.pos = pos;
        this.size = size;
        this.color = color;
    }

    /**
     * Returns a bounding rectangle used for collision or visibility checks.
     *
     * @return the bounding rectangle of the object
     */
    public Rectangle getBoundingBox() {
        boundingBox.set(pos.x, pos.y, size.x, size.y);
        return boundingBox;
    }

    /**
     * Converts the game object to a ShapeEntry for rendering.
     *
     * @return a ShapeEntry with this object's properties
     */
    public ShapeEntry getShapeEntry(){
        return new ShapeEntry(shapes, pos, size, color, this);
    }

    /**
     * Called when the object is added to a screen or scene.
     * Override this to initialize logic.
     */
    public void onCreation(){}

    /**
     * Called when the object is removed from a screen or scene.
     * Override this to clean up resources or logic.
     */
    public void onDestroy(){}

    /**
     * Called every frame to update the object's logic.
     * Override this to implement behavior.
     */
    public void update(){}

    /**
     * Checks whether this object is currently visible inside the camera's viewport.
     * If it is not visible, it can be culled (not rendered).
     *
     * @param cameraViewport the current camera's visible area
     * @return true if the object is within the viewport, false otherwise
     */
    public boolean checkDistanceCulled(Rectangle cameraViewport) {
        return cameraViewport.overlaps(getBoundingBox());
    }
}
