package aopkarja.kasittelijat;

import aopkarja.Koordinaatti;
import aopkarja.kasittelijat.fysiikka.FyysinenKappale;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;

/**
 *
 * @author aopkarja
 */
public class FysiikanKasittelija {
    
    private final KasittelynHoitaja<FyysinenKappale> kasittelija;
    private Koordinaatti painovoima;
    
    public FysiikanKasittelija() {
        this.kasittelija = new KasittelynHoitaja<>();
        painovoima = new Koordinaatti(0, -0.1);
    }
    
    @Kasittelija(KasittelyTyyppi.AJA)
    public void aja() {
        kasittelija.kasittele(new Kasittely<FyysinenKappale>() {
            @Override
            public void tee(FyysinenKappale kohde) {
                kohde.getAlue().siirra(painovoima);
            }
        });
    }
    
    public void lisaa(FyysinenKappale kappale) {
        kasittelija.lisaa(kappale);
    }
}
