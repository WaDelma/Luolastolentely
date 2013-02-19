package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.Luolastolentely;
import aopkarja.kasittelijat.fysiikka.FysiikanKasittelija;
import aopkarja.kasittelijat.fysiikka.FyysinenKappale;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;

/**
 *
 * @author Antti
 */
public class Kivi extends Komponentti {

    private final FyysinenKappale fyysinenKappale;

    public Kivi(Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);

        fyysinenKappale = new FyysinenKappale(getAlue());
        fyysinenKappale.setPaino(0);
        fyysinenKappale.setKitka(0.3);
        Luolastolentely.getInstanssi().teeKasittelijoille(FysiikanKasittelija.class, new Kasittely<FysiikanKasittelija>() {
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
}
