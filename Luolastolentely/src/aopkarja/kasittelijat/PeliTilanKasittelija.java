package aopkarja.kasittelijat;

import aopkarja.Komponentti;
import aopkarja.Luolastolentely;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.kasittely.UI.UIKasittelija;
import aopkarja.kasittely.UI.renderoijat.PelinLoppuRenderoija;
import aopkarja.komponentit.moodit.PelinLoppu;

/**
 *
 * @author Antti
 */
public class PeliTilanKasittelija {

    private final KasittelynHoitaja<Komponentti> kasittelija;
    
    private boolean jatkuu;

    public PeliTilanKasittelija() {
        this.kasittelija = new KasittelynHoitaja<>();
    }

    @Kasittelija(KasittelyTyyppi.AJA)
    public void tee() {
        jatkuu = false;
        kasittelija.kasittele(new Kasittely<Komponentti>() {
            @Override
            public void tee(Komponentti kohde) {
                if (kohde.aktiivinen()) {
                    jatkuu = true;
                }
            }
        });
        if(!jatkuu){
            final PelinLoppu pelinLoppu = new PelinLoppu(new PelinLoppuRenderoija());
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
}
