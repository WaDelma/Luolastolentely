package aopkarja;

import aopkarja.UI.UIKasittelija;
import aopkarja.UI.renderoijat.ValikkoRenderoija;
import aopkarja.hoitajat.VirheidenHoitaja;
import aopkarja.kasittelijat.HiirenKasittelija;
import aopkarja.kasittelijat.TapahtumienKasittelija;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.komponentit.Valikko;

/**
 * Peli itse.
 *
 * @author aopkarja
 */
public class Luolastolentely {

    private static volatile Luolastolentely instanssi;
    private boolean kaynnissa;
    private KasittelynHoitaja kasittelijat;

    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            instanssi = new Luolastolentely();
            instanssi.initialisoi();
            instanssi.aja();
        } catch (RuntimeException e) {
            VirheidenHoitaja.ilmoita(e);
        } catch (Exception e) {
            VirheidenHoitaja.ilmoita(e);
        } finally {
            instanssi.sulje();
        }
    }

    private void initialisoi() {
        kasittelijat = new KasittelynHoitaja();
        TapahtumienKasittelija tapahtumanKasittelija = new TapahtumienKasittelija();
        kasittelijat.lisaa(tapahtumanKasittelija);
        UIKasittelija uiKasittelija = new UIKasittelija(new Valikko(new ValikkoRenderoija(), null));
        tapahtumanKasittelija.lisaaKasittelija(uiKasittelija);
        kasittelijat.lisaa(uiKasittelija);
        kasittelijat.lisaa(new HiirenKasittelija(tapahtumanKasittelija));
        kasittelijat.kasittele(KasittelyTyyppi.KAYNNISTA);
        kaynnissa = true;
    }

    private void aja() {
        do {
            kasittelijat.kasittele(KasittelyTyyppi.AJA);
        } while (kaynnissa);
    }

    private void sulje() {
        kasittelijat.kasittele(KasittelyTyyppi.LOPETA);
    }

    /**
     * Lopettaa pelin.
     */
    public void lopetaPeli() {
        kaynnissa = false;
    }

    /**
     * @return Pelin olio.
     */
    public static Luolastolentely getInstanssi() {
        return instanssi;
    }

    /**
     * @return Peli on käynnissä.
     */
    public boolean kaynnissa() {
        return kaynnissa;
    }
}
