package aopkarja.komponentit.moodit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.Moodi;
import aopkarja.hoitajat.LeikkaustenHoitaja;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.kasittely.UI.Vari;
import aopkarja.kasittely.UI.renderoijat.PainikeRenderoija;
import aopkarja.kasittely.UI.renderoijat.PeliRenderoija;
import aopkarja.kasittely.UI.renderoijat.ValikkoRenderoija;
import aopkarja.kasittely.tapahtumat.HiiriTapahtuma;
import aopkarja.komponentit.LopetusPainike;
import aopkarja.komponentit.Piste;
import aopkarja.komponentit.SiirtymaPainike;

/**
 *
 * @author Antti
 */
public class PelinLoppu extends Moodi {

    public PelinLoppu(Renderoija renderoija) {
        super(renderoija);
        lisaa(new SiirtymaPainike("Yritä uudestaan", Peli.class, PeliRenderoija.class, 400, 500, 100, 25, new PainikeRenderoija(), this).setVari(new Vari(0, 1, 0)));
        lisaa(new SiirtymaPainike("Valikko", Valikko.class, ValikkoRenderoija.class, 400, 450, 100, 25, new PainikeRenderoija(), this).setVari(new Vari(1, 0.5, 0)));
        lisaa(new LopetusPainike("Lopeta", 400, 400, 100, 25, new PainikeRenderoija(), this).setVari(new Vari(0, 0, 0)));
    }

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
}
