package aopkarja.UI;

import aopkarja.Kasittelija;
import aopkarja.KasittelyTyyppi;

/**
 *
 * @author aopkarja
 */
public class UIKasittelija {

    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    public void initialisoi() {
        
    }
    
    @Kasittelija(KasittelyTyyppi.AJA)
    public void aja() {
        
    }

    @Kasittelija(KasittelyTyyppi.LOPETA)
    public void sulje() {
        
    }
}
