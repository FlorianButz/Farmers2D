package de.demoncore.Farmers2D.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.scenes.utils.ShapeEntry;
import de.demoncore.Farmers2D.scenes.utils.Shapes;
import de.demoncore.Farmers2D.utils.Logger;

public class GameObject {

    private Shapes shapes;
    public Vector2 pos;
    public Vector2 size;
    public Color color;
    public Vector2 velocity = Vector2.Zero.cpy();
    public boolean isDistanceCulled = false;

    public boolean collisionEnabled = true;

    public GameObject(Shapes shapes, Vector2 pos, Vector2 size, Color color){
        this.shapes = shapes;
        this.pos = pos;
        this.size = size;
        this.color = color;
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(pos.x, pos.y, size.x, size.y);
    }

    public ShapeEntry getShapeEntry(){
        return new ShapeEntry(shapes, pos, size, color, this);
    }

    public void onCreation(){}
    public void onDestroy(){}
    public void update(){}

    public boolean checkDistanceCulled(Rectangle cameraViewport) {
        //Logger.logInfo("Culled: " + isDistanceCulled + " BoundingBox: " + getBoundingBox());
        return cameraViewport.overlaps(getBoundingBox());
    }
}
