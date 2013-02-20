package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Luolastolentely;
import aopkarja.alueet.Ympyra;
import aopkarja.kasittelijat.TapahtumienKasittelija;
import aopkarja.kasittelijat.fysiikka.FysiikanKasittelija;
import aopkarja.kasittelijat.fysiikka.FyysinenKappale;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.kasittely.UI.Vari;
import aopkarja.kasittely.tapahtumat.EnergianLisaysTapahtuma;
import aopkarja.kasittely.tapahtumat.TormaysTapahtuma;

/**
 *
 * @author Antti
 */
public class EnergiaPallo extends Komponentti {

    private final FyysinenKappale fyysinenKappale;

    public EnergiaPallo(Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        getAlue().lisaa(new Ympyra());

        fyysinenKappale = new FyysinenKappale(getAlue());
        fyysinenKappale.setPaino(0);
        Luolastolentely.getInstanssi().teeKasittelijoille(FysiikanKasittelija.class, new Kasittely<FysiikanKasittelija>() {
            @Override
            public void tee(FysiikanKasittelija kasittelija) {
                kasittelija.lisaa(fyysinenKappale);
            }
        });
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        if (tapahtuma instanceof TormaysTapahtuma && (tapahtuma.getTieto()[0] == fyysinenKappale || tapahtuma.getTieto()[1] == fyysinenKappale)) {
            Luolastolentely.getInstanssi().teeKasittelijoille(TapahtumienKasittelija.class, new Kasittely<TapahtumienKasittelija>() {
                @Override
                public void tee(TapahtumienKasittelija kasittelija) {
                    kasittelija.lisaa(new EnergianLisaysTapahtuma(10));
                }
            });
            this.getOmistaja().poista(this);
            fyysinenKappale.poista();
        }
    }

    @Override
    public Vari getVari() {
        return new Vari(0, 0, 1);
    }
}
