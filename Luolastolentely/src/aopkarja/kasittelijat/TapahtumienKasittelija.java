package aopkarja.kasittelijat;

import aopkarja.kasitttely.Kasittelija;
import aopkarja.kasitttely.KasittelyTyyppi;
import aopkarja.kasitttely.KasittelynHoitaja;
import aopkarja.kasitttely.Tapahtuma;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aopkarja
 */
public class TapahtumienKasittelija {

    private final List<Tapahtuma> tapahtumat;
    private Tapahtuma[] bufferi;
    private KasittelynHoitaja tapahtumienHoitaja;

    public TapahtumienKasittelija() {
        tapahtumat = new ArrayList<>();
        tapahtumienHoitaja = new KasittelynHoitaja();
    }
    
    public void lisaaKasittelija(Object kasittelija){
        tapahtumienHoitaja.lisaa(kasittelija);
    }

    public synchronized void lisaa(Tapahtuma tapahtuma) {
        tapahtumat.add(tapahtuma);
    }

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
