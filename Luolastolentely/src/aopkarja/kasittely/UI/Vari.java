
package aopkarja.kasittely.UI;

/**
 *
 * @author aopkarja
 */
public class Vari {
    private double r, g, b;

    public Vari(double punainen, double vihrea, double sininen) {
        this.r = punainen;
        this.g = vihrea;
        this.b = sininen;
    }

    public double getPunainen() {
        return r;
    }

    public double getVihrea() {
        return g;
    }

    public double getSininen() {
        return b;
    }
}
