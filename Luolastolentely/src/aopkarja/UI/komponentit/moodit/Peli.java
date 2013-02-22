package aopkarja.UI.komponentit.moodit;

import aopkarja.Luolastolentely;
import aopkarja.UI.Komponentti;
import aopkarja.UI.Moodi;
import aopkarja.UI.Renderoija;
import aopkarja.UI.Vari;
import aopkarja.UI.komponentit.Alus;
import aopkarja.UI.komponentit.EnergiaPallo;
import aopkarja.UI.komponentit.Kivi;
import aopkarja.UI.renderoijat.PelaajaRenderoija;
import aopkarja.fysiikka.FysiikanKasittelija;
import aopkarja.fysiikka.LeikkaustenHoitaja;
import aopkarja.fysiikka.asiat.Alue;
import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.hoitajat.SatunnaisuudenHoitaja;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.kasittelijat.PeliTilanKasittelija;
import java.util.ArrayList;
import java.util.Random;

/**
 * Peli moodi
 *
 * @author aopkarja
 */
public class Peli extends Moodi {

    private int vaikeusTaso;
    private int reunus = 50;
    private ArrayList<Alue> kivet;

    /**
     *
     * @param vaikeusTaso
     */
    public Peli(Integer vaikeusTaso) {
        super(new Renderoija());
        ínit(vaikeusTaso);
        lisaaAlusSatunnaiseenKohtaan(new Alus(new PelaajaRenderoija(), this));
    }

    public Peli(Peli peli) {
        super(new Renderoija());
        ínit(peli.getVaikeustaso() + 1);
        for (Komponentti komponentti : peli.getKomponentit()) {
            if(komponentti instanceof Alus){
                lisaaAlusSatunnaiseenKohtaan(new Alus((Alus)komponentti, this));
            }
        }
    }

    private void ínit(Integer vaikeusTaso) {
        this.vaikeusTaso = vaikeusTaso > 7 ? 6 : vaikeusTaso;
        lisaa(new PeliTilanKasittelija(this));
        lisaa(new FysiikanKasittelija(Luolastolentely.getInstanssi().getTapahtumienKasittelija()));
        Random satunnainen = new Random();

        for (int i = 0; i < 10 + 2 * vaikeusTaso; i++) {
            Kivi kivi = new Kivi(new Renderoija(), this);
            kivi.getAlue().skaalaa(SatunnaisuudenHoitaja.getInt(3, 5 + vaikeusTaso));
            kivi.getAlue().kierra(satunnainen.nextDouble(), 0, 1);
            kivi.getAlue().siirra(new Koordinaatti(SatunnaisuudenHoitaja.getInt(reunus, 800 - reunus), SatunnaisuudenHoitaja.getInt(reunus, 600 - reunus)));
            lisaa(kivi);
        }

        kivet = new ArrayList<>();
        for (Komponentti k : getKomponentit()) {
            kivet.add(k.getAlue());
        }

        for (int i = 0; i < 5 + 5 * vaikeusTaso; i++) {
            EnergiaPallo pallo = new EnergiaPallo(10 - vaikeusTaso, new Renderoija(), this);
            do {
                pallo.getAlue().muuta(new Koordinaatti(SatunnaisuudenHoitaja.getInt(reunus, 800 - reunus), SatunnaisuudenHoitaja.getInt(reunus, 600 - reunus)));
            } while (LeikkaustenHoitaja.leikkaako(pallo.getAlue(), kivet) != null);
            lisaa(pallo);
        }
    }

    private void lisaaAlusSatunnaiseenKohtaan(Alus alus) {
        do {
            alus.getAlue().muuta(new Koordinaatti(SatunnaisuudenHoitaja.getInt(reunus, 800 - reunus), SatunnaisuudenHoitaja.getInt(reunus, 600 - reunus)));
        } while (LeikkaustenHoitaja.leikkaako(alus.getAlue(), kivet) != null);
        lisaa(alus);
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        for (Komponentti komponentti : this.getLapset()) {
            komponentti.tapahtuu(tapahtuma);
        }
    }

    public int getVaikeustaso() {
        return vaikeusTaso;
    }

    @Override
    public Vari getVari() {
        return new Vari(0.34, 0.35, 0.33);
    }

    @Override
    public String toString() {
        return super.toString() + " " + vaikeusTaso;
    }
}
