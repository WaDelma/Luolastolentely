package aopkarja;

import aopkarja.UI.Renderoija;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.Tapahtuma;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Komponentti, jonka voi sijoittaa moodiin (kts. {@link UI.UIKasittelija}) tai
 * toiseen komponenttiin.
 *
 * @author aopkarja
 */
public abstract class Komponentti {

    private final List<Komponentti> komponentit;
    private final Komponentti omistaja;
    private final Renderoija renderoija;
    private final Alue alue;

    /**
     *
     * @param renderoija
     * @param omistaja
     */
    public Komponentti(Renderoija renderoija, Komponentti omistaja) {
        komponentit = new ArrayList<>();
        alue = new Alue();
        this.renderoija = renderoija;
        this.omistaja = omistaja;
    }

    /**
     *
     * @param tapahtuma
     */
    public abstract void tapahtuu(Tapahtuma tapahtuma);

    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    private void initialisoi() {
        renderoija.initialisoi(this);
        for (Komponentti komponentti : komponentit) {
            komponentti.initialisoi();
        }
    }

    @Kasittelija(KasittelyTyyppi.RENDEROI)
    private void renderoi() {
        renderoija.renderoi(this);
        for (Komponentti komponentti : komponentit) {
            komponentti.renderoi();
        }
    }
    
    @Kasittelija(KasittelyTyyppi.AJA)
    protected void aja() {
        
    }

    /**
     *
     * @return Komponentin {@link Alue}
     */
    public Alue getAlue() {
        return alue;
    }

    /**
     *
     * @return Komponentin lapset
     */
    public List<Komponentti> getLapset() {
        return Collections.unmodifiableList(komponentit);
    }

    /**
     *
     * @param komponentti
     */
    public void lisaa(Komponentti komponentti) {
        komponentit.add(komponentti);
    }

    /**
     *
     * @return Koponentin vanhempi
     */
    public Komponentti getOmistaja() {
        return omistaja;
    }
}
