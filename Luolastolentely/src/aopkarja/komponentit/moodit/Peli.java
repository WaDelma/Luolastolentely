package aopkarja.komponentit.moodit;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.Moodi;
import aopkarja.kasittelijat.PeliTilanKasittelija;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.kasittely.UI.renderoijat.EnergiaPalloRenderoija;
import aopkarja.kasittely.UI.renderoijat.KiviRenderoija;
import aopkarja.kasittely.UI.renderoijat.PelaajaRenderoija;
import aopkarja.komponentit.Alus;
import aopkarja.komponentit.EnergiaPallo;
import aopkarja.komponentit.Kivi;
import java.util.Random;

/**
 *
 * @author aopkarja
 */
public class Peli extends Moodi {

    /**
     *
     * @param renderoija
     * @param omistaja
     */
    public Peli(Renderoija renderoija) {
        super(renderoija);
        lisaa(new PeliTilanKasittelija());
        lisaa(new Alus(100, 100, new PelaajaRenderoija(), this));
        Random satunnainen = new Random();
        for (int i = 0; i < 10; i++) {
            EnergiaPallo pallo = new EnergiaPallo(new EnergiaPalloRenderoija(), this);
            pallo.getAlue().siirra(new Koordinaatti(20 + satunnainen.nextInt(500), 20 + satunnainen.nextInt(500)));
            pallo.getAlue().skaalaa(5);
            //System.out.println(pallo);
            lisaa(pallo);
        }
        for (int i = 0; i < 10; i++) {
            Kivi kivi = new Kivi(new KiviRenderoija(), this);
            kivi.getAlue().skaalaa(5 + satunnainen.nextInt(4));
            kivi.getAlue().siirra(new Koordinaatti(20 + satunnainen.nextInt(500), 20 + satunnainen.nextInt(500)));
            lisaa(kivi);
        }
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        for (Komponentti komponentti : this.getLapset()) {
            komponentti.tapahtuu(tapahtuma);
        }
    }
}
