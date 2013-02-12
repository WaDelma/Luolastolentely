package aopkarja.kasittely.tapahtumat;

import aopkarja.kasittely.Tapahtuma;

/**
 * {@link Tapahtuma}, jonka hiiri luo kun sen painiketta painetaan
 *
 * @author Antti
 */
public class Painallus extends Tapahtuma {

    private final Boolean alas;
    private final int painike;

    public Painallus(Tapahtuma tyyppi, int painike, boolean alas) {
        super(tyyppi);
        this.alas = alas;
        this.painike = painike;
    }

    @Override
    public Object[] getTieto() {
        return new Object[]{alas, painike};
    }
}
