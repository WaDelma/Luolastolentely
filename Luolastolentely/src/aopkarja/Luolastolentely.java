package aopkarja;

import aopkarja.UI.UIKasittelija;
import aopkarja.UI.komponentit.moodit.Valikko;
import aopkarja.hoitajat.KirjanpidonHoitaja;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.kasittely.kasittelijat.HiiriSaie;
import aopkarja.kasittely.kasittelijat.NappaimistoSaie;
import aopkarja.kasittely.kasittelijat.SisaantulonKasittelija;
import aopkarja.kasittely.kasittelijat.TapahtumienKasittelija;

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
     * @param args Komentorivi argumentit
     */
    public static void main(String[] args) {
        try {
            instanssi = new Luolastolentely();
            instanssi.initialisoi();
            instanssi.aja();
        } catch (RuntimeException e) {
            KirjanpidonHoitaja.ilmoita(e);
        } catch (Exception e) {
            KirjanpidonHoitaja.ilmoita(e);
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
        uiKasittelija = new UIKasittelija(new Valikko());
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

    /**
     * @return Palauttaa tapahtumien käsittelijän
     */
    public TapahtumienKasittelija getTapahtumienKasittelija() {
        return tapahtumanKasittelija;
    }

    /**
     * Tee käsittely kaikille tietyn tyyppisille käsittelijöille
     *
     * @param luokka tyyppi
     * @param kasittely käsittely, joka tehdään
     */
    public <T> void teeKasittelijoille(Class<T> luokka, Kasittely<T> kasittely) {
        kasittelijat.kasittele(luokka, kasittely);
    }
}
