package de.demoncore.Farmers2D.plants.gens;

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

    /**
     *  Gen id List:<pre>
     *  0 -> height             numeric
     *  1 -> yield              numeric
     *  2 -> spreadRange        numeric
     *  </pre>
     * @param id    The id of the gen to get the value of
     * @return      The value of the gen with the provided id <br> Failsafe value -1
     */
    public float getNumericGenValue(int id){
        for (Gen g : gens){
            if(g instanceof NumericGen) {
                NumericGen numGen = (NumericGen) g;
                if (numGen.id == id) return (float) g.value;
            }
        }
        return -1;
    }

}
