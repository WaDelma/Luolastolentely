
package aopkarja.kasittely.tapahtumat;

import aopkarja.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;

/**
 *
 * @author aopkarja
 */
public abstract class HiiriTapahtuma implements Tapahtuma{
    private final int painike;
    private final Koordinaatti koordinaatti;

    public HiiriTapahtuma(int painike, Koordinaatti koordinaatti) {
        this.painike = painike;
        this.koordinaatti = koordinaatti;
    }

    @Override
    public Object[] getTieto() {
        return new Object[]{painike, koordinaatti};
    }
}
