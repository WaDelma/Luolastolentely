package aopkarja.UI.komponentit.moodit;

import aopkarja.Luolastolentely;
import aopkarja.UI.Komponentti;
import aopkarja.UI.Moodi;
import aopkarja.UI.Renderoija;
import aopkarja.UI.Vari;
import aopkarja.UI.komponentit.LopetusPainike;
import aopkarja.UI.komponentit.Piste;
import aopkarja.UI.Siirtyma;
import aopkarja.UI.komponentit.SiirtymaPainike;
import aopkarja.fysiikka.FysiikanKasittelija;
import aopkarja.fysiikka.LeikkaustenHoitaja;
import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.tapahtumat.HiiriTapahtuma;

/**
 * Valikko moodi
 *
 * @author aopkarja
 */
public class Valikko extends Moodi {
    
    public Valikko() {
        super(new Renderoija());
        lisaa(new FysiikanKasittelija(Luolastolentely.getInstanssi().getTapahtumienKasittelija()));
        lisaa(new SiirtymaPainike("Aloita", new Siirtyma(Peli.class, 1), 400, 450, 100, 25, this).setVari(new Vari(0, 1, 0), new Vari(1, 0, 1)));
        lisaa(new LopetusPainike("Lopeta", 400, 400, 100, 25, this).setVari(new Vari(0, 0, 0), new Vari(1, 1, 1)));
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
                    komponentti.tapahtuu(tapahtuma);
                }
            }
        }
    }

    @Override
    public Vari getVari() {
        return new Vari(1.0, 0.5, 0.0);
    }
}
