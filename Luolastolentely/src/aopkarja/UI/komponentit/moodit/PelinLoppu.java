package aopkarja.UI.komponentit.moodit;

import aopkarja.Luolastolentely;
import aopkarja.UI.Moodi;
import aopkarja.UI.Renderoija;
import aopkarja.UI.Siirtyma;
import aopkarja.UI.Vari;
import aopkarja.UI.komponentit.LopetusPainike;
import aopkarja.UI.komponentit.SiirtymaPainike;
import aopkarja.fysiikka.FysiikanKasittelija;

/**
 * Pelin loppumis moodi
 * 
 * @author Antti
 */
public class PelinLoppu extends Moodi {

    public PelinLoppu() {
        super(new Renderoija());
        lisaa(new FysiikanKasittelija(Luolastolentely.getInstanssi().getTapahtumienKasittelija()));
        lisaa(new SiirtymaPainike("Yritä uudestaan", new Siirtyma(Peli.class, 1), 400, 500, 100, 25, this).setVari(new Vari(0, 1, 0), new Vari(1, 0, 1)));
        lisaa(new SiirtymaPainike("Valikko", new Siirtyma(Valikko.class), 400, 450, 100, 25, this).setVari(new Vari(1, 0.5, 0), new Vari(0, 0.5, 1)));
        lisaa(new LopetusPainike("Lopeta", 400, 400, 100, 25, this).setVari(new Vari(0, 0, 0), new Vari(1, 1, 1)));
    }

    @Override
    public Vari getVari() {
        return new Vari(0.2, 0.2, 0.4);
    }
}
