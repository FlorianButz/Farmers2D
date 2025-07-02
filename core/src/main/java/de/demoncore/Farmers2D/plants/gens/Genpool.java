package de.demoncore.Farmers2D.plants.gens;

import com.badlogic.gdx.math.MathUtils;
import de.demoncore.Farmers2D.utils.Logger;

import java.util.ArrayList;

public class Genpool {

    public ArrayList<Gen> gens = new ArrayList<>();

    public Genpool(){}

    public Genpool(float height, float yield, float spreadRange){
        addGens(height, yield, spreadRange);
    }

    public Genpool(ArrayList<Gen> gens){
        this.gens = gens;
    }

    private void addGens(float height, float yield, float spreadRange){
        /*
        Gen id List:
            0 -> height
            1 -> yield
            2 -> spreadRange
        */
        gens.add(new NumericGen(height,0));
        gens.add(new NumericGen(yield, 1));
        gens.add(new NumericGen(spreadRange, 2));
    }

    public Genpool mix(Genpool pool){
        if(gens.size() != pool.gens.size()){
            Logger.logError("cannot mix pools because of unequal sizes", new IllegalArgumentException());
            return null;
        }

        Genpool newPool = new Genpool();

        for (int i = 0; i < gens.size(); i++) {
            Gen g = gens.get(i);
            newPool.gens.add(g.mix(pool.gens.get(i)));
        }

        return newPool;
    }

}
