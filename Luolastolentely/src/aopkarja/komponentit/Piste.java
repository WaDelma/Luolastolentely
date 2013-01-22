package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.UI.Renderoija;
import aopkarja.tapahtuma.Tapahtuma;

/**
 *
 * @author aopkarja
 */
public class Piste extends Komponentti {

    public Piste(Renderoija renderoija, Komponentti omistaja, Koordinaatti koordinaatit) {
        super(renderoija, omistaja);
        getAlue().lisaa(koordinaatit);
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
    }
}
