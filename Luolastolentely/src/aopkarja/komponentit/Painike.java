package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.UI.Renderoija;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.tapahtumat.Painallus;

/**
 * {@link Komponentti}, jota voi painaa
 * 
 * @author aopkarja
 */
public class Painike extends Komponentti {

    /**
     * 
     */
    public boolean painettu;

    public Painike(Renderoija<Painike> renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
    }

    public Painike(int x, int y, int leveys, int korkeus, Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        getAlue().lisaa(new Koordinaatti(x, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y + korkeus));
        getAlue().lisaa(new Koordinaatti(x, y + korkeus));
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        if (tapahtuma instanceof Painallus) {
            if (tapahtuma.getTieto()[0].equals(0)) {
                painettu = true;
            }
        }
    }
}
