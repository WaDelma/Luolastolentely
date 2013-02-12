package aopkarja.kasittelijat.fysiikka;

import aopkarja.Alue;
import aopkarja.Koordinaatti;

/**
 *
 * @author aopkarja
 */
public class FyysinenKappale {

    private Koordinaatti voima;
    private final Alue alue;
    private double paino;

    public double getPaino() {
        return paino;
    }

    public FyysinenKappale(Alue alue) {
        this.alue = alue;
    }

    public Alue getAlue() {
        return alue;
    }

    public void setPaino(double paino) {
        this.paino = paino;
    }

    //TODO:
    public void lisaaPysyvaVoima(int... koordinaatti) {
        //voima.siirra(koordinaatti);
    }
}
