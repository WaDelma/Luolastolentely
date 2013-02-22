package aopkarja.kasittely.kasittelijat;

import aopkarja.Luolastolentely;
import aopkarja.UI.Komponentti;
import aopkarja.UI.UIKasittelija;
import aopkarja.UI.komponentit.Alus;
import aopkarja.UI.komponentit.moodit.Peli;
import aopkarja.UI.komponentit.moodit.PelinLoppu;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;

/**
 *
 * @author Antti
 */
public class PeliTilanKasittelija {

    private final KasittelynHoitaja<Komponentti> kasittelija;
    private boolean eiHavio;
    private boolean eiVoitto;
    private Peli peli;

    public PeliTilanKasittelija(Peli peli) {
        this.peli = peli;
        this.kasittelija = new KasittelynHoitaja<>();
    }

    @Kasittelija(KasittelyTyyppi.AJA)
    public void tee() {
        eiHavio = false;
        eiVoitto = false;
        kasittelija.kasittele(new Kasittely<Komponentti>() {
            @Override
            public void tee(Komponentti kohde) {
                if (kohde.aktiivinen()) {
                    if (kohde instanceof Alus) {
                        eiHavio = true;
                    } else {
                        eiVoitto = true;
                    }
                }
            }
        });
        if (!eiVoitto) {
            peli = new Peli(peli);
            Luolastolentely.getInstanssi().teeKasittelijoille(UIKasittelija.class, new Kasittely<UIKasittelija>() {
                @Override
                public void tee(UIKasittelija kasittelija) {
                    kasittelija.setMoodi(peli);
                }
            });
        } else if (!eiHavio) {
            final PelinLoppu pelinLoppu = new PelinLoppu();
            Luolastolentely.getInstanssi().teeKasittelijoille(UIKasittelija.class, new Kasittely<UIKasittelija>() {
                @Override
                public void tee(UIKasittelija kasittelija) {
                    kasittelija.setMoodi(pelinLoppu);
                }
            });
        }
    }

    public void lisaa(Komponentti komponentti) {
        kasittelija.lisaa(komponentti);
    }

    public void skippaa() {
        final Peli seuraavaLeveli = new Peli(peli.getVaikeustaso() + 1);
        Luolastolentely.getInstanssi().teeKasittelijoille(UIKasittelija.class, new Kasittely<UIKasittelija>() {
            @Override
            public void tee(UIKasittelija kasittelija) {
                kasittelija.setMoodi(seuraavaLeveli);
            }
        });
    }
}
