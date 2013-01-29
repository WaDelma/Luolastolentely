package aopkarja;

import aopkarja.kasittelijat.TapahtumienKasittelija;
import aopkarja.kasittelijat.HiirenKasittelija;
import aopkarja.UI.UIKasittelija;
import aopkarja.UI.renderoijat.ValikkoRenderoija;
import aopkarja.hoitajat.VirheidenHoitaja;
import aopkarja.kasitttely.KasittelyTyyppi;
import aopkarja.kasitttely.KasittelynHoitaja;
import aopkarja.komponentit.Valikko;

/**
 *
 * @author aopkarja
 */
public class Luolastolentely {

    private static Luolastolentely instanssi;
    private boolean kaynnissa;
    private KasittelynHoitaja kasittelijat;

    /**
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
        UIKasittelija uikasittelija = new UIKasittelija(new Valikko(new ValikkoRenderoija(), null));
        tapahtumanKasittelija.lisaaKasittelija(uikasittelija);
        kasittelijat.lisaa(uikasittelija);
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

    public void lopetaPeli() {
        kaynnissa = false;
    }

    public static Luolastolentely getInstanssi() {
        return instanssi;
    }

    public boolean kaynnissa() {
        return kaynnissa;
    }
}
