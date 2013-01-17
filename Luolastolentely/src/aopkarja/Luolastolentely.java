package aopkarja;

import aopkarja.UI.UIKasittelija;
import aopkarja.UI.Valikko;
import aopkarja.kasitttely.KasittelyTyyppi;
import aopkarja.kasitttely.KasittelynHoitaja;

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
        } catch (RuntimeException e){
            VirheidenHoitaja.ilmoita(e);
        } catch (Exception e) {
            VirheidenHoitaja.ilmoita(e);
        } finally {
            instanssi.sulje();
        }
    }

    private void initialisoi() {
        kasittelijat = new KasittelynHoitaja();
        kasittelijat.lisaa(new UIKasittelija(new Valikko()));
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
}
