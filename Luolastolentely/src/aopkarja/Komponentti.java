package aopkarja;

import aopkarja.kasittelijat.PeliTilanKasittelija;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.kasittely.UI.Vari;
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
    private final List<Komponentti> poistettavat;

    /**
     *
     * @param renderoija
     * @param omistaja
     */
    public Komponentti(Renderoija renderoija, Komponentti omistaja) {
        komponentit = new ArrayList<>();
        poistettavat = new ArrayList<>();
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
        renderoija.renderoiAlue(this);
        renderoija.renderoi(this);
        for (Komponentti komponentti : komponentit) {
            komponentti.renderoi();
        }
    }

    @Kasittelija(KasittelyTyyppi.AJA)
    private void ajaminen() {
        for (Komponentti komponentti : poistettavat) {
            komponentit.remove(komponentti);
        }
        poistettavat.clear();
        if (aktiivinen()) {
            aja();
        }
        for (Komponentti komponentti : komponentit) {
            komponentti.ajaminen();
        }
    }

    protected void aja() {
    }

    public boolean aktiivinen() {
        return true;
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

    @Override
    public String toString() {
        StringBuilder rakentaja = new StringBuilder();
        rakentaja.append("Komponentti: ").append('\n');
        rakentaja.append("\tRenderoija: ").append(renderoija).append('\n');
        if (omistaja != null) {
            rakentaja.append("\tVanhempi: ").append(omistaja).append('\n');
        }
        if (!komponentit.isEmpty()) {
            rakentaja.append("\tLapset: ").append(komponentit).append('\n');
        }
        rakentaja.append('\t').append(alue).append('\n');
        return rakentaja.toString();
    }

    public void poista(Komponentti komponentti) {
        poistettavat.add(komponentti);
    }

    public Vari getVari() {
        return new Vari(1, 1, 1);
    }

    public <T> void teeKasittelijoille(Class<T> luokka, Kasittely<T> kasittely) {
        getOmistaja().teeKasittelijoille(luokka, kasittely);
    }
}
