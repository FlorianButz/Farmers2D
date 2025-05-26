package de.demoncore.Farmers2D.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;
import de.demoncore.Farmers2D.Game;
import de.demoncore.Farmers2D.scenes.utils.Shapes;
import de.demoncore.Farmers2D.utils.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

public class RigidBody extends GameObject {

    protected float acceleration = 2f;
    public float friction = 0.875f;
    protected float moveSpeed = 0.25f;
    private Vector2 lastPosition;
    private static final float VELOCITY_THRESHOLD = 0.01f;

    public RigidBody(Shapes shapes, Vector2 pos, Vector2 size, Color color) {
        super(shapes, pos, size, color);
    }

    private void moveWithCollision(Vector2 moveVector) {
        Vector2 scaledMove = moveVector.scl(moveSpeed);
        pos.add(scaledMove);

        GameObject collidingObject = getCollidingObject();
        if (collidingObject != null) {
            handleCollision(collidingObject, moveVector);
        }
    }

    private GameObject getCollidingObject() {
        List<GameObject> objs = new ArrayList<>(Game.instance.getScreenObjects());

        Rectangle thisObj = getBoundingBox();

        for (GameObject g : objs) {
            if (g == null || !g.collisionEnabled || g == this) continue;

            Rectangle otherObj = g.getBoundingBox();

            if (Intersector.overlaps(thisObj, otherObj)) {
                return g;
            }
        }

        return null;
    }


    private void handleCollision(GameObject collidingObject, Vector2 moveVector) {
        Rectangle thisObj = getBoundingBox();
        Rectangle otherObj = collidingObject.getBoundingBox();
        Rectangle intersection = UtilityMethods.getIntersection(thisObj, otherObj);

        Vector2 pushVector = calculatePushVector(intersection, moveVector);
        pos.add(pushVector);

        if (Math.abs(pushVector.x) > 0) velocity.x = 0;
        if (Math.abs(pushVector.y) > 0) velocity.y = 0;

        onCollision(thisObj, otherObj);
        onCollision(this, collidingObject);
    }

    private Vector2 calculatePushVector(Rectangle intersection, Vector2 moveVector) {
        float pushX = 0;
        float pushY = 0;

        if (intersection.width < intersection.height) {
            pushX = (moveVector.x > 0) ? -intersection.width : intersection.width;
        } else {
            pushY = (moveVector.y > 0) ? -intersection.height : intersection.height;
        }

        return new Vector2(pushX, pushY);
    }

    protected void onCollision(Rectangle thisObject, Rectangle otherObject) {}
    protected void onCollision(GameObject thisObject, GameObject otherObject) {}

    public void addForce(Vector2 force) {
        velocity.add(force);
    }

    @Override
    public void update() {
        super.update();
        if (Game.instance.isPaused) return;

        lastPosition = new Vector2(pos);

        if (Math.abs(velocity.x) > VELOCITY_THRESHOLD || Math.abs(velocity.y) > VELOCITY_THRESHOLD) {
            moveWithCollision(new Vector2(velocity.x, 0));
            moveWithCollision(new Vector2(0, velocity.y));

            velocity.scl(MathUtils.clamp(friction, 0f, 1f));
        }
    }
}
