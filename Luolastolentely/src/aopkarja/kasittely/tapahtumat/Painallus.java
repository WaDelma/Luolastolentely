package aopkarja.kasittely.tapahtumat;

import aopkarja.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;

/**
 * {@link Tapahtuma}, jonka hiiri luo kun sen painiketta painetaan
 * 
 * @author Antti
 */
public class Painallus implements Tapahtuma {

    private final int painike;
    private final Koordinaatti koordinaatit;

    public Painallus(int painike, Koordinaatti koordinaatit) {
        this.painike = painike;
        this.koordinaatit = koordinaatit;
    }

    @Override
    public Object[] getTieto() {
        return new Object[]{painike, koordinaatit};
    }
}
