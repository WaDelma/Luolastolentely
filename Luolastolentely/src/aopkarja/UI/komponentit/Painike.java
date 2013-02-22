package aopkarja.UI.komponentit;

import aopkarja.UI.Komponentti;
import aopkarja.UI.Renderoija;
import aopkarja.UI.Vari;
import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.tapahtumat.Painallus;

/**
 * {@link Komponentti}, jota voi painaa
 *
 * @author aopkarja
 */
public class Painike extends Komponentti {

    private Vari vari;
    private boolean painettu;
    private String teksti;
    private Vari painettuVari;
    private Vari eiPainettuVari;

    public Painike(String teksti, int x, int y, int leveys, int korkeus, Komponentti omistaja) {
        super(new Renderoija(), omistaja);
        this.teksti = teksti;
        getAlue().lisaa(new Koordinaatti(x, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y + korkeus));
        
        getAlue().lisaa(new Koordinaatti(x, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y + korkeus));
        getAlue().lisaa(new Koordinaatti(x, y + korkeus));
        vari = new Vari(1, 1, 1);
    }

    public String getTeksti() {
        return teksti;
    }

    public boolean isPainettu() {
        return painettu;
    }
    
    @Override
    public Vari getVari() {
        return vari;
    }

    public Painike setVari(Vari eiPainettu, Vari painettu) {
        vari = eiPainettu;
        painettuVari = painettu;
        eiPainettuVari = eiPainettu;
        return this;
    }
    
    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        Object[] tieto = tapahtuma.getTieto();
        if (tapahtuma instanceof Painallus) {
            if (tieto[0].equals(Boolean.TRUE)) {
                if (tieto[1].equals(0)) {
                    painettu = true;
                    vari = painettuVari;
                }
            } else {
                if (tieto[1].equals(0)) {
                    painettu = false;
                    vari = eiPainettuVari;
                }
            }
        }
    }
}
