package de.demoncore.Farmers2D.scenes.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.GameObject;


public class ShapeEntry {
    private Shapes shape;
    private Vector2 pos;
    private Vector2 size;
    private Color color;
    private GameObject object;

    public ShapeEntry(Shapes shape, Vector2 pos, Vector2 size, Color color, GameObject object){
        this.shape = shape;
        this.pos = pos;
        this.size = size;
        this.color = color;
        this.object = object;
    }

    public ShapeEntry(Shapes shape, Vector2 pos, Vector2 size, Color color){
        this(shape, pos, size, color, null);
    }

    public ShapeEntry(Shapes shape, Vector2 pos, Vector2 size){
        this(shape, pos, size, Color.WHITE, null);
    }

    public ShapeEntry(Shapes shape, Vector2 pos, Vector2 size, GameObject object){
        this(shape, pos, size, Color.WHITE, object);
    }

    public Shapes getShape() {
        return shape;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public GameObject getGameObject() {
        return object;
    }
}
