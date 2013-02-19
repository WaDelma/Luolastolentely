package aopkarja.alueet;

import aopkarja.Alue;
import aopkarja.Koordinaatti;

/**
 *
 * @author Antti
 */
public class Ympyra extends Alue {

    public static final double TARKKUUS = 9;
    private static final double TAU = Math.PI * 2;

    public Ympyra() {
        for (int i = 0; i < TARKKUUS; i++) {
            this.lisaa(new Koordinaatti(Math.cos(i / TARKKUUS * TAU), Math.sin(i / TARKKUUS * TAU)));
        }
    }
}
