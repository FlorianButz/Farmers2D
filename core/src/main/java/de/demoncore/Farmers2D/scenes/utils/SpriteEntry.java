package de.demoncore.Farmers2D.scenes.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SpriteEntry {
    private Vector2 pos;
    private Sprite s;

    public SpriteEntry(Sprite s, Vector2 pos){
        this.s = s;
        this.pos = pos;
    }

    public Sprite getSprite() {
        return s;
    }

    public Vector2 getVector() {
        return pos;
    }
}
