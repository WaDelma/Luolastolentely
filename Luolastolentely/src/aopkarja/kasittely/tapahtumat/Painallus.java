package aopkarja.kasittely.tapahtumat;

import aopkarja.kasitttely.Tapahtuma;
import aopkarja.Koordinaatti;
import aopkarja.kasitttely.Tapahtuma;

/**
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
