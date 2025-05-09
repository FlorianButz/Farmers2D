package de.demoncore.gameObjects;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import de.demoncore.game.GameLogic;
import de.demoncore.game.GameObject;
import de.demoncore.game.SceneManager;
import de.demoncore.utils.Vector3;

public class RigidBody extends GameObject {
    public Vector3 velocity = Vector3.zero();
    public Vector3 lastPosition;
    
    public float friction = 0.875f; // Friction factor
    public float moveSpeed = 0.27f; // Movement speed factor
    
    // Minimum velocity threshold to avoid tiny movements
    private static final float VELOCITY_THRESHOLD = 0.01f;
    
    public RigidBody(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        lastPosition = new Vector3(posX, posY);
    }
    
    @Override
    public void update() {
        super.update();
        if(GameLogic.IsGamePaused()) return;
        
        // Store the last position before movement
        lastPosition = new Vector3(position.x, position.y);
        
        // Handle movement with separate X and Y passes to prevent diagonal issues
        if (Math.abs(velocity.x) > VELOCITY_THRESHOLD || Math.abs(velocity.y) > VELOCITY_THRESHOLD) {
            // Handle X movement first
            moveWithCollision(new Vector3(velocity.x, 0, 0));
            
            // Then handle Y movement
            moveWithCollision(new Vector3(0, velocity.y, 0));
            
            // Apply friction
            velocity = velocity.multiply(friction);
        }
    }
    
    private void moveWithCollision(Vector3 moveVector) {
        // Scale the movement by speed factor
        Vector3 scaledMove = moveVector.multiply(moveSpeed);
        
        // Apply the movement
        position = position.add(scaledMove);
        
        // Check for collisions after movement
        GameObject collidingObject = getCollidingObject();
        
        // If there's a collision, handle it
        if (collidingObject != null) {
            handleCollision(collidingObject, moveVector);
        }
    }
    
    private GameObject getCollidingObject() {
        List<GameObject> objs = new ArrayList<>(SceneManager.getActiveScene().getSceneObjects());
        
        for (GameObject g : objs) {
            if (g == null || !g.collisionEnabled || g == this) continue;
            
            Rectangle thisObj = getBoundingBox();
            Rectangle otherObj = g.getBoundingBox();
            
            if (thisObj.intersects(otherObj)) {
                return g;
            }
        }
        
        return null;
    }
    
    private void handleCollision(GameObject collidingObject, Vector3 moveVector) {
        Rectangle thisObj = getBoundingBox();
        Rectangle otherObj = collidingObject.getBoundingBox();
        Rectangle intersection = thisObj.intersection(otherObj);
        
        // Calculate push vector based on intersection and movement direction
        Vector3 pushVector = calculatePushVector(intersection, moveVector);
        
        // Move object out of collision
        position = position.add(pushVector);
        
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
    
    private Vector3 calculatePushVector(Rectangle intersection, Vector3 moveVector) {
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
        
        return new Vector3(pushX, pushY);
    }
    
    protected void onCollision(Rectangle thisObject, Rectangle otherObject) {
        // Override in subclasses
    }
    
    protected void onCollision(GameObject thisObject, GameObject otherObject) {
        // Override in subclasses
    }
    
    public void addForce(Vector3 force) {
        velocity = velocity.add(force);
    }
}