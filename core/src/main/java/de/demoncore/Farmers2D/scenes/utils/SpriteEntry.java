package de.demoncore.Farmers2D.scenes.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SpriteEntry {
    private Vector2 v;
    private Sprite s;

    public SpriteEntry(Sprite s, Vector2 v){
        this.s = s;
        this.v = v;
    }

    public Sprite getSprite() {
        return s;
    }

    public Vector2 getVector() {
        return v;
    }
}
