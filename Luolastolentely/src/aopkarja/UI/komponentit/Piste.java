package aopkarja.UI.komponentit;

import aopkarja.UI.Komponentti;
import aopkarja.UI.Renderoija;
import aopkarja.fysiikka.asiat.Koordinaatti;
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
        getAlue().lisaa(koordinaatit);
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
