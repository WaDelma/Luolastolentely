package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.UI.Renderoija;
import aopkarja.UI.renderoijat.PainikeRenderoija;
import aopkarja.hoitajat.LeikkaustenHoitaja;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.tapahtumat.HiiriTapahtuma;

/**
 * Valikko moodi(kts. {@link aopkarja.UI.UIKasittelija})/{@link Komponentti} 
 * @author aopkarja
 */
public class Valikko extends Komponentti {

    /**
     *
     * @param renderoija
     * @param omistaja
     */
    public Valikko(Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        lisaa(new AloitusPainike("Aloita", 400, 400, 100, 25, new PainikeRenderoija(), this));
    }

    /**
     *
     * @param tapahtuma
     */
    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        if (tapahtuma.getTyyppi() instanceof HiiriTapahtuma) {
            Object[] tyyppiTieto = tapahtuma.getTyyppi().getTieto();
            for (Komponentti komponentti : this.getLapset()) {
                if (LeikkaustenHoitaja.leikkaako(komponentti.getAlue(), new Piste(null, null, (Koordinaatti) tyyppiTieto[0]).getAlue())) {
                    //System.out.println("leikkaa" + tapahtuma);
                    komponentti.tapahtuu(tapahtuma);
                }
            }
        }
    }
}
