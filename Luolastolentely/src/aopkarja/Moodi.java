package aopkarja;

import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.kasittely.UI.Renderoija;

/**
 *
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
        return split[split.length - 1].toUpperCase();
    }

    @Override
    public <T> void teeKasittelijoille(Class<T> luokka, Kasittely<T> kasittely) {
        kasittelijat.kasittele(luokka, kasittely);
        Luolastolentely.getInstanssi().teeKasittelijoille(luokka, kasittely);
    }
}
