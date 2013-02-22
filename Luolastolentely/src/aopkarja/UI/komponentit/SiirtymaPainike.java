package aopkarja.UI.komponentit;

import aopkarja.UI.Komponentti;
import aopkarja.UI.Siirtyma;
import aopkarja.kasittely.Tapahtuma;

/**
 * Painike, joka vaihtaa moodia siirtymällä.
 *
 * @author aopkarja
 */
public class SiirtymaPainike extends Painike {
    private final Siirtyma siirtyma;

    public SiirtymaPainike(String teksti, Siirtyma siirtyma, int x, int y, int leveys, int korkeus, Komponentti omistaja) {
        super(teksti, x, y, leveys, korkeus, omistaja);
        this.siirtyma = siirtyma;
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        super.tapahtuu(tapahtuma);
        if (isPainettu()) {
            siirtyma.siirry(getOmistaja());
        }
    }
}
