package aopkarja;

import aopkarja.kasittely.UI.UIKasittelija;
import aopkarja.kasittely.UI.renderoijat.ValikkoRenderoija;
import aopkarja.hoitajat.VirheidenHoitaja;
import aopkarja.kasittelijat.fysiikka.FysiikanKasittelija;
import aopkarja.kasittelijat.HiiriSaie;
import aopkarja.kasittelijat.NappaimistoSaie;
import aopkarja.kasittelijat.SisaantulonKasittelija;
import aopkarja.kasittelijat.TapahtumienKasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.komponentit.Valikko;
import java.util.List;

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
    private UIKasittelija uiKasittelija;

    private Luolastolentely() {
    }

    private void initialisoi() {
        kasittelijat = new KasittelynHoitaja();

        //Tapahtumien käsittelijä
        TapahtumienKasittelija tapahtumanKasittelija = new TapahtumienKasittelija();
        kasittelijat.lisaa(tapahtumanKasittelija);

        //UI käsittelijä
        uiKasittelija = new UIKasittelija(new Valikko(new ValikkoRenderoija(), null));
        tapahtumanKasittelija.lisaaKasittelija(uiKasittelija);
        kasittelijat.lisaa(uiKasittelija);

        //Hiiren käsittelijä
        kasittelijat.lisaa(new SisaantulonKasittelija(new HiiriSaie(tapahtumanKasittelija)));

        //Näppäimistön käsittelijä
        kasittelijat.lisaa(new SisaantulonKasittelija(new NappaimistoSaie(tapahtumanKasittelija)));

        //Initialisoi käsittelijät
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
    
    public static <T> List<T> getKasittelijat(Class<T> c){
        return getInstanssi().kasittelijat.getKasittelijat(c);
    }

    public <T> void teeKasittelijoille(Class<T> luokka, Kasittely<T> kasittely) {
        kasittelijat.kasittele(luokka, kasittely);
    }
    
}
