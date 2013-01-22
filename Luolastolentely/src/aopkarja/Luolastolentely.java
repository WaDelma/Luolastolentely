package aopkarja;

import aopkarja.hoitajat.VirheidenHoitaja;
import aopkarja.UI.UIKasittelija;
import aopkarja.UI.renderoijat.ValikkoRenderoija;
import aopkarja.kasitttely.KasittelyTyyppi;
import aopkarja.kasitttely.KasittelynHoitaja;
import aopkarja.komponentit.Valikko;
import aopkarja.tapahtuma.TapahtumienKasittelija;

/**
 *
 * @author aopkarja
 */
public class Luolastolentely {

    private static Luolastolentely instanssi;
    private boolean kaynnissa;
    private KasittelynHoitaja kasittelijat;
    private TapahtumienKasittelija tapahtumienKasittelija;

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
        tapahtumienKasittelija = new TapahtumienKasittelija();
        kasittelijat = new KasittelynHoitaja();
        kasittelijat.lisaa(new UIKasittelija(new Valikko(new ValikkoRenderoija(), null)));
        kasittelijat.lisaa(new HiirenKasittelija(tapahtumienKasittelija));
        kasittelijat.kasittele(KasittelyTyyppi.KAYNNISTA);
        kaynnissa = true;
    }

    private void aja() {
        do {
            tapahtumienKasittelija.kasittele(kasittelijat.getKasittelijat());
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

    boolean kaynnissa() {
        return kaynnissa;
    }
}
