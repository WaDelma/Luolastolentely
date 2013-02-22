package aopkarja.fysiikka;

import aopkarja.fysiikka.asiat.Alue;
import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.kasittely.kasittelijat.TapahtumienKasittelija;
import aopkarja.kasittely.tapahtumat.TormaysTapahtuma;
import java.util.ArrayList;
import java.util.List;

/**
 * Käsittelee fyysisten kappaleiden fysiikkaa
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
    private void aja() {
        kasittelija.kasittele(new Kasittely<FyysinenKappale>() {
            @Override
            public void tee(FyysinenKappale kohde) {
                //kohde.getRotaatioVoima().kerro(1 - kitka);
                kohde.getVoima().skaalaa(1 - kitka);
                Alue alue = kohde.getKomponentti().getAlue();
                alue.siirra(kohde.getVoima());
                //kohde.getAlue().kierra(kohde.getRotaatioVoima().get(0), 0, 1);

                if (!kohde.isPainoton()) {
                    kohde.getVoima().siirra(painovoima);
                }
                Alue leikkaavaAlue = LeikkaustenHoitaja.leikkaako(alue, getAlueet(getStaattisilleEiStaattiset(kasittelija.getKasittelijat(), kohde.isPainoton())));
                if (leikkaavaAlue != null) {
                    Koordinaatti suunta = alue.getKeskipiste();
                    suunta.siirra(leikkaavaAlue.getKeskipiste().vastaKohta());
                    suunta.yksikoi();
                    suunta.skaalaa(kohde.getVoima().etaisyysOrigosta());
                    alue.siirra(suunta);
                    FyysinenKappale toinenKappale = alueenKappale(leikkaavaAlue, kasittelija.getKasittelijat());
                    kohde.getVoima().skaalaa(1 - toinenKappale.getKitka());
                    tapahtumienKasittelija.lisaa(new TormaysTapahtuma(kohde, toinenKappale, suunta));
                }
            }
        });
    }

    /**
     * Filtteröinti tehokkuuden edistämiseksi
     *
     * @param lista Lista fyysistä kappaleista
     * @param staattinen Onko kohde staattinen
     * @return Jos staattinen, niin lista ei staattisista fyysistä kappaleista
     */
    public List<FyysinenKappale> getStaattisilleEiStaattiset(List<FyysinenKappale> lista, boolean staattinen) {
        if (staattinen) {
            lista = new ArrayList<>();
            for (FyysinenKappale fyysinenKappale : lista) {
                if (!fyysinenKappale.isPainoton()) {
                    lista.add(fyysinenKappale);
                }
            }
        }
        return lista;
    }

    /**
     * Palauttaa aluetta vastaavan fyysisen kappaleen.
     *
     * @param alue Alue
     * @param lista Lista fyysistä kappaleista
     * @return fyysinen kappale tai null jos ei ole
     */
    public FyysinenKappale alueenKappale(Alue alue, List<FyysinenKappale> lista) {
        for (FyysinenKappale kappale : lista) {
            if (kappale.getKomponentti().getAlue() == alue) {
                return kappale;
            }
        }
        return null;
    }

    /**
     * Muuttaa fyysisten kappaleiden listan alueiden listaksi.
     *
     * @param lista Lista fyysistä kappaleista
     * @return lista kappaleita vastaavista alueista
     */
    public List<Alue> getAlueet(List<FyysinenKappale> lista) {
        List<Alue> result = new ArrayList<>();
        for (FyysinenKappale kappale : lista) {
            if (kappale.isPoistettu()) {
                continue;
            }
            result.add(kappale.getKomponentti().getAlue());
        }
        return result;
    }

    /**
     * Lisää fyysisen kappaleen, jonka fysiikan käsittelija käsittelee.
     *
     * @param kappale fyysinen kappale
     */
    public void lisaa(FyysinenKappale kappale) {
        kasittelija.lisaa(kappale);
    }
}
