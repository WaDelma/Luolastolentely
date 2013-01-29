package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.UI.Renderoija;
import aopkarja.UI.renderoijat.PainikeRenderoija;
import aopkarja.hoitajat.LeikkaustenHoitaja;
import aopkarja.kasittely.tapahtumat.Painallus;
import aopkarja.kasitttely.Tapahtuma;

/**
 *
 * @author aopkarja
 */
public class Valikko extends Komponentti {

    public Valikko(Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        lisaa(new Painike(400, 400, 100, 25, new PainikeRenderoija(), this));
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        Object[] tieto = tapahtuma.getTieto();
        if (tapahtuma instanceof Painallus) {
            for (Komponentti komponentti : this.getLapset()) {
                if (LeikkaustenHoitaja.leikkaako(komponentti.getAlue(), new Piste(null, null, (Koordinaatti) tieto[1]).getAlue())) {
                    System.out.println("leikkaa");
                    komponentti.tapahtuu(tapahtuma);
                }
            }
        }
    }
}
