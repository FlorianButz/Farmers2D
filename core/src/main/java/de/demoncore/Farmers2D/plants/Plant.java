package de.demoncore.Farmers2D.plants;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.plants.gens.Genpool;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.UtilityMethods;

public class Plant extends GameObject {

    Genpool genpool;

    public Plant(Vector2 pos, Vector2 size, Genpool genpool) {
        super(pos, size, null);
        this.genpool = genpool;
        collisionEnabled = false;
    }

    public Plant(){
        super(null, null, null);
    }

    /**
     *Generate a new Plant based on the provided id values are a little bit randomised around a static base Value
     *
     * @param id    The id of the preset Plant to return
     * @return      New Plant with the exact id
     */
    public static Plant getNewPlant(int id, Vector2 pos){
        Plant returnPlant = new Plant(pos, null, null);

        int[] addValue = UtilityMethods.randomArray(3, 0, 10);

        switch (id){
            case 0:
                returnPlant.genpool = new Genpool(60 + addValue[0], 40 + addValue[1], 20 + addValue[2]);
                returnPlant.size = new Vector2(20, returnPlant.genpool.getNumericGenValue(0));
                returnPlant.color = Color.YELLOW;
                break;
            case 1:
                returnPlant.genpool = new Genpool(20 + addValue[0], 60 + addValue[1], 15 + addValue[2]);
                returnPlant.size = new Vector2(20, returnPlant.genpool.getNumericGenValue(0));
                returnPlant.color = Color.CYAN;
                break;
            default:
                Logger.logError("invalid Plant id->"+id, new IllegalArgumentException());
        }
        return returnPlant;
    }




}
