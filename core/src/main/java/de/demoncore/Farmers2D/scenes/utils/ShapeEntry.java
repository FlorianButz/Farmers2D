package de.demoncore.Farmers2D.scenes.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class ShapeEntry {
    private Shapes shape;
    private Vector2 pos;
    private Vector2 size;
    private Color color;

    public ShapeEntry(Shapes shape, Vector2 pos, Vector2 size, Color color){
        this.shape = shape;
        this.pos = pos;
        this.size = size;
        this.color = color;
    }

    public ShapeEntry(Shapes shape, Vector2 pos, Vector2 size){
        this.shape = shape;
        this.pos = pos;
        this.size = size;
        this.color = Color.WHITE;
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
}
