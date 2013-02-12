package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Luolastolentely;
import aopkarja.UI.Renderoija;
import aopkarja.UI.UIKasittelija;
import aopkarja.UI.renderoijat.PeliRenderoija;
import aopkarja.kasittelijat.FysiikanKasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.Tapahtuma;

/**
 *
 * @author aopkarja
 */
public class AloitusPainike extends Painike {

    public AloitusPainike(String teksti, int x, int y, int leveys, int korkeus, Renderoija renderoija, Komponentti omistaja) {
        super(teksti, x, y, leveys, korkeus, renderoija, omistaja);
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        super.tapahtuu(tapahtuma);
        if (isPainettu()) {
            final Peli peli = new Peli(new PeliRenderoija(), null);
            Luolastolentely.getInstanssi().teeKasittelijoille(UIKasittelija.class, new Kasittely<UIKasittelija>() {
                @Override
                public void tee(UIKasittelija kasittelija) {
                    kasittelija.setMoodi(peli);
                }
            });
            //Luolastolentely.getInstanssi().setMoodi(new Peli(new PeliRenderoija(), null));
        }
    }
}
