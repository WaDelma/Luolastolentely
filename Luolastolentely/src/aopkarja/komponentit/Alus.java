package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.Luolastolentely;
import aopkarja.UI.Renderoija;
import aopkarja.kasittelijat.FysiikanKasittelija;
import aopkarja.kasittelijat.fysiikka.FyysinenKappale;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.tapahtumat.Painallus;

/**
 *
 * @author aopkarja
 */
public class Alus extends Komponentti {

    private final FyysinenKappale fyysinenKappale;

    public Alus(Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        fyysinenKappale = new FyysinenKappale(getAlue());
        Luolastolentely.getInstanssi().teeKasittelijoille(FysiikanKasittelija.class, new Kasittely<FysiikanKasittelija>() {
            @Override
            public void tee(FysiikanKasittelija kasittelija) {
                kasittelija.lisaa(fyysinenKappale);
            }
        });
//        for(FysiikanKasittelija kasittelija: Luolastolentely.getKasittelijat(FysiikanKasittelija.class)){
//            kasittelija.lisaa(fyysinenKappale);
//        }
        fyysinenKappale.setPaino(1.0);
        int x = 10;
        int y = 10;
        int leveys = 10;
        int korkeus = 10;
        getAlue().lisaa(new Koordinaatti(x, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y + korkeus));
        getAlue().lisaa(new Koordinaatti(x, y + korkeus));
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        Object[] tieto = tapahtuma.getTieto();
        if (tapahtuma instanceof Painallus) {
            if (tieto[0].equals(Boolean.TRUE)) {
                switch ((int) tieto[1]) {
                    case 'w':
                        fyysinenKappale.lisaaPysyvaVoima(0, 1);
                        //this.getAlue().siirra(0, 1);
                        break;
                    case 'a':
                        fyysinenKappale.lisaaPysyvaVoima(-1, 0);
                        //this.getAlue().siirra(-1, 0);
                        break;
                    case 's':
                        fyysinenKappale.lisaaPysyvaVoima(0, -1);
                        //this.getAlue().siirra(0, -1);
                        break;
                    case 'd':
                        fyysinenKappale.lisaaPysyvaVoima(1, 0);
                        //this.getAlue().siirra(1, 0);
                        break;
                    default:
                }
            } else {
            }
        }
        System.out.println(getAlue());
    }

    @Override
    public void aja() {
    }
}
