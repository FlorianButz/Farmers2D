package de.demoncore.Farmers2D.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.utils.interfaces.Interactable;

import java.util.ArrayList;

public class UtilityMethods {

    /**
     * Returns the intersection rectangle of two given rectangles.
     * If they do not overlap, returns null.
     *
     * @param a first rectangle
     * @param b second rectangle
     * @return the overlapping Rectangle area or null if no overlap
     */
    public static Rectangle getIntersection(Rectangle a, Rectangle b) {
        if (!a.overlaps(b)) return null;

        float x = Math.max(a.x, b.x);
        float y = Math.max(a.y, b.y);
        float width = Math.min(a.x + a.width, b.x + b.width) - x;
        float height = Math.min(a.y + a.height, b.y + b.height) - y;

        return new Rectangle(x, y, width, height);
    }

    public static void callInteractionOnObjects(){
        ArrayList<GameObject> objects = new ArrayList<>(Game.instance.getScreenObjects());

        for(GameObject obj : objects){
            if(obj.isDistanceCulled) continue;
            if(!(obj instanceof Interactable)) continue;
            Interactable intObj = (Interactable) obj;
            if(!intObj.canInteract()) continue;
            intObj.onInteraction();
        }
    }

    public static String formatVector(Vector2 v, int decimalPlaces){
        String format = "(%."+ decimalPlaces + "f, %." + decimalPlaces + "f)";
        return  String.format(format, v.x, v.y);
    }

    public static float getPercentage(float value, float min, float max){
        float percent;
        if (max != min) {
            if(max < min){
                percent = (value - max) * 100f / (min - max);
            }else {
                percent = (value - min) * 100f / (max - min);
            }
        } else {
            percent = 0f;
        }
        return percent;
    }

}
