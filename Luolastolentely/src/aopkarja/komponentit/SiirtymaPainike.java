package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.Luolastolentely;
import aopkarja.Moodi;
import aopkarja.hoitajat.LokiHoitaja;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.kasittely.UI.UIKasittelija;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author aopkarja
 */
public class SiirtymaPainike extends Painike {

    private Constructor<? extends Moodi> konstruktori;
    private Renderoija moodiRenderoija;

    public SiirtymaPainike(String teksti, Class<? extends Moodi> moodiLuokka, Class<? extends Renderoija> renderoijaLuokka, int x, int y, int leveys, int korkeus, Renderoija renderoija, Komponentti omistaja) {
        super(teksti, x, y, leveys, korkeus, renderoija, omistaja);
        try {
            konstruktori = moodiLuokka.getDeclaredConstructor(Renderoija.class);
            this.moodiRenderoija = renderoijaLuokka.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException ex) {
            LokiHoitaja.ilmoita(ex);
        }
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        super.tapahtuu(tapahtuma);
        if (isPainettu()) {
            getOmistaja().teeKasittelijoille(UIKasittelija.class, new Kasittely<UIKasittelija>() {
                @Override
                public void tee(UIKasittelija kasittelija) {
                    try {
                        kasittelija.setMoodi(konstruktori.newInstance(moodiRenderoija));
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        LokiHoitaja.ilmoita(ex);
                    }
                }
            });
        }
    }
}
