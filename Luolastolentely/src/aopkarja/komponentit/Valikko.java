package aopkarja.komponentit;

import aopkarja.Alue;
import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.hoitajat.LeikkaustenHoitaja;
import aopkarja.UI.Renderoija;
import aopkarja.tapahtuma.Tapahtuma;
import aopkarja.tapahtuma.tapahtumat.Painallus;

/**
 *
 * @author aopkarja
 */
public class Valikko extends Komponentti {

    public Valikko(Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        Object[] tieto = tapahtuma.getTieto();
        if (tapahtuma instanceof Painallus) {
            for (Komponentti komponentti : this.getLapset()) {
                if (LeikkaustenHoitaja.leikkaako(komponentti, new Piste(null, null, (Koordinaatti) tieto[1]))) {
                    komponentti.tapahtuu(tapahtuma);
                }
            }
        }
    }
}
