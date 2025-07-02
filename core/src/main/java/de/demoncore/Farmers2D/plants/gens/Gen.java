package de.demoncore.Farmers2D.plants.gens;

public abstract class Gen<T> {

    public T value;
    public boolean dominant;

    public Gen(T value, boolean dominant){
        this.value = value;
        this.dominant = dominant;
    }

    public abstract Gen mix(Gen gen);

}
