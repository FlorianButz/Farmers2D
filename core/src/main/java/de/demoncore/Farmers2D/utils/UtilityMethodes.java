package de.demoncore.Farmers2D.utils;

import com.badlogic.gdx.math.Rectangle;

public class UtilityMethodes {

    public static Rectangle getIntersection(Rectangle a, Rectangle b) {
        if (!a.overlaps(b)) return null;

        float x = Math.max(a.x, b.x);
        float y = Math.max(a.y, b.y);
        float width = Math.min(a.x + a.width, b.x + b.width) - x;
        float height = Math.min(a.y + a.height, b.y + b.height) - y;

        return new Rectangle(x, y, width, height);
    }

}
