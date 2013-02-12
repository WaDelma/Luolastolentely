package aopkarja.kasittelijat;

import aopkarja.hoitajat.VirheidenHoitaja;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.KasittelyTyyppi;

/**
 * Kasitelee sisaatuloja.
 * 
 * @author aopkarja
 */
public class SisaantulonKasittelija {

    private final SisaantuloSaie saie;

    public SisaantulonKasittelija(SisaantuloSaie saie) {
        this.saie = saie;
    }

    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    public void kaynnista() {
        saie.start();
    }

    @Kasittelija(KasittelyTyyppi.LOPETA)
    private void lopeta() {
        try {
            saie.join();
        } catch (InterruptedException ex) {
            VirheidenHoitaja.ilmoita(ex);
        }
    }
}
