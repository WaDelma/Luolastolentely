package aopkarja.UI.komponentit;

import aopkarja.UI.Komponentti;
import aopkarja.UI.Renderoija;
import aopkarja.UI.Vari;
import aopkarja.fysiikka.FysiikanKasittelija;
import aopkarja.fysiikka.FyysinenKappale;
import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.Tapahtuma;

/**
 * Kivi.
 *
 * @author Antti
 */
public class Kivi extends Komponentti {

    private final FyysinenKappale fyysinenKappale;

    public Kivi(Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);

        fyysinenKappale = new FyysinenKappale(this);
        fyysinenKappale.setPaino(0);
        fyysinenKappale.setKitka(0.7);
        getOmistaja().teeKasittelijoille(FysiikanKasittelija.class, new Kasittely<FysiikanKasittelija>() {
            @Override
            public void tee(FysiikanKasittelija kasittelija) {
                kasittelija.lisaa(fyysinenKappale);
            }
        });

        getAlue().lisaa(new Koordinaatti(2, 3));
        getAlue().lisaa(new Koordinaatti(5, 10));
        getAlue().lisaa(new Koordinaatti(3, 16));

        getAlue().lisaa(new Koordinaatti(2, 3));
        getAlue().lisaa(new Koordinaatti(5, 10));
        getAlue().lisaa(new Koordinaatti(14, 14));

        getAlue().lisaa(new Koordinaatti(2, 3));
        getAlue().lisaa(new Koordinaatti(14, 14));
        getAlue().lisaa(new Koordinaatti(13, 5));

        getAlue().lisaa(new Koordinaatti(2, 3));
        getAlue().lisaa(new Koordinaatti(7, 2));
        getAlue().lisaa(new Koordinaatti(7, 5));

    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
    }

    @Override
    public void aja() {
    }

    @Override
    public Vari getVari() {
        return new Vari(0.5, 0.4, 0.5);
    }
}
