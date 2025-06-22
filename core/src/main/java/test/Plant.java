package test;

import java.awt.*;

public class Plant {
    public GenPool genpool;

    public Plant(GenPool genpool) {
        this.genpool = genpool;
    }

    // Für schnellen Zugriff auf Name und Farbe, kannst du Getter schreiben oder direkt zugreifen
    public String getName() {
        return genpool.getName();
    }

    public Color getColor() {
        return genpool.getFarbe();
    }
}
