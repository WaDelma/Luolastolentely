package aopkarja.fysiikka.asiat;

/**
 * Alue, joka täytetään yksikkö ympyrällä
 * 
 * @author Antti
 */
public class Ympyra extends Alue {

    public static final double TARKKUUS = 18;
    private static final double TAU = Math.PI * 2;

    public Ympyra() {
        double ekaCos = 1;
        double ekaSin = 0;
        for (int i = 1; i <= TARKKUUS; i++) {
            double tokaCos = Math.cos(i / TARKKUUS * TAU);
            double tokaSin = Math.sin(i / TARKKUUS * TAU);
            lisaa(new Koordinaatti());
            lisaa(new Koordinaatti(ekaCos, ekaSin));
            lisaa(new Koordinaatti(tokaCos, tokaSin));
            ekaCos = tokaCos;
            ekaSin = tokaSin;
        }
    }
}
