package de.demoncore.Farmers2D.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.Game;
import de.demoncore.Farmers2D.scenes.utils.Shapes;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.UtilityMethodes;

import java.util.ArrayList;
import java.util.List;

public class RigidBody extends GameObject{

    protected float acceleration = 2f;
    public float friction = 0.875f; // Friction factor
    protected float moveSpeed = 0.25f;
    private Vector2 lastPosition;
    // Minimum velocity threshold to avoid tiny movements
    private static final float VELOCITY_THRESHOLD = 0.01f;

    public RigidBody(Shapes shapes, Vector2 pos, Vector2 size, Color color) {
        super(shapes, pos, size, color);
    }

    private void moveWithCollision(Vector2 moveVector) {
        // Scale the movement by speed factor
        Vector2 scaledMove = moveVector.scl(moveSpeed);

        // Apply the movement
        pos = pos.add(scaledMove);

        // Check for collisions after movement
        GameObject collidingObject = getCollidingObject();

        // If there's a collision, handle it
        if (collidingObject != null) {
            handleCollision(collidingObject, moveVector);
        }
    }

    private GameObject getCollidingObject() {
        List<GameObject> objs = new ArrayList<>(Game.instance.getScreenObjects());

        for (GameObject g : objs) {
            if (g == null || !g.collisionEnabled || g == this) continue;

            Rectangle thisObj = getBoundingBox();
            Rectangle otherObj = g.getBoundingBox();

            if (thisObj.overlaps(otherObj)) {
                return g;
            }
        }

        return null;
    }

    private void handleCollision(GameObject collidingObject, Vector2 moveVector) {
        Rectangle thisObj = getBoundingBox();
        Rectangle otherObj = collidingObject.getBoundingBox();
        Rectangle intersection = UtilityMethodes.getIntersection(thisObj, otherObj);

        // Calculate push vector based on intersection and movement direction
        Vector2 pushVector = calculatePushVector(intersection, moveVector);

        // Move object out of collision
        pos = pos.add(pushVector);

        // Stop velocity in the direction of collision
        if (Math.abs(pushVector.x) > 0) {
            velocity.x = 0;
        }
        if (Math.abs(pushVector.y) > 0) {
            velocity.y = 0;
        }

        // Call collision callbacks
        onCollision(thisObj, otherObj);
        onCollision(this, collidingObject);
    }

    private Vector2 calculatePushVector(Rectangle intersection, Vector2 moveVector) {
        float pushX = 0;
        float pushY = 0;

        // Determine which axis has the smaller intersection and prioritize that for resolution
        // This creates more natural collision responses
        if (intersection.width < intersection.height) {
            // X-axis collision
            pushX = (moveVector.x > 0) ? -intersection.width : intersection.width;
        } else {
            // Y-axis collision
            pushY = (moveVector.y > 0) ? -intersection.height : intersection.height;
        }

        return new Vector2(pushX, pushY);
    }

    protected void onCollision(Rectangle thisObject, Rectangle otherObject) {
        // Override in subclasses
    }

    protected void onCollision(GameObject thisObject, GameObject otherObject) {
        // Override in subclasses
    }

    public void addForce(Vector2 force) {
        velocity = velocity.add(force);
    }

    @Override
    public void update() {
        super.update();
        //if(GameLogic.IsGamePaused()) return;

        // Store the last position before movement
        lastPosition = new Vector2(pos.x, pos.y);

        // Handle movement with separate X and Y passes to prevent diagonal issues
        if (Math.abs(velocity.x) > VELOCITY_THRESHOLD || Math.abs(velocity.y) > VELOCITY_THRESHOLD) {
            // Handle X movement first
            moveWithCollision(new Vector2(velocity.x, 0));

            // Then handle Y movement
            moveWithCollision(new Vector2(0, velocity.y));

            velocity = velocity.scl(friction);
        }
    }
}
