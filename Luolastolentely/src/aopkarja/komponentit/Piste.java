package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.UI.Renderoija;
import aopkarja.kasittely.Tapahtuma;

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
