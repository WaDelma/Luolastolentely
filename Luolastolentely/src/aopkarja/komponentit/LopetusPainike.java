package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Luolastolentely;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;

/**
 *
 * @author aopkarja
 */
public class LopetusPainike extends Painike {

    public LopetusPainike(String teksti, int x, int y, int leveys, int korkeus, Renderoija renderoija, Komponentti omistaja) {
        super(teksti, x, y, leveys, korkeus, renderoija, omistaja);
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        super.tapahtuu(tapahtuma);
        if (isPainettu()) {
            Luolastolentely.getInstanssi().lopetaPeli();
        }
    }
}
