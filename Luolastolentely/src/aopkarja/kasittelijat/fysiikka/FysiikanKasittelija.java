package aopkarja.kasittelijat.fysiikka;

import aopkarja.Alue;
import aopkarja.Koordinaatti;
import aopkarja.hoitajat.LeikkaustenHoitaja;
import aopkarja.kasittelijat.TapahtumienKasittelija;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.kasittely.tapahtumat.TormaysTapahtuma;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aopkarja
 */
public class FysiikanKasittelija {

    private final KasittelynHoitaja<FyysinenKappale> kasittelija;
    private Koordinaatti painovoima;
    private double kitka;
    private final TapahtumienKasittelija tapahtumienKasittelija;

    public FysiikanKasittelija(TapahtumienKasittelija kasittelija) {
        this.tapahtumienKasittelija = kasittelija;
        this.kasittelija = new KasittelynHoitaja<>();
        painovoima = new Koordinaatti(0, -0.05);
        kitka = 0.01;
    }

    @Kasittelija(KasittelyTyyppi.AJA)
    public void aja() {
        kasittelija.kasittele(new Kasittely<FyysinenKappale>() {
            @Override
            public void tee(FyysinenKappale kohde) {
                kohde.getVoima().kerro(1 - kitka);

                kohde.getAlue().siirra(kohde.getVoima());
                if (!kohde.isPainoton()) {
                    kohde.getVoima().siirra(painovoima);
                }
                Alue alue = LeikkaustenHoitaja.leikkaako(kohde.getAlue(), getAlueet(kasittelija.getKasittelijat()));
                if (alue != null) {
                    Koordinaatti suunta = kohde.getAlue().getKeskipiste();
                    suunta.siirra(alue.getKeskipiste().vastaKohta());
                    suunta.yksikoi();
                    suunta.kerro(kohde.getVoima().etaisyys(new double[kohde.getVoima().getAste()]));
                    kohde.getAlue().siirra(suunta);
                    kohde.getVoima().kerro(1 - alueenKappale(alue, kasittelija.getKasittelijat()).getKitka());
                    tapahtumienKasittelija.lisaa(new TormaysTapahtuma(kohde, suunta));
                }
            }
        });
    }

    public FyysinenKappale alueenKappale(Alue alue, List<FyysinenKappale> kasittelijat) {
        for (FyysinenKappale kappale : kasittelijat) {
            if (kappale.getAlue() == alue) {
                return kappale;
            }
        }
        return null;
    }

    public List<Alue> getAlueet(List<FyysinenKappale> lista) {
        List<Alue> result = new ArrayList<>();
        for (FyysinenKappale kappale : lista) {
            if(kappale.isPoistettu()){
                continue;
            }
            result.add(kappale.getAlue());
        }
        return result;
    }

    public void lisaa(FyysinenKappale kappale) {
        kasittelija.lisaa(kappale);
    }
}
