package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.UI.Renderoija;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.tapahtumat.PainalluksenPaasto;
import aopkarja.kasittely.tapahtumat.Painallus;

/**
 * {@link Komponentti}, jota voi painaa
 *
 * @author aopkarja
 */
public class Painike extends Komponentti {

    private boolean painettu;
    private String teksti;

    public Painike(Renderoija<Painike> renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
    }

    public Painike(String teksti, int x, int y, int leveys, int korkeus, Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        this.teksti = teksti;
        getAlue().lisaa(new Koordinaatti(x, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y + korkeus));
        getAlue().lisaa(new Koordinaatti(x, y + korkeus));
    }

    public String getTeksti() {
        return teksti;
    }

    public boolean isPainettu() {
        return painettu;
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        if (tapahtuma instanceof Painallus) {
            if (tapahtuma.getTieto()[0].equals(0)) {
                painettu = true;
            }
        } else if (tapahtuma instanceof PainalluksenPaasto) {
            if (tapahtuma.getTieto()[0].equals(0)) {
                painettu = false;
            }
        }
    }
}
