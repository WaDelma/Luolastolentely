package aopkarja.tapahtuma.tapahtumat;

import aopkarja.tapahtuma.Tapahtuma;
import aopkarja.Koordinaatti;
import aopkarja.tapahtuma.Tapahtuma;

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
