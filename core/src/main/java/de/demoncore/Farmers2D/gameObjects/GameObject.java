package de.demoncore.Farmers2D.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.utils.RenderListener;
import de.demoncore.Farmers2D.utils.UtilityMethods;

import static de.demoncore.Farmers2D.utils.Resources.debugFont;

/**
 * Represents a basic game object with position, size, color, shape and simple physics.
 */
public class GameObject {

    public Vector2 pos;
    public Vector2 size;
    public Color color;
    public Vector2 velocity = Vector2.Zero.cpy();
    public boolean isDistanceCulled = false;
    private final Rectangle boundingBox = new Rectangle();

    private transient RenderListener rListener;

    public boolean collisionEnabled = true;

    /**
     * Creates a new GameObject instance.
     *
     * @param shapes the shape type of the object (e.g. Rectangle, Oval, etc.)
     * @param pos the position of the object in world coordinates
     * @param size the size of the object
     * @param color the color used for rendering the object
     */
    public GameObject(Vector2 pos, Vector2 size, Color color){
        this.pos = pos;
        this.size = size;
        this.color = color;

        rListener = new RenderListener(){
            @Override
            public void onRenderShapes(ShapeRenderer srLine, ShapeRenderer srFilled) {
                drawShapes(srLine, srFilled);
            }

            @Override
            public void onRenderBatch(SpriteBatch sb) {
                drawBatch(sb);
            }

            @Override
            public void onRenderDebug(ShapeRenderer sr, SpriteBatch sb){
                drawDebug(sb, sr);
            }

            @Override
            public void onRenderHUD(SpriteBatch sb, ShapeRenderer srLine, ShapeRenderer srFilled) {
                drawHUD(sb);
            }
        };

        Game.instance.addRenderListener(rListener);
    }

    public void drawDebug(SpriteBatch sb, ShapeRenderer sr){
        if(!shouldDraw()) return;

        String className = getClass().getSimpleName();
        if(debugFont != null) {
            debugFont.setColor(collisionEnabled ? Color.LIME : Color.RED);
            debugFont.draw(sb, className, pos.x - size.x / 2, pos.y + size.y + 12); // 12px über dem Shape
            debugFont.draw(sb, UtilityMethods.formatVector(pos, 2), pos.x - size.x / 2, pos.y + size.y + 24);
        }

    }

    public void drawShapes(ShapeRenderer srLine, ShapeRenderer srFilled) {
        if(!shouldDraw()) return;
        if(color == null) return;
        srFilled.setColor(color);
        srFilled.rect(pos.x, pos.y, size.x, size.y);
    }

    public void drawBatch(SpriteBatch sb) {}

    protected boolean shouldDraw(){
        return !isDistanceCulled && Game.instance.getScreen().screenObjects.contains(this);
    }

    public void drawHUD(SpriteBatch sb){}

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
