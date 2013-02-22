package aopkarja.UI.komponentit;

import aopkarja.UI.Komponentti;
import aopkarja.UI.Renderoija;
import aopkarja.UI.Vari;
import aopkarja.fysiikka.FysiikanKasittelija;
import aopkarja.fysiikka.FyysinenKappale;
import aopkarja.fysiikka.asiat.Ympyra;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.kasittelijat.PeliTilanKasittelija;
import aopkarja.kasittely.kasittelijat.TapahtumienKasittelija;
import aopkarja.kasittely.tapahtumat.EnergianLisaysTapahtuma;
import aopkarja.kasittely.tapahtumat.TormaysTapahtuma;

/**
 * Energiaa antava kerättävä
 *
 * @author Antti
 */
public class EnergiaPallo extends Komponentti {

    private final FyysinenKappale fyysinenKappale;
    private final int arvo;
    private boolean deaktivoitu;

    public EnergiaPallo(int arvo, Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        this.arvo = arvo;
        getAlue().lisaa(new Ympyra());
        getAlue().skaalaa(arvo);

        fyysinenKappale = new FyysinenKappale(this);
        fyysinenKappale.setPaino(0);
        getOmistaja().teeKasittelijoille(FysiikanKasittelija.class, new Kasittely<FysiikanKasittelija>() {
            @Override
            public void tee(FysiikanKasittelija kasittelija) {
                kasittelija.lisaa(fyysinenKappale);
            }
        });

        final EnergiaPallo instanssi = this;
        getOmistaja().teeKasittelijoille(PeliTilanKasittelija.class, new Kasittely<PeliTilanKasittelija>() {
            @Override
            public void tee(PeliTilanKasittelija kasittelija) {
                kasittelija.lisaa(instanssi);
            }
        });
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {

        if (tapahtuma instanceof TormaysTapahtuma) {
            FyysinenKappale kappale1 = (FyysinenKappale) tapahtuma.getTieto()[0];
            FyysinenKappale kappale2 = (FyysinenKappale) tapahtuma.getTieto()[1];
            if (kappale1 == fyysinenKappale || kappale2 == fyysinenKappale) {
                if (kappale1.getKomponentti() instanceof Alus || kappale2.getKomponentti() instanceof Alus) {
                    getOmistaja().teeKasittelijoille(TapahtumienKasittelija.class, new Kasittely<TapahtumienKasittelija>() {
                        @Override
                        public void tee(TapahtumienKasittelija kasittelija) {
                            kasittelija.lisaa(new EnergianLisaysTapahtuma(arvo));
                        }
                    });
                }
                this.getOmistaja().poista(this);
                fyysinenKappale.poista();
                setAktiivisuus(false);
            }
        }
    }

    @Override
    public Vari getVari() {
        return new Vari(0, 0, 1);
    }
}
