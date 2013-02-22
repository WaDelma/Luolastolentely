package aopkarja.UI;

import aopkarja.Luolastolentely;
import aopkarja.fysiikka.LeikkaustenHoitaja;
import aopkarja.fysiikka.asiat.Alue;
import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.tapahtumat.HiiriTapahtuma;
import java.util.Locale;

/**
 * Moodi, jossa ruutu on (kts. {@link aopkarja.UI.UIKasittelija})/{@link Komponentti}
 * @author Antti
 */
public abstract class Moodi extends Komponentti {

    private final KasittelynHoitaja kasittelijat;

    public Moodi(Renderoija renderoija) {
        super(renderoija, null);
        kasittelijat = new KasittelynHoitaja();
    }

    public void lisaa(Object kasittelija) {
        kasittelijat.lisaa(kasittelija);
    }

    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    private void initialisoi() {
        kasittelijat.kasittele(KasittelyTyyppi.KAYNNISTA);
    }

    @Kasittelija(KasittelyTyyppi.AJA)
    private void ajaminen() {
        kasittelijat.kasittele(KasittelyTyyppi.AJA);
    }

    @Override
    public String toString() {
        String name = this.getClass().getName();
        if (name == null) {
            return super.toString();
        }
        String[] split = name.split("\\.");
        return split[split.length - 1].toUpperCase(Locale.ROOT);
    }

    @Override
    public <T> void teeKasittelijoille(Class<T> luokka, Kasittely<T> kasittely) {
        kasittelijat.kasittele(luokka, kasittely);
        Luolastolentely.getInstanssi().teeKasittelijoille(luokka, kasittely);
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        if (tapahtuma.getTyyppi() instanceof HiiriTapahtuma) {
            Alue hiiri = new Alue((Koordinaatti) tapahtuma.getTyyppi().getTieto()[0]);
            for (Komponentti komponentti : this.getLapset()) {
                if (LeikkaustenHoitaja.leikkaako(komponentti.getAlue(), hiiri)) {
                    komponentti.tapahtuu(tapahtuma);
                }
            }
        }
    }
}
