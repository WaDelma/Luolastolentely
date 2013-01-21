
package aopkarja.UI;

import aopkarja.Koordinaatit;

/**
 *
 * @author Antti
 */
public class Painallus implements Tapahtuma {
    private final int painike;
    private final Koordinaatit koordinaatit;
    public Painallus(int painike, Koordinaatit koordinaatit){
        this.painike = painike;
        this.koordinaatit = koordinaatit;
    }

    @Override
    public Object[] getTieto() {
        return new Object[]{painike, koordinaatit};
    }
}
