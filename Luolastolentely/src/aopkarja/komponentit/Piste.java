package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;

/**
 * {@link Komponentti}, joka on piste
 * 
 * @author aopkarja
 */
public class Piste extends Komponentti {

    /**
     *
     * @param renderoija
     * @param omistaja
     * @param koordinaatit
     */
    public Piste(Renderoija renderoija, Komponentti omistaja, Koordinaatti koordinaatit) {
        super(renderoija, omistaja);
        getAlue().lisaa(koordinaatit);
    }

    /**
     *
     * @param tapahtuma
     */
    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
    }
}
