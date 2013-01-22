package aopkarja;

import aopkarja.Koordinaatti;
import aopkarja.UI.Renderoija;
import aopkarja.kasitttely.Kasittelija;
import aopkarja.kasitttely.KasittelyTyyppi;
import aopkarja.tapahtuma.Tapahtuma;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aopkarja
 */
public abstract class Komponentti {

    private final List<Komponentti> komponentit;
    private final Komponentti omistaja;
    private final Renderoija renderoija;
    private final Alue alue;

    public Komponentti(Renderoija renderoija, Komponentti omistaja) {
        komponentit = new ArrayList<>();
        alue = new Alue();
        this.renderoija = renderoija;
        this.omistaja = omistaja;
    }

    public abstract void tapahtuu(Tapahtuma tapahtuma);

    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    public final void initialisoi() {
        renderoija.initialisoi(this);
        for (Komponentti komponentti : komponentit) {
            komponentti.initialisoi();
        }
    }
        
    @Kasittelija(KasittelyTyyppi.AJA)
    public final void renderoi() {
        renderoija.renderoi(this);
        for (Komponentti komponentti : komponentit) {
            komponentti.renderoi();
        }
    }

    public final Alue getAlue() {
        return alue;
    }

    public final List<Komponentti> getLapset() {
        return komponentit;
    }

    public final void lisaa(Komponentti komponentti) {
        komponentit.add(komponentti);
    }

    public final Komponentti getOmistaja() {
        return omistaja;
    }
}
