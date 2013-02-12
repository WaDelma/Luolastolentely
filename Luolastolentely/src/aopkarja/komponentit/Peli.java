package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.UI.Renderoija;
import aopkarja.UI.renderoijat.PelaajaRenderoija;
import aopkarja.kasittely.Tapahtuma;

/**
 *
 * @author aopkarja
 */
public class Peli extends Komponentti {

    /**
     *
     * @param renderoija
     * @param omistaja
     */
    public Peli(Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        lisaa(new Alus(new PelaajaRenderoija(), this));
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        for (Komponentti komponentti : this.getLapset()) {
            komponentti.tapahtuu(tapahtuma);
        }
    }
}
