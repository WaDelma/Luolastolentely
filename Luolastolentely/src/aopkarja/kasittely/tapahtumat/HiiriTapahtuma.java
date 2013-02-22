
package aopkarja.kasittely.tapahtumat;

import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;

/**
 *
 * @author aopkarja
 */
public class HiiriTapahtuma extends Tapahtuma{
    private final Koordinaatti koordinaatti;

    public HiiriTapahtuma(Koordinaatti koordinaatti) {
        this.koordinaatti = koordinaatti;
    }

    @Override
    public Object[] getTieto() {
        return new Object[]{koordinaatti};
    }
}
