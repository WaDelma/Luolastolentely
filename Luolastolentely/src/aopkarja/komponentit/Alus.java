package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.Luolastolentely;
import aopkarja.Moodi;
import aopkarja.kasittelijat.PeliTilanKasittelija;
import aopkarja.kasittelijat.fysiikka.FysiikanKasittelija;
import aopkarja.kasittelijat.fysiikka.FyysinenKappale;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.kasittely.UI.Vari;
import aopkarja.kasittely.tapahtumat.EnergianLisaysTapahtuma;
import aopkarja.kasittely.tapahtumat.Painallus;

/**
 *
 * @author aopkarja
 */
public class Alus extends Komponentti {

    private final FyysinenKappale fyysinenKappale;
    private double energia = 50;
    private int kuolemaAjastin = 200;
    private boolean kuollut;

    public Alus(double x, double y, Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        fyysinenKappale = new FyysinenKappale(getAlue());
        Luolastolentely.getInstanssi().teeKasittelijoille(FysiikanKasittelija.class, new Kasittely<FysiikanKasittelija>() {
            @Override
            public void tee(FysiikanKasittelija kasittelija) {
                kasittelija.lisaa(fyysinenKappale);
            }
        });
        final Alus instanssi = this;
        ((Moodi) getOmistaja()).teeKasittelijoille(PeliTilanKasittelija.class, new Kasittely<PeliTilanKasittelija>() {
            @Override
            public void tee(PeliTilanKasittelija kasittelija) {
                kasittelija.lisaa(instanssi);
            }
        });

        fyysinenKappale.setPaino(1.0);
        int leveys = 10;
        int korkeus = 10;
        getAlue().lisaa(new Koordinaatti(x, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y + korkeus));

        getAlue().lisaa(new Koordinaatti(x, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y + korkeus));
        getAlue().lisaa(new Koordinaatti(x, y + korkeus));
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        if (!aktiivinen()) {
            return;
        }
        Object[] tieto = tapahtuma.getTieto();
        if (tapahtuma instanceof EnergianLisaysTapahtuma) {
            energia += (double) tapahtuma.getTieto()[0];
        }
        if (tapahtuma instanceof Painallus) {
            if (tieto[0].equals(Boolean.TRUE)) {
                if (energia >= 0) {
                    switch ((int) tieto[1]) {
                        case 'w':
                            fyysinenKappale.lisaaVoimanLahde(new Koordinaatti(0, 1));
                            energia -= 0.5;
                            break;
                        case 'a':
                            fyysinenKappale.lisaaVoimanLahde(new Koordinaatti(-1, 0));
                            energia -= 0.5;
                            break;
                        case 's':
                            fyysinenKappale.lisaaVoimanLahde(new Koordinaatti(0, -1));
                            energia -= 0.5;
                            break;
                        case 'd':
                            fyysinenKappale.lisaaVoimanLahde(new Koordinaatti(1, 0));
                            energia -= 0.5;
                            break;
                        default:
                    }
                }
            }
        }
    }

    public double getEnergia() {
        return energia;
    }

    @Override
    protected void aja() {
        if (energia <= 0) {
            kuolemaAjastin--;
            if (kuolemaAjastin <= 0) {
                kuole();
            }
        }
    }

    public void kuole() {
        kuollut = true;
    }

    @Override
    public boolean aktiivinen() {
        return !kuollut;
    }

    @Override
    public Vari getVari() {
        return new Vari(0.5, 1, 1);
    }
}
