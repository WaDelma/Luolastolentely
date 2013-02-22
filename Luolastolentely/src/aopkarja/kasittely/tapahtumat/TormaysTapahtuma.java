
package aopkarja.kasittely.tapahtumat;

import aopkarja.fysiikka.FyysinenKappale;
import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;

/**
 *
 * @author Antti
 */
public class TormaysTapahtuma extends Tapahtuma{
    private final FyysinenKappale kohde;
    private final Koordinaatti suunta;
    private final FyysinenKappale toinenKappale;

    public TormaysTapahtuma(FyysinenKappale kohde, FyysinenKappale toinenKappale, Koordinaatti suunta) {
        this.kohde = kohde;
        this.suunta = suunta;
        this.toinenKappale = toinenKappale;
    }

    @Override
    public Object[] getTieto() {
        return new Object[]{kohde, toinenKappale, suunta};
    }
    
}
