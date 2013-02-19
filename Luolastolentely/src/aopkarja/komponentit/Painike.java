package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;
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
        Object[] tieto = tapahtuma.getTieto();
        if (tapahtuma instanceof Painallus) {
            if (tieto[0].equals(Boolean.TRUE)) {
                if (tieto[1].equals(0)) {
                    painettu = true;
                }
            } else {
                if (tieto[1].equals(0)) {
                    painettu = false;
                }
            }
        }
    }
}
