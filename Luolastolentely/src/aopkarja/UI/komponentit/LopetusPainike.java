package aopkarja.UI.komponentit;

import aopkarja.Luolastolentely;
import aopkarja.UI.Komponentti;
import aopkarja.kasittely.Tapahtuma;

/**
 * Painike, joka lopettaa pelin
 * 
 * @author aopkarja
 */
public class LopetusPainike extends Painike {

    public LopetusPainike(String teksti, int x, int y, int leveys, int korkeus, Komponentti omistaja) {
        super(teksti, x, y, leveys, korkeus, omistaja);
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        super.tapahtuu(tapahtuma);
        if (isPainettu()) {
            Luolastolentely.getInstanssi().lopetaPeli();
        }
    }
}
