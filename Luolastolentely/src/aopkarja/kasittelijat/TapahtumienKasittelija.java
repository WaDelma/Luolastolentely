package aopkarja.kasittelijat;

import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.kasittely.Tapahtuma;
import java.util.ArrayList;
import java.util.List;

/**
 * Käsittelee {@link Tapahtuma} olioit, joita sille annetaan
 * 
 * @author aopkarja
 */
public class TapahtumienKasittelija {

    private final List<Tapahtuma> tapahtumat;
    private Tapahtuma[] bufferi;
    private KasittelynHoitaja tapahtumienHoitaja;

    /**
     *
     */
    public TapahtumienKasittelija() {
        tapahtumat = new ArrayList<>();
        tapahtumienHoitaja = new KasittelynHoitaja();
    }
    
    /**
     *
     * @param kasittelija
     */
    public void lisaaKasittelija(Object kasittelija){
        tapahtumienHoitaja.lisaa(kasittelija);
    }

    /**
     *
     * @param tapahtuma
     */
    public synchronized void lisaa(Tapahtuma tapahtuma) {
        tapahtumat.add(tapahtuma);
    }

    /**
     *
     */
    @Kasittelija(KasittelyTyyppi.AJA)
    public void aja() {
        synchronized (tapahtumat) {
            bufferi = new Tapahtuma[tapahtumat.size()];
            bufferi = tapahtumat.toArray(bufferi);
            tapahtumat.clear();
        }
        for (Tapahtuma tapahtuma : bufferi) {
            tapahtumienHoitaja.kasittele(KasittelyTyyppi.TAPAHTUMA, tapahtuma);
        }
    }
}
