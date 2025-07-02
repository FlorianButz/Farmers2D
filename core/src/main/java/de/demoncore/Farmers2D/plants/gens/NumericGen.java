package de.demoncore.Farmers2D.plants.gens;

import com.badlogic.gdx.math.MathUtils;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.UtilityMethods;

public class NumericGen extends Gen<Float>{

    private float minValue = 1;
    private float maxValue = 100;
    public int id = -1;

    public NumericGen(float value, boolean dominant, int id) {
        super(value, dominant);
        this.id = id;
    }

    public NumericGen(float value, int id) {
        this(value, MathUtils.randomBoolean(), id);
    }

    public NumericGen(float value, boolean dominant, float minValue, float maxValue, int id) {
        this(value, dominant, id);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private NumericGen(){
        super(0f, false);
    }


    @Override
    public NumericGen mix(Gen gen) {
        // Failsafe to stop invalid gen types
        if(!(gen instanceof NumericGen)){
            Logger.logError("invalid gen to mix with numericGen", new IllegalArgumentException());
            return null;
        }

        // Initialize variables and cast gen to NumericGen
        NumericGen temp = new NumericGen();
        NumericGen secondGen = (NumericGen) gen;
        NumericGen baseGen;

        // Calculate the absolute percentage difference between the two values
        float percent1 = UtilityMethods.getPercentage(value, minValue, maxValue);
        float percent2 = UtilityMethods.getPercentage(secondGen.value, secondGen.minValue, secondGen.maxValue);
        float diff = Math.abs(percent1 - percent2);

        // Decide which gene to use as base for mutation
        if((dominant && secondGen.dominant) || (!dominant && !secondGen.dominant)){ // If both genes have the same dominance, randomly pick one as base
            baseGen = MathUtils.randomBoolean() ? this : secondGen;
            temp.dominant = dominant;
        }else{
            baseGen = dominant ? this : secondGen;  // If one gene is dominant, use it as base and randomly assign dominance to child
            temp.dominant = MathUtils.randomBoolean();
        }

        if(id != secondGen.id){
            Logger.logWarning("two plant with unequal id's mixed");
        }

        // Apply values and mutate the value field based on baseGen
        temp.value = mutateValue(baseGen.value, baseGen.minValue, baseGen.maxValue, diff);
        temp.minValue = baseGen.minValue;
        temp.maxValue = baseGen.maxValue;
        temp.id = baseGen.id;

        return temp;
    }

    private float mutateValue(float value, float minValue, float maxValue, float absDifference){
        float mutationValue = MathUtils.random(-absDifference, absDifference);
        float factor = 1 + mutationValue / 100;
        float newValue = value * factor;
        if(newValue < minValue) newValue = minValue;
        if(newValue > maxValue) newValue = maxValue;
        return newValue;
    }

}
