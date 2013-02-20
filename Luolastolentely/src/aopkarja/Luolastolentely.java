package aopkarja;

import aopkarja.hoitajat.LokiHoitaja;
import aopkarja.kasittelijat.HiiriSaie;
import aopkarja.kasittelijat.NappaimistoSaie;
import aopkarja.kasittelijat.SisaantulonKasittelija;
import aopkarja.kasittelijat.TapahtumienKasittelija;
import aopkarja.kasittelijat.fysiikka.FysiikanKasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.kasittely.UI.UIKasittelija;
import aopkarja.kasittely.UI.renderoijat.ValikkoRenderoija;
import aopkarja.komponentit.moodit.Valikko;
import java.util.List;

/**
 * Peli itse.
 *
 * @author aopkarja
 */
public class Luolastolentely {

    private static volatile Luolastolentely instanssi;
    private static volatile boolean kaynnissa;
    private KasittelynHoitaja kasittelijat;
    private UIKasittelija uiKasittelija;
    private TapahtumienKasittelija tapahtumanKasittelija;

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
            LokiHoitaja.ilmoita(e);
        } catch (Exception e) {
            LokiHoitaja.ilmoita(e);
        } finally {
            instanssi.sulje();
        }
    }

    private Luolastolentely() {
    }

    private void initialisoi() {
        kaynnissa = true;

        kasittelijat = new KasittelynHoitaja();

        //Tapahtumien käsittelijä
        tapahtumanKasittelija = new TapahtumienKasittelija();
        kasittelijat.lisaa(tapahtumanKasittelija);

        //UI käsittelijä
        uiKasittelija = new UIKasittelija(new Valikko(new ValikkoRenderoija()));
        tapahtumanKasittelija.lisaaKasittelija(uiKasittelija);
        kasittelijat.lisaa(uiKasittelija);

        //Hiiren käsittelijä
        kasittelijat.lisaa(new SisaantulonKasittelija(new HiiriSaie(tapahtumanKasittelija)));

        //Näppäimistön käsittelijä
        kasittelijat.lisaa(new SisaantulonKasittelija(new NappaimistoSaie(tapahtumanKasittelija)));

        //Fysiikan käsittelijä


        //Initialisoi käsittelijät
        kasittelijat.kasittele(KasittelyTyyppi.KAYNNISTA);
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
    
    public TapahtumienKasittelija getTapahtumienKasittelija() {
        return tapahtumanKasittelija;
    }

    public <T> List<T> getKasittelijat(Class<T> c) {
        return kasittelijat.getKasittelijat(c);
    }

    public <T> void teeKasittelijoille(Class<T> luokka, Kasittely<T> kasittely) {
        kasittelijat.kasittele(luokka, kasittely);
    }
}
