
package aopkarja.kasittely.tapahtumat;

import aopkarja.Koordinaatti;
import aopkarja.kasittelijat.fysiikka.FyysinenKappale;
import aopkarja.kasittely.Tapahtuma;

/**
 *
 * @author Antti
 */
public class TormaysTapahtuma extends Tapahtuma{
    private final FyysinenKappale kohde;
    private final Koordinaatti suunta;

    public TormaysTapahtuma(FyysinenKappale kohde, Koordinaatti suunta) {
        this.kohde = kohde;
        this.suunta = suunta;
    }

    @Override
    public Object[] getTieto() {
        return new Object[]{kohde, suunta};
    }
    
}