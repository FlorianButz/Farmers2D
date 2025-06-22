package test;

import java.awt.Color;
import java.util.Random;

public class GenPool {
    private int groesse;    // size 0-100
    private Color farbe;    // color of the plant
    private String name;    // name of the plant
    private int ertrag;     // yield 0-100

    private static final Random random = new Random();

    public GenPool(String name, Color farbe, int groesse, int ertrag) {
        this.name = name;
        this.farbe = farbe;
        this.groesse = groesse;
        this.ertrag = ertrag;
    }

    public int getGroesse() {
        return groesse;
    }

    public void setGroesse(int groesse) {
        this.groesse = groesse;
    }

    public Color getFarbe() {
        return farbe;
    }

    public void setFarbe(Color farbe) {
        this.farbe = farbe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getErtrag() {
        return ertrag;
    }

    public void setErtrag(int ertrag) {
        this.ertrag = ertrag;
    }

    public GenPool mix(GenPool parent2) {
        String childName = random.nextBoolean() ? name : parent2.name;

        //averaging RGB values
        int r = (farbe.getRed() + parent2.farbe.getRed()) / 2;
        int g = (farbe.getGreen() + parent2.farbe.getGreen()) / 2;
        int b = (farbe.getBlue() + parent2.farbe.getBlue()) / 2;
        Color childColor = new Color(r, g, b);

        int childGroesse = (groesse + parent2.groesse) / 2;
        int childErtrag = (ertrag + parent2.ertrag) / 2;

        // small mutations
        childGroesse = mutateValue(childGroesse, 0, 100);
        childErtrag = mutateValue(childErtrag, 0, 100);

        return new GenPool(childName, childColor, childGroesse, childErtrag);
    }

    private static int mutateValue(int value, int min, int max) {
        int mutation = random.nextInt(5) - 2; // mutation between -2 and +2
        int mutated = value + mutation;
        if (mutated < min) mutated = min;
        if (mutated > max) mutated = max;
        return mutated;
    }

    @Override
    public String toString() {
        return "GenPool{" +
                "name='" + name + '\'' +
                ", farbe=" + farbe +
                ", groesse=" + groesse +
                ", ertrag=" + ertrag +
                '}';
    }
}
