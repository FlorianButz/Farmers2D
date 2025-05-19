package de.demoncore.Farmers2D.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.scenes.utils.ShapeEntry;
import de.demoncore.Farmers2D.scenes.utils.Shapes;

public class GameObjects {

    private Shapes shapes;
    public Vector2 pos;
    public Vector2 size;
    public Color color;

    public boolean collisionEnabled = true;

    public GameObjects(Shapes shapes, Vector2 pos, Vector2 size, Color color){
        this.shapes = shapes;
        this.pos = pos;
        this.size = size;
        this.color = color;
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(pos.x, pos.y, size.x, size.y);
    }

    public ShapeEntry getShapeEntry(){
        return new ShapeEntry(shapes, pos, size, color);
    }

    public void onCreation(){}
    public void onDestroy(){}
    public void update(){}
}
